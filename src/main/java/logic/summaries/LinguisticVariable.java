package logic.summaries;

import logic.membership.FuzzySet;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinguisticVariable {

    private String label;
    private String attributeName;
    private FuzzySet fuzzySet;

    @Override
    public String toString() {
        return label;
    }
}
