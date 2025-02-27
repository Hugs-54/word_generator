package model;

import java.util.ArrayList;
import java.util.List;

public class WordManager {

    private final WordGenerator wordGenerator = new WordGenerator();

    public List<String> generateWords(String model, int number){
        List<String> list = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            list.add(wordGenerator.generateWord(model));
        }
        return list;
    }

    public char validateModel(String model){
        String authorizedSymbols = "azertyuiopqsdfghjklmwxcvbnLVCSKRO";
        for (int i = 0; i < model.length(); i++) {
            if(authorizedSymbols.indexOf(model.charAt(i)) == -1){
                return model.charAt(i);
            }
        }
        return '1';
    }
}
