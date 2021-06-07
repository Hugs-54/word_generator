package controller;

import generator.WordGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import view.Observator;


public class ControllerWordGenerator implements Observator {

    private final WordGenerator wordGenerator;
    @FXML
    private TextField textFieldModel;
    @FXML
    private TextField textFieldNumberOfWords;
    @FXML
    private Label labelVerifyModel;
    @FXML
    private Button buttonGenerate;

    public ControllerWordGenerator(WordGenerator wordGenerator)
    {
        wordGenerator.addObservator(this);
        this.wordGenerator = wordGenerator;
    }

    @FXML
    public void generate()
    {
        wordGenerator.detectionModel(textFieldModel.getText());
        for (String str: wordGenerator) {
            System.out.println(str);
        }
    }

    @FXML
    public void setNumberOfWords()
    {
        if(!textFieldNumberOfWords.getText().isBlank() && !textFieldNumberOfWords.getText().isEmpty())
        {
            wordGenerator.setNumberOfWords(Integer.parseInt(textFieldNumberOfWords.getText()));
            System.out.println(Integer.parseInt(textFieldNumberOfWords.getText()));
        }
    }

    @FXML
    public void checkModel()
    {
        if(wordGenerator.modelIsCorrect(textFieldModel.getText()))
        {
            labelVerifyModel.setText("Everything is correct.");
            buttonGenerate.setDisable(false);
        }
        else
        {
            labelVerifyModel.setText("Something wrong in the model.");
            buttonGenerate.setDisable(true);
        }
    }

    @Override
    public void react()
    {

    }
}
