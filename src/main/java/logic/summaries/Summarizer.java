package logic.summaries;

import data.DatabaseRepository;
import data.PlayerInfo;
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

    Summarizer andSummarizer(LinguisticVariable linguisticVariable) {
        summarizerOperations.add(new Operation(tNorm, linguisticVariable));
        return this;
    }

    Summarizer orSummarizer(LinguisticVariable linguisticVariable) {
        summarizerOperations.add(new Operation(sNorm, linguisticVariable));
        return this;
    }

    Summarizer andQualifier(LinguisticVariable linguisticVariable) {
        qualifierOperations.add(new Operation(tNorm, linguisticVariable));
        return this;
    }

    Summarizer orQualifier(LinguisticVariable linguisticVariable) {
        qualifierOperations.add(new Operation(sNorm, linguisticVariable));
        return this;
    }

    public double getSummarizerValue(PlayerInfo playerInfo) {

        LinguisticVariable linguisticVariable = summarizerOperations.get(0).getLinguisticVariable();
        double attributeValue = playerInfo.getAttributeValue(linguisticVariable.getAttributeName());
        double summarizerValue = linguisticVariable.getFuzzySet().calculateMembership(attributeValue);

        for (int i = 1; i < summarizerOperations.size(); i++) {

            linguisticVariable = summarizerOperations.get(i).getLinguisticVariable();
            attributeValue = playerInfo.getAttributeValue(linguisticVariable.getAttributeName());
            summarizerValue = summarizerOperations.get(i).norm.calculateNorm(
                    summarizerValue,
                    linguisticVariable.getFuzzySet().calculateMembership(attributeValue));
        }
        double qualifierValue = getQualifierValue(playerInfo);
        return tNorm.calculateNorm(summarizerValue, qualifierValue);
    }

    public double getQualifierValue(PlayerInfo playerInfo) {
        if(qualifierOperations.isEmpty()) {
            return 1;
        }

        LinguisticVariable linguisticVariable = qualifierOperations.get(0).getLinguisticVariable();
        double attributeValue = playerInfo.getAttributeValue(linguisticVariable.getAttributeName());
        double qualifierValue = linguisticVariable.getFuzzySet().calculateMembership(attributeValue);

        for (int i = 1; i < qualifierOperations.size(); i++) {

            linguisticVariable = qualifierOperations.get(i).getLinguisticVariable();
            attributeValue = playerInfo.getAttributeValue(linguisticVariable.getAttributeName());
            qualifierValue = qualifierOperations.get(i).norm.calculateNorm(
                    qualifierValue,
                    linguisticVariable.getFuzzySet().calculateMembership(attributeValue));
        }
        return qualifierValue;
    }

    String getSummaryFragment() {
        StringBuilder result = new StringBuilder(" players");
        if(!qualifierOperations.isEmpty()) {
            result.append(" being/having ").append(qualifierOperations.get(0).getLinguisticVariable().getLabel());
            for (int i = 1; i < qualifierOperations.size(); i++) {
                result.append(" ")
                        .append(qualifierOperations.get(i).norm.getSummaryFragment())
                        .append(" being/having ")
                        .append(qualifierOperations.get(i).getLinguisticVariable().getLabel());
            }
        }

        result.append(" are/have ").append(summarizerOperations.get(0).getLinguisticVariable().getLabel());
        for (int i = 1; i < summarizerOperations.size(); i++) {
            result.append(" ")
                    .append(summarizerOperations.get(i).norm.getSummaryFragment())
                    .append(" are/have ")
                    .append(summarizerOperations.get(i).getLinguisticVariable().getLabel());
        }
        return result.toString();
    }
}
