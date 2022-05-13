package SetsModel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ClassicSet {

    @Getter List<Double> elements;

    @Getter Space space;

    @Getter @Setter private boolean complement;

    public ClassicSet(List<Double> elements, Space space, boolean complement) {
        this.elements = elements;
        this.space = space;
        this.complement = complement;
    }
}
