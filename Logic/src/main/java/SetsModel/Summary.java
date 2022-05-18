package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


public class Summary {

    @Getter LinguisticVariable qualifier;
    @Getter List<LinguisticQuantifier> quantifier;
    @Getter List<LinguisticVariable> summarizers;
    @Getter private ClassicSet classicSet1;
    @Getter private ClassicSet classicSet2;
    @Getter Integer multiForm;
    private QualityMeasures qualityMeasures;

    public Summary(LinguisticVariable qualifier, List<LinguisticQuantifier> quantifier, List<LinguisticVariable> summarizers, ClassicSet classicSet1, ClassicSet classicSet2, Integer multiForm) {
        this.qualifier = qualifier;
        this.quantifier = quantifier;
        this.summarizers = summarizers;
        this.classicSet1 = classicSet1;
        this.classicSet2 = classicSet2;
        this.multiForm = multiForm;

        qualityMeasures = new QualityMeasures(summarizers, quantifier, qualifier, classicSet1, classicSet2, multiForm);
    }

    public List<Double> getF_1 () {

        return qualityMeasures.getT_1();
    }
}
