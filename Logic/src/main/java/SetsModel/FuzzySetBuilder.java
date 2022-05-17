package SetsModel;

public final class FuzzySetBuilder {
    private ClassicSet classicSet;
    private Function function;
    private boolean isComplement;

    private FuzzySetBuilder() {
    }

    public static FuzzySetBuilder aFuzzySet() {
        return new FuzzySetBuilder();
    }

    public FuzzySetBuilder withClassicSet(ClassicSet classicSet) {
        this.classicSet = classicSet;
        return this;
    }

    public FuzzySetBuilder withFunction(Function function) {
        this.function = function;
        return this;
    }

    public FuzzySetBuilder withIsComplement(boolean isComplement) {
        this.isComplement = isComplement;
        return this;
    }

    public FuzzySet build() {
        return new FuzzySet(classicSet, function, isComplement);
    }
}
