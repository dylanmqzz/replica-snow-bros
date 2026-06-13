package Utils.Sensor;

import GameElements.HitBox;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import GameElements.NoEstaticos.Jugador.Jugador;
import Utils.Vector2;

public class SensorAtaqueJugador extends Sensor {
    protected boolean enemigoCongelado;
    protected Enemigo enemigoEnRango;
    protected Jugador jugador;

    public SensorAtaqueJugador(Jugador propietario) {
        jugador = propietario;
        centroPropietario = new Vector2(0, 0);
        controladorColisiones = propietario.getMiJuego().getControladorColisiones();
        posicion = propietario.getPosicion().clone();
        hitbox = new HitBox(30, propietario.getHeight(), posicion);
        enemigoCongelado = false;
        enemigoEnRango = null;
        actualizarPosicion();
    }

    @Override
    public void update() {
        enemigoCongelado = false;
        enemigoEnRango = null;
        actualizarPosicion();
        controladorColisiones.buscarColisiones(this);
    }

    protected void actualizarPosicion() {
        int dirMirada = (int) jugador.getDireccionMirada().getX();
        float anchoPropietario = jugador.getWidth();
        float newX = jugador.getPosicion().getX() + anchoPropietario / 2;

        if (dirMirada == -1)
            newX -= hitbox.getBase();
        posicion.setX(newX);
        posicion.setY(jugador.getPosicion().getY());
    }

    public boolean detectaEnemigoCongelado() {
        return enemigoCongelado;
    }

    public Enemigo enemigoEnRango() {
        return enemigoEnRango;
    }

    @Override
    public void colisionar(Enemigo e) {
        if (e.estaCongelado()) {
            enemigoCongelado = true;
            enemigoEnRango = e;
        }
    }

}
