package Grafica.AnimationManagers;

import Grafica.Sprite;
import Utils.BehaviorTree.NodoSelector;

public abstract class AnimationManager {
    protected NodoSelector raizAnimacion;
    protected Sprite sprite;

    public AnimationManager(Sprite sprite) {
        this.sprite = sprite;
        armarArbolAnimacion();
    }

    protected abstract void armarArbolAnimacion();

    public abstract void animar();
    
}
