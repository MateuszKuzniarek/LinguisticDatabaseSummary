package logic.membership;

public abstract class ContinuousFuzzySet extends FuzzySet {
    protected static final double INTEGRAL_PRECISION = 5000;

    public double calculateDegreeOfFuzziness() {
        if (realmStart == realmEnd) { return 0; }
        return calculateSupportCardinality() / (realmEnd - realmStart);
    }

    public double calculateSupportCardinality() {
        double supportWidth = (getTo() - getFrom());
        return supportWidth;
    }

    public double calculateCardinality() {
        double result = 0;
        double start = getFrom();
        double end = getTo();
        double integralStep = (end - start) / INTEGRAL_PRECISION;
        for (double d = start; d < end; d += integralStep) {
            double x1 = calculateMembership(d);
            double x2 = calculateMembership(d + integralStep);
            result += (x1 + x2) / 2 * integralStep;
        }
        return result;
    }

    public FuzzySet getSupport() {
        return new TrapezoidFuzzySet(getFrom(), getFrom(), getTo(), getTo());

    }

    public double getRealmCardinality() {
        return (realmEnd - realmStart);
    }

    public double getCharacteristicFunctionValue(double x) {
        if(x<=getFrom() && x>=getTo()) {
            return 1;
        }
        return 0;
    }
}
