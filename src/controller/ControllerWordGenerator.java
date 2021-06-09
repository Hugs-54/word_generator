package controller;

import generator.Syllable;
import generator.WordGenerator;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;
import view.Observator;

import java.util.Iterator;
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
    @FXML
    private Tooltip tooltipHelp;
    @FXML
    private Text textVerifySyllable;
    @FXML
    private TextField textFieldSyllable;
    @FXML
    private Button buttonCreateSyllable;
    @FXML
    private ListView<String> listViewSyllables;
    private ObservableList<String> listSyllables;
    private boolean isASyllableSelected;
    @FXML
    private MenuItem menuItemDeleteSyll;


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

        this.listSyllables = FXCollections.observableArrayList();
        this.listViewSyllables.setItems(listSyllables);

        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/question_mark_logo.png")));
        image.setFitHeight(18);
        image.setFitWidth(18);
        this.labelHelp.setGraphic(image);
        tooltipHelp.setShowDuration(Duration.seconds(30));
        labelTooltipHelp.setText("How to use model :\n- Number : Generate words randomly to a size of Number\n- C : Random consonne\n- V : Random vowel\n- a-z : The exact letter\n- 2C/2V : two successive and identical letter (2 to 9)\n");


    }

    @FXML
    public void generateModel()
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
        if(Integer.parseInt(textFieldNumberOfWords.getText()) > wordGenerator.getMaxAuthorizeWords())
        {
            Alert boiteDeDialogue = new Alert(Alert.AlertType.ERROR,"For safety, the number of generated words can't exceed "+ wordGenerator.getMaxAuthorizeWords()+".\nYou can change this setting in the menu.", ButtonType.OK);
            boiteDeDialogue.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            PauseTransition pause = new PauseTransition(Duration.seconds(15));
            pause.setOnFinished(evenement -> { Button bouton = (Button) boiteDeDialogue.getDialogPane().lookupButton((ButtonType.OK));
                bouton.fire();});
            pause.play();
            boiteDeDialogue.showAndWait();
        }
        else if(!textFieldNumberOfWords.getText().isBlank() && !textFieldNumberOfWords.getText().isEmpty())
        {
            wordGenerator.setNumberOfWords(Integer.parseInt(textFieldNumberOfWords.getText()));
        }
    }

    public void actionModelCorrect()
    {
        textVerifyModel.setText("The model is correct.");
        buttonGenerate.setDisable(false);
        textVerifyModel.setFill(Color.GREEN);
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
        else if(wordGenerator.isAValidNumber(textFieldModel.getText()))
        {
            if(Integer.parseInt(textFieldModel.getText()) > wordGenerator.getMaxAuthorizeLetter())
            {
                textVerifyModel.setText("For safety, the number of letter can't exceed "+ wordGenerator.getMaxAuthorizeLetter()+".\nYou can change this setting in the menu.");
                buttonGenerate.setDisable(true);
                textVerifyModel.setFill(Color.RED);
            }
            else
            {
                actionModelCorrect();
            }
        }
        else if(letter == ' ')
        {
            actionModelCorrect();
        }
        //If the letter is empty, there is no problem
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
    public void checkSyllable()
    {
        char letter = wordGenerator.modelIsCorrect(textFieldSyllable.getText());
        if(textFieldSyllable.getText().isEmpty() || textFieldSyllable.getText().isBlank())
        {
            textVerifySyllable.setText("Write a model to begin.");
            buttonCreateSyllable.setDisable(true);
            textVerifySyllable.setFill(Color.BLACK);
        }
        else if(wordGenerator.syllableHasS(textFieldSyllable.getText()))
        {
            textVerifySyllable.setText("The syllable can not contains S.");
            buttonCreateSyllable.setDisable(true);
            textVerifySyllable.setFill(Color.RED);
        }
        else if(letter >= 50 && letter <= 57)
        {
            textVerifySyllable.setText("The number '"+letter+"' must be followed by a letter.");
            buttonCreateSyllable.setDisable(true);
            textVerifySyllable.setFill(Color.RED);
        }
        else if(letter != ' ')
        {
            textVerifySyllable.setText("The character '"+letter+"' is not allowed in syllable.");
            buttonCreateSyllable.setDisable(true);
            textVerifySyllable.setFill(Color.RED);
        }
        else
        {
            textVerifySyllable.setText("The syllable is correct.");
            buttonCreateSyllable.setDisable(false);
            textVerifySyllable.setFill(Color.GREEN);
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
    public void isASyllableSelected()
    {
        isASyllableSelected = listViewSyllables.getSelectionModel().getSelectedIndices().size() == 1;
        menuItemDeleteSyll.setDisable(!isASyllableSelected);
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
        TextField textFieldDescription = new TextField();

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
    }

    @FXML
    public void addSyllable()
    {
        wordGenerator.addSyllable(textFieldSyllable.getText());
        textFieldSyllable.setText("");
        refreshListViewSyllables();
    }

    private void refreshListViewSyllables()
    {
        listSyllables.clear();
        for (Iterator<Syllable> it = wordGenerator.iteratorSyllables(); it.hasNext(); ) {
            Syllable sy = it.next();
            listSyllables.add(sy.toString());
        }
    }

    @FXML
    public void deleteSyllable()
    {
        wordGenerator.deleteSyllable(listViewSyllables.getSelectionModel().getSelectedIndices().get(0));
        refreshListViewSyllables();
    }

    @Override
    public void react()
    {
        this.labelCurrentPathFile.setText("Current path file : "+wordGenerator.getCurrentPath());
        checkModel();
    }
}
