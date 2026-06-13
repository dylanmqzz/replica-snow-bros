package Juego;

import java.io.Serializable;

public class RegistroJugador implements Serializable,Comparable<RegistroJugador> {
    protected String nombre;
    protected int puntaje;

    public RegistroJugador(String nombre, int puntaje) {
        this.nombre = nombre;
        this.puntaje = puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int compareTo(RegistroJugador otroJugador) {
        return Integer.compare(otroJugador.getPuntaje(), this.getPuntaje());
    }
    
}
