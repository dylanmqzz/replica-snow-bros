package Movimiento.MovimientoEnemigos;

import java.util.Random;

import GameElements.NoEstaticos.Enemigos.Animales.Indestructibles.Calabaza;
import Utils.Vector2;
import Utils.BehaviorTree.NodoAccion;
import Utils.BehaviorTree.NodoCondicion;
import Utils.BehaviorTree.NodoCondicional;
import Utils.BehaviorTree.NodoSecuencia;
import Utils.BehaviorTree.NodoSelector;
import Utils.Sensor.SensorEntorno;
import Utils.Sensor.SensorManiobra;
import Utils.Sensor.VisorJugador;

public class IACalabaza extends IAEnemigo {
    protected final Vector2 DIRECCION_NULA = new Vector2(0, 0);
    protected int tiempoCooldownInvocacion;
    protected int cooldownInvocacion;
    protected Random rng;
    protected SensorManiobra sensorVertical;
    protected VisorJugador visor;
    protected float velocidadMovimiento;
    protected boolean subiendo;
    protected boolean bajando;
    protected float alturaObjetivo;
    protected Calabaza calabaza;

    public IACalabaza(Calabaza calabaza) {
        super(calabaza);
        tiempoCooldownInvocacion = 225;
        cooldownInvocacion = tiempoCooldownInvocacion;
        velocidadMovimiento = 2;
        direccion = new Vector2(-1, 0);
        rng = new Random();
        subiendo = false;
        bajando = false;
        this.calabaza = calabaza;
    }

    @Override
    protected void inicializarSensores() {
        sensor = new SensorEntorno(enemigo);
        sensorVertical = new SensorManiobra(enemigo, 500, 40);
        visor = new VisorJugador(enemigo, 200);
    }

    @Override
    public void ejecutar() {
        if (!enemigo.estaDetenido()) 
            cooldownInvocacion--;
        sensor.update();
        sensorVertical.update();
        raizComportamiento.tick();
    }

    @Override
    protected void definirComportamiento() {
        raizComportamiento = new NodoSelector();
        
        // Rama enemigo detenido
        NodoAccion detener = new NodoAccion(() -> detener());
        NodoCondicional enemigoDetenido = new NodoCondicional(detener, () -> enemigo.estaDetenido());
        raizComportamiento.addChild(enemigoDetenido);
        // Rama invocacion
        NodoSecuencia invocacion = new NodoSecuencia();
        invocacion.addChild(new NodoCondicion(() -> puedeInvocar()));
        invocacion.addChild(new NodoAccion(() -> invocar()));
        invocacion.addChild(new NodoAccion(() -> aumentarPoder()));
        raizComportamiento.addChild(invocacion);
        // Rama movimiento
        NodoSelector movimiento = new NodoSelector();
        raizComportamiento.addChild(movimiento);
        //-------- Rama subir
        NodoAccion subir = new NodoAccion(() -> subir());
        NodoCondicional estaSubiendo = new NodoCondicional(subir, () -> estaSubiendo());
        movimiento.addChild(estaSubiendo);
        //--------- Rama bajar
        NodoAccion bajar = new NodoAccion(() -> bajar());
        NodoCondicional estaBajando = new NodoCondicional(bajar, () -> estaBajando());
        movimiento.addChild(estaBajando);
        //-------- Rama movimiento por suelo (camino libre)
        NodoAccion continuarMovimiento = new NodoAccion(() -> mover());
        NodoCondicional caminoLibre = new NodoCondicional(continuarMovimiento, () -> caminoLibre());
        movimiento.addChild(caminoLibre);
        //-------- Rama cambiar direccion
        NodoSelector cambiarDireccion = new NodoSelector();
        movimiento.addChild(cambiarDireccion);
        //------------ Rama intentar subir
        NodoSecuencia intentarSubir = new NodoSecuencia();
        intentarSubir.addChild(new NodoCondicion(() -> puedeSubir()));
        intentarSubir.addChild(new NodoAccion(() -> inicarSubida()));
        cambiarDireccion.addChild(intentarSubir);
        //------------ Rama intentar bajar
        NodoSecuencia intentarBajar = new NodoSecuencia();
        intentarBajar.addChild(new NodoCondicion(() -> puedeBajar()));
        intentarBajar.addChild(new NodoAccion(() -> inicarBajada()));
        cambiarDireccion.addChild(intentarBajar);
        //------------ Rama invertir direccion
        cambiarDireccion.addChild(new NodoAccion(() -> cambiarDireccion()));
    }

    // Chequeos movimiento
    protected boolean caminoLibre() {
        return sensor.detectaPiso() && !sensor.detectaPared();
    }

    protected boolean puedeSubir() {
        float difAltura = visor.obtenerAlturaJugador() - enemigo.getPosicion().getY();
        boolean hayPiso = sensorVertical.detectarPisoArriba();
        boolean jugadorArriba = difAltura <= -60;
        boolean estaFlotando = !sensor.detectaPiso();
        return hayPiso && (jugadorArriba || estaFlotando);
    }

    protected boolean puedeBajar() {
        float difAltura = visor.obtenerAlturaJugador() - enemigo.getPosicion().getY();
        boolean hayPiso = sensorVertical.detectarPisoAbajo();
        boolean jugadorAbajo = difAltura >= 60;
        boolean estaFlotando = !sensor.detectaPiso();
        return hayPiso && (jugadorAbajo || estaFlotando);
    }    

    protected boolean estaSubiendo() {
        return subiendo;
    }

    protected boolean estaBajando() {
        return bajando;
    }

    // Chequeos por probabilidad
    protected boolean probabilidad50() {
        int n = rng.nextInt(10);
        return (n % 2) == 0;
    }

    // Chequeos por tiempo
    protected boolean puedeInvocar() {
        return cooldownInvocacion <= 0;
    }
    
    // Acciones movimiento
    protected int mover() {
        enemigo.procesarSolicitudMovimiento(direccion);
        enemigo.getVelocidad().setY(0);
        enemigo.mover();
        return 1;
    }

    protected int detener() {
        bajando = false;
        subiendo = false;
        calabaza.setPuedeColisionar(true);
        enemigo.procesarSolicitudMovimiento(DIRECCION_NULA);
        enemigo.mover();
        return 1;
    }

    protected int cambiarDireccion() {
        direccion.setX(direccion.getX() * (-1) );
        enemigo.procesarSolicitudMovimiento(direccion);
        enemigo.getVelocidad().setY(0);
        enemigo.mover();
        return 1;
    }

    protected int inicarSubida() {
        subiendo = true;
        alturaObjetivo = sensorVertical.getAlturaPisoArriba()-enemigo.getHeight();
        calabaza.setPuedeColisionar(false);
        return 1;
    }

    protected int subir() {
        enemigo.getVelocidad().setY(-velocidadMovimiento);
        enemigo.getVelocidad().setX(0);
        enemigo.mover();
        if (alturaAlcanzada (-1)) {
            subiendo = false;
            calabaza.setPuedeColisionar(true);
            enemigo.getVelocidad().setY(0);
        }
        return 1;
    }

    protected int inicarBajada() {
        bajando = true;
        alturaObjetivo = sensorVertical.getAlturaPisoAbajo()-enemigo.getHeight();
        calabaza.setPuedeColisionar(false);
        return 1;
    }

    protected int bajar() {
        enemigo.getVelocidad().setY(velocidadMovimiento);
        enemigo.getVelocidad().setX(0);
        enemigo.mover();
        if (alturaAlcanzada(1)) {
            bajando = false;
            calabaza.setPuedeColisionar(true);
            enemigo.getVelocidad().setY(0);
        }
        return 1;
    }

    // Acciones invocacion
    protected int invocar() {
        calabaza.invocarFantasma();
        return 1;
    }

    protected int aumentarPoder() {
        tiempoCooldownInvocacion = Math.round(tiempoCooldownInvocacion*0.9f);
        velocidadMovimiento = Math.round(velocidadMovimiento*1.15f);
        cooldownInvocacion = tiempoCooldownInvocacion;
        return 1;
    }

    protected boolean alturaAlcanzada(int dir) {
        float diferencia = alturaObjetivo - enemigo.getPosicion().getY();
        float margenError = 5;

        if (dir == 1) // Cayendo
            return Math.abs(diferencia) <= margenError;
        if (dir == -1) // Saltando
            return Math.abs(diferencia) <= margenError;
        return false;
    }
    
}
