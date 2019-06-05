package logic.qualitymeasures;

import logic.membership.MembershipFunction;
import logic.summaries.Summary;

public class DegreeOfQuantifierCardinality implements QualityMeasure{

    @Override
    public double getQuality(Summary summary) {
        MembershipFunction summaryMembershipFunction = summary.getQuantifier().getMembershipFunction();
        return 1.0 - summaryMembershipFunction.calculateCardinality()/summaryMembershipFunction.getRealmCardinality();
    }
}
