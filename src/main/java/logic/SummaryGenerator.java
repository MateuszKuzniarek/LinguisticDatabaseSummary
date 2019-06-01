package logic;

import data.XmlLoader;
import logic.membership.Quantifier;
import logic.membership.Summarizer;
import logic.qualitymeasures.QualityMeasure;
import logic.summaries.Summary;
import logic.summaries.YagerSummary;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
public class SummaryGenerator {
    private List<Summary> summaries = new ArrayList<>();
    private List<Summarizer> summarizers;
    private List<Quantifier> quantifiers;
    private List<QualityMeasure> qualityMeasures = new ArrayList<>();
    private static final String defaultXmlPath = "src/main/resources/config.xml";

    public SummaryGenerator() {
        loadConfigFromXml(defaultXmlPath);
    }

    public void loadConfigFromXml(String path) {
        XmlLoader xmlLoader = new XmlLoader();
        summarizers = xmlLoader.getSummarizers(path);
        quantifiers = xmlLoader.getQuantifiers(path);
    }

    public void addYagerSummaries(String attributeName) {
        for (Summarizer summarizer : summarizers) {
            if (attributeName.equals(summarizer.getAttributeName())) {
                for (Quantifier quantifier : quantifiers) {
                    YagerSummary yagerSummary = new YagerSummary();
                    yagerSummary.setSummarizer(summarizer);
                    yagerSummary.setQuantifier(quantifier);
                    summaries.add(yagerSummary);
                }
            }
        }
    }

    public void sortSummariesByQuality() {
        for (Summary summary : summaries) {
            for (QualityMeasure qualityMeasure : qualityMeasures) {
                summary.setQuality(summary.getQuality() + qualityMeasure.getQuality(
                        summary));
            }
        }
        summaries.sort(Comparator.comparing(Summary::getQuality));
        Collections.reverse(summaries);
    }
}
