package logic.qualitymeasures;

import data.DatabaseRepository;
import data.PlayerInfo;
import logic.summaries.Summarizer;
import logic.summaries.Summary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DegreeOfCovering implements QualityMeasure {

    private DatabaseRepository databaseRepository = new DatabaseRepository();

    //remembers sums to not calculate them for every quantifier
    private Map<Summarizer, Double> summarizersQualitites = new HashMap<>();

    @Override
    public double getQuality(Summary summary) {
        double sumOfT = 0;
        double sumOfH = 0;
        if(summarizersQualitites.containsKey(summary.getSummarizer())) {
            return summarizersQualitites.get(summary.getSummarizer());
        } else {
            List<PlayerInfo> playerInfoList = databaseRepository.getAllPlayersInfo();
            for (PlayerInfo playerInfo : playerInfoList) {
                if (summary.getSummarizer().getSummarizerValue(playerInfo) > 0.0) {
                    sumOfT++;
                }
            }

            if (summary.getSummarizer().getQualifierOperations().isEmpty()) {
                sumOfH = databaseRepository.getPlayerCount();
            } else {
                for (PlayerInfo playerInfo : playerInfoList) {
                    if (summary.getSummarizer().getQualifierValue(playerInfo) > 0.0) {
                        sumOfH++;
                    }
                }
            }
            summarizersQualitites.put(summary.getSummarizer(), sumOfT/sumOfH);
        }
        return sumOfT / sumOfH;
    }
}
