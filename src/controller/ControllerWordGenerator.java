package controller;

import generator.WordGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
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
    @FXML
    private ListView<String> listViewGeneratedWords;
    private ObservableList<String> listGeneratedWords;
    @FXML
    private Label labelTooltipHelp;
    @FXML
    private Label labelHelp;

    public ControllerWordGenerator(WordGenerator wordGenerator)
    {
        wordGenerator.addObservator(this);
        this.wordGenerator = wordGenerator;
    }

    @FXML
    void initialize()
    {
        this.listGeneratedWords = FXCollections.observableArrayList();
        this.listViewGeneratedWords.setItems(listGeneratedWords);

        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/question_mark_logo.png")));
        image.setFitHeight(18);
        image.setFitWidth(18);
        this.labelHelp.setGraphic(image);
        labelTooltipHelp.setText("How to use model :\n- Number : Generate words randomly of a size of Number\n- C : Random consonne\n- V : Random vowel\n- a-z : The exact letter\n- 2C/2V : two successive and identical letter (2 to 9)\n");

    }

    @FXML
    public void generate()
    {
        wordGenerator.detectionModel(textFieldModel.getText());
        listGeneratedWords.clear();
        for (String str: wordGenerator)
        {
            listGeneratedWords.add(str);
        }
        //carnet.setAuteur(listeParticipants.getSelectionModel().getSelectedIndices().get(0));
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
