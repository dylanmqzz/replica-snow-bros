package Grafica.AnimationManagers;

import Enumerados.EstadoSprite;
import GameElements.NoEstaticos.Enemigos.Jefes.Moghera;
import Grafica.Sprite;
import Utils.BehaviorTree.NodoAccion;
import Utils.BehaviorTree.NodoCondicional;
import Utils.BehaviorTree.NodoSelector;

public class AnimationManagerMO extends AnimationManager {
    protected Moghera enemigo;

    public AnimationManagerMO(Sprite sprite, Moghera enemigo) {
        super(sprite);
        this.enemigo = enemigo;
    }

    @Override
    protected void armarArbolAnimacion() {
        raizAnimacion = new NodoSelector();

        //Rama perdida de vida
        NodoAccion animGolpe = new NodoAccion(() -> animGolpe());
        NodoCondicional sufrioGolpe = new NodoCondicional(animGolpe, () -> sufrioGolpe());
        raizAnimacion.addChild(sufrioGolpe);
        // Rama salto
        NodoAccion animSalto = new NodoAccion(() -> animSalto());
        NodoCondicional saltando = new NodoCondicional(animSalto, () -> saltando());
        raizAnimacion.addChild(saltando);
        // Rama cayendo
        NodoAccion animCaer = new NodoAccion(() -> animCaer());
        NodoCondicional cayendo = new NodoCondicional(animCaer, () -> cayendo());
        raizAnimacion.addChild(cayendo);
        // Rama Muerto
        NodoAccion animMuerto = new NodoAccion(() -> animMuerto());
        NodoCondicional muerto = new NodoCondicional(animMuerto, () -> muerto());
        raizAnimacion.addChild(muerto);
        // Rama Final
        NodoAccion animFinal = new NodoAccion(() -> animFinal());
        NodoCondicional llegaFinal = new NodoCondicional(animFinal, () -> Final());
        raizAnimacion.addChild(llegaFinal);
        // Rama quieto
        raizAnimacion.addChild(new NodoAccion(() -> animQuieto()));
    }

    @Override
    public void animar() {
        raizAnimacion.tick();
    }

    protected boolean muerto() {
        return enemigo.getResistencia() == 0;
    }

    protected boolean Final() {
        return enemigo.getResistencia() <= 3;
    }

    protected boolean seMueve() {
        return enemigo.getVelocidad().getX() != 0;
    }

    protected boolean saltando() {
        return enemigo.estaSaltando();
    }

    protected boolean cayendo() {
        return enemigo.estaCayendo();
    }

    protected boolean sufrioGolpe() {
        return enemigo.sufrioGolpe();
    }

    protected int animMover() {
        sprite.setEstadoActual(EstadoSprite.BASE);
        return 1;
    }

    protected int animSalto() {
        sprite.setEstadoActual(EstadoSprite.SALTO_IZQUIERDA);
        return 1;
    }

    protected int animCaer() {
        sprite.setEstadoActual(EstadoSprite.SALTO_IZQUIERDA);
        return 1;
    }

    protected int animMuerto() {
        sprite.setEstadoActual(EstadoSprite.MUERTE);
        return 1;
    }

    protected int animFinal() {
        sprite.setEstadoActual(EstadoSprite.FINAL);
        return 1;
    }

    protected int animQuieto() {
        sprite.setEstadoActual(EstadoSprite.BASE);
        return 1;
    }

    protected int animGolpe() {
        sprite.setEstadoActual(EstadoSprite.DANIO);
        return 1;
    }

}
