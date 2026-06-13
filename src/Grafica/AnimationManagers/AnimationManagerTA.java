package Grafica.AnimationManagers;

import Enumerados.EstadoSprite;
import GameElements.NoEstaticos.Enemigos.Animales.TrollAmarillo;
import Grafica.Sprite;
import Utils.BehaviorTree.NodoAccion;
import Utils.BehaviorTree.NodoCondicional;
import Utils.BehaviorTree.NodoSelector;

public class AnimationManagerTA extends AnimationManager {
    protected TrollAmarillo enemigo;

    public AnimationManagerTA(Sprite sprite, TrollAmarillo enemigo) {
        super(sprite);
        this.enemigo = enemigo;
    }

    @Override
    protected void armarArbolAnimacion() {
        raizAnimacion = new NodoSelector();
        
        //Rama salto
        NodoAccion animSalto = new NodoAccion(() -> animSalto());
        NodoCondicional saltando = new NodoCondicional(animSalto, () -> saltando());
        raizAnimacion.addChild(saltando);
        //Rama cayendo
        NodoAccion animCaer = new NodoAccion(() -> animCaer());
        NodoCondicional cayendo = new NodoCondicional(animCaer, () -> cayendo());
        raizAnimacion.addChild(cayendo);
        //Rama movimiento
        NodoAccion animMover = new NodoAccion(() -> animMover());
        NodoCondicional estaMoviendose = new NodoCondicional(animMover, () -> seMueve());
        raizAnimacion.addChild(estaMoviendose);
        //Rama quieto
        raizAnimacion.addChild(new NodoAccion(() -> animQuieto()));
    }

    @Override
    public void animar() {
        raizAnimacion.tick();
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

    protected int animMover() {
        if (enemigo.getDireccionMirada().getX() == 1) 
            sprite.setEstadoActual(EstadoSprite.DERECHA);
        else 
            sprite.setEstadoActual(EstadoSprite.IZQUIERDA);
        return 1;
    }

    protected int animSalto() {
        if (enemigo.getDireccionMirada().getX() == 1) 
            sprite.setEstadoActual(EstadoSprite.SALTO_DERECHA);
        else 
            sprite.setEstadoActual(EstadoSprite.SALTO_IZQUIERDA);
        return 1;
    }

    protected int animCaer() {
        if (enemigo.getDireccionMirada().getX() == 1) 
            sprite.setEstadoActual(EstadoSprite.CAYENDO_DERECHA);
        else 
            sprite.setEstadoActual(EstadoSprite.CAYENDO_IZQUIERDA);
        return 1;
    }

    protected int animQuieto() {
        if (enemigo.getDireccionMirada().getX() == 1) 
            sprite.setEstadoActual(EstadoSprite.BASE_DERECHA);
        else 
            sprite.setEstadoActual(EstadoSprite.BASE_IZQUIERDA);
        return 1;
    }
    
}
