import controller.ControllerMenu;
import controller.ControllerWordGenerator;
import generator.WordGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        primaryStage.setTitle("Word Generator");
        WordGenerator wordGenerator = new WordGenerator();
        ControllerWordGenerator controllerWordGenerator = new ControllerWordGenerator(wordGenerator);
        ControllerMenu controllerMenu = new ControllerMenu(wordGenerator);

        FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("fxml/menu.fxml"));
        fxml.setControllerFactory(iC -> controllerMenu);
        root.setTop(fxml.load());

        fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("fxml/wordgenerator.fxml"));
        fxml.setControllerFactory(iC -> controllerWordGenerator);
        root.setCenter(fxml.load());

        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
