package view;

import com.sun.glass.ui.View;
import data.DatabaseRepository;
import data.PlayerInfo;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.SummaryGenerator;
import logic.membership.FuzzySet;
import logic.membership.LinguisticVariable;
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
import sun.security.krb5.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    @FXML
    private ComboBox<String> summarizer1ComboBox;
    @FXML
    private ComboBox<String> summarizer2ComboBox;
    @FXML
    private ComboBox<LinguisticVariable> summarizer1FuzzySetComboBox;
    @FXML
    private ComboBox<LinguisticVariable> summarizer2FuzzySetComboBox;
    @FXML
    private ComboBox<String> qualifier1ComboBox;
    @FXML
    private ComboBox<String> qualifier2ComboBox;
    @FXML
    private ComboBox<LinguisticVariable> qualifier1FuzzySetComboBox;
    @FXML
    private ComboBox<LinguisticVariable> qualifier2FuzzySetComboBox;
    @FXML
    private CheckBox t1;
    @FXML
    private CheckBox t2;
    @FXML
    private CheckBox t3;
    @FXML
    private CheckBox t4;
    @FXML
    private CheckBox t5;
    @FXML
    private CheckBox t6;
    @FXML
    private CheckBox t7;
    @FXML
    private CheckBox t8;
    @FXML
    private CheckBox t9;
    @FXML
    private CheckBox t10;
    @FXML
    private CheckBox t11;
    @FXML
    private CheckBox yCheckBox;
    @FXML
    private CheckBox gCheckBox;
    @FXML
    private CheckBox kCheckBox;
    @FXML
    private Button summarizeButton;
    @FXML
    private TextArea textArea;

    private SummaryGenerator summaryGenerator= new SummaryGenerator();

    private static final List<String> attributes = Arrays.asList("height", "weight", "overall_rating", "potential",
            "acceleration", "sprint_speed", "agility", "shot_power", "stamina", "strength", "aggression", "age");

    @FXML
    public void summarize() {
        textArea.setText("");
        summaryGenerator.clearSummaries();
        LinguisticVariable summarizer1 = summarizer1FuzzySetComboBox.getValue();
        LinguisticVariable summarizer2 = summarizer2FuzzySetComboBox.getValue();
        LinguisticVariable qualifier1 = qualifier1FuzzySetComboBox.getValue();
        LinguisticVariable qualifier2 = qualifier2FuzzySetComboBox.getValue();

        if (yCheckBox.isSelected()) {
            summaryGenerator.addYagerSummaries(summarizer1);
            summaryGenerator.addYagerSummaries(summarizer2);
        }
        if (gCheckBox.isSelected()) {
            summaryGenerator.addCompoundSummaries(summarizer1, summarizer2);
        }
        if (kCheckBox.isSelected()) {
            summaryGenerator.addSummariesWithQualifier(summarizer1, summarizer2, qualifier1, qualifier2);
        }

        addQualityMeasures(summaryGenerator);

        summaryGenerator.sortSummariesByQuality();
        for (Summary summary : summaryGenerator.getSummaries()) {
            textArea.setText(textArea.getText() + summary.getSummary() + "\n");
        }
    }

    @FXML
    public void saveToFile() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(t1.getScene().getWindow());

        if (file != null) {
            SaveFile(textArea.getText(), file);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        summarizeButton.disableProperty().bind(
                Bindings.isNull(summarizer1FuzzySetComboBox.getSelectionModel().selectedItemProperty())
                        .or(Bindings.isNull(summarizer2FuzzySetComboBox.getSelectionModel().selectedItemProperty()))
                        .or(t1.selectedProperty().not()
                                .and(t2.selectedProperty().not())
                                .and(t3.selectedProperty().not())
                                .and(t4.selectedProperty().not())
                                .and(t5.selectedProperty().not())
                                .and(t6.selectedProperty().not())
                                .and(t7.selectedProperty().not())
                                .and(t8.selectedProperty().not())
                                .and(t9.selectedProperty().not())
                                .and(t10.selectedProperty().not())
                                .and(t11.selectedProperty().not())
                        )
                        .or(yCheckBox.selectedProperty().not()
                                .and(gCheckBox.selectedProperty().not()
                                        .and(kCheckBox.selectedProperty().not()))));

        summarizer1ComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            summarizer1FuzzySetComboBox.getItems().clear();
            if(newValue!=null) {
                summarizer1FuzzySetComboBox.getItems()
                        .addAll(summaryGenerator.getLinguisticVariablesByAttributeName(newValue));
            }
        });

        summarizer2ComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            summarizer2FuzzySetComboBox.getItems().clear();
            if(newValue!=null) {
                summarizer2FuzzySetComboBox.getItems()
                        .addAll(summaryGenerator.getLinguisticVariablesByAttributeName(newValue));
            }
        });

        qualifier1ComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            qualifier1FuzzySetComboBox.getItems().clear();
            if(newValue!=null) {
                qualifier1FuzzySetComboBox.getItems()
                        .addAll(summaryGenerator.getLinguisticVariablesByAttributeName(newValue));
            }
        });

        qualifier2ComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            qualifier2FuzzySetComboBox.getItems().clear();
            if(newValue!=null) {
                qualifier2FuzzySetComboBox.getItems()
                        .addAll(summaryGenerator.getLinguisticVariablesByAttributeName(newValue));
            }
        });

        summarizer1ComboBox.getItems().addAll(attributes);
        summarizer2ComboBox.getItems().addAll(attributes);
        qualifier1ComboBox.getItems().addAll(attributes);
        qualifier1ComboBox.getItems().add("");
        qualifier2ComboBox.getItems().addAll(attributes);
        qualifier2ComboBox.getItems().add("");
    }

    @FXML
    public void openConfiguration() {
        try {
            String fxmlFile = "/fxml/configScene.fxml";
            FXMLLoader loader = new FXMLLoader();
            Parent rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));
            ConfigViewController configViewController = loader.getController();
            configViewController.setSummaryGenerator(summaryGenerator);
            configViewController.loadListView();

            Scene scene = new Scene(rootNode, 800, 500);
            resetCombBoxes();
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(t1.getScene().getWindow());
            stage.setTitle("Configuration");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Something went wrong");

            alert.showAndWait();

        }
    }

    @FXML
    public void resetChanges() {
        summaryGenerator = new SummaryGenerator();
        qualifier1FuzzySetComboBox.getItems().clear();
        String qualifier = qualifier1ComboBox.getValue();
        if(qualifier!=null) {
            qualifier1FuzzySetComboBox.getItems().addAll(summaryGenerator.getLinguisticVariablesByAttributeName(qualifier));
        }
        qualifier2FuzzySetComboBox.getItems().clear();
        qualifier = qualifier2ComboBox.getValue();
        if(qualifier!=null) {
            qualifier2FuzzySetComboBox.getItems().addAll(summaryGenerator.getLinguisticVariablesByAttributeName(qualifier));
        }
        summarizer1FuzzySetComboBox.getItems().clear();
        String summarizer = summarizer1ComboBox.getValue();
        if(summarizer!=null) {
            summarizer1FuzzySetComboBox.getItems().addAll(summaryGenerator.getLinguisticVariablesByAttributeName(summarizer));
        }
        summarizer2FuzzySetComboBox.getItems().clear();
        summarizer = summarizer2ComboBox.getValue();
        if(summarizer!=null) {
            summarizer2FuzzySetComboBox.getItems().addAll(summaryGenerator.getLinguisticVariablesByAttributeName(summarizer));
        }

    }

    private void resetCombBoxes() {
        summarizer1ComboBox.getItems().clear();
        summarizer2ComboBox.getItems().clear();
        summarizer1FuzzySetComboBox.getItems().clear();
        summarizer2FuzzySetComboBox.getItems().clear();
        qualifier1ComboBox.getItems().clear();
        qualifier2ComboBox.getItems().clear();
        qualifier1FuzzySetComboBox.getItems().clear();
        qualifier2FuzzySetComboBox.getItems().clear();
        summarizer1ComboBox.getItems().addAll(attributes);
        summarizer2ComboBox.getItems().addAll(attributes);
        qualifier1ComboBox.getItems().addAll(attributes);
        qualifier2ComboBox.getItems().addAll(attributes);
    }

    private void addQualityMeasures(SummaryGenerator summaryGenerator) {

        DatabaseRepository databaseRepository = new DatabaseRepository();
        List<PlayerInfo> playerInfoList = databaseRepository.getAllPlayersInfo();
        int numberOfRecords = databaseRepository.getPlayerCount();

        if (t1.isSelected()) {
            summaryGenerator.getQualityMeasures().add(new DegreeOfTruth(playerInfoList, numberOfRecords));
        }
        if (t2.isSelected()) {
            summaryGenerator.getQualityMeasures().add(new DegreeOfImprecision());
        }
        if (t3.isSelected()) {
            summaryGenerator.getQualityMeasures().add(new DegreeOfCovering(playerInfoList, numberOfRecords));
        }
        if (t4.isSelected()) {
            summaryGenerator.getQualityMeasures().add(new DegreeOfAppropriateness(playerInfoList, numberOfRecords));
        }
        if (t5.isSelected()) {
            summaryGenerator.getQualityMeasures().add(new LengthOfSummary());
        }
        if (t6.isSelected()) {
            summaryGenerator.getQualityMeasures().add(new DegreeOfQuantifierImprecision());
        }
        if (t7.isSelected()) {
            summaryGenerator.getQualityMeasures().add(new DegreeOfQuantifierCardinality());
        }
        if (t8.isSelected()) {
            summaryGenerator.getQualityMeasures().add(new DegreeOfSummarizerCardinality());
        }
        if (t9.isSelected()) {
            summaryGenerator.getQualityMeasures().add(new DegreeOfQualifierImprecision());
        }
        if (t10.isSelected()) {
            summaryGenerator.getQualityMeasures().add(new DegreeOfQualifierCardinality());
        }
        if (t11.isSelected()) {
            summaryGenerator.getQualityMeasures().add(new LengthOfQualifier());
        }
    }

    private void SaveFile(String content, File file) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Could not save file");

            alert.showAndWait();
        }
    }
}
