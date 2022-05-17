package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class FuzzySet implements SetsOperations<FuzzySet> {

    @Getter private ClassicSet classicSet;
    @Getter private Function function;
    @Getter private List<Double> membershipValues;
    @Getter private boolean isComplement;

    public FuzzySet(ClassicSet classicSet, Function function, boolean isComplement) {
        this.classicSet = classicSet;
        this.function = function;
        this.isComplement = isComplement;

        membershipValues = getMembershipValues();
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

        ClassicSet set = classicSet.sum(s2.getClassicSet());

        Function f = new Function(FuzzyOperationsType.Sum, function.getMembershipFunction(),
                s2.getFunction());

        boolean c = isComplement || s2.isComplement();

        return new FuzzySet(set, f, c);
    }

    @Override
    public FuzzySet product(FuzzySet s2) {

        ClassicSet set = classicSet.product(s2.getClassicSet());

        Function f = new Function(FuzzyOperationsType.Product, function.getMembershipFunction(),
                s2.getFunction());

        boolean c = isComplement || s2.isComplement();

        return new FuzzySet(set, f, c);
    }

    private List<Double> getMembershipValues() {

        List<Double> values = new ArrayList<>();

        if (classicSet.isComplement())
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

    public void addElement(Double element) {

        classicSet.addElement(element);

        membershipValues = getMembershipValues();
    }

    public boolean isEmpty() {

        if (classicSet == null)
            return true;

        if (classicSet.getElements().size() == 0)
            return true;

        Double v = membershipValues.stream().filter(x -> x != 0.0).findAny().orElse(0.0);
        if (v.equals(0.0)){
            return true;
        }

        return false;
    }

    public ClassicSet getMedium() {

        if (classicSet == null)
            return null;

        if (classicSet.getElements().isEmpty())
            return null;

        ClassicSet set = new ClassicSet(classicSet.getSpace());

        for (int i = 0; i < membershipValues.size(); i++) {

            if (membershipValues.get(i) > 0.0) {

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

        for (int i = 0; i < membershipValues.size(); i++) {

            if (membershipValues.get(i) >= alpha) {

                set.addElement(classicSet.getElements().get(i));
            }
        }

        return set;
    }


}
