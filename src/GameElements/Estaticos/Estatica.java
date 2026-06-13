package GameElements.Estaticos;

import Enumerados.Eventos;
import GameElements.GameElement;
import Interface.InterfacesDeColision.Colisionable;
import Interface.Visitors.Registrador;
import Juego.Nivel;
import Utils.Vector2;

public abstract class Estatica extends GameElement implements Colisionable {
    protected int tiempoActual;

    public Estatica(Vector2 posicion) {
        super(posicion);
        zOrder = 2;
    }

    public void desplazar() {

    }

    @Override
    public void aceptarRegistro(Registrador r) {
        r.registrar(this);
    }

    public void decrementarTiempoDeVida() {

    }

    @Override
    public void eliminar() {
        Nivel nivel = miJuego.getModoDeJuego().getNivel();
        if (nivel != null)
            nivel.eliminarEstatico(this);
        notify(Eventos.ELIMINACION);
        hitbox = null;
    }

}
