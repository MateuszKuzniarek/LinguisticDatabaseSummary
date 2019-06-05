package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.SummaryGenerator;
import logic.qualitymeasures.DegreeOfImprecision;
import logic.qualitymeasures.DegreeOfTruth;
import logic.summaries.Summary;


public class MainApp extends Application {



    public static void main(String[] args) throws Exception {
        SummaryGenerator summaryGenerator = new SummaryGenerator();
        summaryGenerator.addYagerSummaries("weight");
        summaryGenerator.addYagerSummaries("height");
        summaryGenerator.addCompoundSummaries("weight", "height");
        //summaryGenerator.addSummariesWithQualifier("weight", "height");
        summaryGenerator.getQualityMeasures().add(new DegreeOfImprecision());
        summaryGenerator.sortSummariesByQuality();
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
