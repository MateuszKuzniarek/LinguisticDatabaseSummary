package logic.qualitymeasures;

import logic.summaries.Summary;

public class LengthOfSummary implements QualityMeasure {

    @Override
    public double getQuality(Summary summary) {
        int numberOfSummarizers = summary.getSummarizer().getSummarizerOperations().size();
        return 2 * Math.pow(0.5, numberOfSummarizers);
    }

    @Override
    public String toString() {
        return "T5";
    }
}
