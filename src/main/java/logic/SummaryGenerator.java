package logic;

import data.XmlLoader;
import logic.membership.Quantifier;
import logic.membership.LinguisticVariable;
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

    public void addYagerSummaries(String attributeName) {
        for (LinguisticVariable linguisticVariable : linguisticVariables) {
            if (attributeName.equals(linguisticVariable.getAttributeName())) {
                Summarizer summarizer = new Summarizer();
                summarizer.andSummarizer(linguisticVariable);
                for (Quantifier quantifier : quantifiers) {
                    Summary summary = Summary.builder().quantifier(quantifier).summarizer(summarizer).build();
                    summaries.add(summary);
                }
            }
        }
    }

    public void addCompoundSummaries(String firstAttributeName, String secondAttributeName) {
        for (LinguisticVariable firstLinguisticVariable : linguisticVariables) {
            if (firstAttributeName.equals(firstLinguisticVariable.getAttributeName())) {
                for(LinguisticVariable secondLinguisticVariable : linguisticVariables) {
                    if(secondAttributeName.equals((secondLinguisticVariable.getAttributeName()))) {
                        Summarizer tCompoundSummarizer = new Summarizer();
                        tCompoundSummarizer.andSummarizer(firstLinguisticVariable).andSummarizer(secondLinguisticVariable);
                        for (Quantifier quantifier : quantifiers) {
                            Summary summary = Summary.builder().quantifier(quantifier).summarizer(tCompoundSummarizer).build();
                            summaries.add(summary);
                        }
                    }
                }
            }
        }
    }

    /*
    //todo this function generated more but summaries didnt make any sense
    public void addSummariesWithQualifier(String firstAttributeName, String secondAttributeName) {
        List<LinguisticVariable> firstAttributeLinguisticVariables = new ArrayList<>();
        List<LinguisticVariable> secondAttributeLinguisticVariables = new ArrayList<>();
        for(LinguisticVariable linguisticVariable : linguisticVariables) {
            if(firstAttributeName.equals(linguisticVariable.getAttributeName())) {
                firstAttributeLinguisticVariables.add(linguisticVariable);
            } else if(secondAttributeName.equals(linguisticVariable.getAttributeName())) {
                secondAttributeLinguisticVariables.add(linguisticVariable);
            }
        }

        for (LinguisticVariable summarizerFirstLinguisticVariable : firstAttributeLinguisticVariables) {
            for(LinguisticVariable summarizerSecondLinguisticVariable : secondAttributeLinguisticVariables) {
                for (LinguisticVariable qualifierFirstLinguisticVariable : firstAttributeLinguisticVariables) {
                    if(!qualifierFirstLinguisticVariable.equals(summarizerFirstLinguisticVariable)) {
                        for(LinguisticVariable qualifierSecondLinguisticVariable : secondAttributeLinguisticVariables) {
                            if(!qualifierSecondLinguisticVariable.equals(summarizerSecondLinguisticVariable)) {
                                Summarizer summarizer = new Summarizer();
                                summarizer.andSummarizer(summarizerFirstLinguisticVariable)
                                        .andSummarizer(summarizerSecondLinguisticVariable);
                                summarizer.andQualifier(qualifierFirstLinguisticVariable)
                                        .andQualifier(qualifierSecondLinguisticVariable);
                                for (Quantifier quantifier : quantifiers) {
                                    Summary summary = Summary.builder().quantifier(quantifier).summarizer(summarizer).build();
                                    summaries.add(summary);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    */

    //todo this function generates summaries with one summarizer and one qualifier - it should generate more?
    public void addSummariesWithQualifier(String firstAttributeName, String secondAttributeName) {
        for (LinguisticVariable firstLinguisticVariable : linguisticVariables) {
            if (firstAttributeName.equals(firstLinguisticVariable.getAttributeName())) {
                for(LinguisticVariable secondLinguisticVariable : linguisticVariables) {
                    if(secondAttributeName.equals((secondLinguisticVariable.getAttributeName()))) {
                        Summarizer summarizer = new Summarizer();
                        summarizer.andSummarizer(firstLinguisticVariable);
                        summarizer.andQualifier(secondLinguisticVariable);
                        for (Quantifier quantifier : quantifiers) {
                            if(quantifier.isRelative()) {
                                Summary summary = Summary.builder().quantifier(quantifier).summarizer(summarizer).build();
                                summaries.add(summary);
                            }
                        }
                    }
                }
            }
        }
    }

    public void sortSummariesByQuality() {
        for (Summary summary : summaries) {
            for (QualityMeasure qualityMeasure : qualityMeasures) {
                double quality = qualityMeasure.getQuality(summary);
                summary.setQuality(summary.getQuality() + quality);
            }
            //System.out.println(summary.getSummary());

        }
        summaries.sort(Comparator.comparing(Summary::getQuality));
        Collections.reverse(summaries);
    }
}
