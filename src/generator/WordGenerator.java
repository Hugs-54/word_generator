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
    private final StringBuilder strg;
    private final ArrayList<String> generatedWords;
    private final ArrayList<Syllable> syllables;
    private int maxAuthorizeWords;
    private int maxAuthorizeLetter;
    private int numberOfWords;
    private SaveManager saveManager;

    public WordGenerator()
    {
        this.vowels = new char[]{'a','e','u','i','o','y'};
        this.consonne = new char[]{'z','r','t','p','q','s','d','f','g','h','j','k','l','m','w','x','c','v','b','n'};
        this.strg = new StringBuilder();
        this.random = new Random();
        this.generatedWords = new ArrayList<>();
        this.syllables = new ArrayList<>();
        this.numberOfWords = 10;
        this.saveManager = new SaveManager();
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
        strg.delete(0,strg.length());
        for(int i = 0; i < length; i++)
        {
            int choice = random.nextInt(2);
            if(choice == 0)
            {
                choice = random.nextInt(vowels.length);
                strg.append(vowels[choice]);
            }
            else
            {
                choice = random.nextInt(consonne.length);
                strg.append(consonne[choice]);
            }
        }
        return strg.toString();
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

    private String addRandomSyllable() {
        return syllables.get(random.nextInt(syllables.size())).toString();}

    public void resetStringBuilder()
    {
        strg.delete(0,strg.length());
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
        for(int i = 0; i < model.length(); i++)
        {
            letter = model.charAt(i);
            if(letter >= 50 && letter <= 57)
            {
                i++;
                if(model.charAt(i) == 'V')
                {
                    multipleLetter = addVowel();
                    strg.append(String.valueOf(multipleLetter).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));
                }
                else if (model.charAt(i) == 'C')
                {
                    multipleLetter = addConsonne();
                    strg.append(String.valueOf(multipleLetter).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));

                }
                else if (model.charAt(i) == 'S')
                {
                    syllable = dataModel(addRandomSyllable());
                    strg.append(String.valueOf(syllable).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter))-1)));
                }
                else
                {
                    multipleLetter = model.charAt(i);
                    strg.append(String.valueOf(multipleLetter).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));
                }
            }
            else if(letter == 'V')
            {
                strg.append(addVowel());
            }
            else if(letter == 'C')
            {
                strg.append(addConsonne());
            }
            else if(letter == 'S')
            {
                dataModel(addRandomSyllable());
            }
            else
            {
                strg.append(letter);
            }
        }
        return strg.toString();
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
                resetStringBuilder();
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
        for(int i = 0; i < model.length(); i++)
        {
            char letter = model.charAt(i);
            if(letter >= 50 && letter <= 57)
            {
                if(i == model.length()-1)
                {
                    return letter;
                }
                char nextLetter = model.charAt(i+1);
                if(nextLetter != 'C' && nextLetter != 'V' && nextLetter != 'S' && (nextLetter < 97 || nextLetter > 122))
                {
                    return letter;
                }
                i++;
            }
            else if(letter != 'C' && letter != 'V' && letter != 'S' && letter != ' ' && letter != '-' && letter != '\'' && (letter < 97 || letter > 122))
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
}
