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
    public char addVowel()
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
     * Get a random word generate by a model
     * @param model of the word
     * @return String of the generated word
     */
    public String dataModel(String model)
    {
        strg.delete(0,strg.length());
        for(int i = 0; i < model.length(); i++)
        {
            char letter = model.charAt(i);
            if(letter >= 50 && letter <= 57)
            {
                i++;
                char multipleLetter;
                if(model.charAt(i) == 'V')
                {
                    multipleLetter = addVowel();
                }
                else if (model.charAt(i) == 'C')
                {
                    multipleLetter = addConsonne();
                }
                else
                {
                    multipleLetter = model.charAt(i);
                }
                strg.append(String.valueOf(multipleLetter).repeat(Math.max(0, Integer.parseInt(String.valueOf(letter)))));
            }
            else if(letter == 'V')
            {
                strg.append(addVowel());
            }
            else if(letter == 'C')
            {
                strg.append(addConsonne());
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
            if(letter >= 50 && letter <= 57)
            {
                if(i == model.length()-1)
                {
                    return letter;
                }
                char nextLetter = model.charAt(i+1);
                if(nextLetter != 'C' && nextLetter != 'V' && (nextLetter < 97 || nextLetter > 122))
                {
                    return letter;
                }
                i++;
            }
            else if(letter != 'C' && letter != 'V' && letter != ' ' && letter != '-' && letter != '\'' && (letter < 97 || letter > 122))
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

    public boolean isAValidNumber(String string)
    {
        for (int i = 0; i < string.length(); i++)
        {
            char letter = string.charAt(i);
            if((letter == 48 || letter == 49) && string.length() == 1)
            {
                return false;
            }
            if(letter < 48|| letter > 57)
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

    public int getNumberOfWords() {
        return numberOfWords;
    }
}
