package logic.summaries;

import data.XmlLoader;
import logic.summaries.Quantifier;
import logic.summaries.LinguisticVariable;
import logic.qualitymeasures.QualityMeasure;
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

    private void loadConfigFromXml(String path) {
        XmlLoader xmlLoader = new XmlLoader();
        linguisticVariables = xmlLoader.getLinguisticVariables(path);
        quantifiers = xmlLoader.getQuantifiers(path);
    }

    private List<Summary> getYagerSummaries(LinguisticVariable linguisticVariable) {
        List<Summary> result = new ArrayList<>();
        for (Quantifier quantifier : quantifiers) {
            Summarizer summarizer = new Summarizer();
            summarizer.andSummarizer(linguisticVariable);
            Summary summary = Summary.builder().quantifier(quantifier).summarizer(summarizer).build();
            result.add(summary);
        }
        return result;
    }

    private List<Summary> getCompoundSummaries(LinguisticVariable firstLinguisticVariable, LinguisticVariable secondLinguisticVariable) {
        List<Summary> result = new ArrayList<>();
        for (Quantifier quantifier : quantifiers) {
            Summarizer tCompoundSummarizer = new Summarizer();
            tCompoundSummarizer.andSummarizer(firstLinguisticVariable).andSummarizer(secondLinguisticVariable);
            Summarizer sCompoundSummarizer = new Summarizer();
            sCompoundSummarizer.orSummarizer(firstLinguisticVariable).orSummarizer(secondLinguisticVariable);
            Summary tSummary = Summary.builder().quantifier(quantifier).summarizer(tCompoundSummarizer).build();
            Summary sSummary = Summary.builder().quantifier(quantifier).summarizer(sCompoundSummarizer).build();
            result.add(tSummary);
            result.add(sSummary);
        }
        return result;
    }

    private List<Summary> getYagerAndCompoundSummaries(LinguisticVariable firstLinguisticVariable, LinguisticVariable secondLinguisticVariable) {
        List<Summary> result;
        result = getYagerSummaries(firstLinguisticVariable);
        result.addAll(getYagerSummaries(secondLinguisticVariable));
        result.addAll(getCompoundSummaries(firstLinguisticVariable, secondLinguisticVariable));
        return result;
    }

    public void addYagerSummaries(LinguisticVariable linguisticVariable) {
        summaries.addAll(getYagerSummaries(linguisticVariable));
    }

    public void addCompoundSummaries(LinguisticVariable firstLinguisticVariable, LinguisticVariable secondLinguisticVariable) {
        summaries.addAll(getCompoundSummaries(firstLinguisticVariable, secondLinguisticVariable));
    }

    public void addSummariesWithQualifier(LinguisticVariable firstSummarizer, LinguisticVariable secondSummarizer,
            LinguisticVariable firstQualifier, LinguisticVariable secondQualifier) {

        //first qualifier
        List<Summary> newSummaries = getYagerAndCompoundSummaries(firstSummarizer, secondSummarizer);
        if(firstQualifier!=null) {
            for (Summary summary : newSummaries) {
                summary.getSummarizer().andQualifier(firstQualifier);
            }
            summaries.addAll(newSummaries);
        }

        //second qualifier
        if(secondQualifier!=null) {
            newSummaries = getYagerAndCompoundSummaries(firstSummarizer, secondSummarizer);
            for(Summary summary : newSummaries) {
                summary.getSummarizer().andQualifier(secondQualifier);
            }
            summaries.addAll(newSummaries);
        }

        //both qualifiers
        if(firstQualifier!=null && secondQualifier!=null) {
            newSummaries = getYagerAndCompoundSummaries(firstSummarizer, secondSummarizer);
            for(Summary summary : newSummaries) {
                summary.getSummarizer().andQualifier(firstQualifier).andQualifier(secondQualifier);
            }
            summaries.addAll(newSummaries);

            newSummaries = getYagerAndCompoundSummaries(firstSummarizer, secondSummarizer);
            for(Summary summary : newSummaries) {
                summary.getSummarizer().orQualifier(firstQualifier).orQualifier(secondQualifier);
            }
            summaries.addAll(newSummaries);
        }
    }

    public List<LinguisticVariable> getLinguisticVariablesByAttributeName(String attributeName) {
        List<LinguisticVariable> result = new ArrayList<>();
        for(LinguisticVariable linguisticVariable : linguisticVariables) {
            if(attributeName.equals(linguisticVariable.getAttributeName())) {
                result.add(linguisticVariable);
            }
        }
        return result;
    }

    public void sortSummariesByQuality() {
        for (Summary summary : summaries) {
            for (QualityMeasure qualityMeasure : qualityMeasures) {
                double quality = qualityMeasure.getQuality(summary);
                summary.setQualities(summary.getQualities() + qualityMeasure.toString() + " = " + quality + " ");
                summary.setQuality(summary.getQuality() + quality);
            }
            summary.setQuality(summary.getQuality()/qualityMeasures.size());

        }
        summaries.sort(Comparator.comparing(Summary::getQuality));
        Collections.reverse(summaries);
    }

    public void clearSummaries() {
        summaries.clear();
    }
}
