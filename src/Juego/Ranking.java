package Juego;

import java.io.Serializable;
import java.util.ArrayList;

public class Ranking implements Serializable {
    protected ArrayList<RegistroJugador> topJugadores;

    public Ranking () {
        topJugadores = new ArrayList<RegistroJugador>();
    }

    public void agregarJugador(RegistroJugador j) {
        topJugadores.add(j);
        topJugadores.sort(null);
        if (topJugadores.size()>5)
            topJugadores.remove(topJugadores.size() - 1);
    }

    public ArrayList<RegistroJugador> getTopJugadores() {
        return topJugadores;
    }

}
