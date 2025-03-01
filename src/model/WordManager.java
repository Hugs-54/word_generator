package model;

import model.exception.IllegalSymbolModelException;
import model.exception.NoSyllableCreatedException;

import java.util.ArrayList;
import java.util.List;

public class WordManager {

    private final WordGenerator wordGenerator = new WordGenerator();
    private final SyllableManager syllableManager = SyllableManager.getInstance();

    public List<String> generateWords(String model, int number){
        List<String> list = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            list.add(wordGenerator.generateWord(model));
        }
        return list;
    }

    public void validateModel(String model) throws IllegalSymbolModelException, NoSyllableCreatedException {
        String authorizedSymbols = "azertyuiopqsdfghjklmwxcvbnLVCSKRO";
        String authorizedSyllableSymbol = "SKRO";
        char symbol;
        for (int i = 0; i < model.length(); i++) {
            symbol = model.charAt(i);
            if(authorizedSymbols.indexOf(symbol) == -1){
                throw new IllegalSymbolModelException("Le symbole "+symbol+" n'est pas autorisé.");
            } else if (authorizedSyllableSymbol.indexOf(symbol) != -1) {
                try{
                    syllableManager.isSyllableAllowed(symbol);
                }catch (NoSyllableCreatedException e){
                    throw new NoSyllableCreatedException(e.getMessage());
                }
            }
        }
    }
}
