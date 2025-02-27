package model;

import java.util.*;

public final class SyllableManager implements Iterable<Syllable> {

    private final ArrayList<Syllable> fullVowelSyllables = new ArrayList<>();
    private final ArrayList<Syllable> fullConsonantSyllables = new ArrayList<>();
    private final ArrayList<Syllable> mixedSyllables = new ArrayList<>();
    private final ArrayList<Syllable> allSyllables = new ArrayList<>();
    private final Random random = new Random();

    public void addSyllable(String syllable){
        if(SymbolDeterminer.hasVowel(syllable) && SymbolDeterminer.hasConsonant(syllable)){
            Syllable s = new Syllable(syllable,SyllableType.MIXED);
            mixedSyllables.add(s);
            allSyllables.add(s);
        }else if(SymbolDeterminer.hasVowel(syllable) && !SymbolDeterminer.hasConsonant(syllable)){
            Syllable s = new Syllable(syllable,SyllableType.FULL_VOWEL);
            fullVowelSyllables.add(s);
            allSyllables.add(s);
        }else{
            Syllable s = new Syllable(syllable,SyllableType.FULL_CONSONANT);
            fullConsonantSyllables.add(s);
            allSyllables.add(s);
        }
    }

    public Syllable getRandomSyllable() {
        return allSyllables.get(random.nextInt(allSyllables.size()));
    }

    public Syllable getRandomSyllableMixed() {
        return mixedSyllables.get(random.nextInt(mixedSyllables.size()));
    }

    public Syllable getRandomSyllableFullConsonant() {
        return fullConsonantSyllables.get(random.nextInt(fullConsonantSyllables.size()));
    }

    public Syllable getRandomSyllableFullVowel() {
        return fullVowelSyllables.get(random.nextInt(fullVowelSyllables.size()));
    }

    public char validateModel(String model){
        String authorizedSymbols = "azertyuiopqsdfghjklmwxcvbnLVC";
        for (int i = 0; i < model.length(); i++) {
            if(authorizedSymbols.indexOf(model.charAt(i)) == -1){
                return model.charAt(i);
            }
        }
        return '1';
    }

    @Override
    public Iterator<Syllable> iterator() {
        return allSyllables.iterator();
    }
}
