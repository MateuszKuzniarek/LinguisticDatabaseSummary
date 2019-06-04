package logic.norms;

public class MinTNorm extends TNorm {

    @Override
    public double calculateNorm(double a, double b) {
        return Math.min(a, b);
    }
}
