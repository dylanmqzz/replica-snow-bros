package Movimiento.MovimientoEnemigos;

import GameElements.NoEstaticos.Enemigos.Enemigo;
import Utils.Vector2;
import Utils.BehaviorTree.NodoAccion;
import Utils.BehaviorTree.NodoCondicional;
import Utils.BehaviorTree.NodoSelector;
import Utils.Sensor.SensorEntorno;
import Utils.Sensor.VisorJugador;

public class IADemonioRojo extends IAEnemigo {
    protected final int UMBRAL_ATASCO = 18;
    protected final Vector2 DIRECCION_NULA = new Vector2(0, 0);
    protected VisorJugador visor;
    protected int tiempoUltimaColision;
    protected boolean atascado;
    protected float alturaObjetivo;
    protected boolean escalando;

    public IADemonioRojo(Enemigo enemigo) {
        super(enemigo);
        direccion = new Vector2(-1, 0);
        tiempoUltimaColision = UMBRAL_ATASCO;
        alturaObjetivo = 0;
        escalando = false;
    }

    public void ejecutar() {
        sensor.update();
        tiempoUltimaColision++;
        raizComportamiento.tick();
    }

    @Override
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
        // Rama escalada
        NodoSelector movimientoEscalera = new NodoSelector();
        NodoAccion escalar = new NodoAccion(() -> escalar());
        NodoCondicional estaEscalando = new NodoCondicional(escalar, () -> estaEscalando());
        movimientoEscalera.addChild(estaEscalando);
        NodoAccion iniciarEscalada = new NodoAccion(() -> iniciarEscalada());
        NodoCondicional puedeEscalar = new NodoCondicional(iniciarEscalada, () -> puedeEscalar());
        movimientoEscalera.addChild(puedeEscalar);
        raizComportamiento.addChild(movimientoEscalera);
        // Rama desatascar
        NodoAccion corregirPosicion = new NodoAccion(() -> corregirMovimiento());
        NodoCondicional atascado = new NodoCondicional(corregirPosicion, () -> estaAtascado());
        raizComportamiento.addChild(atascado);
        // Rama patrullar
        NodoSelector patrullar = new NodoSelector();
        NodoCondicional condicionPisoMA = new NodoCondicional(patrullar, () -> pisoMismaAltura());
        NodoAccion cambiarDireccion = new NodoAccion(() -> cambiarDireccion());
        NodoCondicional condicionPared = new NodoCondicional(cambiarDireccion, () -> hayPared());
        NodoAccion continuarCaminando = new NodoAccion(() -> seguirCaminando());
        patrullar.addChild(condicionPared);
        patrullar.addChild(continuarCaminando);
        raizComportamiento.addChild(condicionPisoMA);
        raizComportamiento.addChild(cambiarDireccion);
    }

    @Override
    protected void inicializarSensores() {
        sensor = new SensorEntorno(enemigo);
        visor = new VisorJugador(enemigo, 120);
    }

    public boolean pisoMismaAltura() {
        return sensor.detectaPiso();
    }

    public boolean hayPared() {
        return sensor.detectaPared();
    }

    protected boolean puedeEscalar() {
        float difAlturaJugador = visor.obtenerAlturaJugador() - enemigo.getPosicion().getY();
        boolean jugadorDistintaAltura = Math.abs(difAlturaJugador) >= 60;
        boolean hayEscalera = enemigo.estaEnEscalera();
        if (difAlturaJugador > 0) hayEscalera = hayEscalera && sensor.detectaEscaleraParaBajar();
            return jugadorDistintaAltura && hayEscalera;
    }

    protected boolean estaAtascado() {
        return atascado;
    }

    protected boolean estaEscalando() {
        return escalando;
    }

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

    protected int movimientoCongelado() {
        direccion.setX(enemigo.getDireccionEmpuje());
        enemigo.procesarSolicitudMovimiento(direccion);
        enemigo.getVelocidad().setX(enemigo.getDireccionEmpuje() * 7);
        enemigo.mover();
        return 1;
    }

    protected int detener() {
        enemigo.procesarSolicitudMovimiento(DIRECCION_NULA);
        enemigo.mover();
        return 1;
    }

    protected int caer() {
        direccion.setX(0);
        enemigo.procesarSolicitudMovimiento(direccion);
        enemigo.mover();
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

    protected int iniciarEscalada() {
        centrarEnemigo();
        float dirEscalada = Math.signum(visor.obtenerAlturaJugador() - enemigo.getPosicion().getY());
        direccion.setY(dirEscalada);
        escalando = true;
        return 1;
    }

    protected int escalar() {
        enemigo.getVelocidad().setY(3 * direccion.getY());
        enemigo.getVelocidad().setX(0);
        enemigo.mover();
        if (chequearFinEscalada())
            escalando = false;
        return 1;
    }

    protected boolean chequearFinEscalada() {
        if (direccion.getY() == -1 && !enemigo.estaEnEscalera())
            return true;
        if (direccion.getY() == 1 && enemigo.enSuelo())
            return true;
        return false;
    }

    protected void centrarEnemigo() {
        int cellCoord = (int) enemigo.getPosicion().getX() / 40;
        if (direccion.getX() == -1)
            enemigo.getPosicion().setX(cellCoord * 40);
        else 
            enemigo.getPosicion().setX((cellCoord + 1) * 40);
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
