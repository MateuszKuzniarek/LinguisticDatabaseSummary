package logic.summaries;

import data.DatabaseRepository;

public class SimpleSummarizer extends Summarizer{

    private DatabaseRepository databaseRepository = new DatabaseRepository();

    @Override
    public double getValue(int id) {
        double value = databaseRepository.getPlayerAttribute(id, getLinguisticVariable().getAttributeName());
        return getLinguisticVariable().getMembershipFunction().calculateMembership(value);
    }

    @Override
    public String getSummaryFragment() {
        return " players are/have " + getLinguisticVariable().getLabel();
    }
}
