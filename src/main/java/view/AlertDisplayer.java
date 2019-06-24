package view;

import javafx.scene.control.Alert;

class AlertDisplayer {

    static void displayErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setContentText(message);

        alert.showAndWait();

    }
}
