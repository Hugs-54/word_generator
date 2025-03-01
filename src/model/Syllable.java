package model;

import static model.SyllableType.FULL_VOWEL;
import static model.SyllableType.MIXED;

public class Syllable {

    private final String model;
    private final SyllableType type;

    public Syllable(String model, SyllableType type)
    {
        this.model = model;
        this.type = type;
    }

    public SyllableType getType() {
        return type;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < model.length(); i++) {
            stringBuilder.append(SymbolDeterminer.symbolToText(model.charAt(i)));
        }
        return stringBuilder.toString();
    }

    public String getModel(){
        return model;
    }

    public boolean containsVowel() {
        return type.equals(MIXED) || type.equals(FULL_VOWEL);
    }
}
