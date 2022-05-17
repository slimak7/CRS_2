package SetsModel;

import java.util.List;

public final class SummaryBuilder {
    LinguisticVariable qualifier;
    LinguisticQuantifier quantifier;
    List<LinguisticVariable> summarizers;

    private SummaryBuilder() {
    }

    public static SummaryBuilder aSummary() {
        return new SummaryBuilder();
    }

    public SummaryBuilder withQualifier(LinguisticVariable qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    public SummaryBuilder withQuantifier(LinguisticQuantifier quantifier) {
        this.quantifier = quantifier;
        return this;
    }

    public SummaryBuilder withSummarizers(List<LinguisticVariable> summarizers) {
        this.summarizers = summarizers;
        return this;
    }

    public Summary build() {
        return new Summary(qualifier, quantifier, summarizers);
    }
}
