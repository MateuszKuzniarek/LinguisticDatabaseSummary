package logic.membership;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Quantifier {

    private String label;
    private MembershipFunction membershipFunction;
    private boolean isRelative = true;

    public double getValue(double r) {
        return membershipFunction.calculateMembership(r);
    }
}
