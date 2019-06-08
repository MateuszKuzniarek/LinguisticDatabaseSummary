package view;

import javafx.scene.control.Alert;

public class AlertDisplayer {

    public static void displayErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);

        alert.showAndWait();

    }
}
