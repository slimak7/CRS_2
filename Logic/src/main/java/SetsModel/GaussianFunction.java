package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class GaussianFunction implements MembershipFunction {

    @Getter Double m, s, l, r;

    @Override
    public Double calculateMembership(Double x) {

        if (x >= l && x <= r)
            return Math.exp(-0.5 * Math.pow((x - m)/s, 2));
        else
            return 0.0;
    }

    @Override
    public Double getSupportRange() {
        return r-l;
    }

    @Override
    public Double getCardinalityRange() {
        return Math.sqrt(2 * Math.PI) * s;
    }
}
