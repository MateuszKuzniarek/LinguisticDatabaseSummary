package logic.qualitymeasures;

import logic.summaries.Operation;
import logic.summaries.Summary;

public class DegreeOfSummarizerCardinality implements QualityMeasure {

    @Override
    public double getQuality(Summary summary) {
        double quality = 1;
        double numberOfSummarizers = summary.getSummarizer().getSummarizerOperations().size();
        for (Operation summarizer : summary.getSummarizer().getSummarizerOperations()) {
            double cardinality = summarizer.getLinguisticVariable().getMembershipFunction().calculateCardinality();
            double realCardinality = summarizer.getLinguisticVariable().getMembershipFunction().getRealmCardinality();
            quality *= cardinality/realCardinality;
        }
        return 1.0 - Math.pow(quality, 1.0 / numberOfSummarizers);
    }
}
