package logic.summaries;

import logic.membership.FuzzySet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Quantifier {

    private String label;
    private FuzzySet fuzzySet;
    private boolean isRelative = true;

    public double getValue(double r) {
        return fuzzySet.calculateMembership(r);
    }
}
