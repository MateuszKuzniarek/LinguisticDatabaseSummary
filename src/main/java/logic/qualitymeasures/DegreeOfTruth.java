package logic.qualitymeasures;

import data.DatabaseRepository;
import logic.summaries.Summary;

public class DegreeOfTruth extends QualityMeasure {

    private DatabaseRepository databaseRepository = new DatabaseRepository();

    //todo program calculates r for every quantifier which makes it suboptimal
    @Override
    public double getQuality(Summary summary) {
        int numberOfRecords = databaseRepository.getPlayerCount();
        double sumOfMembershipValues = 0;
        for (int i = 1; i <= numberOfRecords; i++) {
            double value = databaseRepository.getPlayerAttribute(i, summary.getSummarizer().getAttributeName());
            sumOfMembershipValues += summary.getSummarizer().getMembershipFunction().calculateMembership(value);
        }
        return summary.getQuantifier().getValue(sumOfMembershipValues);
    }
}
