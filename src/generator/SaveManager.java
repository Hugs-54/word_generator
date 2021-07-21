package generator;

import java.io.*;
import java.util.Iterator;
import java.util.Scanner;

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
     * Read and save a txt file
     */
    public String readFile()
    {
        try
        {
            Scanner scanner = new Scanner(new File(String.valueOf(outputFile)));
            StringBuilder strb = new StringBuilder();
            //read until end of file (EOF)
            while (scanner.hasNextLine())
            {
                strb.append(scanner.nextLine()).append("\n");
            }
            scanner.close();
            return strb.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
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
            for (String syllable : syllables)
            {
                wordGenerator.addSyllable(syllable);
            }

            //Model
            wordGenerator.deleteAllModels();
            String mo = br.readLine();
            String[] models = mo.split("\\|");
            for (String model : models)
            {
                wordGenerator.addModel(model);
            }

            myReader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void rewrite(String text)
    {
        try {
            FileWriter myWriter = new FileWriter(outputFile);
            myWriter.write(text);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
