package Observers;

import java.util.LinkedList;

import Enumerados.Eventos;

public interface Observable {
    public void suscribe(Observer suscriptor);

    public void unsuscribe(Observer suscriptor);

    public void notify(Eventos e);

    public LinkedList<Observer> observers();
    
}
