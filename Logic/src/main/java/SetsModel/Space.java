package SetsModel;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import org.decimal4j.util.DoubleRounder;

@AllArgsConstructor
public class Space {

    public enum SpaceType{
        discrete, dense
    };

    public class Range {

        public Double min, max;
    }

    @Getter SpaceType spaceType;
    @Getter Range range;

    public boolean contains(Double value) {

        if (getSpaceType().equals(SpaceType.dense)){

            if (value >= getRange().min && value <= getRange().max)
                return true;
        }
        else {

            Double d = DoubleRounder.round(value, 0);

            if (!value.equals(d)) {
                return false;
            }
            else {
                if (value >= getRange().min && value <= getRange().max)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

}
