package generator;

public class Syllable {

    private String syllable;
    private boolean containsVowel;

    public Syllable(String syllable)
    {
        this.syllable = syllable;
        this.containsVowel = false;
        checkVowelInSyllable();
    }

    public int getLength() {
        return syllable.length();
    }

    public String toString() {
        return syllable;
    }

    public void setSyllable(String syllable) {
        this.syllable = syllable;
        checkVowelInSyllable();
    }

    public boolean containsVowel() {
        return containsVowel;
    }

    private void checkVowelInSyllable()
    {
        char letter;
        for (int i = 0; i < syllable.length(); i++) {
            letter = syllable.charAt(i);
            if(letter == 'a' || letter == 'e' || letter == 'u' || letter == 'i' || letter == 'o' || letter == 'y' || letter == 'V' || (letter >= 224 && letter <= 253))
            {
                containsVowel = true;
            }
        }
    }
}
