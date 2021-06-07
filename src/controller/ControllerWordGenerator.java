package controller;

import generator.WordGenerator;
import view.Observator;

public class ControllerWordGenerator implements Observator {

    private WordGenerator wordGenerator;

    public ControllerWordGenerator(WordGenerator wordGenerator)
    {
        wordGenerator.addObservator(this);
    }


    @Override
    public void react() {

    }
}
