package Movimiento.MovimientoEnemigos;

import GameElements.NoEstaticos.Enemigos.Animales.Indestructibles.Fantasma;
import Utils.Vector2;
import Utils.BehaviorTree.NodoAccion;
import Utils.BehaviorTree.NodoCondicional;
import Utils.BehaviorTree.NodoSelector;
import Utils.Sensor.SensorEntorno;
import Utils.Sensor.VisorJugador;

public class IAFantasma extends IAEnemigo {
    protected final int COOLDOWN_CAMBIO_DIRECCION = 30;
    protected VisorJugador visor;
    protected int timerCambioDireccion;
    protected float velocidadPersecucion = 2.5f;

    public IAFantasma(Fantasma fantasma) {
        super(fantasma);
        direccion = new Vector2(-1, 0);
        timerCambioDireccion = 0;
    }

    @Override
    public void ejecutar() {
        sensor.update();
        timerCambioDireccion--;
        raizComportamiento.tick();
    }

    @Override
    protected void inicializarSensores() {
        sensor = new SensorEntorno(enemigo);
        visor = new VisorJugador(enemigo, 500);
    }

    @Override
    protected void definirComportamiento() {
        raizComportamiento = new NodoSelector();

        // Rama cambio de direccion
        NodoAccion cambioDireccion = new NodoAccion(() -> cambiarDireccion());
        NodoCondicional puedeCambiarDireccion = new NodoCondicional(cambioDireccion, () -> puedeCambiarDireccion());
        raizComportamiento.addChild(puedeCambiarDireccion);
        // Rama movimiento
        NodoAccion mover = new NodoAccion(() -> mover());
        raizComportamiento.addChild(mover);
    }

    // Chequeos para el cambio de direccion
    protected boolean puedeCambiarDireccion() {
        return timerCambioDireccion <= 0;
    }

    // Acciones de movimiento
    protected int cambiarDireccion() {
        timerCambioDireccion = COOLDOWN_CAMBIO_DIRECCION;
        Vector2 nuevaDir = enemigo.getPosicion().direccionHacia(visor.obtenerPosicionJugador());
        nuevaDir.normalizar();
        direccion.copy(nuevaDir);
        enemigo.getDireccionMirada().setX(Math.signum(nuevaDir.getX()));
        return 1;
    }

    protected int mover() {
        enemigo.getVelocidad().copy(direccion);
        enemigo.getVelocidad().multiplicar(velocidadPersecucion);
        enemigo.mover();
        return 1;
    }
    
}
