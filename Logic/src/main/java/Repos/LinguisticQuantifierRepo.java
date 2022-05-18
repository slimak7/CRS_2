package Repos;

import SetsModel.LinguisticQuantifier;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@ToString
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

    public List<LinguisticQuantifier> getAll() {

        List<LinguisticQuantifier> q = new ArrayList<>();

        quantifiers.forEach((key, v) -> q.add(v));

        return q;
    }
}
