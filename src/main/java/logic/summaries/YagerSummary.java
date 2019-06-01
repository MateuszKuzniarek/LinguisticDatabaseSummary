package logic.summaries;


public class YagerSummary extends Summary {

    @Override
    public String getSummary() {
        return " players are/have " + getSummarizer().getLabel() + " (" + getQuality() + ")";
    }
}
