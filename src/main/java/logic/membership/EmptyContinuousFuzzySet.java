package logic.membership;

public class EmptyContinuousFuzzySet extends ContinuousFuzzySet {

    public EmptyContinuousFuzzySet(double realStart, double realEnd) {
        setRealmStart(realStart);
        setRealmEnd(realEnd);
    }

    @Override
    public double calculateMembership(double attributeValue) {
        return 0;
    }

    @Override
    protected double getFrom() {
        return 0;
    }

    @Override
    protected double getTo() {
        return 0;
    }

    @Override
    public FuzzySet getCore() {
        return new EmptyContinuousFuzzySet(getRealmStart(), getRealmEnd());
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public FuzzySet getAlphaCut(double alpha) {
        return new EmptyContinuousFuzzySet(getRealmStart(), getRealmEnd());
    }
}
