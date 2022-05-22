package SetsModel;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


public class Summary {

    @Getter private LinguisticVariable qualifier;
    @Getter private List<LinguisticQuantifier> quantifier;
    @Getter private List<LinguisticVariable> summarizers;
    @Getter private ClassicSet classicSetP1;
    @Getter private ClassicSet classicSetP2;
    @Getter private ClassicSet classicSetW;
    @Getter private Integer multiForm;
    @Getter private SummaryTypes summaryType;
    @Getter private QualityMeasures qualityMeasures;
    @Getter private Connector connector;


    public Summary(LinguisticVariable qualifier, List<LinguisticQuantifier> quantifier, List<LinguisticVariable> summarizers, ClassicSet classicSetP1, ClassicSet classicSetP2, ClassicSet classicSetW, Integer multiForm, SummaryTypes summaryType, QualityMeasures qualityMeasures, Connector connector) {
        this.qualifier = qualifier;
        this.quantifier = quantifier;
        this.summarizers = summarizers;
        this.classicSetP1 = classicSetP1;
        this.classicSetP2 = classicSetP2;
        this.classicSetW = classicSetW;
        this.multiForm = multiForm;
        this.summaryType = summaryType;
        this.qualityMeasures = qualityMeasures;
        this.connector = connector;

        this.qualityMeasures = new QualityMeasures(summarizers, connector, quantifier, qualifier, classicSetP1, classicSetP2, classicSetW, multiForm, summaryType);
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


                    for (int j = 1; j < summarizers.size(); j++) {

                        text += " " + connector.toString() + " " + summarizers.get(j).getString();
                    }


                } else {

                    text += " domów, które są/mają " + qualifier.getString() + " jest/ma ";

                    text += summarizers.get(0).getString();

                    for (int j = 1; j < summarizers.size(); j++) {

                        text += " " + connector.toString() + " " + summarizers.get(j).getString();
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



    private List<Double> getAverageMeasures() {

        List<Double> measures = new ArrayList<>();

        return measures;
    }
}
