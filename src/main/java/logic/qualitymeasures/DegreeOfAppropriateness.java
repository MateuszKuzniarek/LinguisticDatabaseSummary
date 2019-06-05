package logic.qualitymeasures;

import data.DatabaseRepository;
import data.PlayerInfo;
import logic.membership.LinguisticVariable;
import logic.summaries.Operation;
import logic.summaries.Summarizer;
import logic.summaries.Summary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DegreeOfAppropriateness implements QualityMeasure{

    DatabaseRepository databaseRepository = new DatabaseRepository();

    //remembers sums to not calculate them for every quantifier
    private Map<Summarizer, Double> summarizersQualities = new HashMap<>();

    @Override
    public double getQuality(Summary summary) {
        double result = 0;
        if(summarizersQualities.containsKey(summary.getSummarizer())) {
            return summarizersQualities.get(summary.getSummarizer());
        } else {
            int numberOfRecords = databaseRepository.getPlayerCount();
            double product = 1;
            List<PlayerInfo> playerInfoList = databaseRepository.getAllPlayersInfo();
            for (Operation operation : summary.getSummarizer().getSummarizerOperations()) {
                double sumOfG = 0;
                for (PlayerInfo playerInfo : playerInfoList) {

                    LinguisticVariable linguisticVariable = operation.getLinguisticVariable();
                    double attributeValue = playerInfo.getAttributeValue(linguisticVariable.getAttributeName());
                    double summarizerValue = linguisticVariable.getMembershipFunction().calculateMembership(attributeValue);

                    if (summarizerValue > 0.0) {
                        sumOfG++;
                    }
                }
                product *= (sumOfG / numberOfRecords);
            }
            DegreeOfCovering degreeOfCovering = new DegreeOfCovering();
            result = Math.abs(product-degreeOfCovering.getQuality(summary));
            summarizersQualities.put(summary.getSummarizer(), result);
        }
        return result;
    }
}
