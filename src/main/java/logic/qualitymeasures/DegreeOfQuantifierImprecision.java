package logic.qualitymeasures;

import logic.summaries.Summary;

public class DegreeOfQuantifierImprecision implements QualityMeasure{
    @Override
    public double getQuality(Summary summary) {
        return 1.0 - summary.getQuantifier().getFuzzySet().calculateDegreeOfFuzziness();
    }

    @Override
    public String toString() {
        return "T6";
    }
}
