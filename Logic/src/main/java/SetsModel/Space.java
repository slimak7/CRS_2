package SetsModel;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;

public class Space {

    public enum SpaceType{
        discrete, dense
    }

    @Getter SpaceType spaceType;
    @Getter Pair<Double, Double> range;

    public Space(SpaceType spaceType, Pair<Double, Double> range) {
        this.spaceType = spaceType;
        this.range = range;
    }
}
