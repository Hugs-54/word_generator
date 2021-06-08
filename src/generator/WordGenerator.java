package generator;

import view.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordGenerator extends Subject implements Iterable<String> {

    private final Random random;
    private char[] vowels;
    private char[] consonne;
    private StringBuilder strg;
    private int choice;
    private ArrayList<String> generatedWords;
    private int numberOfWords;

    public WordGenerator()
    {
        this.vowels = new char[]{'a','e','u','i','o','y'};
        this.consonne = new char[]{'z','r','t','p','q','s','d','f','g','h','j','k','l','m','w','x','c','v','b','n'};
        this.strg = new StringBuilder();
        this.random = new Random();
        this.generatedWords = new ArrayList<>();
        this.numberOfWords = 10;
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
            choice = random.nextInt(2);
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
    public String addVowel()
    {
        return String.valueOf(vowels[random.nextInt(vowels.length)]);
    }

    /**
     * Generate a random consonne
     * @return String of the consonne
     */
    private String addConsonne() {
        return String.valueOf(consonne[random.nextInt(consonne.length)]);
    }

    /**
     * Get a random word generate by a model
     * @param model of the word
     * @return String of the generated word
     */
    public String dataModel(String model)
    {
        strg.delete(0,strg.length());
        for(int i = 0; i < model.length(); i++)
        {
            if(model.charAt(i) == '2')
            {
                i++;
                String letter = "";
                if(model.charAt(i) == 'V')
                {
                    letter = addVowel();
                }
                else if (model.charAt(i) == 'C')
                {
                    letter = addConsonne();
                }
                strg.append(letter);
                strg.append(letter);
            }
            else if(model.charAt(i) == 'V')
            {
                strg.append(addVowel());
            }
            else if(model.charAt(i) == 'C')
            {
                strg.append(addConsonne());
            }
            else
            {
                strg.append(model.charAt(i));
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
        if(model.length() == 1)
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

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public char modelIsCorrect(String model)
    {
        //Add for number
        for(int i = 0; i < model.length(); i++)
        {
            char letter = model.charAt(i);
            if(letter != 'C' && letter != 'V' && letter != ' ')
            {
                return letter;
            }
        }
        return ' ';
        /*String regex = "[a-zà-ÿCV'\\-\\s]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(model);
        return matcher.matches();*/
    }

    @Override
    public Iterator<String> iterator() {
        return generatedWords.iterator();
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }
}
