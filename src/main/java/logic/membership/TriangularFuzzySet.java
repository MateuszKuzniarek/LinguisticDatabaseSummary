package logic.membership;

public class TriangularFuzzySet extends TrapezoidFuzzySet {

    public TriangularFuzzySet(double a1, double b, double a2) {
        setA1(a1);
        setA2(a2);
        setB1(b);
        setB2(b);
    }

    @Override
    public String getFunctionType() {
        return "triangular";
    }

    @Override
    public String getDefinition() {
        return "a1 = " + getA1() + " a2 = " + getA2() + " a3 = " + getB1();
    }
}
