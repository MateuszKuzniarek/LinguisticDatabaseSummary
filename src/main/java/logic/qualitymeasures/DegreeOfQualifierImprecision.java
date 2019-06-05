package logic.qualitymeasures;

import logic.summaries.Operation;
import logic.summaries.Summary;

public class DegreeOfQualifierImprecision implements QualityMeasure {

    @Override
    public double getQuality(Summary summary) {
        long numberOfQualifiers = summary.getSummarizer().getQualifierOperations().size();

        //because summary without qualifier can be presented as summary with qualifier returning 1 for every record
        if(numberOfQualifiers==0) numberOfQualifiers =1;
        double quality = 1;
        for (Operation qualifier : summary.getSummarizer().getQualifierOperations()) {
            quality *= qualifier.getLinguisticVariable().getFuzzySet().calculateDegreeOfFuzziness();
        }
        return 1.0 - Math.pow(quality, 1.0 / numberOfQualifiers);
    }
}
