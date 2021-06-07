package generator;

import view.Subject;

import java.util.Random;

public class WordGenerator extends Subject {

    private final Random random;
    private char[] vowels;
    private char[] consonne;
    private StringBuilder strg;
    private int choice;

    public WordGenerator()
    {
        this.vowels = new char[]{'a','e','u','i','o','y'};
        this.consonne = new char[]{'z','r','t','p','q','s','d','f','g','h','j','k','l','m','w','x','c','v','b','n'};
        this.strg = new StringBuilder();
        this.random = new Random();
    }

    /**
     * Generate a word randomly
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
     * Get the word by the type of the model
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
     * @return String of the generated word by the correct model
     */
    public String detectionModel(String model)
    {
        if(model.length() == 1)
        {
            return blindModel(Integer.parseInt(model));
        }
        else
        {
            return dataModel(model);
        }
    }
}
