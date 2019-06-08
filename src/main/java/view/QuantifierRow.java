package view;

import logic.summaries.Quantifier;
import lombok.Data;

@Data
public class QuantifierRow {
    private String name;
    private String isRelative;
    private String functionType;
    private String definition;
    private Quantifier quantifier;

    public QuantifierRow(Quantifier quantifier) {
        this.name = quantifier.getLabel();
        this.isRelative = quantifier.isRelative() ? "tak" : "nie";
        this.functionType = quantifier.getFuzzySet().getFunctionType();
        this.definition = quantifier.getFuzzySet().getDefinition();
    }
}
