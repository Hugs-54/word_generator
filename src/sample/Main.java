package sample;

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
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        BorderPane borderPane = new BorderPane();
        primaryStage.setTitle("Word Generator");
        WordGenerator wordGenerator = new WordGenerator();
        for(int x = 0; x < 20; x++)
        {
            System.out.println(wordGenerator.detectionModel("hC2VC VCy"));
        }

        ControllerWordGenerator controllerWordGenerator = new ControllerWordGenerator(wordGenerator);
        //borderPane.getChildren().add(controllerWordGenerator);
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
