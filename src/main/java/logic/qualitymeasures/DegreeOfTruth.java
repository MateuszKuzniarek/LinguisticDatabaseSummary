package logic.qualitymeasures;

import data.DatabaseRepository;
import logic.membership.Summarizer;
import logic.summaries.Summary;

import java.util.HashMap;
import java.util.Map;

public class DegreeOfTruth extends QualityMeasure {

    private DatabaseRepository databaseRepository = new DatabaseRepository();

    //remembers sums to not calculate them for every quantifier
    private Map<Summarizer, Double> summarizersSumOfMembershipValues = new HashMap<>();

    @Override
    public double getQuality(Summary summary) {
        double sumOfMembershipValues = 0;
        if(summarizersSumOfMembershipValues.containsKey(summary.getSummarizer())) {
            sumOfMembershipValues = summarizersSumOfMembershipValues.get(summary.getSummarizer());
        }
        else {
            int numberOfRecords = databaseRepository.getPlayerCount();
            for (int i = 1; i <= numberOfRecords; i++) {
                double value = databaseRepository.getPlayerAttribute(i, summary.getSummarizer().getAttributeName());
                sumOfMembershipValues += summary.getSummarizer().getMembershipFunction().calculateMembership(value);
            }
            summarizersSumOfMembershipValues.put(summary.getSummarizer(), sumOfMembershipValues);
        }

        return summary.getQuantifier().getValue(sumOfMembershipValues);
    }
}
