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
    @Getter private ClassicSet classicSetP1;
    @Getter private ClassicSet classicSetP2;
    @Getter private ClassicSet classicSetW;
    @Getter private Integer multiForm;
    @Getter private SummaryTypes summaryType;

    public List<Double> getT_1() {

        List<Double> measures = new ArrayList<>();

        Double r = 0.0;
        Double r2 = 0.0;
        Double m = 0.0;
        Double m2 = 0.0;

        if (summaryType.equals(SummaryTypes.single)) {

            for (var sum : summarizers
            ) {
                sum.getCurrentFuzzySet().setClassicSet(classicSetP2);
            }

            FuzzySet sumSet = andConnection(summarizers.stream().map(x -> x.getCurrentFuzzySet()).collect(Collectors.toList()));

            if (multiForm.equals(1)) {

                for (var v : sumSet.getMembershipValuesList()
                ) {

                    r += v;
                }

                m = Double.valueOf(classicSetP2.getElements().size());
            }

            if (multiForm.equals(2)) {

                qualifier.getCurrentFuzzySet().setClassicSet(classicSetP1);

                FuzzySet qSet = qualifier.getCurrentFuzzySet();
                for (int i = 0; i < qSet.getMembershipValuesList().size(); i++) {


                    if (qSet.getMembershipValuesList().get(i) > 0.0) {

                        r2 += qSet.getMembershipValuesList().get(i);
                        r += sumSet.getMembershipValuesList().get(i);
                    }
                }

                m2 = Double.valueOf(classicSetP1.getElements().size());
            }


        }
        else {

            if (multiForm.equals(1)) {

                for (var sum : summarizers
                ) {
                    sum.getCurrentFuzzySet().setClassicSet(classicSetP1);
                }

                FuzzySet sumSet1 = andConnection(summarizers.stream().map(x -> x.getCurrentFuzzySet()).collect(Collectors.toList()));

                r = sumSet1.getCardinalValue();

                m = Double.valueOf(classicSetP1.getElements().size());

                for (var sum : summarizers
                ) {
                    sum.getCurrentFuzzySet().setClassicSet(classicSetP2);
                }

                FuzzySet sumSet2 = andConnection(summarizers.stream().map(x -> x.getCurrentFuzzySet()).collect(Collectors.toList()));

                r2 = sumSet2.getCardinalValue();

                m2 = Double.valueOf(classicSetP2.getElements().size());

            }
            if (multiForm.equals(2)) {

                for (var sum : summarizers
                ) {
                    sum.getCurrentFuzzySet().setClassicSet(classicSetP1);
                }

                FuzzySet sumSet1 = andConnection(summarizers.stream().map(x -> x.getCurrentFuzzySet()).collect(Collectors.toList()));

                r = sumSet1.getCardinalValue();

                m = Double.valueOf(classicSetP1.getElements().size());

                for (var sum : summarizers
                ) {
                    sum.getCurrentFuzzySet().setClassicSet(classicSetP2);
                }

                FuzzySet sumSet2 = andConnection(summarizers.stream().map(x -> x.getCurrentFuzzySet()).collect(Collectors.toList()));

                qualifier.getCurrentFuzzySet().setClassicSet(classicSetW);

                for(int i = 0; i < sumSet2.getMembershipValuesList().size(); i++) {

                    r2 += Math.min(sumSet2.getMembershipValuesList().get(i),
                            qualifier.getCurrentFuzzySet().getMembershipValuesList().get(i));
                }

                m2 = Double.valueOf(classicSetP2.getElements().size());
            }
            if (multiForm.equals(3)) {

                for (var sum : summarizers
                ) {
                    sum.getCurrentFuzzySet().setClassicSet(classicSetP1);
                }

                FuzzySet sumSet1 = andConnection(summarizers.stream().map(x -> x.getCurrentFuzzySet()).collect(Collectors.toList()));

                qualifier.getCurrentFuzzySet().setClassicSet(classicSetW);

                for(int i = 0; i < sumSet1.getMembershipValuesList().size(); i++) {

                    r += Math.min(sumSet1.getMembershipValuesList().get(i),
                            qualifier.getCurrentFuzzySet().getMembershipValuesList().get(i));
                }

                m = Double.valueOf(classicSetP1.getElements().size());

                for (var sum : summarizers
                ) {
                    sum.getCurrentFuzzySet().setClassicSet(classicSetP2);
                }

                FuzzySet sumSet2 = andConnection(summarizers.stream().map(x -> x.getCurrentFuzzySet()).collect(Collectors.toList()));

                r2 = sumSet2.getCardinalValue();

                m2 = Double.valueOf(classicSetP2.getElements().size());

            }
            if (multiForm.equals(4)) {

                for (var sum : summarizers
                ) {
                    sum.getCurrentFuzzySet().setClassicSet(classicSetP1);
                }

                FuzzySet sumSet1 = andConnection(summarizers.stream().map(x -> x.getCurrentFuzzySet()).collect(Collectors.toList()));

                r = sumSet1.getCardinalValue();
                m = Double.valueOf(classicSetP1.getElements().size());

                for (var sum : summarizers
                ) {
                    sum.getCurrentFuzzySet().setClassicSet(classicSetP2);
                }

                FuzzySet sumSet2 = andConnection(summarizers.stream().map(x -> x.getCurrentFuzzySet()).collect(Collectors.toList()));

                r2 = sumSet1.getCardinalValue();
                m2 = Double.valueOf(classicSetP2.getElements().size());
            }
        }

        for (var q:quantifiers) {

            if (summaryType.equals(SummaryTypes.single)) {
                if (multiForm.equals(1)) {

                    if (q.getQuantifierType().equals(LinguisticQuantifiersTypes.relative)) {

                        measures.add(q.getSet().getFunction().calculateMembership(r / m));
                    } else {

                        measures.add(q.getSet().getFunction().calculateMembership(r));
                    }
                }
                if (multiForm.equals(2)) {

                    measures.add(q.getSet().getFunction().calculateMembership(r / r2));

                }
            }
            else {

                if (multiForm.equals(1) || multiForm.equals(2) || multiForm.equals(3)) {

                    if (q.getQuantifierType().equals(LinguisticQuantifiersTypes.relative)) {

                        measures.add(q.getSet().getFunction().getMembershipFunction().calculateMembership((r/m) / ((r/m) + (r2/m2))));
                    }
                    else {
                        measures.add(0.0);
                    }
                }
                if (multiForm.equals(4)) {

                    measures.add(lukasiewiczImplication(r / m, r2 / m2));
                }
                else {
                    measures.add(0.0);
                }

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
            sum.getCurrentFuzzySet().setClassicSet(classicSetP1);
            product *= sum.getCurrentFuzzySet().getDegreeOfFuzziness();
        }

        return 1 - Math.pow(product, 1.0 / summarizers.size());

    }

    public Double getT_3 () {

        Double qualifierMembership = 1.0;
        Integer t = 0;
        Integer h = 0;

        if (qualifier != null) {

            qualifier.getCurrentFuzzySet().setClassicSet(classicSetP1);
        }

        for (var sum:summarizers
        ) {

            sum.getCurrentFuzzySet().setClassicSet(classicSetP1);

        }

        FuzzySet set = andConnection(summarizers.stream().map(LinguisticVariable::getCurrentFuzzySet).collect(Collectors.toList()));

        for (int i = 0; i < classicSetP1.getElements().size(); i++) {

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
            sum.getCurrentFuzzySet().setClassicSet(classicSetP1);
        }

        for (var sum : summarizers) {
            int r = 0;
            for (var v:sum.getCurrentFuzzySet().getMembershipValuesList()) {
                if (v > 0) {
                    r++;
                }
            }
            product *= (double) r / classicSetP1.getElements().size();
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

                supp /= classicSetP1.getElements().size();
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

                card /= classicSetP1.getElements().size();
            }

            values.add(1.0 - card);
        }
        return values;
    }

    public Double getT_8 () {

        double product = 1.0;

        for (var sum : summarizers) {
            product *= sum.getCurrentFuzzySet().getFunction().getMembershipFunction().getCardinalityRange() / classicSetP1.getElements().size();
        }

        return 1 - Math.pow(product, (double) 1 / summarizers.size());
    }

    public Double getT_9 () {

        if (qualifier == null) return 0.0;

        qualifier.getCurrentFuzzySet().setClassicSet(classicSetP1);

        return 1 - qualifier.getCurrentFuzzySet().getDegreeOfFuzziness();
    }

    public Double getT_10 () {

        if (qualifier == null) return 0.0;

        Double product = 1.0;
        List<FuzzySet> sets = qualifier.getAllFuzzySets();

        for (var variable : sets) {
            product *= variable.getFunction().getMembershipFunction().getCardinalityRange() / classicSetP1.getElements().size();
        }
        return 1 - Math.pow(product, (double) 1 / sets.size());
    }

    public Double getT_11 () {

        if (qualifier == null) return 0.0;

        return 2 * Math.pow(0.5, qualifier.getAllFuzzySets().size());
    }

}
