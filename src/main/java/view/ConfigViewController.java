package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.SummaryGenerator;
import logic.membership.LinguisticVariable;
import logic.membership.Quantifier;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ConfigViewController implements Initializable {

    @Getter
    @Setter
    private SummaryGenerator summaryGenerator;

    @FXML
    private TableView<SummarizerRow> summarizersTableView;
    @FXML
    private TableView<QuantifierRow> quantifiersTableView;
    @FXML
    private TableColumn summarizerNameColumn;
    @FXML
    private TableColumn summarizerAttributeColumn;
    @FXML
    private TableColumn summarizerFunctionTypeColumn;
    @FXML
    private TableColumn summarizerDefinitionColumn;
    @FXML
    private TableColumn quantifierNameColumn;
    @FXML
    private TableColumn quantifierIsRelativeColumn;
    @FXML
    private TableColumn quantifierFunctionTypeColumn;
    @FXML
    private TableColumn quantifierDefinitionColumn;

    private static final List<String> attributes = Arrays.asList("height", "weight", "overall_rating", "potential",
            "acceleration", "sprint_speed", "agility", "shot_power", "stamina", "strength", "aggression", "age");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        summarizerNameColumn.setCellValueFactory( new PropertyValueFactory<LinguisticVariable, String>("name"));
        summarizerAttributeColumn.setCellValueFactory( new PropertyValueFactory<LinguisticVariable, String>("attribute"));
        summarizerFunctionTypeColumn.setCellValueFactory( new PropertyValueFactory<LinguisticVariable, String>("functionType"));
        summarizerDefinitionColumn.setCellValueFactory( new PropertyValueFactory<LinguisticVariable, String>("definition"));

        quantifierNameColumn.setCellValueFactory( new PropertyValueFactory<LinguisticVariable, String>("name"));
        quantifierIsRelativeColumn.setCellValueFactory( new PropertyValueFactory<LinguisticVariable, String>("isRelative"));
        quantifierFunctionTypeColumn.setCellValueFactory( new PropertyValueFactory<LinguisticVariable, String>("functionType"));
        quantifierDefinitionColumn.setCellValueFactory( new PropertyValueFactory<LinguisticVariable, String>("definition"));
    }

    @FXML
    public void deleteSummarizer() {
        SummarizerRow summarizerRow = summarizersTableView.getSelectionModel().getSelectedItem();
        LinguisticVariable linguisticVariable = summarizerRow.getLinguisticVariable();
        summaryGenerator.getLinguisticVariables().remove(linguisticVariable);
        summarizersTableView.getItems().remove(summarizerRow);
    }

    @FXML
    public void addSummarizer() {
        SummarizerRow summarizerRow = summarizersTableView.getSelectionModel().getSelectedItem();
        LinguisticVariable linguisticVariable = summarizerRow.getLinguisticVariable();
        summaryGenerator.getLinguisticVariables().remove(linguisticVariable);
        summarizersTableView.getItems().remove(summarizerRow);
    }

    @FXML
    public void deleteQuantifier() {
        QuantifierRow quantifierRow = quantifiersTableView.getSelectionModel().getSelectedItem();
        Quantifier quantifier = quantifierRow.getQuantifier();
        summaryGenerator.getQuantifiers().remove(quantifier);
        quantifiersTableView.getItems().remove(quantifierRow);
    }

    @FXML
    public void addQuantifier() {

    }

    @FXML
    public void close() {
        Stage stage = (Stage)summarizersTableView.getScene().getWindow();
        stage.close();
    }

    public void loadListView(SummaryGenerator summaryGenerator) {
        this.summaryGenerator = summaryGenerator;
        for(LinguisticVariable linguisticVariable : summaryGenerator.getLinguisticVariables()) {
            summarizersTableView.getItems().add(new SummarizerRow(linguisticVariable));
        }
        for(Quantifier quantifier : summaryGenerator.getQuantifiers()) {
            quantifiersTableView.getItems().add(new QuantifierRow(quantifier));
        }
    }
}
