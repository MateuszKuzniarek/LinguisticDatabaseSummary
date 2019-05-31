package view;

import data.DatabaseRepository;
import data.XmlLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {



    public static void main(String[] args) throws Exception {
        DatabaseRepository repository = new DatabaseRepository();
        XmlLoader xmlLoader = new XmlLoader();
        System.out.println(repository.getPlayerInfo(1));
        System.out.println(xmlLoader.getSummarizers("src/main/resources/config.xml"));
        System.out.println(xmlLoader.getQuantifiers("src/main/resources/config.xml"));

        launch(args);

    }

    public void start(Stage stage) throws Exception {


        String fxmlFile = "/fxml/scene.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));


        Scene scene = new Scene(rootNode, 500, 500);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Linguistic Database Summary");
        stage.setScene(scene);
        stage.show();
    }
}
