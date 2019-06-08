package logic.summaries;

import logic.norms.Norm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Operation {

    Norm norm;
    LinguisticVariable linguisticVariable;
}
