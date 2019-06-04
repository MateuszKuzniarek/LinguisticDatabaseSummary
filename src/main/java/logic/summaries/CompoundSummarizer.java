package logic.summaries;

import logic.norms.MaxSNorm;
import logic.norms.MinTNorm;
import logic.norms.Norm;
import logic.membership.LinguisticVariable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompoundSummarizer extends Summarizer{

    private Norm tNorm = new MinTNorm();
    private Norm sNorm = new MaxSNorm();
    private List<Operation> operations = new ArrayList<>();
    private Double value = null;

    @Getter
    @Setter
    @AllArgsConstructor
    private class Operation {
        Norm norm;
        LinguisticVariable linguisticVariable;
    }

    public CompoundSummarizer and(LinguisticVariable linguisticVariable) {
        operations.add(new Operation(tNorm, linguisticVariable));
        return this;
    }

    public CompoundSummarizer or(LinguisticVariable linguisticVariable) {
        operations.add(new Operation(sNorm, linguisticVariable));
        return this;
    }

    @Override
    public double getValue(int id) {
        double value = operations.get(0).getLinguisticVariable().getMembershipFunction().calculateMembership(id);
        for(int i=1; i<operations.size(); i++) {
            value = operations.get(i).norm.calculateNorm(value,
                    operations.get(i).getLinguisticVariable().getMembershipFunction().calculateMembership(id));
        }
        return value;
    }

    @Override
    public String getSummaryFragment() {
        String result = "players are/have ";
        result += operations.get(0).getLinguisticVariable().getLabel();
        for(int i=1; i<operations.size(); i++) {
            result += " "  + operations.get(i).norm.getSummaryFragment() + " " +
                    operations.get(i).getLinguisticVariable().getLabel();
        }
        return result;
    }
}