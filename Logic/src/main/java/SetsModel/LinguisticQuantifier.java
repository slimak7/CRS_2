package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class LinguisticQuantifier {

    @Getter LinguisticQuantifiersTypes quantifierType;
    @Getter String name;
    @Getter FuzzySet set;
}
