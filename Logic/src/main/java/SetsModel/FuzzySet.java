package SetsModel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class FuzzySet implements SetsOperations<FuzzySet>, Cloneable {

    @Getter private ClassicSet classicSet;
    @Getter private MembershipFunction function;
    @Getter private List<Double> membershipValuesList;
    @Getter private boolean isComplement;
    @Getter @Setter private FuzzyOperationsType operationType;
    @Getter @Setter private FuzzySet set;

    public FuzzySet(ClassicSet classicSet, MembershipFunction function, boolean isComplement) {
        this.classicSet = classicSet;
        this.function = function;
        this.isComplement = isComplement;
        operationType = FuzzyOperationsType.None;

        membershipValuesList = getMembershipValues();
    }

    public FuzzySet(ClassicSet classicSet, MembershipFunction function, boolean isComplement, FuzzyOperationsType operationType, FuzzySet set) {
        this.classicSet = classicSet;
        this.function = function;
        this.isComplement = isComplement;
        this.operationType = operationType;
        this.set = set;

        membershipValuesList = getMembershipValues();
    }

    public FuzzySet(MembershipFunction function) {
        this.function = function;
        isComplement = false;
    }

    public FuzzySet(MembershipFunction function, boolean isComplement) {
        this.function = function;
        this.isComplement = isComplement;
        operationType = FuzzyOperationsType.None;
    }

    @Override
    public FuzzySet sum(FuzzySet s2) {

        boolean c = isComplement || s2.isComplement();



        return new FuzzySet(s2.getClassicSet(), s2.getFunction(), c, FuzzyOperationsType.Sum, this);
    }

    @Override
    public FuzzySet product(FuzzySet s2) {

        boolean c = isComplement || s2.isComplement();



        return new FuzzySet(s2.getClassicSet(), s2.getFunction(), c, FuzzyOperationsType.Product, this);


    }

    private List<Double> getMembershipValues() {

        List<Double> values = new ArrayList<>();

        if (classicSet.isComplement() || classicSet.getElements() == null)
            return values;

        for (var element:classicSet.getElements()
             ) {

            if (!isComplement)
                values.add(calculateMembership(element));
            else
                values.add(1 - calculateMembership(element));
        }

        return values;

    }

    public void setClassicSet(ClassicSet set) {

        classicSet = set;

        if (function != null)
            membershipValuesList = getMembershipValues();
    }

    public void addElement(Double element) {

        classicSet.addElement(element);

        membershipValuesList = getMembershipValues();
    }

    public boolean isEmpty() {

        if (classicSet == null)
            return true;

        if (classicSet.getElements().size() == 0)
            return true;

        Double v = membershipValuesList.stream().filter(x -> x != 0.0).findAny().orElse(0.0);
        if (v.equals(0.0)){
            return true;
        }

        return false;
    }

    public ClassicSet getSupport() {

        if (classicSet == null)
            return null;

        if (classicSet.getElements().isEmpty())
            return null;

        ClassicSet set = new ClassicSet(classicSet.getSpace());

        for (int i = 0; i < membershipValuesList.size(); i++) {

            if (membershipValuesList.get(i) > 0.0) {

                set.addElement(classicSet.getElements().get(i));
            }
        }


        return set;
    }

    public ClassicSet getAlphaCross(Double alpha) {

        if (classicSet == null)
            return null;

        if (classicSet.getElements().isEmpty())
            return null;

        ClassicSet set = new ClassicSet(classicSet.getSpace());

        for (int i = 0; i < membershipValuesList.size(); i++) {

            if (membershipValuesList.get(i) >= alpha) {

                set.addElement(classicSet.getElements().get(i));
            }
        }

        return set;
    }

    public Double getDegreeOfFuzziness() {
        Double spaceRange = getClassicSet().getSpace().range.getMax() - getClassicSet().getSpace().range.getMin();
        Double membershipFunctionRange = function.getSupportRange();
        return  membershipFunctionRange/ spaceRange;
    }

    public Double getCardinalValue() {

        if (classicSet == null)
            return null;

        if (classicSet.getElements().isEmpty())
            return null;

        Double value = 0.0;

        for (int i = 0; i < membershipValuesList.size(); i++) {

            if (membershipValuesList.get(i).equals(0.0)) {

                value++;
            }
        }

        return value;
    }

    public boolean contains(ClassicSet set) {

        for (var element:set.getElements()
             ) {

            if (function.calculateMembership(element) <= 0.0)
                return false;
        }
        return true;
    }

    public Double calculateMembership(Double x) {

        if (operationType.equals(FuzzyOperationsType.None)) {

            return function.calculateMembership(x);
        }
        else {

            switch (operationType){

                case Sum -> {

                    List<Double> options = new ArrayList<>();

                    options.add(function.calculateMembership(x));
                    options.add(set.calculateMembership(x));

                    return options.stream().max(Double::compareTo).orElse(0.0);

                }
                case Product -> {

                    List<Double> options = new ArrayList<>();

                    options.add(function.calculateMembership(x));
                    options.add(set.calculateMembership(x));

                    return options.stream().min(Double::compareTo).orElse(0.0);

                }
            }
        }

        return 0.0;
    }

    public Space getSpace() {
        return classicSet.getSpace();
    }

    public boolean isNormal () {
        //TODO:
        return true;
    }

    public boolean isConvex() {
        //TODO:
        return true;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
