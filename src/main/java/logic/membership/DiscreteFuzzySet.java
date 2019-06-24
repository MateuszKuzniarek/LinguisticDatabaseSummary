package logic.membership;

import logic.norms.Norm;
import logic.utils.Point;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DiscreteFuzzySet extends FuzzySet {

    @Setter
    @Getter
    private List<Point> membershipValues = new ArrayList<>();

    @Override
    public double calculateMembership(double attributeValue) {
        for(Point point : membershipValues) {
            if(Math.abs(point.getX()-attributeValue)<precision) {
                return point.getY();
            }
        }
        return 0;
    }

    @Override
    public double calculateDegreeOfFuzziness() {
        double realmCardinality = getRealmCardinality();
        if (realmCardinality==0) {
            return 0;
        }
        return calculateSupportCardinality() / realmCardinality;
    }

    @Override
    public double calculateSupportCardinality() {
        return getSupport().calculateCardinality();
    }

    @Override
    public double calculateCardinality() {
        double sum = 0;
        for(Point point : membershipValues) {
            sum += point.getY();
        }
        return sum;
    }

    @Override
    public double getRealmCardinality() {
        return membershipValues.size();
    }

    @Override
    public FuzzySet getSupport() {
        DiscreteFuzzySet support = new DiscreteFuzzySet();
        for(Point point : membershipValues) {
            if(point.getY()>0) {
                support.getMembershipValues().add(point);
            }
        }
        support.setRealmStart(getRealmStart());
        support.setRealmEnd(getRealmEnd());
        return support;
    }

    @Override
    public FuzzySet getCore() {
        List<Point> corePoints = membershipValues.stream().filter(p -> Math.abs(p.getY()-1.0)<precision).collect(
                Collectors.toList());
        DiscreteFuzzySet core = new DiscreteFuzzySet();
        core.setMembershipValues(corePoints);
        core.setRealmStart(getRealmStart());
        core.setRealmEnd(getRealmEnd());
        return core;
    }

    @Override
    public double getHeight() {
        return membershipValues.stream().max(Comparator.comparing(Point::getY)).get().getY();
    }

    @Override
    public FuzzySet getAlphaCut(double alpha) {
        List<Point> alphaCutPoints = membershipValues.stream().filter(p -> Math.abs(p.getY()-alpha)<precision).collect(
                Collectors.toList());
        DiscreteFuzzySet alphaCut = new DiscreteFuzzySet();
        alphaCut.setMembershipValues(alphaCutPoints);
        alphaCut.setRealmStart(getRealmStart());
        alphaCut.setRealmEnd(getRealmEnd());
        return alphaCut;
    }

    @Override
    public FuzzySet getComplement() {
        List<Point> complementPoints = new ArrayList<>();
        for(Point point : membershipValues) {
            Point newPoint = new Point();
            newPoint.setX(point.getX());
            newPoint.setY(1.0-point.getY());
        }
        DiscreteFuzzySet complement = new DiscreteFuzzySet();
        complement.setMembershipValues(complementPoints);
        complement.setRealmStart(getRealmStart());
        complement.setRealmEnd(getRealmEnd());
        return complement;
    }

    @Override
    public FuzzySet combine(Norm norm, FuzzySet fuzzySet) {

        List<Point> combinedPoints = new ArrayList<>();
        for(Point point : membershipValues) {
            Point newPoint = new Point();
            newPoint.setX(point.getX());
            newPoint.setY(norm.calculateNorm(point.getY(), fuzzySet.calculateMembership(point.getX())));
        }
        DiscreteFuzzySet combinedSet = new DiscreteFuzzySet();
        combinedSet.setMembershipValues(combinedPoints);
        combinedSet.setRealmStart(getRealmStart());
        combinedSet.setRealmEnd(getRealmEnd());
        return combinedSet;
    }

    @Override
    public String getFunctionType() {
        return "discrete";
    }

    @Override
    public String getDefinition() {
        StringBuilder result = new StringBuilder();
        for(Point point : membershipValues) {
            result.append(point.getY()).append("/").append(point.getX()).append(";");
        }
        result = new StringBuilder(result.substring(0, result.length() - 1));
        return result.toString();
    }

    public static List<Point> getPointsOutOfDefinition(String setDefinition) {
        List<Point> result = new ArrayList<>();
        String[] pointStrings = setDefinition.split(";");
        for(String pointString : pointStrings) {
            String[] coordinates = pointString.split("/");
            double y = Double.parseDouble(coordinates[0]);
            double x = Double.parseDouble(coordinates[1]);
            Point point = new Point();
            point.setX(x);
            point.setY(y);
            result.add(point);
        }
        return result;
    }
}
