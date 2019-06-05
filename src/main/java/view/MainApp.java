package view;

import data.DatabaseRepository;
import data.PlayerInfo;
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

import java.util.List;


public class MainApp extends Application {



    public static void main(String[] args) throws Exception {
        SummaryGenerator summaryGenerator = new SummaryGenerator();
        summaryGenerator.addYagerSummaries("weight");
        summaryGenerator.addYagerSummaries("height");
        summaryGenerator.addCompoundSummaries("weight", "height");
        summaryGenerator.addSummariesWithQualifier("weight", "height");

        DatabaseRepository databaseRepository = new DatabaseRepository();
        List<PlayerInfo> playerInfoList = databaseRepository.getAllPlayersInfo();
        int numberOfRecords = databaseRepository.getPlayerCount();

        summaryGenerator.getQualityMeasures().add(new DegreeOfTruth(playerInfoList, numberOfRecords));
        summaryGenerator.getQualityMeasures().add(new DegreeOfImprecision());
        summaryGenerator.getQualityMeasures().add(new DegreeOfCovering(playerInfoList, numberOfRecords));
        summaryGenerator.getQualityMeasures().add(new DegreeOfAppropriateness(playerInfoList, numberOfRecords));
        summaryGenerator.getQualityMeasures().add(new LengthOfSummary());
        summaryGenerator.getQualityMeasures().add(new DegreeOfQuantifierImprecision());
        summaryGenerator.getQualityMeasures().add(new DegreeOfQuantifierCardinality());
        summaryGenerator.getQualityMeasures().add(new DegreeOfSummarizerCardinality());
        summaryGenerator.getQualityMeasures().add(new DegreeOfQualifierImprecision());
        summaryGenerator.getQualityMeasures().add(new DegreeOfQualifierCardinality());
        summaryGenerator.getQualityMeasures().add(new LengthOfQualifier());
        summaryGenerator.sortSummariesByQuality();
        for(Summary summary : summaryGenerator.getSummaries()) {
            System.out.println(summary.getSummary());
        }


        launch(args);

    }

    public void start(Stage stage) throws Exception {


        String fxmlFile = "/fxml/scene.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));

        Scene scene = new Scene(rootNode, 500, 700);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Linguistic Database Summary");
        stage.setScene(scene);
        stage.show();
    }
}
