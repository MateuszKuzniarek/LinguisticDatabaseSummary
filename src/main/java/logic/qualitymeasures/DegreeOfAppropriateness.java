package logic.qualitymeasures;

import data.DatabaseRepository;
import logic.summaries.Operation;
import logic.summaries.Summarizer;
import logic.summaries.Summary;

import java.util.HashMap;
import java.util.Map;

public class DegreeOfAppropriateness implements QualityMeasure{

    DatabaseRepository databaseRepository = new DatabaseRepository();

    //remembers sums to not calculate them for every quantifier
    private Map<Summarizer, Double> summarizersQualitites = new HashMap<>();

    @Override
    public double getQuality(Summary summary) {
        double result = 0;
        if(summarizersQualitites.containsKey(summary.getSummarizer())) {
            return summarizersQualitites.get(summary.getSummarizer());
        } else {
            int numberOfRecords = databaseRepository.getPlayerCount();
            double product = 1;
            for (Operation operation : summary.getSummarizer().getSummarizerOperations()) {
                double sumOfG = 0;
                for (int i = 1; i < numberOfRecords; i++) {
                    double attributeValue = databaseRepository.getPlayerAttribute(i,
                            operation.getLinguisticVariable().getAttributeName());

                    double summarizerValue = operation.getLinguisticVariable()
                            .getMembershipFunction().calculateMembership(attributeValue);
                    if (summarizerValue > 0.0) {
                        sumOfG++;
                    }
                }
                product *= (sumOfG / numberOfRecords);
            }
            DegreeOfCovering degreeOfCovering = new DegreeOfCovering();
            result = Math.abs(product-degreeOfCovering.getQuality(summary));
            summarizersQualitites.put(summary.getSummarizer(), result);
        }
        return result;
    }
}
