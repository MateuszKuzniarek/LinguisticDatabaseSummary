package view;

import logic.summaries.LinguisticVariable;
import lombok.Data;

@Data
class SummarizerRow {
    private String name;
    private String attribute;
    private String functionType;
    private String definition;
    private LinguisticVariable linguisticVariable;

    SummarizerRow(LinguisticVariable linguisticVariable) {
        this.name = linguisticVariable.getLabel();
        this.attribute = linguisticVariable.getAttributeName();
        if("trapezoid".equals(linguisticVariable.getFuzzySet().getFunctionType())) {
            this.functionType = "Trapezoidalna";
        } else if("triangular".equals(linguisticVariable.getFuzzySet().getFunctionType())) {
            this.functionType = "Trójkątna";
        } else if("discrete".equals(linguisticVariable.getFuzzySet().getFunctionType())) {
            this.functionType = "Dyskretna";
        }
        this.definition = linguisticVariable.getFuzzySet().getDefinition();
        this.linguisticVariable = linguisticVariable;
    }
}
