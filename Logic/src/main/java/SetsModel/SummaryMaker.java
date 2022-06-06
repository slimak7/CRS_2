package SetsModel;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class SummaryMaker {

    @Getter public List<LinguisticVariable> qualifiers;
    @Getter public List<LinguisticQuantifier> quantifier;
    @Getter public List<LinguisticVariable> summarizers;
    @Getter public Integer multiForm;
    @Getter public SummaryTypes summaryType;
    @Getter public QualityMeasures qualityMeasures;
    @Getter public Connector connector;

    private Double T_2, T_3, T_4, T_5, T_8, T_9, T_10, T_11;

    private List<String> houseTypes;

    public SummaryMaker(List<LinguisticVariable> qualifiers, List<LinguisticQuantifier> quantifier, List<LinguisticVariable> summarizers, Integer multiForm, SummaryTypes summaryType, QualityMeasures qualityMeasures, Connector connector, List<String> houseTypes) {
        this.qualifiers = qualifiers;
        this.quantifier = quantifier;
        this.summarizers = summarizers;
        this.multiForm = multiForm;
        this.summaryType = summaryType;
        this.qualityMeasures = qualityMeasures;
        this.connector = connector;
        this.houseTypes = houseTypes;

        List<FuzzySet> summarizersSets = new ArrayList<FuzzySet>();

        for (var sum:summarizers
             ) {

            summarizersSets.addAll(sum.getCurrentFuzzySet());
        }

        List<FuzzySet> qualifiersSets = new ArrayList<>();

        if (qualifiers != null)
            for (var qualifier:qualifiers) {

                qualifiersSets.addAll(qualifier.getCurrentFuzzySet());
            }

        this.qualityMeasures = new QualityMeasures(summarizersSets, qualifiers == null ? null : qualifiersSets, this, summarizersSets.get(0).getClassicSet().getElements().size());
    }


    public List<Summary> getStringSummariesWithAverageT(List<Double> weights) {

        List<Double> T_1 = qualityMeasures.getT_1();

        if (summaryType.equals(SummaryTypes.multi)) {

            List<Double> T_6 = qualityMeasures.getT_6();
            List<Double> T_7 = qualityMeasures.getT_7();

            T_2 = qualityMeasures.getT_2();
            T_3 = qualityMeasures.getT_3();
            T_4 = qualityMeasures.getT_4();
            T_5 = qualityMeasures.getT_5();
            T_8 = qualityMeasures.getT_8();
            T_9 = qualityMeasures.getT_9();
            T_10 = qualityMeasures.getT_10();
            T_11 = qualityMeasures.getT_11();

        }


        List<Summary> summaries = new ArrayList<>();

        if (quantifier != null)
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


                    text += " domów, które są/mają ";

                    int j = 0;
                    for (var q:qualifiers
                         ) {

                        text += q.getString();

                        if (j < qualifiers.size()-1)
                            text += " i ";

                        j++;
                    }

                    text += " są/mają " + summarizers.get(0).getString();

                    for (j = 1; j < summarizers.size(); j++) {

                        text += " " + connector.toString() + " " + summarizers.get(j).getString();
                    }

                }


            } else {

                text += quantifier.get(i).name;

                if (multiForm.equals(1)) {


                    text += " domy typu " + houseTypes.get(0) + " w porównaniu do domów typu " + houseTypes.get(1);

                    text += " są/mają " + summarizers.get(0).getString();

                    for (int j = 1; j < summarizers.size()/2; j++) {

                        text += " " + connector.toString() + " " + summarizers.get(j).getString();
                    }
                }
                if (multiForm.equals(2)) {

                    text += " domy typu " + houseTypes.get(0) + " w porównaniu do tych domów typu " + houseTypes.get(1) + ", które są/mają ";

                    int j = 0;
                    for (var q:qualifiers
                    ) {

                        text += q.getString();

                        if (j < qualifiers.size()-1)
                            text += " i ";

                        j++;
                    }

                    text += " są/mają " + summarizers.get(0).getString();

                    for (j = 1; j < summarizers.size()/2; j++) {

                        text += " " + connector.toString() + " " + summarizers.get(j).getString();
                    }
                }
                if (multiForm.equals(3)) {

                    text += " domy typu " + houseTypes.get(0) + ", które są/mają ";

                    int j = 0;
                    for (var q:qualifiers
                    ) {

                        text += q.getString();

                        if (j < qualifiers.size()-1)
                            text += " i ";

                        j++;
                    }

                    text += " w porównaniu do domów typu " + houseTypes.get(1);
                    text += " są/mają " + summarizers.get(0).getString();

                    for (j = 1; j < summarizers.size()/2; j++) {

                        text += " " + connector.toString() + " " + summarizers.get(j).getString();
                    }
                }

            }


            summaries.add(new Summary(text, Arrays.asList(T_1.get(i))));
        }

        if (multiForm.equals(4)) {

            String text = "Więcej domów typu " + houseTypes.get(0) + " niż " + "domów typu " + houseTypes.get(1);

            text += " są/mają " + summarizers.get(0).getString();

            for (int j = 1; j < summarizers.size()/2; j++) {

                text += " " + connector.toString() + " " + summarizers.get(j).getString();
            }

            summaries.add(new Summary(text, Arrays.asList(T_1.get(0))));
        }

        return summaries;
    }



    private Double getAverageMeasure(Double T1, Double T6, Double T7, List<Double> weights) {

        Double denominator = 0.0;

        for(var w:weights) {
            denominator += w;
        }

        Double numerator = T1 * weights.get(0) + T_2 * weights.get(1) + T_3 * weights.get(2) + T_4 * weights.get(3) +
                T_5 * weights.get(4) + T6 * weights.get(5) + T7 * weights.get(6) + T_8 * weights.get(7) +
                T_9 * weights.get(8) + T_10 * weights.get(9) + T_10 * weights.get(10);

        return numerator/denominator;
    }
}
