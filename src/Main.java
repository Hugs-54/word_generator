import controller.ControllerMenu;
import controller.ControllerWordGenerator;
import generator.WordGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();
        primaryStage.setTitle("Word Generator");
        WordGenerator wordGenerator = new WordGenerator();
        ControllerWordGenerator controllerWordGenerator = new ControllerWordGenerator(wordGenerator);
        ControllerMenu controllerMenu = new ControllerMenu(wordGenerator,controllerWordGenerator);

        FXMLLoader fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("fxml/menu.fxml"));
        fxml.setControllerFactory(iC -> controllerMenu);
        root.setTop(fxml.load());

        fxml = new FXMLLoader();
        fxml.setLocation(getClass().getResource("fxml/wordgenerator.fxml"));
        fxml.setControllerFactory(iC -> controllerWordGenerator);
        root.setCenter(fxml.load());

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
