package GameElements.NoEstaticos.Enemigos.Jefes;

import Enumerados.Eventos;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;

public abstract class Jefe extends Enemigo {
    protected boolean puedeAtacar;
    protected boolean puedeColisionar;
    protected boolean saltando;
    protected boolean cayendo;
    protected int sufrioGolpe;

    public Jefe(Vector2 posicion) {
        super(posicion);
        puntos = 5000;
        puntosCongelado = 1000;
        resistenciaMaxima = 20;
        resistencia = resistenciaMaxima;
        puedeColisionar = true;
        saltando = false;
        cayendo = false;
        puedeAtacar = true;
        sufrioGolpe = 0;
    }

    public void restarResistencia() {
        if (resistencia > 0)
            resistencia--;
        sufrioGolpe = 5;
        if (resistencia == 0)
            eliminado = true;
    }

    public boolean sufrioGolpe() {
        return sufrioGolpe > 0;
    }

    public boolean puedeAtacar() {
        if (getResistencia() <= 3)
            puedeAtacar = false;
        return puedeAtacar;
    }

    public void setPuedeColisionar(boolean value) {
        puedeColisionar = value;
    }

    public void setSaltando(boolean value) {
        saltando = value;
    }

    public void setCayendo(boolean value) {
        cayendo = value;
    }

    public boolean estaSaltando() {
        return saltando;
    }

    public boolean estaCayendo() {
        return cayendo;
    }

    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

    public void eliminar() {
        notify(Eventos.PUNTOS);
        miJuego.getJugador().sumarPuntaje(puntos);
        miJuego.getModoDeJuego().getNivel().eliminarEnemigo(this);
        notify(Eventos.ACTUALIZACION_GRAFICA);
        notify(Eventos.ELIMINACION);
        observers.clear();
        hitbox = null;
    }

}
