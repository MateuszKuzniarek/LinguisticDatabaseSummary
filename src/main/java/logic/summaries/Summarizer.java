package logic.summaries;

import logic.membership.LinguisticVariable;
import lombok.Data;

@Data
public abstract class Summarizer {
    private LinguisticVariable linguisticVariable;
    public abstract double getValue(int id);
    public abstract String getSummaryFragment();
}
