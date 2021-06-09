package generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveManager {

    private File outputFile;

    public SaveManager()
    {

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
        return outputFile.toString();
    }

    public boolean hasAPath() {
        return outputFile != null;
    }
}
