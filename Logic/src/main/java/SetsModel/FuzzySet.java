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
}
