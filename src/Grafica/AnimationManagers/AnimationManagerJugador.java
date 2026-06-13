package Grafica.AnimationManagers;

import Enumerados.EstadoSprite;
import GameElements.NoEstaticos.Jugador.Jugador;
import Grafica.Sprite;
import Utils.BehaviorTree.NodoAccion;
import Utils.BehaviorTree.NodoCondicional;
import Utils.BehaviorTree.NodoSelector;

public class AnimationManagerJugador extends AnimationManager {
    protected Jugador jugador;

    public AnimationManagerJugador(Sprite sprite, Jugador jugador) {
        super(sprite);
        this.jugador = jugador;
    }

    @Override
    public void animar() {
        raizAnimacion.tick();
    }

    @Override
    protected void armarArbolAnimacion() {
        raizAnimacion = new NodoSelector();

        // Rama muerte
        NodoCondicional estaMuerto = new NodoCondicional(new NodoAccion(() -> animMuerte()), () -> estaMuerto());
        raizAnimacion.addChild(estaMuerto);
        // Rama reaparicion
        NodoCondicional estaReapareciendo = new NodoCondicional(new NodoAccion(() -> animReaparece()), () -> reaparece());
        raizAnimacion.addChild(estaReapareciendo);
        // Rama ataque
        NodoSelector ataque = new NodoSelector();
        NodoCondicional estaAtacando = new NodoCondicional(ataque, () -> estaAtacando());
        raizAnimacion.addChild(estaAtacando);
        NodoCondicional lanzando = new NodoCondicional(new NodoAccion(() -> animLanzar()), () -> ataqueLanzamiento());
        NodoCondicional pateando = new NodoCondicional(new NodoAccion(() -> animPatear()), () -> ataquePatada());
        ataque.addChild(lanzando);
        ataque.addChild(pateando);
        // Rama escalar
        NodoCondicional estaEscalando = new NodoCondicional(new NodoAccion(() -> animEscalar()), () -> estaEscalando());
        raizAnimacion.addChild(estaEscalando);
        // Rama salto
        NodoCondicional estaSaltando = new NodoCondicional(new NodoAccion(() -> animSalto()), () -> estaSaltando());
        raizAnimacion.addChild(estaSaltando);
        // Rama caminar
        NodoCondicional estaCaminando = new NodoCondicional(new NodoAccion(() -> animCaminar()), () -> estaCaminando());
        raizAnimacion.addChild(estaCaminando);
        // Rama idle
        NodoAccion idle = new NodoAccion(() -> animIdle());
        raizAnimacion.addChild(idle);
    }

    public boolean estaEscalando() {
        return jugador.escalando();
    }

    public boolean reaparece() {
        return jugador.reaparece();
    }

    public boolean estaMuerto() {
        return jugador.getVidas() <= 0;
    }

    public boolean estaSaltando() {
        return jugador.estaSaltando();
    }

    public boolean estaAtacando() {
        return !jugador.getPuedeAtacar();
    }

    public boolean ataqueLanzamiento() {
        return jugador.getAtaque() == Jugador.Ataque.LANZAR;
    }

    public boolean ataquePatada() {
        return jugador.getAtaque() == Jugador.Ataque.PATEAR;
    }

    public boolean estaCaminando() {
        return jugador.getDireccion().getX() != 0;
    }

    public int animLanzar() {
        if (jugador.getDireccionMirada().getX() == 1)
            sprite.setEstadoActual(EstadoSprite.LANZAR_DERECHA);
        else
            sprite.setEstadoActual(EstadoSprite.LANZAR_IZQUIERDA);
        return 1;
    }

    public int animPatear() {
        if (jugador.getDireccionMirada().getX() == 1)
            sprite.setEstadoActual(EstadoSprite.PATEAR_DERECHA);
        else
            sprite.setEstadoActual(EstadoSprite.PATEAR_IZQUIERDA);
        return 1;
    }

    public int animSalto() {
        if (jugador.getDireccionMirada().getX() == 1)
            sprite.setEstadoActual(EstadoSprite.SALTO_DERECHA);
        else
            sprite.setEstadoActual(EstadoSprite.SALTO_IZQUIERDA);
        return 1;
    }

    public int animCaminar() {
        if (jugador.getDireccion().getX() > 0)
            sprite.setEstadoActual(EstadoSprite.DERECHA);
        else
            sprite.setEstadoActual(EstadoSprite.IZQUIERDA);
        return 1;
    }

    public int animIdle() {
        if (jugador.getDireccionMirada().getX() == 1)
            sprite.setEstadoActual(EstadoSprite.BASE_DERECHA);
        else
            sprite.setEstadoActual(EstadoSprite.BASE_IZQUIERDA);
        return 1;
    }

    public int animMuerte() {
        sprite.setEstadoActual(EstadoSprite.MUERTE);
        return 1;
    }

    public int animReaparece() {
        sprite.setEstadoActual(EstadoSprite.APARICION);
        return 1;
    }

    public int animEscalar() {
        sprite.setEstadoActual(EstadoSprite.ESCALANDO);
        return 1;
    }

}
