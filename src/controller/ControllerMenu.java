package controller;

import generator.WordGenerator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
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

    @FXML
    public void setMaxAuthorizeWords()
    {
        TextInputDialog inputDialog = new TextInputDialog(String.valueOf(wordGenerator.getMaxAuthorizeWords()));
        inputDialog.setTitle("Maximum number of words");
        inputDialog.setHeaderText("Set the maximum of words that can be generated.");
        inputDialog.setContentText("Number max ");
        inputDialog.showAndWait().ifPresent(value -> {
            if(!value.isBlank() && !value.isEmpty())
            {
                wordGenerator.setMaxAuthorizeWords(Integer.parseInt(value));
            }
        });
    }

    @FXML
    public void setMaxAuthorizeLetter()
    {
        TextInputDialog inputDialog = new TextInputDialog(String.valueOf(wordGenerator.getMaxAuthorizeLetter()));
        inputDialog.setTitle("Maximum number of letter");
        inputDialog.setHeaderText("Set the maximum of letter that contains a word.");
        inputDialog.setContentText("Number max ");
        inputDialog.showAndWait().ifPresent(value -> {
            if(!value.isBlank() && !value.isEmpty())
            {
                wordGenerator.setMaxAuthorizeLetter(Integer.parseInt(value));
            }
        });
    }

    @FXML
    public void quit()
    {
        Platform.exit();
    }

    @Override
    public void react() {

    }
}
