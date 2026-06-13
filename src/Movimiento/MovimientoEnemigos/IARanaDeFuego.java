package Movimiento.MovimientoEnemigos;

import GameElements.NoEstaticos.Enemigos.Animales.RanaDeFuego;
import Utils.Vector2;
import Utils.BehaviorTree.NodoAccion;
import Utils.BehaviorTree.NodoCondicion;
import Utils.BehaviorTree.NodoCondicional;
import Utils.BehaviorTree.NodoSecuencia;
import Utils.BehaviorTree.NodoSelector;
import Utils.Sensor.SensorEntorno;
import Utils.Sensor.VisorJugador;

public class IARanaDeFuego extends IAEnemigo {
    protected final Vector2 DIRECCION_NULA = new Vector2(0, 0);
    protected final int UMBRAL_ATASCO = 18;
    protected VisorJugador visor;
    protected int tiempoUltimaColision;
    protected boolean atascado;
    protected float alturaObjetivo;
    protected RanaDeFuego rana;

    public IARanaDeFuego(RanaDeFuego rana) {
        super(rana);
        direccion = new Vector2(1, 0);
        tiempoUltimaColision = UMBRAL_ATASCO;
        alturaObjetivo = 0;
        this.rana = rana;
    }

    public void ejecutar() {
        sensor.update();
        tiempoUltimaColision++;
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
        // Rama Lanzamiento
        NodoSecuencia ataque = new NodoSecuencia();
        ataque.addChild(new NodoCondicion(() -> visualizaJugador()));
        ataque.addChild(new NodoAccion(() -> detenerse()));
        ataque.addChild(new NodoAccion(() -> atacar()));
        raizComportamiento.addChild(ataque);
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
        visor = new VisorJugador(enemigo, 100);
    }

    public boolean visualizaJugador() {
        return visor.objetivoALaVista();
    }

    public int atacar() {
        if (((RanaDeFuego)enemigo).puedeAtacar())
            rana.lanzar(direccion);
        return 1;
    }

    public boolean pisoMismaAltura() {
        return sensor.detectaPiso();
    }

    public boolean hayPared() {
        return sensor.detectaPared();
    }

    protected boolean estaAtascado() {
        return atascado;
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

    protected int caer() {
        direccion.setX(0);
        enemigo.procesarSolicitudMovimiento(direccion);
        enemigo.mover();
        return 1;
    }

    protected int detenerse() {
        enemigo.getVelocidad().setX(0);
        return 1;
    }

    protected int iniciarCorreccionMovimiento() {
        if (!hayPared()) {
            alturaObjetivo = enemigo.getPosicion().getY()+40;
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

    protected boolean chequearFinEscalada() {
        if (direccion.getY() == -1 && !enemigo.estaEnEscalera()) 
            return true;
        if (direccion.getY() == 1 && enemigo.enSuelo()) 
            return true;
        return false;
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
