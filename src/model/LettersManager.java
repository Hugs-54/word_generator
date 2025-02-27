package model;

import java.util.Random;

public final class LettersManager {

    private final Random random = new Random();
    private final char[] alphabet = new char[]{'a','e','u','i','o','y','z','r','t','p','q','s','d','f','g','h','j','k','l','m','w','x','c','v','b','n'};

    public char getRandomLetter(){
        return alphabet[random.nextInt(26)];
    }

    public char getRandomVowel(){
        return alphabet[random.nextInt(6)];
    }

    public char getRandomConsonant(){
        return alphabet[random.nextInt(20)+6];
    }
}
