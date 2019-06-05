package logic.membership;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LinguisticVariable {

    private String label;
    private String attributeName;
    private FuzzySet fuzzySet;
}
