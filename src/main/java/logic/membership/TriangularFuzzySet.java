package logic.membership;

public class TriangularFuzzySet extends TrapezoidFuzzySet {

    public TriangularFuzzySet(double a1, double b, double a2) {
        setA1(a1);
        setA2(a2);
        setB1(b);
        setB2(b);
    }
}
