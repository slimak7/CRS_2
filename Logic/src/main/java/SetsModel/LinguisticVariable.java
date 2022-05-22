package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ToString
public class LinguisticVariable {

    @Getter private AttributeType attributeType;

    @Getter private Map<String, FuzzySet> labels;

    @Getter  private String currentLabel;


    public LinguisticVariable(AttributeType attributeType, Map<String, FuzzySet> labels, String currentLabel) {
        this.attributeType = attributeType;
        this.labels = labels;
        this.currentLabel = currentLabel;
    }

    public LinguisticVariable(AttributeType attributeType) {

        this.attributeType = attributeType;
        labels = new LinkedHashMap<>();
    }

    public void setCurrentLabel(String name) {
        currentLabel = name;
    }

    public void addLabel(String label, FuzzySet set) {

        labels.put(label, set);
    }

    public FuzzySet getFuzzySet(String label) {

        return labels.get(label);
    }

    public FuzzySet getCurrentFuzzySet() {

        return labels.get(currentLabel);
    }

    public List<FuzzySet> getAllFuzzySets () {

        List<FuzzySet> sets = new ArrayList<>();

        labels.forEach((key, value) -> sets.add(value));

        return sets;
    }

    public String getString() {

        return currentLabel + " " + attributeType.toString();
    }

    public List<String> getAllLabels() {

        List<String> labels = new ArrayList<>();

        this.labels.forEach((key, value) -> labels.add(key));

        return labels;
    }

    public FuzzySet getFuzzySet(Integer index) {

        return getAllFuzzySets().get(index);
    }

}
