package Repos;

import SetsModel.LinguisticQuantifier;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LinguisticQuantifierRepo {

    Map<String, LinguisticQuantifier> quantifiers;

    public LinguisticQuantifierRepo() {

        this.quantifiers = new LinkedHashMap<>();

    }

    public void addQuantifier(LinguisticQuantifier quantifier) {

        quantifiers.put(quantifier.getName(), quantifier);
    }

    public LinguisticQuantifier getQuantifier(String name) {

        return quantifiers.get(name);
    }
}
