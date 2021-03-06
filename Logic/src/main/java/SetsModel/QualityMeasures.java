package SetsModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class QualityMeasures {

    @Getter private List<FuzzySet> summarizers;
    @Getter private SummaryMaker summaryMaker;

    private Integer elementsCount;

    public QualityMeasures(List<FuzzySet> summarizers, List<FuzzySet> qualifierList, SummaryMaker summaryMaker, Integer elementsCount) {
        this.summarizers = summarizers;
        this.summaryMaker = summaryMaker;

        if (qualifierList != null) {

            this.qualifier = connection(qualifierList);

        }

        this.elementsCount = elementsCount;
    }

    private FuzzySet qualifier;

    public List<Double> getT_1() {



        List<Double> measures = new ArrayList<>();

        Double r = 0.0;
        Double r2 = 0.0;
        Double m = 0.0;
        Double m2 = 0.0;

        if (summaryMaker.summaryType.equals(SummaryTypes.single)) {


            FuzzySet sumSet = connection(summarizers);


            if (summaryMaker.multiForm.equals(1)) {

                for (var v : sumSet.getMembershipValuesList()
                ) {

                    r += v;
                }

                m = Double.valueOf(elementsCount);
            }

            if (summaryMaker.multiForm.equals(2)) {

                for (int i = 0; i < qualifier.getMembershipValuesList().size(); i++) {


                    if (qualifier.getMembershipValuesList().get(i) > 0.0) {

                        r2 += qualifier.getMembershipValuesList().get(i);
                        r += sumSet.getMembershipValuesList().get(i);
                    }
                }

                m2 = Double.valueOf(elementsCount);
            }


        }
        else {

            if (summaryMaker.multiForm.equals(1)) {


                FuzzySet sumSet1 = connection(summarizers.subList(0, (summarizers.size()/2)));

                r = sumSet1.getCardinalValue();

                m = Double.valueOf(elementsCount);

                FuzzySet sumSet2 = connection(summarizers.subList((summarizers.size()/2), summarizers.size()));


                r2 = sumSet2.getCardinalValue();

                m2 = Double.valueOf(elementsCount);

            }
            if (summaryMaker.multiForm.equals(2)) {



                FuzzySet sumSet1 = connection(summarizers.subList(0, (summarizers.size()/2)));


                r = sumSet1.getCardinalValue();

                m = Double.valueOf(elementsCount);


                FuzzySet sumSet2 = connection(summarizers.subList((summarizers.size()/2), summarizers.size()));


                FuzzySet sq = sumSet2.product(qualifier);

                for(int i = 0; i < sq.getMembershipValuesList().size(); i++) {

                    r2 += sq.getMembershipValuesList().get(i);
                }

                m2 = Double.valueOf(elementsCount);
            }
            if (summaryMaker.multiForm.equals(3)) {



                FuzzySet sumSet1 = connection(summarizers.subList(0, (summarizers.size()/2)));

                FuzzySet sq;

                if (qualifier.getMembershipValuesList().size() > sumSet1.getMembershipValuesList().size())
                {
                    sq = qualifier.product(sumSet1);
                }
                else
                {
                    sq = sumSet1.product(qualifier);
                }

                for(int i = 0; i < sq.getMembershipValuesList().size(); i++) {

                    r += sq.getMembershipValuesList().get(i);
                }

                m = Double.valueOf(elementsCount);


                FuzzySet sumSet2 = connection(summarizers.subList((summarizers.size()/2), summarizers.size()));


                r2 = sumSet2.getCardinalValue();

                m2 = Double.valueOf(elementsCount);

            }
            if (summaryMaker.multiForm.equals(4)) {




                FuzzySet sumSet1 = connection(summarizers.subList(0, (summarizers.size()/2)));


                r = sumSet1.getCardinalValue();
                m = Double.valueOf(elementsCount);




                FuzzySet sumSet2 = connection(summarizers.subList((summarizers.size()/2), summarizers.size()));


                r2 = sumSet2.getCardinalValue();
                m2 = Double.valueOf(elementsCount);
            }
        }

        if (summaryMaker.quantifier != null)
        for (var q:summaryMaker.quantifier) {

            if (summaryMaker.summaryType.equals(SummaryTypes.single)) {
                if (summaryMaker.multiForm.equals(1)) {

                    if (q.getQuantifierType().equals(LinguisticQuantifiersTypes.relative)) {

                        measures.add(q.getSet().getFunction().calculateMembership(r / m));
                    } else {

                        measures.add(q.getSet().getFunction().calculateMembership(r));
                    }
                }
                if (summaryMaker.multiForm.equals(2)) {

                    measures.add(q.getSet().getFunction().calculateMembership(r / r2));

                }
            }
            else {

                if (summaryMaker.multiForm.equals(1) || summaryMaker.multiForm.equals(2) || summaryMaker.multiForm.equals(3)) {

                    if (q.getQuantifierType().equals(LinguisticQuantifiersTypes.relative)) {

                        measures.add(q.getSet().getFunction().calculateMembership((r/m) / ((r/m) + (r2/m2))));
                    }
                    else {
                        measures.add(0.0);
                    }
                }


            }
        }

        if (summaryMaker.multiForm.equals(4)) {

            measures.add(lukasiewiczImplication(r / m, r2 / m2));
        }

        return measures;
    }

    private FuzzySet connection(List<FuzzySet> sets) {

        FuzzySet set = sets.get(0);

        if (sets.size() == 1)
            return set;

        for (int i = 1; i < sets.size(); i++) {

            if (summaryMaker.connector.equals(Connector.or))
                set = set.sum(sets.get(i));
            else
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
            product *= sum.getDegreeOfFuzziness();
        }

        return 1 - Math.pow(product, 1.0 / summarizers.size());

    }

    public Double getT_3 () {
        Double qualifierMembership = 1.0;
        Integer t = 0;
        Integer h = 0;

        FuzzySet set = connection(summarizers);



        for (int i = 0; i < set.getClassicSet().getElements().size(); i++) {
            if (qualifier != null) {

                if (qualifier.getMembershipValuesList().size() > i)
                    qualifierMembership = qualifier.getMembershipValuesList().get(i);
            }

            if (qualifierMembership > 0) {
                h++;

                if (set.getMembershipValuesList().size() > i)
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

        for (var sum : summarizers) {
            int r = 0;
            for (var v:sum.getMembershipValuesList()) {
                if (v > 0) {
                    r++;
                }
            }
            product *= (double) r / elementsCount;
        }
        return Math.abs(product - T3);
    }

    public Double getT_5 () {

        return 2 * Math.pow(0.5, summarizers.size());
    }

    public List<Double> getT_6 () {

        List<Double> values = new ArrayList<>();

        if (summaryMaker.quantifier == null)
        {
            values.add(1.0);
            return values;
        }

        for (var q:summaryMaker.quantifier
             ) {

            Double supp = q.getSet().getFunction().getSupportRange();

            if (q.getQuantifierType().equals(LinguisticQuantifiersTypes.absolute)) {

                supp /= elementsCount;
            }

            values.add(1.0 - supp);
        }
        return values;
    }

    public List<Double> getT_7 () {

        List<Double> values = new ArrayList<>();

        if (summaryMaker.quantifier == null) {
            values.add(1.0);
            return values;
        }

        for (var q:summaryMaker.quantifier
        ) {

            Double card = q.getSet().getFunction().getCardinalityRange();

            if (q.getQuantifierType().equals(LinguisticQuantifiersTypes.absolute)) {

                card /= elementsCount;
            }

            values.add(1.0 - card);
        }
        return values;
    }

    public Double getT_8 () {

        double product = 1.0;

        for (var sum : summarizers) {
            Double l = sum.getClassicSet().getSpace().range.getMin();
            Double r = sum.getClassicSet().getSpace().range.getMax();
            Double s = sum.getFunction().getCardinalityRange() / (r-l);
            product *= s;
        }
        return 1 - Math.pow(product, (double) 1 / summarizers.size());
    }

    public Double getT_9 () {

        if (qualifier == null) return 0.0;


        return 1 - qualifier.getDegreeOfFuzziness();
    }

    public Double getT_10 () {

        if (qualifier == null) return 0.0;

        Double product = 1.0;
        List<FuzzySet> sets = new ArrayList<>();

        for (var q:summaryMaker.qualifiers
             ) {

            sets.addAll(q.getAllFuzzySets());
        }

        for (var variable : sets) {

            Double s = 0.0;
            for (var v:variable.getMembershipValuesList()) {

                s += v;

            }

            s /= elementsCount;

            product *= s;
        }
        return 1 - Math.pow(product, (double) 1 / sets.size());
    }

    public Double getT_11 () {

        if (qualifier == null)
        {
            return 1.0;
        }
        return 2 * Math.pow(0.5, summaryMaker.qualifiers.size());
    }

}
