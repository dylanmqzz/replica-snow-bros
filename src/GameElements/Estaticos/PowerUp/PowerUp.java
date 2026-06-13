package GameElements.Estaticos.PowerUp;

import GameElements.HitBox;
import GameElements.Estaticos.Estatica;
import Utils.Vector2;
import Enumerados.Efectos;
import Enumerados.Eventos;

public abstract class PowerUp extends Estatica {
    protected Efectos efecto;
    protected boolean activo;
    protected int tiempoRestante;
    protected static final int tiempoDeVida = 500;

    public PowerUp(Vector2 posicion) {
        super(posicion);
        activo = false;
        hitbox = new HitBox(40, 40, posicion);
        tiempoActual = tiempoDeVida;
        puntos = 300;
    }

    public void decrementarTiempoDeVida() {
        tiempoActual--;
        if (tiempoActual == 0)
            eliminar();
    }

    public Efectos getEfecto() {
        miJuego.getJugador().sumarPuntaje(puntos);
        notify(Eventos.PUNTOS);
        return efecto;
    }

    public void eliminar() {
        super.eliminar();
    }

}
