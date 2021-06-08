package controller;

import generator.WordGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import view.Observator;


public class ControllerWordGenerator implements Observator {

    private final WordGenerator wordGenerator;
    @FXML
    private TextField textFieldModel;
    @FXML
    private TextField textFieldNumberOfWords;
    @FXML
    private Text textVerifyModel;
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
        char letter = (wordGenerator.modelIsCorrect(textFieldModel.getText()));
        if(textFieldModel.getText().isEmpty() || textFieldModel.getText().isBlank())
        {
            textVerifyModel.setText("Write a model to begin.");
            buttonGenerate.setDisable(true);
            textVerifyModel.setFill(Color.BLACK);
        }
        //If the letter is empty, there is no problem
        else if(letter == ' ' || wordGenerator.isAValidNumber(textFieldModel.getText()))
        {
            textVerifyModel.setText("The model is correct.");
            buttonGenerate.setDisable(false);
            textVerifyModel.setFill(Color.GREEN);
        }
        else if(letter >= 50 && letter <= 57)
        {
            textVerifyModel.setText("The number '"+letter+"' must be followed by a letter.");
            buttonGenerate.setDisable(true);
            textVerifyModel.setFill(Color.RED);
        }
        else
        {
            textVerifyModel.setText("The character '"+letter+"' is not allowed in the model.");
            buttonGenerate.setDisable(true);
            textVerifyModel.setFill(Color.RED);
        }
    }

    @Override
    public void react()
    {

    }
}
