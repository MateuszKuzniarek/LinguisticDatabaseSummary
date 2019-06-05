package logic.qualitymeasures;

import data.DatabaseRepository;
import logic.summaries.Summarizer;
import logic.summaries.Summary;

import java.util.HashMap;
import java.util.Map;

public class DegreeOfCovering implements QualityMeasure {

    private DatabaseRepository databaseRepository = new DatabaseRepository();

    //remembers sums to not calculate them for every quantifier
    private Map<Summarizer, Double> summarizersQualitites = new HashMap<>();

    @Override
    public double getQuality(Summary summary) {
        double numberOfRecords = databaseRepository.getPlayerCount();
        double sumOfT = 0;
        double sumOfH = 0;
        if(summarizersQualitites.containsKey(summary.getSummarizer())) {
            return summarizersQualitites.get(summary.getSummarizer());
        } else {
            for (int i = 1; i <= numberOfRecords; i++) {
                if (summary.getSummarizer().getSummarizerValue(i) > 0.0) {
                    sumOfT++;
                }
            }

            if (summary.getSummarizer().getQualifierOperations().isEmpty()) {
                sumOfH = numberOfRecords;
            } else {
                for (int i = 1; i <= numberOfRecords; i++) {
                    if (summary.getSummarizer().getQualifierValue(i) > 0.0) {
                        sumOfT++;
                    }
                }
            }
            summarizersQualitites.put(summary.getSummarizer(), sumOfT/sumOfH);
        }
        return sumOfT / sumOfH;
    }
}
