package logic.membership;

import lombok.Getter;
import lombok.Setter;

//todo this class is suited only for continuous function, it has to be abstract class for both continuous and discrete
public abstract class MembershipFunction {

    private static final double INTEGRAL_PRECISION = 5000;

    @Getter
    @Setter
    private double realmStart;

    @Getter
    @Setter
    private double realmEnd;

    public abstract double calculateMembership(double attributeValue);

    protected abstract double getFrom();

    protected abstract double getTo();

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

    public double getRealmCardinality() {
        return (realmEnd - realmStart);
    }
}
