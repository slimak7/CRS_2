package SetsModel;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.math3.analysis.UnivariateFunction;

import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
public class TriangularFunction implements MembershipFunction {

    @Getter private Double a, b, c;

    @Override
    public Double calculateMembership(Double x) {

        if (x <= a)
            return 0.0;
        else if (x > a && x < b)
            return (x - a) / (b - a);
        else if (x.equals(b))
            return 1.0;
        else if (x > b && x < c)
            return (c - x) / (c - b);
        else
            return 0.0;

    }

    @Override
    public Double getSupport() {
        return b - a;
    }

    @Override
    public Double getCardinality() {
        return 0.5 * (b - a);
    }


}
