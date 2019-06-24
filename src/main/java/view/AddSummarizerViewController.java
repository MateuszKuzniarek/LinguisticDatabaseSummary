package view;

import exceptions.WrongDataException;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.summaries.SummaryGenerator;
import logic.membership.DiscreteFuzzySet;
import logic.membership.FuzzySet;
import logic.summaries.LinguisticVariable;
import logic.membership.TrapezoidFuzzySet;
import logic.membership.TriangularFuzzySet;
import logic.utils.Point;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddSummarizerViewController implements Initializable {

    @Getter
    @Setter
    private SummaryGenerator summaryGenerator;
    @Getter
    @Setter
    private ConfigViewController configViewController;
    @FXML
    private TextField nameTextField;
    @FXML
    private ComboBox<String> attributeComboBox;
    @FXML
    private ComboBox<String> functionTypeComboBox;
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
    private Label realmStartLabel;
    @FXML
    private Label realmEndLabel;
    @FXML
    private Label p1Label;
    @FXML
    private Label p2Label;
    @FXML
    private Label p3Label;
    @FXML
    private Label p4Label;
    @FXML
    private Button saveButton;

    private static final List<String> attributes = Arrays.asList("height", "weight", "overall_rating", "potential",
            "acceleration", "sprint_speed", "agility", "shot_power", "stamina", "strength", "aggression", "age");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        attributeComboBox.getItems().addAll(attributes);
        functionTypeComboBox.getItems().addAll(Arrays.asList("Trapezoidalna", "Trojkątna", "Dyskretna"));
        functionTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if("Trapezoidalna".equals(newValue)) {
                setTrapezoidVisibility();
            } else if("Trojkątna".equals(newValue)) {
                setTriangularVisibility();
            } else if("Dyskretna".equals(newValue)) {
                setDiscreteVisibility();
            }
        });
        saveButton.disableProperty().bind(
                Bindings.isNull(nameTextField.textProperty())
                        .or(Bindings.isNull(attributeComboBox.getSelectionModel().selectedItemProperty()))
                        .or(Bindings.isNull(functionTypeComboBox.getSelectionModel().selectedItemProperty())));
    }

    @FXML
    public void save() {
        try {
            if("Trapezoidalna".equals(functionTypeComboBox.getValue())) {
                addTrapezoidSummarizer();
            } else if("Trojkątna".equals(functionTypeComboBox.getValue())) {
                addTriangularSummarizer();
            } else if("Dyskretna".equals(functionTypeComboBox.getValue())) {
                addDiscreteSummarizer();
            }
            configViewController.loadListView();
            Stage stage = (Stage)p1TextField.getScene().getWindow();
            stage.close();
        } catch (WrongDataException | NumberFormatException e) {
            //e.printStackTrace();
            AlertDisplayer.displayErrorAlert("Nieprawidłowe dane");
        } catch (Exception e) {
            e.printStackTrace();
            AlertDisplayer.displayErrorAlert("Coś poszło nie tak");
        }
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage)p1TextField.getScene().getWindow();
        stage.close();
    }

    private void addTrapezoidSummarizer() throws Exception {
        String name = nameTextField.getText();
        String attribute = attributeComboBox.getValue();
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
        LinguisticVariable linguisticVariable = new LinguisticVariable(name, attribute, fuzzySet);
        summaryGenerator.getLinguisticVariables().add(linguisticVariable);
    }


    private void addTriangularSummarizer() throws Exception {
        String name = nameTextField.getText();
        String attribute = attributeComboBox.getValue();
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
        LinguisticVariable linguisticVariable = new LinguisticVariable(name, attribute, fuzzySet);
        summaryGenerator.getLinguisticVariables().add(linguisticVariable);
    }

    private void addDiscreteSummarizer() throws Exception {
        String name = nameTextField.getText();
        String attribute = attributeComboBox.getValue();
        List<Point> points = DiscreteFuzzySet.getPointsOutOfDefinition(realmStartTextField.getText());
        for(Point point : points) {
            if(point.getY()>1) throw new WrongDataException("wrong data");
        }
        DiscreteFuzzySet discreteFuzzySet = new DiscreteFuzzySet();
        discreteFuzzySet.setMembershipValues(points);
        LinguisticVariable linguisticVariable = new LinguisticVariable(name, attribute, discreteFuzzySet);
        summaryGenerator.getLinguisticVariables().add(linguisticVariable);
    }

    private void setDiscreteVisibility() {
        realmStartTextField.setVisible(true);
        realmStartLabel.setVisible(true);
        realmStartLabel.setText("Punkty: (y1/x1;y2/x2;...;yn/xn)");
        realmEndTextField.setVisible(false);
        realmEndLabel.setVisible(false);
        p1TextField.setVisible(false);
        p1Label.setVisible(false);
        p2TextField.setVisible(false);
        p2Label.setVisible(false);
        p3TextField.setVisible(false);
        p3Label.setVisible(false);
        p4TextField.setVisible(false);
        p4Label.setVisible(false);
    }

    private void setTriangularVisibility() {
        realmStartTextField.setVisible(true);
        realmStartLabel.setVisible(true);
        realmStartLabel.setText("Początek przestrzeni rozważań");
        realmEndTextField.setVisible(true);
        realmEndLabel.setVisible(true);
        p1TextField.setVisible(true);
        p1Label.setVisible(true);
        p2TextField.setVisible(true);
        p2Label.setVisible(true);
        p3TextField.setVisible(true);
        p3Label.setVisible(true);
        p4TextField.setVisible(false);
        p4Label.setVisible(false);
    }

    private void setTrapezoidVisibility() {
        realmStartTextField.setVisible(true);
        realmStartLabel.setVisible(true);
        realmStartLabel.setText("Początek przestrzeni rozważań");
        realmEndTextField.setVisible(true);
        realmEndLabel.setVisible(true);
        p1TextField.setVisible(true);
        p1Label.setVisible(true);
        p2TextField.setVisible(true);
        p2Label.setVisible(true);
        p3TextField.setVisible(true);
        p3Label.setVisible(true);
        p4TextField.setVisible(true);
        p4Label.setVisible(true);
    }

}
