package SetsModel;

import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@ToString
public class LinguisticVariable {

    @Getter private AttributeType attributeType;

    @Getter private Map<String, FuzzySet> labels;

    @Getter private List<String> currentLabels;


    public LinguisticVariable(AttributeType attributeType, Map<String, FuzzySet> labels, String currentLabel) {
        this.attributeType = attributeType;
        this.labels = labels;

        currentLabels = new ArrayList<String>();
        currentLabels.add(currentLabel);
    }

    public LinguisticVariable(AttributeType attributeType) {

        this.attributeType = attributeType;
        labels = new LinkedHashMap<>();
        currentLabels = new ArrayList<>();
    }

    public void addCurrentLabel(String name) {
        currentLabels.add(name);
    }

    public void addCurrentLabel(Integer index) {

        currentLabels.addAll(getLabelsWithIndexes(Arrays.asList(index)));
    }

    public void addCurrentLabel(List<Integer> indexes) {

        currentLabels.addAll(getLabelsWithIndexes(indexes));
    }

    public void addLabel(String label, FuzzySet set) {

        labels.put(label, set);
    }

    public FuzzySet getFuzzySet(String label) {

        return labels.get(label);
    }

    public List<FuzzySet> getCurrentFuzzySet() {

        List<FuzzySet> fuzzySets = new ArrayList<>();

        labels.forEach((key, value) -> {

            if (currentLabels.contains(key))
                fuzzySets.add(value);
        });

        return fuzzySets;
    }

    public List<FuzzySet> getAllFuzzySets () {

        List<FuzzySet> sets = new ArrayList<>();

        labels.forEach((key, value) -> sets.add(value));

        return sets;
    }

    public String getString() {

        List<String> s = new ArrayList<>();
        String text = "";

        this.labels.forEach((key, value) -> {
            if (currentLabels.contains(key)) {
                s.add(key);
            }
        });

        int i = 0;
        for (var label:s
             ) {
            text += label;

            if (i >= 0 && i < s.size()-1)
                text += " i ";

            i++;
        }

        return text + " " + attributeType.toString();
    }

    public List<String> getAllLabels() {

        List<String> labels = new ArrayList<>();

        this.labels.forEach((key, value) -> labels.add(key));

        return labels;
    }

    public FuzzySet getFuzzySet(Integer index) {

        return getAllFuzzySets().get(index);
    }

    public Space getSpace() {

        return getFuzzySet(0).getSpace();
    }

    public void setClassicSet(ClassicSet set) {

        this.labels.forEach((key, value) -> value.setClassicSet(set));
    }

    public List<String> getLabelsWithIndexes(List<Integer> indexes) {

        List<String> labels = new ArrayList<>();

        AtomicReference<Integer> i = new AtomicReference<>(0);
        this.labels.forEach((key, value) -> {
            if (indexes.contains(i.get()))
                labels.add(key);

            i.getAndSet(i.get() + 1);
        });
        return labels;
    }

    public void clearCurrentLabels() {
        currentLabels.clear();
    }

}
