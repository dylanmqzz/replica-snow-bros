package Observers;

import javax.swing.JLabel;

import Enumerados.Eventos;
import GameElements.GameElement;

public class ObserverPuntaje extends JLabel implements Observer {
    protected GameElement entidad;
    protected int puntaje;

    public ObserverPuntaje (GameElement enti) {
        super();
        entidad = enti;
        puntaje = 0;
    }

    public void update (Eventos e) {
        if (e == Eventos.PUNTOS) {
            puntaje = entidad.getPuntos();
            mostrarPuntaje();
        }
    }

    private void mostrarPuntaje() {
        String rutaPuntaje = "src//Assets//Sprites//Puntajes//" + puntaje + ".gif";
        setBounds((int) entidad.getPosicion().getX() ,(int) entidad.getPosicion().getY() + 20, 60, 40);
        this.setText("<html><img src='file:" + rutaPuntaje + "' width='50' height='50'></html>");
        setVisible(true);
    }

}
