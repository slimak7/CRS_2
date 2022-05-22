package SetsModel;

import java.util.List;

public final class SummaryBuilder {
    private LinguisticVariable qualifier;
    private List<LinguisticQuantifier> quantifier;
    private List<LinguisticVariable> summarizers;
    private ClassicSet classicSetP1;
    private ClassicSet classicSetP2;
    private ClassicSet classicSetW;
    private Integer multiForm;
    private SummaryTypes summaryType;
    private QualityMeasures qualityMeasures;
    private Connector connector;

    private SummaryBuilder() {
    }

    public static SummaryBuilder aSummary() {
        return new SummaryBuilder();
    }

    public SummaryBuilder withQualifier(LinguisticVariable qualifier) {
        this.qualifier = qualifier;
        return this;
    }

    public SummaryBuilder withQuantifier(List<LinguisticQuantifier> quantifier) {
        this.quantifier = quantifier;
        return this;
    }

    public SummaryBuilder withSummarizers(List<LinguisticVariable> summarizers) {
        this.summarizers = summarizers;
        return this;
    }

    public SummaryBuilder withClassicSetP1(ClassicSet classicSetP1) {
        this.classicSetP1 = classicSetP1;
        return this;
    }

    public SummaryBuilder withClassicSetP2(ClassicSet classicSetP2) {
        this.classicSetP2 = classicSetP2;
        return this;
    }

    public SummaryBuilder withClassicSetW(ClassicSet classicSetW) {
        this.classicSetW = classicSetW;
        return this;
    }

    public SummaryBuilder withMultiForm(Integer multiForm) {
        this.multiForm = multiForm;
        return this;
    }

    public SummaryBuilder withSummaryType(SummaryTypes summaryType) {
        this.summaryType = summaryType;
        return this;
    }

    public SummaryBuilder withQualityMeasures(QualityMeasures qualityMeasures) {
        this.qualityMeasures = qualityMeasures;
        return this;
    }

    public SummaryBuilder withConnector(Connector connector) {
        this.connector = connector;
        return this;
    }

    public Summary build() {
        return new Summary(qualifier, quantifier, summarizers, classicSetP1, classicSetP2, classicSetW, multiForm, summaryType, qualityMeasures, connector);
    }
}
