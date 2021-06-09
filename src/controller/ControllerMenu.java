package controller;

import generator.WordGenerator;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.Observator;

import java.io.File;
import java.io.IOException;

public class ControllerMenu implements Observator {

    private final WordGenerator wordGenerator;


    public ControllerMenu(WordGenerator wordGenerator)
    {
        this.wordGenerator = wordGenerator;
    }

    @FXML
    public void createOutuputFile() throws IOException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter("TXT","*.txt");
        fileChooser.getExtensionFilters().add(txt);
        File path = fileChooser.showSaveDialog(stage);
        if(path != null)
        {
            wordGenerator.createFile(path);
        }
    }

    @FXML
    public void selectOutuputFile()
    {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter("TXT","*.txt");
        fileChooser.getExtensionFilters().add(txt);
        File path = fileChooser.showOpenDialog(stage);
        if(path != null)
        {
            wordGenerator.selectOutuputFile(path);
        }
    }

    @Override
    public void react() {

    }
}
