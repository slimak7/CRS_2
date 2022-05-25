package SetsModel;

import javafx.util.Pair;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SummaryMaker {

    @Getter private LinguisticVariable qualifier;
    @Getter private List<LinguisticQuantifier> quantifier;
    @Getter private List<LinguisticVariable> summarizers;
    @Getter private Integer multiForm;
    @Getter private SummaryTypes summaryType;
    @Getter private QualityMeasures qualityMeasures;
    @Getter private Connector connector;


    public SummaryMaker(LinguisticVariable qualifier, List<LinguisticQuantifier> quantifier, List<LinguisticVariable> summarizers, Integer multiForm, SummaryTypes summaryType, QualityMeasures qualityMeasures, Connector connector) {
        this.qualifier = qualifier;
        this.quantifier = quantifier;
        this.summarizers = summarizers;
        this.multiForm = multiForm;
        this.summaryType = summaryType;
        this.qualityMeasures = qualityMeasures;
        this.connector = connector;

        List<FuzzySet> summarizersSets = new ArrayList<FuzzySet>();

        for (var sum:summarizers
             ) {

            summarizersSets.addAll(sum.getCurrentFuzzySet());
        }

        this.qualityMeasures = new QualityMeasures(summarizersSets,
                connector, quantifier, qualifier == null ? null : qualifier.getCurrentFuzzySet(), qualifier, multiForm, summaryType);
    }


    public List<Summary> getStringSummariesWithAverageT() {

        List<Double> T_1 = qualityMeasures.getT_1();
        List<Double> T_6 = qualityMeasures.getT_6();
        List<Double> T_7 = qualityMeasures.getT_7();

        Double T_2, T_3, T_4, T_5, T_8, T_9, T_10, T_11;

        T_2 = qualityMeasures.getT_2();
        T_3 = qualityMeasures.getT_3();
        T_4 = qualityMeasures.getT_4();
        T_5 = qualityMeasures.getT_5();
        T_8 = qualityMeasures.getT_8();
        T_9 = qualityMeasures.getT_9();
        T_10 = qualityMeasures.getT_10();
        T_11 = qualityMeasures.getT_11();


        List<Summary> summaries = new ArrayList<>();

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


            summaries.add(new Summary(text, Arrays.asList(T_1.get(i), T_2, T_3, T_4, T_5, T_6.get(i), T_7.get(i), T_8, T_9, T_10, T_11)));
        }

        return summaries;
    }



    private List<Double> getAverageMeasures() {

        List<Double> measures = new ArrayList<>();

        return measures;
    }
}
