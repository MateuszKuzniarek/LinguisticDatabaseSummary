package logic;

import data.XmlLoader;
import logic.membership.Quantifier;
import logic.membership.LinguisticVariable;
import logic.qualitymeasures.QualityMeasure;
import logic.summaries.CompoundSummarizer;
import logic.summaries.SimpleSummarizer;
import logic.summaries.Summarizer;
import logic.summaries.Summary;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
public class SummaryGenerator {
    private List<Summary> summaries = new ArrayList<>();
    private List<LinguisticVariable> linguisticVariables;
    private List<Quantifier> quantifiers;
    private List<QualityMeasure> qualityMeasures = new ArrayList<>();
    private static final String defaultXmlPath = "src/main/resources/config.xml";

    public SummaryGenerator() {
        loadConfigFromXml(defaultXmlPath);
    }

    public void loadConfigFromXml(String path) {
        XmlLoader xmlLoader = new XmlLoader();
        linguisticVariables = xmlLoader.getLinguisticVariables(path);
        quantifiers = xmlLoader.getQuantifiers(path);
    }

    public void addYagerSummaries(String attributeName) {
        for (LinguisticVariable linguisticVariable : linguisticVariables) {
            if (attributeName.equals(linguisticVariable.getAttributeName())) {
                SimpleSummarizer summarizer = new SimpleSummarizer();
                summarizer.setLinguisticVariable(linguisticVariable);
                for (Quantifier quantifier : quantifiers) {
                    Summary summary = new Summary();
                    summary.setSummarizer(summarizer);
                    summary.setQuantifier(quantifier);
                    summaries.add(summary);
                }
            }
        }
    }

    public void addCompundSummaries(String firstAttributeName, String secondAttributeName) {
        for (LinguisticVariable firstLinguisticVariable : linguisticVariables) {
            if (firstAttributeName.equals(firstLinguisticVariable.getAttributeName())) {
                for(LinguisticVariable secondLinguisticVariable : linguisticVariables) {
                    if(secondAttributeName.equals((secondLinguisticVariable.getAttributeName()))) {
                        CompoundSummarizer tCompoundSummarizer = new CompoundSummarizer();
                        tCompoundSummarizer.and(firstLinguisticVariable).and(secondLinguisticVariable);
                        CompoundSummarizer sCompoundSummarizer = new CompoundSummarizer();
                        sCompoundSummarizer.or(firstLinguisticVariable).or(secondLinguisticVariable);
                        for (Quantifier quantifier : quantifiers) {
                            Summary tSummary = new Summary();
                            Summary sSummary = new Summary();
                            tSummary.setSummarizer(tCompoundSummarizer);
                            sSummary.setSummarizer(sCompoundSummarizer);
                            tSummary.setQuantifier(quantifier);
                            sSummary.setQuantifier(quantifier);
                            summaries.add(tSummary);
                            summaries.add(sSummary);
                        }
                    }
                }
            }
        }
    }

    public void sortSummariesByQuality() {
        for (Summary summary : summaries) {
            for (QualityMeasure qualityMeasure : qualityMeasures) {
                summary.setQuality(summary.getQuality() + qualityMeasure.getQuality(summary));
            }
        }
        summaries.sort(Comparator.comparing(Summary::getQuality));
        Collections.reverse(summaries);
    }
}
