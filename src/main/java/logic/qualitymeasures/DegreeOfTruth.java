package logic.qualitymeasures;

import data.DatabaseRepository;
import logic.summaries.Summarizer;
import logic.summaries.Summary;

import java.util.HashMap;
import java.util.Map;

public class DegreeOfTruth extends QualityMeasure {

    private DatabaseRepository databaseRepository = new DatabaseRepository();

    //remembers sums to not calculate them for every quantifier
    private Map<Summarizer, Double> summarizersSumOfMembershipValues = new HashMap<>();

    private double getSumOfSummarizers(Summary summary) {
        double sumOfMembershipValues = 0;
        if(summarizersSumOfMembershipValues.containsKey(summary.getSummarizer())) {
            sumOfMembershipValues = summarizersSumOfMembershipValues.get(summary.getSummarizer());
        }
        else {
            int numberOfRecords = databaseRepository.getPlayerCount();
            for (int i = 1; i <= numberOfRecords; i++) {
                sumOfMembershipValues += summary.getSummarizer().getSummarizerValue(i);
            }
            summarizersSumOfMembershipValues.put(summary.getSummarizer(), sumOfMembershipValues);
        }
        return sumOfMembershipValues;
    }

    private double getQualityForAbsoluteQuantifier(Summary summary) {
        return summary.getQuantifier().getValue(getSumOfSummarizers(summary));
    }

    private double getQualityForRelativeQuantifier(Summary summary) {
        double sumOfSummarizerValues = getSumOfSummarizers(summary);
        if(summary.getSummarizer().getQualifierOperations().isEmpty()) {
            return summary.getQuantifier().getValue(sumOfSummarizerValues/databaseRepository.getPlayerCount());
        }
        double sumOfQualifierValues = 0;
        if(summarizersSumOfMembershipValues.containsKey(summary.getQuantifier())) {
            sumOfQualifierValues = summarizersSumOfMembershipValues.get(summary.getQuantifier());
        }
        else {
            int numberOfRecords = databaseRepository.getPlayerCount();
            for (int i = 1; i <= numberOfRecords; i++) {
                sumOfQualifierValues += summary.getSummarizer().getQualifierValue(i);
            }
            summarizersSumOfMembershipValues.put(summary.getSummarizer(), sumOfQualifierValues);
        }

        return summary.getQuantifier().getValue(sumOfSummarizerValues/sumOfQualifierValues);
    }

    @Override
    public double getQuality(Summary summary) {
        if(summary.getQuantifier().isRelative()) {
            return getQualityForRelativeQuantifier(summary);
        }
        return getQualityForAbsoluteQuantifier(summary);
    }
}
