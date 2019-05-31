package logic.membership;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Quantifier {

    protected String label;
    protected MembershipFunction membershipFunction;
    public abstract double getValue(double r);
}
