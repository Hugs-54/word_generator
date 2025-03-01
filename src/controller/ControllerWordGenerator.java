package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import model.Syllable;
import model.SyllableManager;
import model.WordGenerator;
import model.WordManager;
import model.exception.IllegalSymbolModelException;
import model.exception.NoSyllableCreatedException;
import view.Observator;

import java.io.File;
import java.util.Iterator;
import java.util.Optional;


public class ControllerWordGenerator implements Observator {

    private final WordManager wordManager = new WordManager();
    private final WordGenerator wordGenerator;
    private final SyllableManager syllableManager = SyllableManager.getInstance();
    @FXML
    private TextField textFieldModel;

    private int numberOfWords = 50;
    @FXML
    private Text textVerifyModel;
    @FXML
    private Button buttonGenerate;
    @FXML
    private ListView<String> listViewGeneratedWords;
    private ObservableList<String> listGeneratedWords;
    private boolean isAWordSelected;
    @FXML
    private Label labelTooltipHelpMo;
    @FXML
    private Label labelHelpMo;
    @FXML
    private MenuItem menuItemUseAsModel;
    @FXML
    private MenuItem menuItemSaveWord;
    @FXML
    private MenuItem menuItemSaveModel;
    @FXML
    private Label labelCurrentPathFile;
    @FXML
    private Tooltip tooltipHelpMo;
    @FXML
    private Text textVerifySyllable;
    @FXML
    private TextField textFieldSyllable;
    @FXML
    private Button buttonCreateSyllable;
    @FXML
    private ListView<String> listViewSyllables;
    private ObservableList<String> listSyllables;
    @FXML
    private MenuItem menuItemDeleteSyll;
    @FXML
    private Label labelHelpSy;
    @FXML
    private Label labelTooltipHelpSy;
    @FXML
    private Tooltip tooltipHelpSy;
    @FXML
    private ListView<String> listViewModels;
    private ObservableList<String> listModels;
    @FXML
    private MenuItem menuItemDeleteModel;
    @FXML
    private MenuItem menuItemUseModel;
    @FXML
    private MenuItem menuItemSaveModelTextField;

    @FXML
    private TextArea textAreaOutputFile;
    @FXML
    private Label labelOutputFile;
    @FXML
    private Button modifyOutputFile;
    @FXML
    private HBox HBoxConfirmOutputFile;
    @FXML
    private Label labelTooltipWarning;
    @FXML
    private Tooltip tooltipWarning;
    @FXML
    private Label labelWarning;


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

        this.listModels = FXCollections.observableArrayList();
        this.listViewModels.setItems(listModels);

        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/images/question_mark_logo.png")));
        ImageView image2 = new ImageView(new Image(getClass().getResourceAsStream("/images/question_mark_logo.png")));
        ImageView image3 = new ImageView(new Image(getClass().getResourceAsStream("/images/exclamation_point_logo.png")));
        image.setFitHeight(18);
        image.setFitWidth(18);
        image2.setFitHeight(18);
        image2.setFitWidth(18);
        image3.setFitHeight(18);
        image3.setFitWidth(18);
        this.labelHelpMo.setGraphic(image);
        this.labelHelpSy.setGraphic(image2);
        this.labelWarning.setGraphic(image3);
        tooltipHelpMo.setShowDuration(Duration.seconds(30));
        tooltipHelpSy.setShowDuration(Duration.seconds(30));
        tooltipWarning.setShowDuration(Duration.seconds(30));
        labelTooltipHelpMo.setText("How to use Model :\n- Number : Generate words randomly to a size of Number\n- C : Random consonne\n- V : Random vowel\n- S : Random syllable\n- S/ : Random syllable that contains vowel\n- a-z : The exact letter\n- 2C~2V~2S~2S/ : two successive and identical letter or syllable (2 to 9)\n- apostrophe, hyphen and letters with accent are allowed.");
        labelTooltipHelpSy.setText("How to create Syllable :\nLike in model, C - V - letter\napostrophe, hyphen, letters with accent\n2C~2V are allowed (2 to 9).");
        labelTooltipWarning.setText("When you confirm, the file will be rewrite.");
    }

    @FXML
    public void generateModel()
    {
        listGeneratedWords.clear();
        listGeneratedWords.addAll(wordManager.generateWords(textFieldModel.getText(),numberOfWords));
    }

    /**
     * Modify the state of the button generate and the verification text
     * @param text of verication
     * @param disable boolean to deactivate the button
     * @param color of the text
     */
    public void actionModel(String text, boolean disable, Color color)
    {
        textVerifyModel.setText(text);
        buttonGenerate.setDisable(disable);
        textVerifyModel.setFill(color);
    }

    @FXML
    public void checkModel() {
        try {
            wordManager.validateModel(textFieldModel.getText());
            if (textFieldModel.getText().isEmpty()) {
                actionModel("Ecrivez un modèle pour débuter.", true, Color.BLACK);
            } else {
                actionModel("Le modèle est correct.", false, Color.GREEN);
            }

        } catch (IllegalSymbolModelException | NoSyllableCreatedException e) {
            actionModel(e.toString(), true, Color.RED);
        }
    }


    /**
     * Modify the state of the button create and the verification text
     * @param text of verication
     * @param disable boolean to deactivate the button
     * @param color of the text
     */
    public void actionSyllable(String text, boolean disable, Color color)
    {
        textVerifySyllable.setText(text);
        buttonCreateSyllable.setDisable(disable);
        textVerifySyllable.setFill(color);
    }

    @FXML
    public void checkSyllable()
    {
        //TODO à changer pour des exceptions
        char letter = syllableManager.validateModel(textFieldSyllable.getText());
        if(textFieldSyllable.getText().isEmpty() || textFieldSyllable.getText().isBlank())
        {
            actionSyllable("Write a model to begin.",true,Color.BLACK);
        }
        else if(letter == 'S' || letter == 'K' || letter == 'R' || letter == 'O')
        {
            actionSyllable("The syllable can't contains '"+letter+"'.",true,Color.RED);
        }
        else if(letter != '1')
        {
            actionSyllable("The character '"+letter+"' is not allowed in syllable.",true,Color.RED);
        }
        else
        {
            actionSyllable("The syllable is correct.",false,Color.GREEN);
        }
    }

    @FXML
    public void isAWordSelected()
    {
        isAWordSelected = listViewGeneratedWords.getSelectionModel().getSelectedIndices().size() == 1;
        menuItemSaveWord.setDisable(!isAWordSelected || !wordGenerator.hasAPath());
        menuItemSaveModel.setDisable(!isAWordSelected);
        menuItemUseAsModel.setDisable(!isAWordSelected);
    }

    @FXML
    public void isASyllableSelected()
    {
        boolean isASyllableSelected = listViewSyllables.getSelectionModel().getSelectedIndices().size() == 1;
        menuItemDeleteSyll.setDisable(!isASyllableSelected);
    }

    @FXML
    public void useAWordAsModel()
    {
        textFieldModel.setText(listViewGeneratedWords.getSelectionModel().getSelectedItem());
        checkModel();
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
        syllableManager.addSyllable(textFieldSyllable.getText());
        textFieldSyllable.setText("");
        refreshListViewSyllables();
        checkModel();
    }

    private void refreshListViewSyllables()
    {
        listSyllables.clear();
        for (Syllable sy : syllableManager) {
            listSyllables.add(sy.getModel());
        }
    }

    @FXML
    public void deleteSyllable()
    {
        syllableManager.deleteSyllable(listViewSyllables.getSelectionModel().getSelectedItem());
        refreshListViewSyllables();
        checkModel();
    }

    @FXML
    public void isAModelSelected()
    {
        boolean isAModelSelected = listViewModels.getSelectionModel().getSelectedIndices().size() == 1;
        menuItemDeleteModel.setDisable(!isAModelSelected);
        menuItemUseModel.setDisable(!isAModelSelected);
    }

    @FXML
    public void deleteModel()
    {
        wordGenerator.removeModel(listViewModels.getSelectionModel().getSelectedIndex());
        refreshListViewModels();
    }

    private void refreshListViewModels()
    {
        listModels.clear();
        for (Iterator<String> it = wordGenerator.iteratorModels(); it.hasNext(); ) {
            String s = it.next();
            listModels.add(s);
        }
    }

    @FXML
    public void saveModelFromWords()
    {
        wordGenerator.addModel(listViewGeneratedWords.getSelectionModel().getSelectedItem());
        refreshListViewModels();
    }

    @FXML
    public void saveModelFromTextField()
    {
        wordGenerator.addModel(textFieldModel.getText());
        refreshListViewModels();
    }

    @FXML
    public void useModel()
    {
        textFieldModel.setText(listViewModels.getSelectionModel().getSelectedItem());
        checkModel();
    }

    @FXML
    public void isSelectedTextField()
    {
        menuItemSaveModelTextField.setDisable(textFieldModel.getText().length() <= 1);
    }

    @FXML
    public void selectOutuputFile()
    {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        if(wordGenerator.getCurrentPath().equals(""))
        {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        }
        else
        {
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home"),wordGenerator.getCurrentPathDirectory()));
        }
        FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter("TXT","*.txt");
        fileChooser.getExtensionFilters().add(txt);
        File path = fileChooser.showOpenDialog(stage);
        if(path != null)
        {
            wordGenerator.selectOutputFile(path);
        }
    }

    public void newProject()
    {
        textFieldModel.clear();
        textFieldSyllable.clear();
        listGeneratedWords.clear();
        checkModel();
        checkSyllable();
    }

    @FXML
    public void generateFantasyWords()
    {
        wordGenerator.generateWordsFromType("fantasy");
        listGeneratedWords.clear();
        for (String str: wordGenerator)
        {
            listGeneratedWords.add(str);
        }
    }

    @FXML
    public void modifyTextOutputFile()
    {
        modifyOutputFile.setVisible(false);
        HBoxConfirmOutputFile.setVisible(true);
        textAreaOutputFile.setVisible(true);
        labelOutputFile.setVisible(false);
        textAreaOutputFile.setText(labelOutputFile.getText());
    }

    @FXML
    public void confirmTextOutputFile()
    {
        modifyOutputFile.setVisible(true);
        HBoxConfirmOutputFile.setVisible(false);
        textAreaOutputFile.setVisible(false);
        labelOutputFile.setVisible(true);
        labelOutputFile.setText(textAreaOutputFile.getText());
        wordGenerator.rewriteOutputFile(textAreaOutputFile.getText());
    }

    @FXML
    public void cancelTextOutputFile()
    {
        modifyOutputFile.setVisible(true);
        HBoxConfirmOutputFile.setVisible(false);
        textAreaOutputFile.setVisible(false);
        labelOutputFile.setVisible(true);
    }

    private void refreshLabelAndTextAreaOutputFile()
    {
        String str = wordGenerator.getTextOutuputFile();
        labelOutputFile.setText(str);
        textAreaOutputFile.setText(str);
    }

    @Override
    public void react()
    {
        this.labelCurrentPathFile.setText("Current path file : "+wordGenerator.getCurrentPath());
        refreshListViewModels();
        refreshListViewSyllables();
        refreshLabelAndTextAreaOutputFile();
        checkModel();
    }


}
