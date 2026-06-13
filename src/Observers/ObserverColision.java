package Observers;

import Enumerados.Eventos;
import GameElements.GameElement;
import Juego.ControladorColisiones;

public class ObserverColision implements Observer {
    protected ControladorColisiones controladorColisiones;
    protected GameElement elementoObservado;

    public ObserverColision(ControladorColisiones controlador, GameElement elemento) {
        controladorColisiones = controlador;
        elementoObservado = elemento;
    }

    public void update(Eventos e) {
        if (e == Eventos.COLISION)
            controladorColisiones.buscarColisiones(elementoObservado);
    }
    
}
