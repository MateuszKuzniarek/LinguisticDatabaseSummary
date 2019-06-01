package logic.qualitymeasures;

import logic.summaries.Summary;

public abstract class QualityMeasure {
    public abstract double getQuality(Summary summary);
}
