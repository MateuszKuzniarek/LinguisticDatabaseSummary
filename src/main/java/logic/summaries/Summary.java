package logic.summaries;

import jdk.nashorn.internal.objects.annotations.Constructor;
import logic.membership.Quantifier;
import logic.membership.LinguisticVariable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Summary {
    private Summarizer summarizer;
    private Quantifier quantifier;
    @Builder.Default
    private Double quality = 0d;

    public String getSummary() {
        return getQuantifier().getLabel() + summarizer.getSummaryFragment() + " (" + getQuality() + ")";
    }
}
