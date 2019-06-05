package logic.membership;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrapezoidFuzzySet extends ContinuousFuzzySet {
    private double a1, b1, b2, a2;

    public double calculateMembership(double attributeValue) {
        if (attributeValue >= b1 && attributeValue <= b2) { return 1; }
        else if (attributeValue <= a1) { return 0; }
        else if (attributeValue >= a2) { return 0; }
        else if (attributeValue < b1) { return (1 / (b1 - a1)) * (attributeValue - a1); }
        else { return (1 / (b2 - a2)) * (attributeValue - a2); }
    }

    @Override
    public double getFrom() {
        return a1;
    }

    @Override
    public double getTo() {
        return a2;
    }

    @Override
    public FuzzySet getCore() {
        TrapezoidFuzzySet core = new TrapezoidFuzzySet(b1, b1, b2, b2);
        core.setRealmStart(getRealmStart());
        core.setRealmEnd(getRealmEnd());
        return core;
    }

    @Override
    public double getHeight() {
        return 1;
    }

    @Override
    public FuzzySet getAlphaCut(double alpha) {
        if(alpha<0 || alpha>1) return new EmptyContinuousFuzzySet(getRealmStart(), getRealmEnd());
        double startingX = (alpha/(1 / (b1 - a1))) + a1;
        double endingX = (alpha/(1 / (b2 - a2))) + a2;
        TrapezoidFuzzySet alphaCut = new TrapezoidFuzzySet(startingX, startingX, endingX, endingX);
        alphaCut.setRealmEnd(getRealmEnd());
        alphaCut.setRealmStart(getRealmStart());
        return alphaCut;
    }
}
