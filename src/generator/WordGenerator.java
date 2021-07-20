package generator;

import view.Subject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class WordGenerator extends Subject implements Iterable<String> {

    private final Random random;
    private final char[] vowels;
    private final char[] consonne;
    private final ArrayList<String> generatedWords;
    private final ArrayList<Syllable> syllables;
    private final ArrayList<String> models;
    private int maxAuthorizeWords;
    private int maxAuthorizeLetter;
    private int numberOfWords;
    private final SaveManager saveManager;

    public WordGenerator()
    {
        this.vowels = new char[]{'a','e','u','i','o','y'};
        this.consonne = new char[]{'z','r','t','p','q','s','d','f','g','h','j','k','l','m','w','x','c','v','b','n'};
        this.random = new Random();
        this.generatedWords = new ArrayList<>();
        this.syllables = new ArrayList<>();
        this.models = new ArrayList<>();
        this.numberOfWords = 10;
        this.saveManager = new SaveManager(this);
        this.maxAuthorizeWords = 50;
        this.maxAuthorizeLetter = 50;
    }

    /**
     * Generate a word with random letters
     * @param length of the word
     * @return String of the word
     */
    public String blindModel(int length)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length; i++)
        {
            int choice = random.nextInt(2);
            if(choice == 0)
            {
                choice = random.nextInt(vowels.length);
                stringBuilder.append(vowels[choice]);
            }
            else
            {
                choice = random.nextInt(consonne.length);
                stringBuilder.append(consonne[choice]);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Generate a random vowel
     * @return String of the vowel
     */
    private char addVowel()
    {
        return vowels[random.nextInt(vowels.length)];
    }

    /**
     * Generate a random consonne
     * @return String of the consonne
     */
    private char addConsonne() {
        return consonne[random.nextInt(consonne.length)];
    }

    /**
     * Generate a random syllable
     * @return String of the syllable
     */
    private String addRandomSyllable()
    {
        return syllables.get(random.nextInt(syllables.size())).toString();
    }

    private String addRandomVowelSyllable()
    {
        Syllable sy = syllables.get(random.nextInt(syllables.size()));
        while(!sy.containsVowel())
        {
            sy = syllables.get(random.nextInt(syllables.size()));
        }
        return sy.toString();
    }

    /**
     * Check if it exist at least one syllable that contains a vowel
     * @return true is there is a vowel
     */
    public boolean syllablesHasVowels()
    {
        for (Syllable syllable : syllables)
        {
            if (syllable.containsVowel())
            {
                return true;
            }
        }
        return false;
    }

    public boolean syllableVowelRequested(String model)
    {
        for (int i = 0; i < model.length(); i++) {
            if(model.charAt(i) == 'S')
            {
                if(i != model.length()-1)
                {
                    if(model.charAt(i+1) == '/')
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * Get a random word generate by a model
     * @param model of the word
     * @return String of the generated word
     */
    public String dataModel(String model)
    {
        char multipleLetter;
        char letter;
        String syllable;
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < model.length(); i++)
        {
            letter = model.charAt(i);
            if(letter >= 50 && letter <= 57)
            {
                i++;
                if(model.charAt(i) == 'V')
                {
                    multipleLetter = addVowel();
                    stringBuilder.append(String.valueOf(multipleLetter).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));
                }
                else if (model.charAt(i) == 'C')
                {
                    multipleLetter = addConsonne();
                    stringBuilder.append(String.valueOf(multipleLetter).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));

                }
                else if (model.charAt(i) == 'S')
                {
                    if(i == model.length()-1)
                    {
                        syllable = dataModel(addRandomSyllable());
                        stringBuilder.append(String.valueOf(syllable).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));
                    }
                    else if(model.charAt(i+1) == '/')
                    {
                        syllable = dataModel(addRandomVowelSyllable());
                        stringBuilder.append(String.valueOf(syllable).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));
                        i++;
                    }
                    else
                    {
                        syllable = dataModel(addRandomSyllable());
                        stringBuilder.append(String.valueOf(syllable).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));
                    }

                }
                else
                {
                    multipleLetter = model.charAt(i);
                    stringBuilder.append(String.valueOf(multipleLetter).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));
                }
            }
            else if(letter == 'V')
            {
                stringBuilder.append(addVowel());
            }
            else if(letter == 'C')
            {
                stringBuilder.append(addConsonne());
            }
            else if(letter == 'S')
            {
                if(i == model.length()-1)
                {
                    stringBuilder.append(dataModel(addRandomSyllable()));
                }
                else if(model.charAt(i+1) == '/')
                {
                    stringBuilder.append(dataModel(addRandomVowelSyllable()));
                    i++;
                }
                else
                {
                    stringBuilder.append(dataModel(addRandomSyllable()));
                }
            }
            else
            {
                stringBuilder.append(letter);
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Detect the model
     * @param model to detect
     */
    public void detectionModel(String model)
    {
        generatedWords.clear();
        if(isAValidNumber(model))
        {
            for(int i = 0; i < numberOfWords; i++)
            {
                generatedWords.add(blindModel(Integer.parseInt(model)));
            }
        }
        else
        {
            for(int i = 0; i < numberOfWords; i++)
            {
                generatedWords.add(dataModel(model));
            }
        }
    }

    /**
     * Set the number of words to generate
     * @param numberOfWords number
     */
    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    /**
     * Detect if a model is correct
     * @param model to verify
     * @return the char that it's not allowed
     */
    public char modelIsCorrect(String model)
    {
        if(model.length() == 1 && model.charAt(0) == '/')
        {
            return '/';
        }
        for(int i = 0; i < model.length(); i++)
        {
            char letter = model.charAt(i);
            if(letter == 'S' && syllables.size() == 0)
            {
                return letter;
            }
            if(letter >= 50 && letter <= 57)
            {
                if(i == model.length()-1)
                {
                    return letter;
                }
                char nextLetter = model.charAt(i+1);
                if(nextLetter != 'C' && nextLetter != 'V' && nextLetter != 'S' && (nextLetter < 97 || nextLetter > 122) && (nextLetter < 224 || nextLetter > 253))
                {
                    return letter;
                }
                i++;
            }
            else if(letter == '/' && model.charAt(i-1) != 'S')
            {
                return letter;
            }
            else if(letter != 'C' && letter != 'V' && letter != 'S' && letter != ' ' && letter != '/' && letter != '-' && letter != '\'' && (letter < 97 || letter > 122) && (letter < 224 || letter > 253))
            {
                return letter;
            }
        }
        return ' ';
    }

    /**
     * Detect if a model is a number. 1 and 0 not allowed
     * @param model to verify
     * @return true it's a number
     */
    public boolean isAValidNumber(String model)
    {
        char letter;
        for (int i = 0; i < model.length(); i++)
        {
            letter = model.charAt(i);
            if((letter == 48 || letter == 49) && model.length() == 1)
            {
                return false;
            }
            if(letter < 48 || letter > 57)
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<String> iterator() {
        return generatedWords.iterator();
    }

    public Iterator<String> iteratorModels() {
        return models.iterator();
    }

    public Iterator<Syllable> iteratorSyllables()
    {
        return syllables.iterator();
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void saveAWord(String word, String description)
    {
        saveManager.saveAWord(word, description);
    }

    public void createFile(File path) throws IOException {
        saveManager.createFile(path);
        notifyObservators();
    }

    public void selectOutuputFile(File path)
    {
        saveManager.defineOutputFile(path);
        notifyObservators();
    }

    public String getCurrentPath() {
        return saveManager.getCurrentPath();
    }

    public String getCurrentPathDirectory() {
        String[] str = saveManager.getCurrentPath().split("\\\\");
        StringBuilder s = new StringBuilder();
        for (int i = 3; i < str.length - 1; i++) {
            s.append(str[i]);
            if(i < str.length-2)
            {
                s.append("\\");
            }
        }
        return s.toString();
    }

    public boolean hasAPath() {
        return saveManager.hasAPath();
    }

    public int getMaxAuthorizeWords() {
        return maxAuthorizeWords;
    }

    public int getMaxAuthorizeLetter() {
        return maxAuthorizeLetter;
    }

    public void setMaxAuthorizeWords(int maxAuthorizeWords) {
        this.maxAuthorizeWords = maxAuthorizeWords;
    }

    public void setMaxAuthorizeLetter(int maxAuthorizeLetter) {
        this.maxAuthorizeLetter = maxAuthorizeLetter;
        notifyObservators();
    }

    public boolean stringContainsNumber(String string)
    {
        for (int i = 0; i < string.length(); i++) {
            if(string.charAt(i) >= 48 && string.charAt(i) <= 57)
            {
                return true;
            }
        }
        return false;
    }

    public boolean syllableHasS(String string)
    {
        for (int i = 0; i < string.length(); i++) {
            if(string.charAt(i) == 'S')
            {
                return true;
            }
        }
        return false;
    }

    public void addSyllable(String sy)
    {
        syllables.add(new Syllable(sy));
    }

    public void deleteSyllable(int i) {
        syllables.remove(i);
    }

    public void deleteAllSyllable()
    {
        syllables.clear();
    }

    public void deleteAllModels()
    {
        models.clear();
    }

    public void removeModel(int selectedIndex)
    {
        models.remove(selectedIndex);
    }

    public void addModel(String selectedItem)
    {
        models.add(selectedItem);
    }

    public void saveProject(File path)
    {
        saveManager.saveProject(path);
    }

    public void openProject(File path)
    {
        saveManager.openProject(path);
        notifyObservators();
    }

    public void newProject()
    {

        deleteAllModels();
        deleteAllSyllable();
        selectOutuputFile(null);
    }
}
