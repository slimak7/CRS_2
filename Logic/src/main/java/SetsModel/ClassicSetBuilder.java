package SetsModel;

import java.util.List;

public final class ClassicSetBuilder {
    private List<Double> elements;
    private Space space;
    private boolean complement;

    private ClassicSetBuilder() {
    }

    public static ClassicSetBuilder aClassicSet() {
        return new ClassicSetBuilder();
    }

    public ClassicSetBuilder withElements(List<Double> elements) {
        this.elements = elements;
        return this;
    }

    public ClassicSetBuilder withSpace(Space space) {
        this.space = space;
        return this;
    }

    public ClassicSetBuilder withComplement(boolean complement) {
        this.complement = complement;
        return this;
    }

    public ClassicSet build() {
        return new ClassicSet(elements, space, complement);
    }
}
