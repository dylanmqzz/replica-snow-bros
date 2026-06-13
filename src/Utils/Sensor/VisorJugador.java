package Utils.Sensor;

import GameElements.HitBox;
import GameElements.Estaticos.Decoracion.Obstaculo.Pared;
import GameElements.Estaticos.Decoracion.Obstaculo.ParedDestructible;
import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Obstaculo.Trampa;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import GameElements.NoEstaticos.Jugador.Jugador;
import Utils.Vector2;

public class VisorJugador extends Sensor {
    protected Enemigo propietario;
    protected Jugador objetivo;
    protected boolean objetoEnMedio;
    protected float rangoVision;

    public VisorJugador(Enemigo propietario, float rango) {
        this.propietario = propietario;
        controladorColisiones = propietario.getMiJuego().getControladorColisiones();
        objetivo = propietario.getMiJuego().getJugador();
        rangoVision = rango;
        hitbox = new HitBox(rango, 10, propietario.getPosicion().clone());
    }

    protected void actualizarPosicion() {
        if (propietario.getDireccionMirada().getX() == -1)
            hitbox.getPosition().setX(propietario.getPosicion().getX() - rangoVision);
        else
            hitbox.getPosition().setX(propietario.getPosicion().getX() - propietario.getHitbox().getBase());
        hitbox.getPosition().setY(propietario.getPosicion().getY());
    }

    public boolean objetivoEnRango() {
        float distanciaObj = propietario.getPosicion().direccionHacia(objetivo.getPosicion()).modulo();
        if (distanciaObj <= rangoVision)
            return true;
        return false;
    }

    public boolean objetivoALaVista() {
        update();
        if (!objetivoEnRango() || objetoEnMedio)
            return false;
        Vector2 dirObjetivo = propietario.getPosicion().direccionHacia(objetivo.getPosicion());
        if (dirObjetivo.getX() * propietario.getDireccionMirada().getX() > 0) {
            if (Math.abs(dirObjetivo.getY()) <= 10)
                return true;
        }
        return false;
    }

    public float obtenerAlturaJugador() {
        return objetivo.getPosicion().getY();
    }

    public int obtenerDireccionJugador() {
        float dir = propietario.getPosicion().direccionHacia(objetivo.getPosicion()).getX();
        return dir > 0 ? 1 : -1;
    }

    public Vector2 obtenerPosicionJugador() {
        return objetivo.getPosicion();
    }

    @Override
    public void update() {
        objetoEnMedio = false;
        actualizarPosicion();
        controladorColisiones.buscarColisiones(this);
    }

    @Override
    public void colisionar(Plataforma p) {
        objetoEnMedio = true;
    }

    @Override
    public void colisionar(SueloResbaladizo pr) {
        objetoEnMedio = true;
    }

    @Override
    public void colisionar(PlataformaQuebradiza pq) {
        objetoEnMedio = true;
    }

    @Override
    public void colisionar(PlataformaMovil pm) {
        objetoEnMedio = true;
    }

    @Override
    public void colisionar(Trampa t) {
        objetoEnMedio = true;
    }

    @Override
    public void colisionar(Pared p) {
        objetoEnMedio = true;
    }

    @Override
    public void colisionar(ParedDestructible p) {
        objetoEnMedio = true;
    }

}
