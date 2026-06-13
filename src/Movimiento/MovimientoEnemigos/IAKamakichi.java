package Movimiento.MovimientoEnemigos;

import java.util.Random;

import Utils.Vector2;
import Utils.BehaviorTree.*;
import Utils.Sensor.CapturadorGolpes;
import Utils.Sensor.SensorEntorno;
import Utils.Sensor.SensorManiobra;
import GameElements.NoEstaticos.Enemigos.Jefes.Kamakichi;

public class IAKamakichi extends IAEnemigo {
    protected final float VELOCIDAD_MOVIMIENTO = 2;
    protected final float VELOCIDAD_MOVIMIENTO_ENFURECIDO = 3;
    protected final int COOLDOWN_ATAQUE = 150;
    protected final int COOLDOWN_ATAQUE_ENFURECIDO = 90;
    protected final int DURACION_PAUSA_POST_ATAQUE = 45;
    protected CapturadorGolpes capturadorGolpes;
    protected SensorManiobra sensorVertical;
    protected int timerAtaque;
    protected boolean subiendo;
    protected boolean bajando;
    protected float alturaObjetivo;
    protected int ataquesRestantes;
    protected Random rng;
    protected int pausaPostAtaque;
    protected Kamakichi kamakichi;

    public IAKamakichi(Kamakichi jefe) {
        super(jefe);
        direccion = new Vector2(0, -1);
        timerAtaque = 0;
        subiendo = false;
        bajando = false;
        alturaObjetivo = 0;
        ataquesRestantes = 0;
        rng = new Random();
        pausaPostAtaque = -1;
        kamakichi = jefe;
    }

    @Override
    protected void definirComportamiento() {
        raizComportamiento = new NodoSelector();

        // Rama de movimiento obligatorio post-ataque
        NodoSecuencia movimientoObligatorio = new NodoSecuencia();
        movimientoObligatorio.addChild(new NodoCondicion(this::pasoTiempoPostAtaque));
        movimientoObligatorio.addChild(new NodoAccion(this::cambiarDireccion));
        movimientoObligatorio.addChild(new NodoAccion(this::iniciarMovimiento));
        raizComportamiento.addChild(movimientoObligatorio);
        // Rama de movimiento vertical en curso
        NodoSelector movimientoEnCurso = new NodoSelector();
        movimientoEnCurso.addChild(new NodoCondicional(new NodoAccion(this::subir), this::estaSubiendo));
        movimientoEnCurso.addChild(new NodoCondicional(new NodoAccion(this::bajar), this::estaBajando));
        raizComportamiento.addChild(movimientoEnCurso);
        // Rama para ejecutar un ataque si quedan ataques pendientes
        NodoSecuencia ejecutarAtaque = new NodoSecuencia();
        ejecutarAtaque.addChild(new NodoCondicion(this::tieneAtaquesPendientes));
        ejecutarAtaque.addChild(new NodoAccion(this::detenerse));
        ejecutarAtaque.addChild(new NodoAccion(this::atacar));
        raizComportamiento.addChild(ejecutarAtaque);
        // Rama para iniciar un ciclo de ataque
        NodoSecuencia iniciarCicloAtaque = new NodoSecuencia();
        iniciarCicloAtaque.addChild(new NodoCondicion(this::puedeIniciarCicloAtaque));
        iniciarCicloAtaque.addChild(new NodoAccion(this::prepararAtaques));
        raizComportamiento.addChild(iniciarCicloAtaque);
        // Rama Inactivo (fallback): si no hace nada de lo anterior, se queda quieto.
        raizComportamiento.addChild(new NodoAccion(this::detenerse));
    }

    @Override
    protected void inicializarSensores() {
        sensor = new SensorEntorno(enemigo);
        sensorVertical = new SensorManiobra(enemigo, 500, 100);
        Vector2 posSensorGolpes = new Vector2(enemigo.getWidth() / 2, (enemigo.getHeight() / 2) + 12.5f);
        capturadorGolpes = new CapturadorGolpes(enemigo, posSensorGolpes, 60, 60);
    }

    @Override
    public void ejecutar() {
        sensor.update();
        sensorVertical.update();
        capturadorGolpes.update();

        if (timerAtaque > 0)
            timerAtaque--;
        if (pausaPostAtaque > 0) 
            pausaPostAtaque--;
        raizComportamiento.tick();
    }

    protected boolean puedeIniciarCicloAtaque() {
        return timerAtaque <= 0 && ataquesRestantes == 0;
    }

    protected boolean tieneAtaquesPendientes() {
        return ataquesRestantes > 0 && kamakichi.puedeAtacar();
    }

    protected boolean estaSubiendo() {
        return subiendo;
    }

    protected boolean estaBajando() {
        return bajando;
    }

    protected boolean pasoTiempoPostAtaque() {
        return pausaPostAtaque == 0;
    }

    protected boolean estaEnfurecido() {
        return enemigo.getResistencia() <= enemigo.resistenciaMaxima() / 2;
    }

    protected int atacar() {
        kamakichi.lanzar();
        ataquesRestantes--;
        if (ataquesRestantes == 0)
            pausaPostAtaque = DURACION_PAUSA_POST_ATAQUE;
        return 1;
    }

    protected int prepararAtaques() {
        ataquesRestantes = rng.nextInt(3) + 1;
        timerAtaque = -1;
        return 1;
    }

    protected int detenerse() {
        enemigo.getVelocidad().setX(0f);
        enemigo.getVelocidad().setY(0f);
        return 1;
    }

    protected int cambiarDireccion() {
        pausaPostAtaque = -1;
        direccion.setY(direccion.getY() * (-1));
        return 1;
    }

    protected int iniciarMovimiento() {
        if (direccion.getY() < 0 && sensorVertical.detectarPisoArriba())
            iniciarSubida();
        else if (direccion.getY() > 0 && sensorVertical.detectarPisoAbajo())
            iniciarBajada();
        else {
            direccion.setY(direccion.getY() * (-1));
            pausaPostAtaque = 1;
        }
        return 1;
    }

    protected int iniciarSubida() {
        subiendo = true;
        kamakichi.setSaltando(true);
        alturaObjetivo = sensorVertical.getAlturaPisoArriba() - enemigo.getHeight();
        kamakichi.setPuedeColisionar(false);
        return 1;
    }

    protected int subir() {
        float velocidadActual = estaEnfurecido() ? VELOCIDAD_MOVIMIENTO_ENFURECIDO : VELOCIDAD_MOVIMIENTO;
        enemigo.getVelocidad().setY(-velocidadActual);

        if (enemigo.getPosicion().getY() <= alturaObjetivo) {
            enemigo.getPosicion().setY(alturaObjetivo);
            subiendo = false;
            kamakichi.setSaltando(false);
            enemigo.getVelocidad().setY(0);
            kamakichi.setPuedeColisionar(true);
            timerAtaque = estaEnfurecido() ? COOLDOWN_ATAQUE_ENFURECIDO : COOLDOWN_ATAQUE;
        }
        return 1;
    }

    protected int iniciarBajada() {
        bajando = true;
        kamakichi.setSaltando(true);
        alturaObjetivo = sensorVertical.getAlturaPisoAbajo() - enemigo.getHeight();
        kamakichi.setPuedeColisionar(false);
        return 1;
    }

    protected int bajar() {
        float velocidadActual = estaEnfurecido() ? VELOCIDAD_MOVIMIENTO_ENFURECIDO : VELOCIDAD_MOVIMIENTO;
        enemigo.getVelocidad().setY(velocidadActual);

        if (enemigo.getPosicion().getY() >= alturaObjetivo) {
            enemigo.getPosicion().setY(alturaObjetivo);
            bajando = false;
            kamakichi.setSaltando(false);
            enemigo.getVelocidad().setY(0);
            kamakichi.setPuedeColisionar(true);
            timerAtaque = estaEnfurecido() ? COOLDOWN_ATAQUE_ENFURECIDO : COOLDOWN_ATAQUE;
        }
        return 1;
    }

    protected boolean alturaAlcanzada() {
        float diferencia = alturaObjetivo - enemigo.getPosicion().getY();
        float margenError = 5;
        return Math.abs(diferencia) <= margenError;
    }

}
