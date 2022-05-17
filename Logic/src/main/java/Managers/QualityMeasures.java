package Managers;

import Repos.LinguisticQuantifierRepo;
import SetsModel.LinguisticQuantifier;
import SetsModel.LinguisticVariable;
import SetsModel.Summary;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class QualityMeasures {

    @Getter private List<LinguisticVariable> summarizers;
    @Getter private LinguisticQuantifier quantifier;
    @Getter private List<LinguisticVariable> qualifiers;


    public List<Double> getT_1() {

        List<Double> measures = new ArrayList<>();




        return measures;
    }

}
