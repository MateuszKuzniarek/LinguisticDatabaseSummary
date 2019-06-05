package logic.membership;

import logic.utils.Point;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
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
        if (realmStart == realmEnd) { return 0; }
        return calculateSupportCardinality() / (realmEnd - realmStart);
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
        return (realmEnd - realmStart);
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
    public double getCharacteristicFunctionValue(double x) {
        if(membershipValues.stream().anyMatch(p -> Objects.equals(p.getX(), x))) {
            return 1;
        }
        return 0;
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

    //todo how does complement of discrete set even look like? think about it
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
}
