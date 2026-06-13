package GameElements.Estaticos.Decoracion.Obstaculo;

import Enumerados.Eventos;
import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;

public class ParedDestructible extends Pared {
    protected int resistencia;

    public ParedDestructible(Vector2 pos) {
        super(pos);
        resistencia = 5;
        puntos = 150;
    }
    
    public void RestarResistencia() {
        resistencia--;
        if (resistencia == 0)
            eliminar();
    }

    public void eliminar() {
        notify(Eventos.ELIMINACION);
        miJuego.getJugador().sumarPuntaje(150);
        notify(Eventos.PUNTOS);
        notify(Eventos.ACTUALIZACION_GRAFICA);
        hitbox = null;
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

}
