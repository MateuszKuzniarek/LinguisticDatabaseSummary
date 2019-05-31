package logic.membership;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Summarizer {

    private String label;
    private String attributeName;
    private MembershipFunction membershipFunction;
}
