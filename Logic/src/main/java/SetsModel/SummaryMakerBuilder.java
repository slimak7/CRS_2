package SetsModel;

import java.util.List;

public final class SummaryMakerBuilder {
    private List<LinguisticVariable> qualifiers;
    private List<LinguisticQuantifier> quantifier;
    private List<LinguisticVariable> summarizers;
    private Integer multiForm;
    private SummaryTypes summaryType;
    private QualityMeasures qualityMeasures;
    private Connector connector;
    private List<String> houseTypes;

    private SummaryMakerBuilder() {
    }

    public static SummaryMakerBuilder aSummaryMaker() {
        return new SummaryMakerBuilder();
    }

    public SummaryMakerBuilder withQualifiers(List<LinguisticVariable> qualifiers) {
        this.qualifiers = qualifiers;
        return this;
    }

    public SummaryMakerBuilder withQuantifier(List<LinguisticQuantifier> quantifier) {
        this.quantifier = quantifier;
        return this;
    }

    public SummaryMakerBuilder withSummarizers(List<LinguisticVariable> summarizers) {
        this.summarizers = summarizers;
        return this;
    }

    public SummaryMakerBuilder withMultiForm(Integer multiForm) {
        this.multiForm = multiForm;
        return this;
    }

    public SummaryMakerBuilder withSummaryType(SummaryTypes summaryType) {
        this.summaryType = summaryType;
        return this;
    }

    public SummaryMakerBuilder withQualityMeasures(QualityMeasures qualityMeasures) {
        this.qualityMeasures = qualityMeasures;
        return this;
    }

    public SummaryMakerBuilder withConnector(Connector connector) {
        this.connector = connector;
        return this;
    }

    public SummaryMakerBuilder withHouseTypes(List<String> houseTypes) {
        this.houseTypes = houseTypes;
        return this;
    }

    public SummaryMaker build() {
        return new SummaryMaker(qualifiers, quantifier, summarizers, multiForm, summaryType, qualityMeasures, connector, houseTypes);
    }
}
