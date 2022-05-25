package Repos;

import SetsModel.Summary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SummaryRepo {

    private List<Summary> summaries;

    public SummaryRepo() {

        summaries = new ArrayList<>();
    }

    public void addSummary(Summary summary) {

        summaries.add(summary);
    }

    public Summary getSummary(Integer index) {

        return summaries.get(index);
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

        String s = "";

        for (var sum:summaries) {

            s += sum.getText() + " [" + sum.getQualityMeasures().get(0) + "]" + "\n";
        }

        return s;
    }
}
