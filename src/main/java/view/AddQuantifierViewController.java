package view;

import exceptions.WrongDataException;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.summaries.SummaryGenerator;
import logic.membership.FuzzySet;
import logic.summaries.Quantifier;
import logic.membership.TrapezoidFuzzySet;
import logic.membership.TriangularFuzzySet;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class AddQuantifierViewController implements Initializable {

    @Getter
    @Setter
    private SummaryGenerator summaryGenerator;
    @Getter
    @Setter
    private ConfigViewController configViewController;
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<String> functionTypeComboBox;
    @FXML
    private CheckBox relativeCheckBox;
    @FXML
    private TextField realmStartTextField;
    @FXML
    private TextField realmEndTextField;
    @FXML
    private TextField p1TextField;
    @FXML
    private TextField p2TextField;
    @FXML
    private TextField p3TextField;
    @FXML
    private TextField p4TextField;
    @FXML
    private Label p4Label;
    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        functionTypeComboBox.getItems().addAll(Arrays.asList("Trapezoidalna", "Trojkątna"));
        functionTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if("Trapezoidalna".equals(newValue)) {
                setTrapezoidVisibility();
            } else if("Trojkątna".equals(newValue)) {
                setTriangularVisibility();
            }
        });
        saveButton.disableProperty().bind(
                Bindings.isNull(nameTextField.textProperty())
                        .or(Bindings.isNull(functionTypeComboBox.getSelectionModel().selectedItemProperty())));

        relativeCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                realmStartTextField.setText("0");
                realmStartTextField.setDisable(true);
                realmEndTextField.setText("1");
                realmStartTextField.setDisable(true);
            } else {
                realmStartTextField.setText("");
                realmStartTextField.setDisable(false);
                realmEndTextField.setText("");
                realmStartTextField.setDisable(false);

            }
        });
    }

    @FXML
    public void save() {
        try {
            if("Trapezoidalna".equals(functionTypeComboBox.getValue())) {
                addTrapezoidQuantifier();
            } else if("Trojkątna".equals(functionTypeComboBox.getValue())) {
                addTriangularQuantifier();
            }
            configViewController.loadListView();
            Stage stage = (Stage)p1TextField.getScene().getWindow();
            stage.close();
        } catch (WrongDataException | NumberFormatException e) {
            //e.printStackTrace();
            AlertDisplayer.displayErrorAlert("Błąd", "Nieprawidłowe dane");
        } catch (Exception e) {
            e.printStackTrace();
            AlertDisplayer.displayErrorAlert("Błąd", "Coś poszło nie tak");
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage)p1TextField.getScene().getWindow();
        stage.close();
    }

    private void addTrapezoidQuantifier() throws Exception {
        String name = nameTextField.getText();
        double p1 = Double.parseDouble(p1TextField.getText());
        double p2 = Double.parseDouble(p2TextField.getText());
        double p3 = Double.parseDouble(p3TextField.getText());
        double p4 = Double.parseDouble(p4TextField.getText());
        double realmStart = Double.parseDouble(realmStartTextField.getText());
        double realmEnd = Double.parseDouble(realmEndTextField.getText());
        if(!(realmStart<=p1 && p1<=p2 && p2<=p3 && p3<=p4 && p4<=realmEnd)) {
            throw new WrongDataException("wrong data");
        }
        FuzzySet fuzzySet = new TrapezoidFuzzySet(p1,p2,p3,p4);
        fuzzySet.setRealmStart(realmStart);
        fuzzySet.setRealmEnd(realmEnd);
        Quantifier quantifier = new Quantifier();
        quantifier.setLabel(name);
        quantifier.setRelative(relativeCheckBox.isSelected());
        quantifier.setFuzzySet(fuzzySet);
        summaryGenerator.getQuantifiers().add(quantifier);
    }


    private void addTriangularQuantifier() throws Exception {
        String name = nameTextField.getText();
        double p1 = Double.parseDouble(p1TextField.getText());
        double p2 = Double.parseDouble(p2TextField.getText());
        double p3 = Double.parseDouble(p3TextField.getText());
        double realmStart = Double.parseDouble(realmStartTextField.getText());
        double realmEnd = Double.parseDouble(realmEndTextField.getText());
        if(!(realmStart<=p1 && p1<=p2 && p2<=p3 && p3<=realmEnd)) {
            throw new WrongDataException("wrong data");
        }
        FuzzySet fuzzySet = new TriangularFuzzySet(p1,p2,p3);
        fuzzySet.setRealmStart(realmStart);
        fuzzySet.setRealmEnd(realmEnd);
        Quantifier quantifier = new Quantifier();
        quantifier.setLabel(name);
        quantifier.setRelative(relativeCheckBox.isSelected());
        quantifier.setFuzzySet(fuzzySet);
        summaryGenerator.getQuantifiers().add(quantifier);
    }

    private void setTriangularVisibility() {
        p4TextField.setVisible(false);
        p4Label.setVisible(false);
    }

    private void setTrapezoidVisibility() {
        p4TextField.setVisible(true);
        p4Label.setVisible(true);
    }

}
