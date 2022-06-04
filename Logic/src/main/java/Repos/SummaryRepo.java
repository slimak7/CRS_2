package Repos;

import SetsModel.Summary;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SummaryRepo {

    private Set<Summary> summaries;
    private Integer measureIndex;
    private Double degreeOfTruth;

    public SummaryRepo() {

        summaries = new HashSet<Summary>();
        measureIndex = 11;
        degreeOfTruth = 0.5;
    }

    public void addSummary(Summary summary) {

        summaries.add(summary);

    }

    public Summary getSummary(Integer index) {

        return summaries.stream().toList().get(index);
    }

    public List<Summary> getSummaries(Double degreeOfTruth) {

        return summaries.stream().filter(x -> x.getQualityMeasures().get(x.getQualityMeasures().size()-1) >= degreeOfTruth).collect(Collectors.toList());
    }

    public void clearAll() {
        summaries.clear();
    }

    public void addAll(List<Summary> summaries) {
        this.summaries.addAll(summaries);


    }

    public String getAllToString() {

        List<Summary> sum = summaries.stream().filter(x -> x.getQualityMeasures().get(measureIndex) >= degreeOfTruth).collect(Collectors.toList());

        sum.sort((x1, x2) -> {

            if (x1.getQualityMeasures().get(measureIndex) > x2.getQualityMeasures().get(measureIndex))
                return -1;
            if (x1.getQualityMeasures().get(measureIndex) < x2.getQualityMeasures().get(measureIndex))
                return 1;
            else return 0;
        });


        String text = "";

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_EVEN);

        for (var s:sum) {

            text += s.getText() + " [" + df.format(s.getQualityMeasures().get(measureIndex)) + "]" + "\n";
        }

        return text;
    }

    public String getAllToStringWithMeasures() {

        List<Summary> sum = summaries.stream().filter(x -> x.getQualityMeasures().get(measureIndex) >= degreeOfTruth).collect(Collectors.toList());

        sum.sort((x1, x2) -> {

            if (x1.getQualityMeasures().get(measureIndex) > x2.getQualityMeasures().get(measureIndex))
                return -1;
            if (x1.getQualityMeasures().get(measureIndex) < x2.getQualityMeasures().get(measureIndex))
                return 1;
            else return 0;
        });


        String text = "";

        for (var s:sum) {

            text += s.getText() + " [";

            int i = 0;
            for (var measure:s.getQualityMeasures()
                 ) {

                text += measure;

                if (i < s.getQualityMeasures().size()-1)
                    text += ";";

                i++;
            }
            text += "]\n";
        }

        return text;
    }

    public void setMeasureToSort(Integer index) {

        measureIndex = index;
    }

    public void setDegreeOfTruth(Double degree) {

        degreeOfTruth = degree;
    }
}
