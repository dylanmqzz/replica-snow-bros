package Grafica.AnimationManagers;

import Enumerados.EstadoSprite;
import Grafica.Sprite;
import Utils.BehaviorTree.NodoAccion;
import Utils.BehaviorTree.NodoCondicional;
import Utils.BehaviorTree.NodoSelector;
import GameElements.NoEstaticos.Enemigos.Jefes.Kamakichi;

public class AnimationManagerKM extends AnimationManager {
    protected Kamakichi enemigo;

    public AnimationManagerKM(Sprite sprite, Kamakichi enemigo) {
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
        NodoAccion animMovimiento = new NodoAccion(() -> animMover());
        NodoCondicional seMueve = new NodoCondicional(animMovimiento, () -> seMueve());
        raizAnimacion.addChild(seMueve);
        // Rama Muerto
        NodoAccion animMuerto = new NodoAccion(() -> animQuieto());
        NodoCondicional muerto = new NodoCondicional(animMuerto, () -> muerto());
        raizAnimacion.addChild(muerto);
        // Rama Atacar
        NodoAccion animAtacar = new NodoAccion(() -> animAtacar());
        NodoCondicional ataca = new NodoCondicional(animAtacar, () -> atacar());
        raizAnimacion.addChild(ataca);
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

    protected boolean atacar() {
        return !enemigo.puedeAtacar() && enemigo.getResistencia() > 3;
    }

    protected boolean seMueve() {
        return enemigo.estaSaltando();
    }

    protected boolean sufrioGolpe() {
        return enemigo.sufrioGolpe();
    }

    protected int animMover() {
        sprite.setEstadoActual(EstadoSprite.SUBIDA);
        return 1;
    }

    protected int animMuerto() {
        sprite.setEstadoActual(EstadoSprite.MUERTE);
        return 1;
    }

    protected int animAtacar() {
        sprite.setEstadoActual(EstadoSprite.ATAQUE);
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
