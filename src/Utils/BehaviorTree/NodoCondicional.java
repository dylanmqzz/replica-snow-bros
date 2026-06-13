package Utils.BehaviorTree;

import java.util.function.Supplier;

public class NodoCondicional extends Nodo{
    protected Nodo hijo;
    protected Supplier<Boolean> condicion;

    public NodoCondicional(Nodo hijo, Supplier<Boolean> condicion) {
        this.condicion = condicion;
        this.hijo = hijo;
    }

    @Override
    public Status tick() {
        if (condicion.get()) 
            return hijo.tick();
        else 
            return Status.FAILURE;
    }
    
}
