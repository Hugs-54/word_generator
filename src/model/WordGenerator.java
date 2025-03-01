package model;

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
    private int numberOfWords;
    private final SaveManager saveManager;
    private final Type fantasy;

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
        this.fantasy = new Type("Fantasy.wg");
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


    public String generateWord(String model){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < model.length(); i++) {
            stringBuilder.append(SymbolDeterminer.symbolToText(model.charAt(i)));
        }
        return stringBuilder.toString();
    }

    /**
     * Get a random word generate by a model
     * @param model of the word
     * @return String of the generated word
     */
    /*public String dataModel(String model,String type)
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
                        if(type.equals("fantasy"))
                        {
                            syllable = dataModel(fantasy.getRandomSyllable(),"fantasy");
                        }
                        else
                        {
                            syllable = dataModel(addRandomSyllable(),"none");
                        }
                        stringBuilder.append(String.valueOf(syllable).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));
                    }
                    else if(model.charAt(i+1) == '/')
                    {
                        if(type.equals("fantasy"))
                        {
                            syllable = dataModel(fantasy.getRandomVowelSyllable(),"fantasy");
                        }
                        else
                        {
                            syllable = dataModel(addRandomVowelSyllable(),"none");
                        }
                        stringBuilder.append(String.valueOf(syllable).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));
                        i++;
                    }
                    else
                    {
                        if(type.equals("fantasy"))
                        {
                            syllable = dataModel(fantasy.getRandomSyllable(),"fantasy");
                        }
                        else
                        {
                            syllable = dataModel(addRandomSyllable(),"none");
                        }
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
                    if(type.equals("fantasy"))
                    {
                        stringBuilder.append(dataModel(fantasy.getRandomSyllable(),"fantasy"));
                    }
                    else
                    {
                        stringBuilder.append(dataModel(addRandomSyllable(),"none"));
                    }
                }
                else if(model.charAt(i+1) == '/')
                {
                    if(type.equals("fantasy"))
                    {
                        stringBuilder.append(dataModel(fantasy.getRandomVowelSyllable(),"fantasy"));
                    }
                    else
                    {
                        stringBuilder.append(dataModel(addRandomVowelSyllable(),"none"));
                    }
                    i++;
                }
                else
                {
                    if(type.equals("fantasy"))
                    {
                        stringBuilder.append(dataModel(fantasy.getRandomSyllable(),"fantasy"));
                    }
                    else
                    {
                        stringBuilder.append(dataModel(addRandomSyllable(),"none"));
                    }
                }
            }
            else
            {
                stringBuilder.append(letter);
            }
        }
        return stringBuilder.toString();
    }*/


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

    public void saveAWord(String word, String description)
    {
        saveManager.saveAWord(word, description);
        notifyObservators();
    }

    public void createFile(File path) throws IOException {
        saveManager.createFile(path);
        notifyObservators();
    }

    public void selectOutputFile(File path)
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

    public void addSyllable(String sy)
    {
        //syllables.add(new Syllable(sy));
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
        selectOutputFile(null);
    }

    public void generateWordsFromType(String type)
    {
        generatedWords.clear();
        for (int i = 0; i < numberOfWords; i++)
        {
            if(type.equals("fantasy"))
            {
                //generatedWords.add(dataModel(fantasy.getRandomModel(), type));
            }
        }
    }

    public String getTextOutuputFile()
    {
        return saveManager.readFile();
    }

    public void rewriteOutputFile(String text)
    {
        saveManager.rewrite(text);
    }
}
