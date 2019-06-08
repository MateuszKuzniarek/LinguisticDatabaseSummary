package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {



    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {


        String fxmlFile = "/fxml/scene.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = loader.load(getClass().getResourceAsStream(fxmlFile));

        Scene scene = new Scene(rootNode, 700, 700);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Linguistic Database Summary");
        stage.setScene(scene);
        stage.show();
    }
}
