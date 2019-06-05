package logic.qualitymeasures;

import logic.summaries.Operation;
import logic.summaries.Summary;

public class DegreeOfImprecision implements QualityMeasure {

    @Override
    public double getQuality(Summary summary) {
        long numberOfSummarizers = 0;
        double quality = 1;
        for (Operation summarizer : summary.getSummarizer().getSummarizerOperations()) {
            numberOfSummarizers++;
            quality *= summarizer.getLinguisticVariable().getMembershipFunction().calculateDegreeOfFuzziness();
        }
        return 1.0 - Math.pow(quality, 1.0 / numberOfSummarizers);
    }
}
