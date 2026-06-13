package Grafica.AnimationManagers;

import Enumerados.EstadoSprite;
import GameElements.NoEstaticos.Enemigos.Animales.RanaDeFuego;
import Grafica.Sprite;
import Utils.BehaviorTree.NodoAccion;
import Utils.BehaviorTree.NodoCondicional;
import Utils.BehaviorTree.NodoSelector;

public class AnimationManagerRF extends AnimationManager {
    protected RanaDeFuego enemigo;

    public AnimationManagerRF(Sprite sprite, RanaDeFuego enemigo) {
        super(sprite);
        this.enemigo = enemigo;
    }

    @Override
    protected void armarArbolAnimacion() {
        raizAnimacion = new NodoSelector();
        
        //Rama ataque
        NodoAccion animAtaque = new NodoAccion(() -> animAtaque());
        NodoCondicional atacando = new NodoCondicional(animAtaque, () -> atacando());
        raizAnimacion.addChild(atacando);
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

    protected boolean atacando() {
        return !enemigo.puedeAtacar();
    }

    protected int animMover() {
        if (enemigo.getDireccionMirada().getX() == 1) 
            sprite.setEstadoActual(EstadoSprite.DERECHA);
        else 
            sprite.setEstadoActual(EstadoSprite.IZQUIERDA);
        return 1;
    }

    protected int animQuieto() {
        if (enemigo.getDireccionMirada().getX() == 1) 
            sprite.setEstadoActual(EstadoSprite.BASE_DERECHA);
        else 
            sprite.setEstadoActual(EstadoSprite.BASE_IZQUIERDA);
        return 1;
    }

    protected int animAtaque() {
        if (enemigo.getDireccionMirada().getX() == 1) 
            sprite.setEstadoActual(EstadoSprite.LANZAR_DERECHA);
        else 
            sprite.setEstadoActual(EstadoSprite.LANZAR_IZQUIERDA);
        return 1;
    }
    
}
