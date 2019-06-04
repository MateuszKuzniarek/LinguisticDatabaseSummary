package logic.summaries;

import data.DatabaseRepository;
import logic.membership.LinguisticVariable;
import logic.norms.MaxSNorm;
import logic.norms.MinTNorm;
import logic.norms.Norm;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Summarizer {

    private DatabaseRepository databaseRepository = new DatabaseRepository();
    private Norm tNorm = new MinTNorm();
    private Norm sNorm = new MaxSNorm();
    private List<Operation> summarizerOperations = new ArrayList<>();
    private List<Operation> qualifierOperations = new ArrayList<>();

    public Summarizer andSummarizer(LinguisticVariable linguisticVariable) {
        summarizerOperations.add(new Operation(tNorm, linguisticVariable));
        return this;
    }

    public Summarizer orSummarizer(LinguisticVariable linguisticVariable) {
        summarizerOperations.add(new Operation(sNorm, linguisticVariable));
        return this;
    }

    public Summarizer andQualifier(LinguisticVariable linguisticVariable) {
        qualifierOperations.add(new Operation(tNorm, linguisticVariable));
        return this;
    }

    public Summarizer orQualifier(LinguisticVariable linguisticVariable) {
        qualifierOperations.add(new Operation(sNorm, linguisticVariable));
        return this;
    }

    public double getSummarizerValue(int id) {
        double attributeValue = databaseRepository.getPlayerAttribute(id,
                summarizerOperations.get(0).getLinguisticVariable().getAttributeName());

        double summarizerValue = summarizerOperations.get(0).getLinguisticVariable()
                .getMembershipFunction().calculateMembership(attributeValue);

        for (int i = 1; i < summarizerOperations.size(); i++) {

            attributeValue = databaseRepository.getPlayerAttribute(id,
                    summarizerOperations.get(i).getLinguisticVariable().getAttributeName());

            summarizerValue += summarizerOperations.get(i).getLinguisticVariable()
                    .getMembershipFunction().calculateMembership(attributeValue);
        }
        double qualifierValue = getQualifierValue(id);
        return tNorm.calculateNorm(summarizerValue, qualifierValue);
    }

    public double getQualifierValue(int id) {
        if(qualifierOperations.isEmpty()) {
            return 1;
        }
        double attributeValue = databaseRepository.getPlayerAttribute(id,
                qualifierOperations.get(0).getLinguisticVariable().getAttributeName());

        double qualifierValue = qualifierOperations.get(0).getLinguisticVariable()
                .getMembershipFunction().calculateMembership(attributeValue);

        for (int i = 1; i < qualifierOperations.size(); i++) {

            attributeValue = databaseRepository.getPlayerAttribute(id,
                    qualifierOperations.get(0).getLinguisticVariable().getAttributeName());

            qualifierValue += qualifierOperations.get(0).getLinguisticVariable()
                    .getMembershipFunction().calculateMembership(attributeValue);

        }
        return qualifierValue;
    }

    public String getSummaryFragment() {
        String result = " players";
        if(!qualifierOperations.isEmpty()) {
            result += " being/having " + qualifierOperations.get(0).getLinguisticVariable().getLabel();
            for (int i = 1; i < qualifierOperations.size(); i++) {
                result += " " + qualifierOperations.get(i).norm.getSummaryFragment() + " " +
                        qualifierOperations.get(i).getLinguisticVariable().getLabel();
            }
        }

        result += " are/have " + summarizerOperations.get(0).getLinguisticVariable().getLabel();
        for (int i = 1; i < summarizerOperations.size(); i++) {
            result += " " + summarizerOperations.get(i).norm.getSummaryFragment() + " " +
                    summarizerOperations.get(i).getLinguisticVariable().getLabel();
        }
        return result;
    }
}
