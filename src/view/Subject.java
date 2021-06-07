package view;

import java.util.ArrayList;

public class Subject {

    private ArrayList<Observator> list;

    public Subject()
    {
        this.list = new ArrayList<>();
    }

    public void addObservator(Observator obs)
    {
        list.add(obs);
    }

    public void notifyObservators()
    {
        for (Observator obs: list)
        {
            obs.react();
        }
    }
}
