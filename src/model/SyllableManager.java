package model;

import model.exception.NoSyllableCreatedException;

import java.util.*;

public final class SyllableManager implements Iterable<Syllable> {

    private final ArrayList<Syllable> fullVowelSyllables = new ArrayList<>();
    private final ArrayList<Syllable> fullConsonantSyllables = new ArrayList<>();
    private final ArrayList<Syllable> mixedSyllables = new ArrayList<>();
    private final ArrayList<Syllable> allSyllables = new ArrayList<>();
    private final Random random = new Random();

    private static SyllableManager instance;
    public static SyllableManager getInstance() {
        if (instance == null) {
            instance = new SyllableManager();
        }
        return instance;
    }

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
        if(allSyllables.isEmpty()){
            return new Syllable("",SyllableType.EMPTY);
        }
        return allSyllables.get(random.nextInt(allSyllables.size()));
    }

    public Syllable getRandomSyllableMixed() {
        if(mixedSyllables.isEmpty()){
            return new Syllable("",SyllableType.EMPTY);
        }
        return mixedSyllables.get(random.nextInt(mixedSyllables.size()));
    }

    public Syllable getRandomSyllableFullConsonant() {
        if(fullConsonantSyllables.isEmpty()){
            return new Syllable("",SyllableType.EMPTY);
        }
        return fullConsonantSyllables.get(random.nextInt(fullConsonantSyllables.size()));
    }

    public Syllable getRandomSyllableFullVowel() {
        if(fullVowelSyllables.isEmpty()){
            return new Syllable("",SyllableType.EMPTY);
        }
        return fullVowelSyllables.get(random.nextInt(fullVowelSyllables.size()));
    }

    //TODO faire avec exception
    public char validateModel(String model){
        String authorizedSymbols = "azertyuiopqsdfghjklmwxcvbnLVC";
        for (int i = 0; i < model.length(); i++) {
            if(authorizedSymbols.indexOf(model.charAt(i)) == -1){
                return model.charAt(i);
            }
        }
        return '1';
    }


    public void isSyllableAllowed(char symbol) throws NoSyllableCreatedException {
        switch (symbol){
            case 'S':
                if(allSyllables.isEmpty()){
                    throw new NoSyllableCreatedException("Vous devez rajouter au moins une syllabe pour utiliser S.");
                }
                break;
            case 'K':
                if(mixedSyllables.isEmpty()){
                    throw new NoSyllableCreatedException("Vous devez rajouter au moins une syllabe avec voyelle pour utiliser K.");
                }
                break;
            case 'R':
                if(fullConsonantSyllables.isEmpty()){
                    throw new NoSyllableCreatedException("Vous devez rajouter au moins une syllabe uniquement de consonne pour utiliser R.");
                }
                break;
            case 'O':
                if(fullVowelSyllables.isEmpty()){
                    throw new NoSyllableCreatedException("Vous devez rajouter au moins une syllabe uniquement de voyelle pour utiliser O.");
                }
                break;
        }
    }

    @Override
    public Iterator<Syllable> iterator() {
        return allSyllables.iterator();
    }

    public void deleteSyllable(String syllable) {
        allSyllables.removeIf(s -> s.getModel().equals(syllable));
        mixedSyllables.removeIf(s -> s.getModel().equals(syllable));
        fullVowelSyllables.removeIf(s -> s.getModel().equals(syllable));
        fullConsonantSyllables.removeIf(s -> s.getModel().equals(syllable));
    }
}
