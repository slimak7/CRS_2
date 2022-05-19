package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class QualityMeasures {

    @Getter private List<LinguisticVariable> summarizers;
    @Getter private List<LinguisticQuantifier> quantifiers;
    @Getter private LinguisticVariable qualifier;
    @Getter private ClassicSet classicSet1;
    @Getter private ClassicSet classicSet2;
    @Getter Integer multiForm;


    public List<Double> getT_1() {

        List<Double> measures = new ArrayList<>();

        double r = 0;
        double r2 = 0;
        double m = 0;
        double m2 = 0;

        if (qualifier == null) { // bez kwalifikatora

            for (var sum:summarizers
                 ) {

                sum.getCurrentFuzzySet().setClassicSet(classicSet1);

            }

            FuzzySet set = andConnection(summarizers.stream().map(LinguisticVariable::getCurrentFuzzySet).collect(Collectors.toList()));

            for (var v:set.getMembershipValuesList()
                 ) {

                r += v;
            }


            m = classicSet1.getElements().size();

            if (classicSet2 != null) { // wielopodmiotowe

                for (var sum:summarizers
                ) {

                    sum.getCurrentFuzzySet().setClassicSet(classicSet2);

                }

                set = andConnection(summarizers.stream().map(LinguisticVariable::getCurrentFuzzySet).collect(Collectors.toList()));

                for (var v:set.getMembershipValuesList()
                ) {

                    r2 += v;
                }

                m2 = classicSet2.getElements().size();
            }
        } else {
            if (classicSet2 != null) {
                if (multiForm.equals(2)) { // druga forma
                    for (var sum:summarizers
                    ) {

                        sum.getCurrentFuzzySet().setClassicSet(classicSet1);

                    }

                    FuzzySet set = andConnection(summarizers.stream().map(LinguisticVariable::getCurrentFuzzySet).collect(Collectors.toList()));

                    for (var v:set.getMembershipValuesList()
                    ) {

                        r += v;
                    }
                    m = classicSet1.getElements().size();

                    for (var sum:summarizers
                    ) {

                        sum.getCurrentFuzzySet().setClassicSet(classicSet2);

                    }

                    set = andConnection(summarizers.stream().map(LinguisticVariable::getCurrentFuzzySet).collect(Collectors.toList()));

                    qualifier.getCurrentFuzzySet().setClassicSet(classicSet2);
                    set = set.product(qualifier.getCurrentFuzzySet());

                    for (var v:set.getMembershipValuesList()
                    ) {

                        r2 += v;
                    }
                    m2 = classicSet2.getElements().size();
                }

                if (multiForm == 3) { // trzecia forma
                    for (var sum:summarizers
                    ) {

                        sum.getCurrentFuzzySet().setClassicSet(classicSet2);

                    }

                    FuzzySet set = andConnection(summarizers.stream().map(LinguisticVariable::getCurrentFuzzySet).collect(Collectors.toList()));

                    for (var v:set.getMembershipValuesList()
                    ) {

                        r2 += v;
                    }
                    m2 = classicSet2.getElements().size();

                    for (var sum:summarizers
                    ) {

                        sum.getCurrentFuzzySet().setClassicSet(classicSet1);

                    }

                    set = andConnection(summarizers.stream().map(LinguisticVariable::getCurrentFuzzySet).collect(Collectors.toList()));

                    qualifier.getCurrentFuzzySet().setClassicSet(classicSet1);
                    set = set.product(qualifier.getCurrentFuzzySet());

                    for (var v:set.getMembershipValuesList()
                    ) {

                        r2 += v;
                    }
                    m = classicSet1.getElements().size();
                }
            } else {
                for (var sum:summarizers
                ) {

                    sum.getCurrentFuzzySet().setClassicSet(classicSet1);

                }

                FuzzySet set = andConnection(summarizers.stream().map(LinguisticVariable::getCurrentFuzzySet).collect(Collectors.toList()));

                qualifier.getCurrentFuzzySet().setClassicSet(classicSet1);
                set = set.product(qualifier.getCurrentFuzzySet());

                for (var v:set.getMembershipValuesList()
                ) {

                    r += v;
                }
            }
        }

        for (var q:quantifiers
             ) {

            if (q.getQuantifierType().equals(LinguisticQuantifiersTypes.absolute) && qualifier == null) {
                measures.add(q.getSet().getFunction().calculateMembership(r));
            } else if (q.getQuantifierType().equals(LinguisticQuantifiersTypes.relative)) {
                if (classicSet2 != null) {
                    if (multiForm.equals(4)) { // czwarta forma

                        measures.add(1 - lukasiewiczImplication(r / m, r2 / m2));
                    }
                    measures.add(q.getSet().getFunction().calculateMembership((r / m) / (r / m + r2 / m2)));
                }
                measures.add(q.getSet().getFunction().calculateMembership(r / m));
            }

        }

        return measures;
    }

    private FuzzySet andConnection(List<FuzzySet> sets) {

        FuzzySet set = sets.get(0);

        if (sets.size() == 1)
            return set;

        for (int i = 1; i < sets.size(); i++) {

            set = set.product(sets.get(i));
        }

        return set;
    }

    public double lukasiewiczImplication (Double a, Double b) {
        return Math.min(1 - a + b, 1);
    }

    public Double getT_2 () {

        Double product = 1.0;

        for (var sum:summarizers
        ) {
            sum.getCurrentFuzzySet().setClassicSet(classicSet1);
            product *= sum.getCurrentFuzzySet().getDegreeOfFuzziness();
        }

        return 1 - Math.pow(product, 1.0 / summarizers.size());

    }

    public Double getT_3 () {

        Double qualifierMembership = 1.0;
        Integer t = 0;
        Integer h = 0;

        if (qualifier != null) {

            qualifier.getCurrentFuzzySet().setClassicSet(classicSet1);
        }

        for (var sum:summarizers
        ) {

            sum.getCurrentFuzzySet().setClassicSet(classicSet1);

        }

        FuzzySet set = andConnection(summarizers.stream().map(LinguisticVariable::getCurrentFuzzySet).collect(Collectors.toList()));

        for (int i = 0; i < classicSet1.getElements().size(); i++) {

            if (qualifier != null) {
                qualifierMembership = qualifier.getCurrentFuzzySet().getMembershipValuesList().get(i);
            }

            if (qualifierMembership > 0) {
                h++;

                if (set.getMembershipValuesList().get(i) > 0.0) {
                        t++;
                    }

            }
        }


        return Double.valueOf(t) / h;
    }

    public Double getT_4 () {

        Double product = 1.0;
        Double T3 = getT_3();

        for (var sum:summarizers
        ) {
            sum.getCurrentFuzzySet().setClassicSet(classicSet1);
        }

        for (var sum : summarizers) {
            int r = 0;
            for (var v:sum.getCurrentFuzzySet().getMembershipValuesList()) {
                if (v > 0) {
                    r++;
                }
            }
            product *= (double) r / classicSet1.getElements().size();
        }

        return Math.abs(product - T3);
    }

    public Double getT_5 () {

        return 2 * Math.pow(0.5, summarizers.size());
    }

    public List<Double> getT_6 () {

        List<Double> values = new ArrayList<>();

        for (var q:quantifiers
             ) {

            Double supp = q.getSet().getFunction().getMembershipFunction().getSupportRange();

            if (q.getQuantifierType().equals(LinguisticQuantifiersTypes.absolute)) {

                supp /= classicSet1.getElements().size();
            }

            values.add(supp);
        }
        return values;
    }

    public List<Double> getT_7 () {

        List<Double> values = new ArrayList<>();

        for (var q:quantifiers
        ) {

            Double card = q.getSet().getFunction().getMembershipFunction().getCardinalityRange();

            if (q.getQuantifierType().equals(LinguisticQuantifiersTypes.absolute)) {

                card /= classicSet1.getElements().size();
            }

            values.add(1.0 - card);
        }
        return values;
    }

    public Double getT_8 () {

        double product = 1.0;

        for (var sum : summarizers) {
            product *= sum.getCurrentFuzzySet().getFunction().getMembershipFunction().getCardinalityRange() / classicSet1.getElements().size();
        }

        return 1 - Math.pow(product, (double) 1 / summarizers.size());
    }

    public Double getT_9 () {

        if (qualifier == null) return 0.0;

        qualifier.getCurrentFuzzySet().setClassicSet(classicSet1);

        return 1 - qualifier.getCurrentFuzzySet().getDegreeOfFuzziness();
    }

    public Double getT_10 () {

        if (qualifier == null) return 0.0;

        Double product = 1.0;
        List<FuzzySet> sets = qualifier.getAllFuzzySets();

        for (var variable : sets) {
            product *= variable.getFunction().getMembershipFunction().getCardinalityRange() / classicSet1.getElements().size();
        }
        return 1 - Math.pow(product, (double) 1 / sets.size());
    }

    public Double getT_11 () {

        if (qualifier == null) return 0.0;

        return 2 * Math.pow(0.5, qualifier.getAllFuzzySets().size());
    }

}
