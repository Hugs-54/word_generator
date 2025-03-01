package model;

public class SymbolDeterminer {
    private static final LettersManager lettersManager = new LettersManager();
    private static final SyllableManager syllableManager = SyllableManager.getInstance();
    private static final String consonants = "zrRtpmlkKjhgfdsqwxcCvbn";
    private static final String vowels = "aeioOuyVK";

    public static String symbolToText(char symbol){
        switch (symbol) {
            case 'L' : return String.valueOf(lettersManager.getRandomLetter());
            case 'C' : return String.valueOf(lettersManager.getRandomConsonant());
            case 'V' : return String.valueOf(lettersManager.getRandomVowel());
            case 'S' : return syllableManager.getRandomSyllable().toString();
            case 'K' : return syllableManager.getRandomSyllableMixed().toString();
            case 'R' : return syllableManager.getRandomSyllableFullConsonant().toString();
            case 'O' : return syllableManager.getRandomSyllableFullVowel().toString();
            default : return String.valueOf(symbol);
        }
    }

    public static boolean hasConsonant(String text) {
        for (int i = 0; i < text.length(); i++) {
            if(consonants.indexOf(text.charAt(i)) != -1){
                return true;
            }
        }
        return false;
    }

    public static boolean hasVowel(String text){
        for (int i = 0; i < text.length(); i++) {
            if(vowels.indexOf(text.charAt(i)) != -1){
                return true;
            }
        }
        return false;
    }

}
