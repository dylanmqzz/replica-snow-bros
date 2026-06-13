package Utils.BehaviorTree;

import java.util.ArrayList;

public abstract class NodoCompuesto extends Nodo {
    protected ArrayList<Nodo> hijos;

    public NodoCompuesto() {
        hijos = new ArrayList<Nodo>();
    }

    public void addChild(Nodo h) {
        hijos.add(h);
    }
    
}
