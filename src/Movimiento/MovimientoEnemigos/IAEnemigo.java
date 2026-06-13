package Movimiento.MovimientoEnemigos;

import GameElements.NoEstaticos.Enemigos.Enemigo;
import Utils.Vector2;
import Utils.BehaviorTree.NodoCompuesto;
import Utils.Sensor.SensorEntorno;

public abstract class IAEnemigo {
    protected Enemigo enemigo;
    protected NodoCompuesto raizComportamiento;
    protected Vector2 direccion;
    protected SensorEntorno sensor;

    public IAEnemigo(Enemigo enemigo) {
        this.enemigo = enemigo;
        definirComportamiento();
        inicializarSensores();
    }

    protected abstract void definirComportamiento();

    protected abstract void inicializarSensores();

    public abstract void ejecutar();
    
}
