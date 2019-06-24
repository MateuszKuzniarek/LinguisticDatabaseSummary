package logic.qualitymeasures;

import data.PlayerInfo;
import logic.summaries.LinguisticVariable;
import logic.summaries.Operation;
import logic.summaries.Summarizer;
import logic.summaries.Summary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DegreeOfAppropriateness implements QualityMeasure{

    private List<PlayerInfo> playerInfoList;
    private int numberOfRecords;

    public DegreeOfAppropriateness(List<PlayerInfo> playerInfoList, int numberOfRecords) {
        this.playerInfoList = playerInfoList;
        this.numberOfRecords = numberOfRecords;
    }

    //remembers sums to not calculate them for every quantifier
    private Map<Summarizer, Double> summarizersQualities = new HashMap<>();

    @Override
    public double getQuality(Summary summary) {
        double result;
        if(summarizersQualities.containsKey(summary.getSummarizer())) {
            return summarizersQualities.get(summary.getSummarizer());
        } else {
            double product = 1;
            for (Operation operation : summary.getSummarizer().getSummarizerOperations()) {
                double sumOfG = 0;
                for (PlayerInfo playerInfo : playerInfoList) {

                    LinguisticVariable linguisticVariable = operation.getLinguisticVariable();
                    double attributeValue = playerInfo.getAttributeValue(linguisticVariable.getAttributeName());
                    double summarizerValue = linguisticVariable.getFuzzySet().calculateMembership(attributeValue);

                    if (summarizerValue > 0.0) {
                        sumOfG++;
                    }
                }
                product *= (sumOfG / numberOfRecords);
            }
            DegreeOfCovering degreeOfCovering = new DegreeOfCovering(playerInfoList, numberOfRecords);
            result = Math.abs(product-degreeOfCovering.getQuality(summary));
            summarizersQualities.put(summary.getSummarizer(), result);
        }
        return result;
    }

    @Override
    public String toString() {
        return "T4";
    }
}
