package GameElements.Estaticos.Decoracion.Plataforma;

import Enumerados.Eventos;
import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;

public class PlataformaQuebradiza extends Plataforma {
    protected static final int tiempoDeVida = 30;
    protected boolean fueColisionado;

    public PlataformaQuebradiza(Vector2 pos) {
        super(pos);
        fueColisionado = false;
        tiempoActual = tiempoDeVida;
        puntos = 300;
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

    public void decrementarTiempoDeVida() {
        if (fueColisionado) {
            tiempoActual--;
            if (tiempoActual == 0)
                eliminar();
        }
    }

    public void pisoJugador() {
        fueColisionado = true;
    }

    public void eliminar() {
        notify(Eventos.ELIMINACION);
        notify(Eventos.ACTUALIZACION_GRAFICA);
        hitbox = null;
        miJuego.getJugador().sumarPuntaje(300);
        notify(Eventos.PUNTOS);
    }

}
