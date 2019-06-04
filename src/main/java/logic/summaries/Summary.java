package logic.summaries;

import logic.membership.Quantifier;
import logic.membership.LinguisticVariable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Summary {
    private Summarizer summarizer;
    private Quantifier quantifier;
    private Double quality = 0d;

    public String getSummary() {
        return getQuantifier().getLabel() + summarizer.getSummaryFragment() + " (" + getQuality() + ")";
    }
}
