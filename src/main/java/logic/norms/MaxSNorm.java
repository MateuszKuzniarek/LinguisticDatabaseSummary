package logic.norms;

public class MaxSNorm extends SNorm{

    @Override
    public double calculateNorm(double a, double b) {
        return Math.max(a,b);
    }
}
