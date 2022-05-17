package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;


public class LinguisticVariable {

    @Getter private AttributeType attributeType;

    @Getter private Map<String, FuzzySet> labels;

    @Getter  private String currentLabel;


    public LinguisticVariable(AttributeType attributeType, Map<String, FuzzySet> labels, String currentLabel) {
        this.attributeType = attributeType;
        this.labels = labels;
        this.currentLabel = currentLabel;
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

}
