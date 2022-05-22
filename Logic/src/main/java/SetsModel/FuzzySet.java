package SetsModel;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class FuzzySet implements SetsOperations<FuzzySet> {

    @Getter private ClassicSet classicSet;
    @Getter private Function function;
    @Getter private List<Double> membershipValuesList;
    @Getter private boolean isComplement;

    public FuzzySet(ClassicSet classicSet, Function function, boolean isComplement) {
        this.classicSet = classicSet;
        this.function = function;
        this.isComplement = isComplement;

        membershipValuesList = getMembershipValues();
    }

    public FuzzySet(Function function) {
        this.function = function;
        isComplement = false;
    }

    public FuzzySet(Function function, boolean isComplement) {
        this.function = function;
        this.isComplement = isComplement;
    }

    @Override
    public FuzzySet sum(FuzzySet s2) {


        Function f = new Function(FuzzyOperationsType.Sum, function.getMembershipFunction(),
                s2.getFunction());

        boolean c = isComplement || s2.isComplement();

        return new FuzzySet(classicSet, f, c);
    }

    @Override
    public FuzzySet product(FuzzySet s2) {

        Function f = new Function(FuzzyOperationsType.Product, function.getMembershipFunction(),
                s2.getFunction());

        boolean c = isComplement || s2.isComplement();

        return new FuzzySet(classicSet, f, c);
    }

    private List<Double> getMembershipValues() {

        List<Double> values = new ArrayList<>();

        if (classicSet.isComplement() || classicSet.getElements() == null)
            return values;

        for (var element:classicSet.getElements()
             ) {

            if (!isComplement)
                values.add(function.calculateMembership(element));
            else
                values.add(1 - function.calculateMembership(element));
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

        return (double) getSupport().getElements().size() / (double) getClassicSet().getElements().size();
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

    public boolean isNormal () {
        //TODO:
        return true;
    }

    public boolean isConvex() {
        //TODO:
        return true;
    }

}
