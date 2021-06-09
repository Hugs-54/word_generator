package controller;

import generator.WordGenerator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;
import view.Observator;

import java.util.Optional;


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
    private boolean isAWordSelected;
    @FXML
    private Label labelTooltipHelp;
    @FXML
    private Label labelHelp;
    @FXML
    private MenuItem menuItemUseAsModel;
    @FXML
    private MenuItem menuItemSaveWord;
    @FXML
    private Label labelCurrentPathFile;

    public ControllerWordGenerator(WordGenerator wordGenerator)
    {
        wordGenerator.addObservator(this);
        this.wordGenerator = wordGenerator;
        this.isAWordSelected = false;
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
        labelTooltipHelp.setText("How to use model :\n- Number : Generate words randomly to a size of Number\n- C : Random consonne\n- V : Random vowel\n- a-z : The exact letter\n- 2C/2V : two successive and identical letter (2 to 9)\n");

    }

    @FXML
    public void generate()
    {
        wordGenerator.detectionModel(textFieldModel.getText());
        listGeneratedWords.clear();
        for (String str: wordGenerator) {
            listGeneratedWords.add(str);
        }
    }

    @FXML
    public void setNumberOfWords()
    {
        if(!textFieldNumberOfWords.getText().isBlank() && !textFieldNumberOfWords.getText().isEmpty())
        {
            wordGenerator.setNumberOfWords(Integer.parseInt(textFieldNumberOfWords.getText()));
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

    @FXML
    public void isAWordSelected()
    {
        isAWordSelected = listViewGeneratedWords.getSelectionModel().getSelectedIndices().size() == 1;
        menuItemSaveWord.setDisable(!isAWordSelected || !wordGenerator.hasAPath());
        menuItemUseAsModel.setDisable(!isAWordSelected);
    }

    @FXML
    public void useAWordAsModel()
    {
        textFieldModel.setText(listViewGeneratedWords.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void saveAWord()
    {
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        dialog.setTitle("Saving a word");
        dialog.setHeaderText("You can write a description and modify your word.");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField textFieldWord = new TextField(listViewGeneratedWords.getSelectionModel().getSelectedItem());
        /*HBox hboxWord = new HBox();
        hboxWord.getChildren().addAll(new Label("Word "),textFieldWord);
        hboxWord.setAlignment(Pos.CENTER);*/

        TextField textFieldDescription = new TextField();
        /*HBox hboxDescription = new HBox();
        hboxDescription.getChildren().addAll(new Label("Description "),textFieldDescription);
        hboxDescription.setAlignment(Pos.CENTER);*/

        GridPane gridPane = new GridPane();
        gridPane.setVgap(3);
        gridPane.add(new Label("Word "),0,0);
        gridPane.add(textFieldWord,1,0);
        gridPane.add(new Label("Description  "),0,1);
        gridPane.add(textFieldDescription,1,1);
        gridPane.setAlignment(Pos.CENTER);

        dialogPane.setContent(gridPane);
        Platform.runLater(textFieldWord::requestFocus);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new Pair<>(textFieldWord.getText(),textFieldDescription.getText());
            }
            return null;
        });
        Optional<Pair<String,String>> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((pair) -> {
            String word = pair.getKey();
            String description = pair.getValue();
            wordGenerator.saveAWord(word,description);
        });
        /*
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Write a description");
        inputDialog.setHeaderText("You can write a description for your word.");
        inputDialog.setContentText("Description :");
        inputDialog.showAndWait().ifPresent(description ->
        {
            wordGenerator.saveAWord(listViewGeneratedWords.getSelectionModel().getSelectedIndices().get(0),description);
        });*/
    }

    @Override
    public void react()
    {
        this.labelCurrentPathFile.setText("Current path file : "+wordGenerator.getCurrentPath());
    }
}
