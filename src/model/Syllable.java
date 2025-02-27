package model;

import static model.SyllableType.FULL_VOWEL;
import static model.SyllableType.MIXED;

public class Syllable {

    private final String syllable;
    private final SyllableType type;

    public Syllable(String syllable, SyllableType type)
    {
        this.syllable = syllable;
        this.type = type;
    }

    public SyllableType getType() {
        return type;
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < syllable.length(); i++) {
            stringBuilder.append(SymbolDeterminer.symbolToText(syllable.charAt(i)));
        }
        return stringBuilder.toString();
    }

    public boolean containsVowel() {
        return type.equals(MIXED) || type.equals(FULL_VOWEL);
    }
}
