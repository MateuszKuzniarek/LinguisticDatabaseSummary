package logic.membership;

public class AbsoluteQuantifier extends Quantifier {

    @Override
    public double getValue(double r) {
        return membershipFunction.calculateMembership(r);
    }
}
