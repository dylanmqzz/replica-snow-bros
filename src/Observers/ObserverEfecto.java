package Observers;

import java.io.File;
import javax.swing.JLabel;

import Enumerados.Eventos;
import Enumerados.Efectos;
import GameElements.NoEstaticos.Jugador.Jugador;

public class ObserverEfecto extends JLabel implements Observer {
    protected Jugador jugador;
    protected Efectos efectoActual;
    
    public ObserverEfecto(Jugador jugador) {
        super();
        this.jugador = jugador;
        setVisible(false);
    }
    
    public void update(Eventos e) {
        switch(e) {
            case PERDIDAVIDA:
                setVisible(false);
                break;
            case POW_AZUL:
                efectoActual = Efectos.VELOCIDAD;
                mostrarEfecto();
                break;
            case POW_ROJA:
                efectoActual = Efectos.MATA_RAPIDO;
                mostrarEfecto();
                break;
            case POW_VERDE:
                efectoActual = Efectos.DETENER_ENEMIGOS;
                mostrarEfecto();
                break;
            case ACTUALIZACION_GRAFICA:
                if (isVisible())
                    mostrarEfecto();
                break;
            case SIN_EFECTOS:
                setVisible(false);
                break;
            case DOBLE_PUNTOS:
                efectoActual = Efectos.DOBLE_PUNTOS;
                mostrarEfecto();
                break;
            default:
                break;
        }
    }
    
    private void mostrarEfecto() {
        String rutaEfecto;
        if (jugador.getDireccionMirada().getX() == 1) {
            rutaEfecto = "src/Assets" + File.separator + "Sprites" + File.separator + efectoActual.toString() + "_DERECHA.gif";
            actualizarPosicion(1);
        } else {
            rutaEfecto = "src/Assets" + File.separator + "Sprites" + File.separator + efectoActual.toString() + "_IZQUIERDA.gif";
            actualizarPosicion(-1);
        }
        this.setText("<html><img src='file:" + rutaEfecto + "' width='50' height='50'></html>");
        setVisible(true);
    }
    
    private void actualizarPosicion(int mirada) {
        int x = Math.round(jugador.getPosicion().getX());
        int y = Math.round(jugador.getPosicion().getY());
        if(mirada==1)
            setBounds(x - 7, y + 40, 40, 35);
        else
            setBounds(x + 7, y + 40, 40, 35);
    }
    
}
