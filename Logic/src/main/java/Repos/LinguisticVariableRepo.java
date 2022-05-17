package Repos;

import SetsModel.LinguisticVariable;
import SetsModel.AttributeType;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;


public class LinguisticVariableRepo {

    @Getter private List<LinguisticVariable> linguisticVariables;

    public LinguisticVariableRepo() {
        this.linguisticVariables = new LinkedList<>();
    }

    public LinguisticVariable getVariable(int index) {

        return linguisticVariables.get(index);
    }

    public LinguisticVariable getVariable(AttributeType type) {

        return linguisticVariables.stream().filter(x -> x.getAttributeType().equals(type)).findAny().orElse(null);
    }

    public void addVariable(LinguisticVariable variable) {

        linguisticVariables.add(variable);
    }
}
