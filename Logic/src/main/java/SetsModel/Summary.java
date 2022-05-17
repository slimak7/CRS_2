package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@AllArgsConstructor
public class Summary {

    @Getter LinguisticVariable qualifier;
    @Getter LinguisticQuantifier quantifier;
    @Getter List<LinguisticVariable> summarizers;


}
