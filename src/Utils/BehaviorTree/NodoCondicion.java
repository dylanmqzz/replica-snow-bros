package Utils.BehaviorTree;

import java.util.function.Supplier;

public class NodoCondicion extends Nodo {
    protected Supplier<Boolean> condicion;

    public NodoCondicion(Supplier<Boolean> condicion) {
        this.condicion = condicion;
    }

    @Override
    public Status tick() {
        if (condicion.get()) 
            return Status.SUCCES;
        else 
            return Status.FAILURE;
    }
    
}
