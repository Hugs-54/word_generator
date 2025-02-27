package model;

import java.io.*;
import java.util.Random;

public class Type {

    private String[] models;
    private Syllable[] syllables;
    private Random random;

    public Type(String name)
    {
        try
        {
            InputStream inputStream = this.getClass().getResourceAsStream("/types/"+name);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(inputStreamReader);

            //Syllable
            String sy = br.readLine();
            String[] syll = sy.split("\\|");
            this.syllables = new Syllable[syll.length];
            for (int i = 0; i < syllables.length; i++)
            {
                //this.syllables[i] = new Syllable(syll[i]);
            }

            //Model
            String mo = br.readLine();
            String[] mode = mo.split("\\|");
            this.models = new String[mode.length];
            System.arraycopy(mode, 0, this.models, 0, models.length);

            //myReader.close();
            inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.random = new Random();
    }

    public String getRandomSyllable()
    {
        int choice = random.nextInt(syllables.length);
        return syllables[choice].toString();
    }

    public String getRandomVowelSyllable()
    {
        Syllable sy = syllables[random.nextInt(syllables.length)];
        while(!sy.containsVowel())
        {
            sy = syllables[random.nextInt(syllables.length)];
        }
        return sy.toString();
    }

    public String getRandomModel()
    {
        int choice = random.nextInt(models.length);
        return models[choice];
    }
}
