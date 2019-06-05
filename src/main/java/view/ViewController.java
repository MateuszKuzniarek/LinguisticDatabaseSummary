package view;

import data.DatabaseRepository;
import data.PlayerInfo;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    @FXML
    private ComboBox<String> attribute1ComboBox;
    @FXML
    private ComboBox<String> attribute2ComboBox;
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

    @FXML
    public void summarize() {
        textArea.setText("");
        SummaryGenerator summaryGenerator = new SummaryGenerator();
        String attribute1 = attribute1ComboBox.getValue();
        String attribute2 = attribute2ComboBox.getValue();

        if (yCheckBox.isSelected()) {
            summaryGenerator.addYagerSummaries(attribute1);
            summaryGenerator.addYagerSummaries(attribute2);
        }
        if (gCheckBox.isSelected()) {
            summaryGenerator.addCompoundSummaries(attribute1, attribute2);
        }
        if (kCheckBox.isSelected()) {
            summaryGenerator.addSummariesWithQualifier(attribute1, attribute2);
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
                Bindings.isNull(attribute1ComboBox.getSelectionModel().selectedItemProperty())
                        .or(Bindings.isNull(attribute2ComboBox.getSelectionModel().selectedItemProperty()))
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

        attribute1ComboBox.getItems().add("height");
        attribute1ComboBox.getItems().add("weight");
        attribute1ComboBox.getItems().add("overall_rating");
        attribute1ComboBox.getItems().add("potential");
        attribute1ComboBox.getItems().add("acceleration");
        attribute1ComboBox.getItems().add("sprint_speed");
        attribute1ComboBox.getItems().add("agility");
        attribute1ComboBox.getItems().add("shot_power");
        attribute1ComboBox.getItems().add("stamina");
        attribute1ComboBox.getItems().add("strength");
        attribute1ComboBox.getItems().add("aggression");
        attribute1ComboBox.getItems().add("age");

        attribute2ComboBox.getItems().add("overall_rating");
        attribute2ComboBox.getItems().add("height");
        attribute2ComboBox.getItems().add("potential");
        attribute2ComboBox.getItems().add("acceleration");
        attribute2ComboBox.getItems().add("weight");
        attribute2ComboBox.getItems().add("sprint_speed");
        attribute2ComboBox.getItems().add("agility");
        attribute2ComboBox.getItems().add("shot_power");
        attribute2ComboBox.getItems().add("stamina");
        attribute2ComboBox.getItems().add("strength");
        attribute2ComboBox.getItems().add("aggression");
        attribute2ComboBox.getItems().add("age");
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
