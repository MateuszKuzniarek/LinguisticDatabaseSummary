package logic.qualitymeasures;

import data.PlayerInfo;
import logic.summaries.Summarizer;
import logic.summaries.Summary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DegreeOfTruth implements QualityMeasure {

    private List<PlayerInfo> playerInfoList;
    private int numberOfRecords;

    public DegreeOfTruth(List<PlayerInfo> playerInfoList, int numberOfRecords) {
        this.playerInfoList = playerInfoList;
        this.numberOfRecords = numberOfRecords;
    }

    //remembers sums to not calculate them for every quantifier
    private Map<Summarizer, Double> summarizersSumOfMembershipValues = new HashMap<>();
    private Map<Summarizer, Double> qualifiersSumOfQuaValues = new HashMap<>();

    private double getSumOfSummarizers(Summary summary) {
        double sumOfMembershipValues = 0;
        if(summarizersSumOfMembershipValues.containsKey(summary.getSummarizer())) {
            sumOfMembershipValues = summarizersSumOfMembershipValues.get(summary.getSummarizer());
        }
        else {
            for (PlayerInfo playerInfo : playerInfoList) {
                sumOfMembershipValues += summary.getSummarizer().getSummarizerValue(playerInfo);
            }
            summarizersSumOfMembershipValues.put(summary.getSummarizer(), sumOfMembershipValues);
        }
        return sumOfMembershipValues;
    }

    private double getQualityForAbsoluteQuantifier(Summary summary) {
        return summary.getQuantifier().getValue(getSumOfSummarizers(summary));
    }

    private double getQualityForRelativeQuantifier(Summary summary) {
        double sumOfSummarizerValues = getSumOfSummarizers(summary);
        if(summary.getSummarizer().getQualifierOperations().isEmpty()) {
            return summary.getQuantifier().getValue(sumOfSummarizerValues/ numberOfRecords);
        }
        double sumOfQualifierValues = 0;
        if(qualifiersSumOfQuaValues.containsKey(summary.getQuantifier())) {
            sumOfQualifierValues = qualifiersSumOfQuaValues.get(summary.getQuantifier());
        }
        else {
            for (PlayerInfo playerInfo : playerInfoList) {
                sumOfQualifierValues += summary.getSummarizer().getQualifierValue(playerInfo);
            }
            qualifiersSumOfQuaValues.put(summary.getSummarizer(), sumOfQualifierValues);
        }

        return summary.getQuantifier().getValue(sumOfSummarizerValues/sumOfQualifierValues);
    }

    @Override
    public double getQuality(Summary summary) {
        if(summary.getQuantifier().isRelative()) {
            return getQualityForRelativeQuantifier(summary);
        }
        return getQualityForAbsoluteQuantifier(summary);
    }
}
