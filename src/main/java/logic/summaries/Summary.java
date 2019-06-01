package logic.summaries;

import logic.membership.Quantifier;
import logic.membership.Summarizer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Summary {
    private Summarizer summarizer;
    private Quantifier quantifier;
    private Double quality = 0d;

    public abstract String getSummary();
}
