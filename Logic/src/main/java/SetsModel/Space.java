package SetsModel;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;
import org.decimal4j.util.DoubleRounder;

@ToString
@AllArgsConstructor
public class Space {

    public enum SpaceType{
        discrete, dense
    };


    @Getter SpaceType spaceType;
    @Getter Range range;

    public boolean contains(Double value) {

        if (value >= getRange().getMin() && value <= getRange().getMax())
            return true;

        return false;
    }

}
