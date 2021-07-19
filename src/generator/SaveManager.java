package generator;

import java.io.*;
import java.util.Iterator;

public class SaveManager {

    private File outputFile;
    private WordGenerator wordGenerator;

    public SaveManager(WordGenerator wg)
    {
        this.outputFile = null;
        this.wordGenerator = wg;
    }

    /**
     * Save a word in a text file
     * @param word to save
     * @param description wrote by the user to define the word
     */
    public void saveAWord(String word, String description)
    {
        try {
            FileWriter myWriter = new FileWriter(outputFile,true);
            myWriter.write("\n"+word+" } "+description);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Define the outuput file to save
     * @param path of the file
     */
    public void defineOutputFile(File path)
    {
        this.outputFile = path;
    }

    /**
     * Create a file to write words
     * @param path of the new file
     * @throws IOException exception
     */
    public void createFile(File path) throws IOException {
        this.outputFile = path;
        if (outputFile.createNewFile())
        {
            System.out.println("File created: " + outputFile.getName());
        }
        else {
            System.out.println("File already exists.");
        }
    }

    public String getCurrentPath() {
        if(outputFile != null)
        {
            return outputFile.toString();
        }
        return "";
    }

    public boolean hasAPath() {
        return outputFile != null;
    }

    public void saveProject(File path)
    {
       try
       {
           FileWriter myWriter = new FileWriter(path);

           myWriter.write(getCurrentPath()+"\n");
           for (Iterator<Syllable> it = wordGenerator.iteratorSyllables(); it.hasNext(); )
           {
               Syllable sy = it.next();
               myWriter.write(sy.toString()+"|");
           }
           myWriter.write("\n");

           for (Iterator<String> it = wordGenerator.iteratorModels(); it.hasNext(); )
           {
               String s = it.next();
               myWriter.write(s+"|");
           }

           myWriter.close();
           System.out.println("Successfully wrote to the file.");
       }
       catch (IOException e)
       {
           System.out.println("An error occurred.");
           e.printStackTrace();
       }
    }

    public void openProject(File path)
    {
        try
        {
            FileReader myReader = new FileReader(path);
            BufferedReader br = new BufferedReader(myReader);

            //Output file
            String output = br.readLine();
            if(!output.isEmpty() && !output.isBlank())
            {
                defineOutputFile(new File(output));
            }

            //Syllable
            wordGenerator.deleteAllSyllable();
            String sy = br.readLine();
            String[] syllables = sy.split("\\|");
            for (int i = 0; i < syllables.length; i++)
            {
                wordGenerator.addSyllable(syllables[i]);
            }

            //Model
            wordGenerator.deleteAllModels();
            String mo = br.readLine();
            String[] models = mo.split("\\|");
            for (int i = 0; i < models.length; i++)
            {
                wordGenerator.addModel(models[i]);
            }

            myReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
