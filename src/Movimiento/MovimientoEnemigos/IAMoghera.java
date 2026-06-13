package Movimiento.MovimientoEnemigos;

import Utils.Vector2;
import Utils.BehaviorTree.NodoAccion;
import Utils.BehaviorTree.NodoCondicion;
import Utils.BehaviorTree.NodoSecuencia;
import Utils.BehaviorTree.NodoSelector;
import Utils.Sensor.SensorEntorno;
import Utils.Sensor.SensorManiobra;
import Utils.Sensor.VisorJugadorJefes;
import GameElements.NoEstaticos.Enemigos.Jefes.Moghera;

public class IAMoghera extends IAEnemigo {
    protected final int COLDOWN_SALTO = 120;
    protected final int UMBRAL_ATASCO = 18;
    protected VisorJugadorJefes visor;
    protected SensorManiobra sensorVertical;
    protected int tiempoUltimaColision;
    protected boolean atascado;
    protected float alturaObjetivo;
    protected int cooldownVertical;
    protected Moghera moghera;

    public IAMoghera(Moghera jefe) {
        super(jefe);
        direccion = new Vector2(-1, 0);
        inicializarSensores();
        tiempoUltimaColision = UMBRAL_ATASCO;
        alturaObjetivo = 0;
        cooldownVertical = 0;
        moghera = jefe;
    }

    public void ejecutar() {
        if (!(enemigo.getResistencia() == 0)) {
            sensor.update();
            sensorVertical.update();
            raizComportamiento.tick();
            if (cooldownVertical > 0)
                cooldownVertical--;
        }
    }

    protected void definirComportamiento() {
        raizComportamiento = new NodoSelector();

        // Rama Vertical
        NodoSelector movimientoVerticalEnCurso = new NodoSelector();
        // Subrama: Continuar saltando si ya está en el aire
        NodoSecuencia continuarSalto = new NodoSecuencia();
        continuarSalto.addChild(new NodoCondicion(() -> saltando()));
        continuarSalto.addChild(new NodoAccion(() -> salto()));
        movimientoVerticalEnCurso.addChild(continuarSalto);
        // Subrama: Continuar cayendo si ya está cayendo
        NodoSecuencia continuarCaida = new NodoSecuencia();
        continuarCaida.addChild(new NodoCondicion(() -> cayendo()));
        continuarCaida.addChild(new NodoAccion(() -> caer()));
        movimientoVerticalEnCurso.addChild(continuarCaida);
        raizComportamiento.addChild(movimientoVerticalEnCurso);
        // Rama: Iniciar nuevo salto
        NodoSecuencia intentarSubir = new NodoSecuencia();
        intentarSubir.addChild(new NodoCondicion(() -> puedeMoverseVertical())); // No está saltando/cayendo y cooldown
        intentarSubir.addChild(new NodoCondicion(() -> pisoArriba()));           // Hay plataforma arriba
        intentarSubir.addChild(new NodoAccion(() -> iniciarSalto()));            // Iniciar el salto
        raizComportamiento.addChild(intentarSubir);                              
        // Rama: Iniciar nueva Caida
        NodoSecuencia intentarCaer = new NodoSecuencia();
        intentarCaer.addChild(new NodoCondicion(() -> puedeMoverseVertical())); // No está saltando/cayendo y cooldown
        intentarCaer.addChild(new NodoCondicion(() -> pisoAbajo()));            // Hay plataforma arriba
        intentarCaer.addChild(new NodoAccion(() -> inicarCaida()));             // Iniciar el salto
        raizComportamiento.addChild(intentarCaer);                              
        // Rama Lanzamiento
        NodoSecuencia ataque = new NodoSecuencia();
        ataque.addChild(new NodoCondicion(() -> visualizaJugador()));
        ataque.addChild(new NodoAccion(() -> detenerse()));
        ataque.addChild(new NodoAccion(() -> atacar()));
        raizComportamiento.addChild(ataque);
    }

    @Override
    protected void inicializarSensores() {
        sensor = new SensorEntorno(enemigo);
        sensorVertical = new SensorManiobra(enemigo, 500, 90);
        visor = new VisorJugadorJefes(enemigo, 600);
    }

    protected boolean puedeIniciarMovimientoVertical() {
        if (cooldownVertical > 0)
            cooldownVertical--;
        return cooldownVertical == 0;
    }

    public int movimientoNulo() {
        return 1;
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
        boolean puede = false;
        if (enemigo.getResistencia() > 3)
            puede = cooldownVertical == 0;
        return puede;
    }

    // Chequeos de suelo
    protected boolean saltando() {
        return moghera.estaSaltando();
    }

    public boolean pisoAbajo() {
        return sensorVertical.detectarPisoAbajo();
    }

    public boolean pisoArriba() {
        return sensorVertical.detectarPisoArriba();
    }

    protected boolean cayendo() {
        return moghera.estaCayendo();
    }

    public boolean visualizaJugador() {
        return visor.objetivoALaVista();
    }

    public int atacar() {
        if (moghera.puedeAtacar())
            moghera.lanzar(direccion);
        return 1;
    }

    public boolean pisoMismaAltura() {
        return sensor.detectaPiso();
    }

    public boolean hayPared() {
        return sensor.detectaPared();
    }

    protected int movimientoCongelado() {
        enemigo.procesarSolicitudMovimiento(direccion);
        enemigo.mover();
        return 1;
    }

    protected int detenerse() {
        enemigo.getVelocidad().setX(0);
        return 1;
    }

    protected int inicarCaida() {
        cooldownVertical = COLDOWN_SALTO;
        moghera.setAreaSinColision(enemigo.getPosicion().getY() + enemigo.getHeight());
        moghera.setPuedeColisionar(false);
        moghera.setCayendo(true);
        alturaObjetivo = sensorVertical.getAlturaPisoAbajo() - enemigo.getHeight();
        return 1;
    }

    protected int caer() {
        enemigo.getVelocidad().setY(5);
        enemigo.mover();
        if (enemigo.getPosicion().getY() >= alturaObjetivo) {
            enemigo.getPosicion().setY(alturaObjetivo);
            enemigo.getVelocidad().setY(0);
            moghera.setPuedeColisionar(true);
            moghera.setCayendo(false);
        }
        return 1;
    }

    protected int iniciarSalto() {
        cooldownVertical = COLDOWN_SALTO;
        moghera.setAreaSinColision(sensorVertical.getAlturaPisoArriba());
        moghera.setPuedeColisionar(false);
        moghera.setSaltando(true);
        alturaObjetivo = sensorVertical.getAlturaPisoArriba() - enemigo.getHeight();
        return 1;
    }

    protected int salto() {
        enemigo.getVelocidad().setY(-7);
        enemigo.mover();
        if (alturaAlcanzada(-1)) {
            moghera.setPuedeColisionar(true);
            moghera.setSaltando(false);
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
