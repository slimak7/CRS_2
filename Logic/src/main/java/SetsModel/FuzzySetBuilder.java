package SetsModel;

public final class FuzzySetBuilder {
    private ClassicSet classicSet;
    private FuzzyOperationsType operationType;
    private FuzzySet set;
    private MembershipFunction function;
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

    public FuzzySetBuilder withOperationType(FuzzyOperationsType operationType) {
        this.operationType = operationType;
        return this;
    }

    public FuzzySetBuilder withSet(FuzzySet set) {
        this.set = set;
        return this;
    }

    public FuzzySetBuilder withFunction(MembershipFunction function) {
        this.function = function;
        return this;
    }

    public FuzzySetBuilder withIsComplement(boolean isComplement) {
        this.isComplement = isComplement;
        return this;
    }

    public FuzzySet build() {
        FuzzySet fuzzySet = new FuzzySet(function, isComplement);
        fuzzySet.setClassicSet(classicSet);
        fuzzySet.setOperationType(operationType);
        fuzzySet.setSet(set);
        return fuzzySet;
    }
}
