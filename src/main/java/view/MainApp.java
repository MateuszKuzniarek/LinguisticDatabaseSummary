package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.SummaryGenerator;
import logic.qualitymeasures.DegreeOfAppropriateness;
import logic.qualitymeasures.DegreeOfCovering;
import logic.qualitymeasures.DegreeOfImprecision;
import logic.qualitymeasures.DegreeOfQualifierCardinality;
import logic.qualitymeasures.DegreeOfQualifierImprecision;
import logic.qualitymeasures.DegreeOfQuantifierCardinality;
import logic.qualitymeasures.DegreeOfQuantifierImprecision;
import logic.qualitymeasures.DegreeOfSummarizerCardinality;
import logic.qualitymeasures.DegreeOfTruth;
import logic.qualitymeasures.LengthOfQualifier;
import logic.qualitymeasures.LengthOfSummary;
import logic.summaries.Summary;


public class MainApp extends Application {



    public static void main(String[] args) throws Exception {
        SummaryGenerator summaryGenerator = new SummaryGenerator();
        summaryGenerator.addYagerSummaries("weight");
        summaryGenerator.addYagerSummaries("height");
        summaryGenerator.addCompoundSummaries("weight", "height");
        summaryGenerator.addSummariesWithQualifier("weight", "height");
        summaryGenerator.getQualityMeasures().add(new DegreeOfTruth());
        summaryGenerator.getQualityMeasures().add(new DegreeOfImprecision());
        summaryGenerator.getQualityMeasures().add(new DegreeOfCovering());
        summaryGenerator.getQualityMeasures().add(new DegreeOfAppropriateness());
        summaryGenerator.getQualityMeasures().add(new LengthOfSummary());
        summaryGenerator.getQualityMeasures().add(new DegreeOfQuantifierImprecision());
        summaryGenerator.getQualityMeasures().add(new DegreeOfQuantifierCardinality());
        summaryGenerator.getQualityMeasures().add(new DegreeOfSummarizerCardinality());
        summaryGenerator.getQualityMeasures().add(new DegreeOfQualifierImprecision());
        summaryGenerator.getQualityMeasures().add(new DegreeOfQualifierCardinality());
        summaryGenerator.getQualityMeasures().add(new LengthOfQualifier());
        summaryGenerator.sortSummariesByQuality();
        System.out.println("--------------------------------------\n\n\n\n\n\n");
        for(Summary summary : summaryGenerator.getSummaries()) {
            System.out.println(summary.getSummary());
        }


        launch(args);

    }

    public void start(Stage stage) throws Exception {


        String fxmlFile = "/fxml/scene.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));


        Scene scene = new Scene(rootNode, 500, 700);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Linguistic Database Summary");
        stage.setScene(scene);
        stage.show();
    }
}
