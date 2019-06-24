package logic.membership;

import logic.norms.Norm;

public abstract class ContinuousFuzzySet extends FuzzySet {
    private static final double INTEGRAL_PRECISION = 5000;

    public double calculateDegreeOfFuzziness() {
        if (realmStart == realmEnd) { return 0; }
        return calculateSupportCardinality() / (realmEnd - realmStart);
    }

    public double calculateSupportCardinality() {
        return getSupport().calculateCardinality();
    }

    public double calculateCardinality() {
        double result = 0;
        double start = realmStart;
        double end = realmEnd;
        double integralStep = (end - start) / INTEGRAL_PRECISION;
        for (double d = start; d < end; d += integralStep) {
            double x1 = calculateMembership(d);
            double x2 = calculateMembership(d + integralStep);
            result += (x1 + x2) / 2 * integralStep;
        }
        return result;
    }

    public FuzzySet getSupport() {
        FuzzySet thisFuzzySet = this;
        ContinuousFuzzySet supportSet = new ContinuousFuzzySet() {

            private FuzzySet fuzzySet = thisFuzzySet;

            @Override
            public double calculateMembership(double attributeValue) {
                if(fuzzySet.calculateMembership(attributeValue) > 0) return 1;
                else return 0;
            }
        };
        supportSet.setRealmStart(getRealmStart());
        supportSet.setRealmEnd(getRealmEnd());
        return supportSet;
    }

    public double getRealmCardinality() {
        return (realmEnd - realmStart);
    }

    @Override
    public FuzzySet getCore() {
        FuzzySet thisFuzzySet = this;
        ContinuousFuzzySet coreSet = new ContinuousFuzzySet() {

            private FuzzySet fuzzySet = thisFuzzySet;

            @Override
            public double calculateMembership(double attributeValue) {
                if(Math.abs(fuzzySet.calculateMembership(attributeValue) - 1.0)<precision) return 1;
                else return 0;
            }
        };
        coreSet.setRealmStart(getRealmStart());
        coreSet.setRealmEnd(getRealmEnd());
        return coreSet;
    }

    @Override
    public double getHeight() {
        double result = 0;
        double start = realmStart;
        double end = realmEnd;
        double step = (end - start) / INTEGRAL_PRECISION;
        for (double d = start; d < end; d += step) {
            if(calculateMembership(d)>result) {
                result = calculateMembership(d);
            }
        }
        return result;
    }

    @Override
    public FuzzySet getAlphaCut(double alpha) {
        FuzzySet thisFuzzySet = this;
        ContinuousFuzzySet alphaCut = new ContinuousFuzzySet() {

            private FuzzySet fuzzySet = thisFuzzySet;

            @Override
            public double calculateMembership(double attributeValue) {
                if(fuzzySet.calculateMembership(attributeValue) > alpha) return 1;
                else return 0;
            }
        };
        alphaCut.setRealmStart(getRealmStart());
        alphaCut.setRealmEnd(getRealmEnd());
        return alphaCut;
    }

    @Override
    public FuzzySet getComplement() {
        FuzzySet thisFuzzySet = this;
        ContinuousFuzzySet complement = new ContinuousFuzzySet() {

            private FuzzySet fuzzySet = thisFuzzySet;

            @Override
            public double calculateMembership(double attributeValue) {
                return 1.0 - fuzzySet.calculateMembership(attributeValue);
            }
        };
        complement.setRealmStart(getRealmStart());
        complement.setRealmEnd(getRealmEnd());
        return complement;
    }

    @Override
    public FuzzySet combine(Norm norm, FuzzySet fuzzySet) {
        FuzzySet thisFuzzySet = this;
        ContinuousFuzzySet combinedSet = new ContinuousFuzzySet() {

            private FuzzySet firstFuzzySet = thisFuzzySet;
            private FuzzySet secondFuzzySet = fuzzySet;

            @Override
            public double calculateMembership(double attributeValue) {
                return norm.calculateNorm(firstFuzzySet.calculateMembership(attributeValue),
                        secondFuzzySet.calculateMembership(attributeValue));
            }
        };
        combinedSet.setRealmStart(getRealmStart());
        combinedSet.setRealmEnd(getRealmEnd());
        return combinedSet;
    }

    @Override
    public String getFunctionType() {
        return "continuous";
    }

    @Override
    public String getDefinition() {
        return "continuous";
    }
}
