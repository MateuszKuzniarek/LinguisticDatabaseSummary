package view;

import data.DatabaseRepository;
import data.PlayerInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.SummaryGenerator;
import logic.membership.FuzzySet;
import logic.membership.TrapezoidFuzzySet;
import logic.norms.MinTNorm;
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
