package Movimiento.MovimientoEnemigos;

import GameElements.NoEstaticos.Enemigos.Animales.TrollAmarillo;
import Utils.Vector2;
import Utils.BehaviorTree.NodoAccion;
import Utils.BehaviorTree.NodoCondicion;
import Utils.BehaviorTree.NodoCondicional;
import Utils.BehaviorTree.NodoSecuencia;
import Utils.BehaviorTree.NodoSelector;
import Utils.Sensor.SensorEntorno;
import Utils.Sensor.SensorManiobra;
import Utils.Sensor.VisorJugador;

public class IATrollAmarillo extends IAEnemigo {
    protected final int COLDOWN_SALTO = 60;
    protected final int UMBRAL_ATASCO = 18;
    protected final Vector2 DIRECCION_NULA = new Vector2(0, 0);
    protected VisorJugador visor;
    protected SensorManiobra sensorVeritcal;
    protected float alturaObjetivo;
    protected float distanciaObjetivo;
    protected int cooldownSalto;
    protected int tiempoUltimaColision;
    protected boolean atascado;
    protected TrollAmarillo troll;

    public IATrollAmarillo(TrollAmarillo troll) {
        super(troll);
        direccion = new Vector2(-1, 0);
        alturaObjetivo = 0;
        cooldownSalto = 0;
        tiempoUltimaColision = UMBRAL_ATASCO;
        atascado = false;
        distanciaObjetivo = 0;
        atascado = false;
        this.troll = troll;
    }

    public void ejecutar() {
        if (cooldownSalto > 0)
            cooldownSalto--;
        tiempoUltimaColision++;
        sensor.update();
        raizComportamiento.tick();
    }

    protected void definirComportamiento() {
        raizComportamiento = new NodoSelector();

        // Rama enemigo detenido
        NodoAccion detener = new NodoAccion(() -> detener());
        NodoCondicional enemigoDetenido = new NodoCondicional(detener, () -> enemigo.estaDetenido());
        raizComportamiento.addChild(enemigoDetenido);
        // Rama congelado
        NodoAccion movimientoCongelado = new NodoAccion(() -> movimientoCongelado());
        NodoCondicional congelado = new NodoCondicional(movimientoCongelado, () -> enemigo.estaCongelado());
        raizComportamiento.addChild(congelado);
        // Rama desatascar
        NodoAccion corregirPosicion = new NodoAccion(() -> corregirMovimiento());
        NodoCondicional atascado = new NodoCondicional(corregirPosicion, () -> estaAtascado());
        raizComportamiento.addChild(atascado);
        // Rama movimiento vertical
        NodoSelector movimientoVertical = new NodoSelector();
        NodoAccion salto = new NodoAccion(() -> salto());
        NodoCondicional estaSaltando = new NodoCondicional(salto, () -> saltando());
        movimientoVertical.addChild(estaSaltando);
        NodoAccion caer = new NodoAccion(() -> caer());
        NodoCondicional estaCayendo = new NodoCondicional(caer, () -> cayendo());
        movimientoVertical.addChild(estaCayendo);
        raizComportamiento.addChild(movimientoVertical);
        // Rama persecucion
        NodoSelector persecucion = new NodoSelector();
        NodoCondicional detectaJugador = new NodoCondicional(persecucion, () -> detectaJugador());
        raizComportamiento.addChild(detectaJugador);
        // ---- jugador en distinta altura
        NodoSelector busquedaVertical = new NodoSelector();
        NodoCondicional puedeMoverVertical = new NodoCondicional(busquedaVertical, () -> puedeMoverseVertical());
        NodoCondicional jugadorDistintaAltura = new NodoCondicional(puedeMoverVertical, () -> jugadorDistintaAltura());
        persecucion.addChild(jugadorDistintaAltura);
        // -------- jugador mas arriba
        NodoSecuencia intentarSubir = new NodoSecuencia();
        NodoCondicional jugadorArriba = new NodoCondicional(intentarSubir, () -> jugadorMasArriba());
        intentarSubir.addChild(new NodoCondicion(() -> pisoArriba()));
        intentarSubir.addChild(new NodoAccion(() -> iniciarSalto()));
        busquedaVertical.addChild(jugadorArriba);
        // -------- jugador mas abajo
        NodoSecuencia intentarBajar = new NodoSecuencia();
        NodoCondicional jugadorAbajo = new NodoCondicional(intentarBajar, () -> jugadorMasAbajo());
        intentarBajar.addChild(new NodoCondicion(() -> pisoAbajo()));
        intentarBajar.addChild(new NodoAccion(() -> inicarCaida()));
        busquedaVertical.addChild(jugadorAbajo);
        // Rama patrullaje
        NodoSelector patrullaje = new NodoSelector();
        raizComportamiento.addChild(patrullaje);
        NodoSelector mover = new NodoSelector();
        NodoAccion caminar = new NodoAccion(() -> seguirCaminando());
        NodoCondicional hayCamino = new NodoCondicional(caminar, () -> caminoLibre());
        mover.addChild(hayCamino);
        mover.addChild(new NodoAccion(() -> cambiarDireccion()));
        patrullaje.addChild(mover);
    }

    @Override
    protected void inicializarSensores() {
        sensor = new SensorEntorno(enemigo);
        visor = new VisorJugador(enemigo, 160);
        sensorVeritcal = new SensorManiobra(enemigo, 150, 40);
    }

    // Chequeos para persecucion
    public boolean detectaJugador() {
        return visor.objetivoEnRango();
    }

    public boolean jugadorDistintaAltura() {
        float difAltura = Math.abs(enemigo.getPosicion().getY() - visor.obtenerAlturaJugador());
        return difAltura >= 30;
    }

    public boolean jugadorMasArriba() {
        return (enemigo.getPosicion().getY() - visor.obtenerAlturaJugador()) >= 70;
    }

    public boolean jugadorMasAbajo() {
        return (enemigo.getPosicion().getY() - visor.obtenerAlturaJugador()) <= -70;
    }

    protected boolean puedeMoverseVertical() {
        return cooldownSalto == 0;
    }

    // Chequeos de suelo
    public boolean pisoMismaAltura() {
        return sensor.detectaPiso();
    }

    public boolean pisoAbajo() {
        sensorVeritcal.update();
        return sensorVeritcal.detectarPisoAbajo();
    }

    public boolean pisoArriba() {
        sensorVeritcal.update();
        return sensorVeritcal.detectarPisoArriba();
    }

    protected boolean estaAtascado() {
        return atascado;
    }

    // Chequeos para obstaculos
    public boolean hayPared() {
        return sensor.detectaPared();
    }

    public boolean caminoLibre() {
        return !sensor.detectaPared() && pisoMismaAltura();
    }

    // Chequeos movimiento vertical
    protected boolean saltando() {
        return troll.estaSaltando();
    }

    protected boolean cayendo() {
        return troll.estaCayendo();
    }

    // Acciones movimiento
    protected int seguirCaminando() {
        enemigo.procesarSolicitudMovimiento(direccion);
        enemigo.mover();
        return 1;
    }

    protected int cambiarDireccion() {
        direccion.setX(direccion.getX() * (-1));
        enemigo.procesarSolicitudMovimiento(direccion);
        enemigo.mover();
        if (tiempoUltimaColision < UMBRAL_ATASCO)
            iniciarCorreccionMovimiento();
        tiempoUltimaColision = 0;
        return 1;
    }

    protected int iniciarCorreccionMovimiento() {
        if (!hayPared()) {
            alturaObjetivo = enemigo.getPosicion().getY() + 40;
            atascado = true;
            return 1;
        }
        return 0;
    }

    protected int corregirMovimiento() {
        if (hayPared())
            direccion.setX(direccion.getX() * (-1));
        enemigo.procesarSolicitudMovimiento(direccion);
        enemigo.mover();
        if (alturaAlcanzada(1))
            atascado = false;
        return 1;
    }

    protected int movimientoCongelado() {
        direccion.setX(enemigo.getDireccionEmpuje());
        enemigo.procesarSolicitudMovimiento(direccion);
        enemigo.getVelocidad().setX(enemigo.getDireccionEmpuje() * 7);
        enemigo.mover();
        return 1;
    }

    protected int detener() {
        troll.setSaltando(false);
        troll.setCayendo(false);
        ((TrollAmarillo)enemigo).setPuedeColisionar(true);
        enemigo.procesarSolicitudMovimiento(DIRECCION_NULA);
        enemigo.mover();
        return 1;
    }

    protected int mirarJugador() {
        int dir = visor.obtenerDireccionJugador();
        direccion.setX(dir);
        return 1;
    }

    protected int inicarCaida() {
        cooldownSalto = COLDOWN_SALTO;
        troll.setAreaSinColision(enemigo.getPosicion().getY() + enemigo.getHeight());
        troll.setPuedeColisionar(false);
        troll.setCayendo(true);
        alturaObjetivo = sensorVeritcal.getAlturaPisoAbajo() - 40;
        return 1;
    }

    protected int caer() {
        enemigo.getVelocidad().setY(5);
        enemigo.getVelocidad().setX(direccion.getX());
        enemigo.mover();
        if (alturaAlcanzada(1)) {
            troll.setPuedeColisionar(true);
            troll.setCayendo(false);
        }
        return 1;
    }

    protected int iniciarSalto() {
        cooldownSalto = COLDOWN_SALTO;
        troll.setAreaSinColision(sensorVeritcal.getAlturaPisoArriba());
        troll.setPuedeColisionar(false);
        troll.setSaltando(true);
        alturaObjetivo = sensorVeritcal.getAlturaPisoArriba() - 40;
        return 1;
    }

    protected int salto() {
        enemigo.getVelocidad().setY(-7);
        enemigo.getVelocidad().setX(direccion.getX());
        enemigo.mover();
        if (alturaAlcanzada(-1)) {
            troll.setPuedeColisionar(true);
            troll.setSaltando(false);
        }
        return 1;
    }

    protected boolean alturaAlcanzada(int dir) {
        float diferencia = alturaObjetivo - enemigo.getPosicion().getY();
        float margenError = 10;

        if (dir == 1) // Cayendo
            return Math.abs(diferencia) <= margenError;
        if (dir == -1) // Saltando
            return Math.abs(diferencia) <= margenError;
        return false;
    }

}
