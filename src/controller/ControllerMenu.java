package controller;

import generator.WordGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import view.Observator;

public class ControllerMenu implements Observator {

    private final WordGenerator wordGenerator;


    public ControllerMenu(WordGenerator wordGenerator)
    {
        this.wordGenerator = wordGenerator;
    }



    @Override
    public void react() {

    }
}
