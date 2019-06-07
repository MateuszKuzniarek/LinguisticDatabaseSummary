package logic.membership;

import logic.norms.Norm;
import lombok.Getter;
import lombok.Setter;

public abstract class FuzzySet {

    protected static final double precision = 0.001;

    @Getter
    @Setter
    protected double realmStart;

    @Getter
    @Setter
    protected double realmEnd;

    public abstract double calculateMembership(double attributeValue);

    public abstract double calculateDegreeOfFuzziness();

    public abstract double calculateSupportCardinality();

    public abstract double calculateCardinality();

    public abstract double getRealmCardinality();

    public abstract FuzzySet getSupport();

    public abstract FuzzySet getCore();

    public abstract double getHeight();

    public abstract FuzzySet getAlphaCut(double alpha);

    public abstract FuzzySet getComplement();

    public abstract FuzzySet combine(Norm norm, FuzzySet fuzzySet);

    public abstract String getFunctionType();

    public abstract String getDefinition();
}
