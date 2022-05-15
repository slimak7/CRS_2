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
    @Getter private MembershipFunction function;
    @Getter private List<Double> membershipValues;

    public FuzzySet(ClassicSet classicSet, MembershipFunction function) {
        this.classicSet = classicSet;
        this.function = function;

        membershipValues = getMembershipValues();
    }

    @Override
    public FuzzySet sum(FuzzySet s2) {
        return null;
    }

    @Override
    public FuzzySet product(FuzzySet s2) {
        return null;
    }

    private List<Double> getMembershipValues() {

        List<Double> values = new ArrayList<>();

        for (var element:classicSet.getElements()
             ) {

            values.add(function.calculateMembership(element));
        }

        return values;
    }
}
