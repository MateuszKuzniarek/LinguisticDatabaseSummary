package logic.membership;

import lombok.Getter;
import lombok.Setter;

//todo this class is suited only for continuous function, it has to be abstract class for both continuous and discrete
public abstract class FuzzySet {

    @Getter
    @Setter
    protected double realmStart;

    @Getter
    @Setter
    protected double realmEnd;

    public abstract double calculateMembership(double attributeValue);

    protected abstract double getFrom();

    protected abstract double getTo();

    public abstract double calculateDegreeOfFuzziness();

    public abstract double calculateSupportCardinality();

    public abstract double calculateCardinality();

    public abstract double getRealmCardinality();

    public abstract FuzzySet getSupport();

    public abstract double getCharacteristicFunctionValue(double x);

    public abstract FuzzySet getCore();

    public abstract double getHeight();

    public abstract FuzzySet getAlphaCut(double alpha);

    public double getComplementValue(double x) {
        return 1.0 - calculateMembership(x);
    }
}
