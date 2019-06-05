package logic.qualitymeasures;

import logic.summaries.Summary;

public class LengthOfQualifier implements QualityMeasure {
    @Override
    public double getQuality(Summary summary) {

        long numberOfQualifiers = summary.getSummarizer().getQualifierOperations().size();
        //because summary without qualifier can be presented as summary with qualifier returning 1 for every record
        if(numberOfQualifiers==0) numberOfQualifiers = 1;
        return 2 * Math.pow(0.5, numberOfQualifiers);
    }
}
