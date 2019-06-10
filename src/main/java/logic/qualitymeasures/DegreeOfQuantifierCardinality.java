package logic.qualitymeasures;

import logic.membership.FuzzySet;
import logic.summaries.Summary;

public class DegreeOfQuantifierCardinality implements QualityMeasure{

    @Override
    public double getQuality(Summary summary) {
        FuzzySet summaryFuzzySet = summary.getQuantifier().getFuzzySet();
        return 1.0 - summaryFuzzySet.calculateCardinality()/ summaryFuzzySet.getRealmCardinality();
    }

    @Override
    public String toString() {
        return "T7";
    }
}
