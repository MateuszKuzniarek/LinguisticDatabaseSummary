package logic.qualitymeasures;

import logic.summaries.Summary;

public interface QualityMeasure {
    double getQuality(Summary summary);
}
