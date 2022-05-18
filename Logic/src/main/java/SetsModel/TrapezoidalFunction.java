package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class TrapezoidalFunction implements MembershipFunction{

    @Getter private Double a, b, c, d;

    @Override
    public Double calculateMembership(Double x) {

        if (x <= a)
            return 0.0;
        else if (x > a && x < b)
            return (x - a)/(b - a);
        else if (x >= b && x <= c)
            return 1.0;
        else if (x > c && x < d)
            return (d - x)/(d - c);
        else
            return 0.0;
    }

    @Override
    public Double getSupport() {
        return b - a;
    }

    @Override
    public Double getCardinality() {
        return 0.5 * (b - a + (c - d));
    }


}
