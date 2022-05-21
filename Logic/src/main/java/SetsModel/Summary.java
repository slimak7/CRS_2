package SetsModel;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class Summary {

    @Getter LinguisticVariable qualifier;
    @Getter List<LinguisticQuantifier> quantifier;
    @Getter List<LinguisticVariable> summarizers;
    @Getter private ClassicSet classicSet1;
    @Getter private ClassicSet classicSet2;
    @Getter Integer multiForm;
    @Getter SummaryTypes summaryType;
    private QualityMeasures qualityMeasures;

    public Summary(LinguisticVariable qualifier, List<LinguisticQuantifier> quantifier, List<LinguisticVariable> summarizers, ClassicSet classicSetP1, ClassicSet classicSetP2, ClassicSet classicSetW, Integer multiForm, SummaryTypes summaryType) {
        this.qualifier = qualifier;
        this.quantifier = quantifier;
        this.summarizers = summarizers;
        this.classicSet1 = classicSet1;
        this.classicSet2 = classicSet2;
        this.multiForm = multiForm;
        this.summaryType = summaryType;

        qualityMeasures = new QualityMeasures(summarizers, quantifier, qualifier, classicSetP1, classicSetP2, classicSetW, multiForm, summaryType);
    }

    public List<Double> getT_1() {

        return qualityMeasures.getT_1();
    }

    public List<String> getStringSummariesWithAverageT() {

        List<Double> measures = getT_1();

        List<String> summaries = new ArrayList<>();

        for (int i = 0; i < quantifier.size(); i++) {

            String text = "";

            if (summaryType.equals(SummaryTypes.single)) {

                text = quantifier.get(i).name;

                if (multiForm.equals(1)) {

                    text += " domów jest/ma ";

                    text += summarizers.get(0).getString();

                    for (int j = 1; j < summarizers.size(); i++) {

                        text += " i " + summarizers.get(j).getString();
                    }


                } else {

                    text += " domów, które są/mają" + qualifier.getString() + " jest ";

                    text += summarizers.get(0).getString();

                    for (int j = 1; j < summarizers.size(); i++) {

                        text += " i " + summarizers.get(j).getString();
                    }

                }


            } else {


            }

            text += "[" + measures.get(i) + "]";

            summaries.add(text);
        }

        return summaries;
    }

    public String getT_1Values() {

        List<Double> measures = getT_1();

        String s = "";

        for (int i = 0; i < quantifier.size(); i++) {

            s += quantifier.get(i).getName() + " " + measures.get(i) + "\n";
        }

        return s;
    }
}
