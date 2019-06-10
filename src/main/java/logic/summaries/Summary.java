package logic.summaries;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.text.DecimalFormat;

@Getter
@Setter
@ToString
@Builder
public class Summary {
    private Summarizer summarizer;
    private Quantifier quantifier;
    @Builder.Default
    private Double quality = 0d;
    @Builder.Default
    private String qualities = "";

    public String getSummary() {
        DecimalFormat df = new DecimalFormat("0.00");
        return getQuantifier().getLabel() + summarizer.getSummaryFragment() + " (" + df.format(getQuality()) + ")";
    }
}
