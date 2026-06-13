package Utils.BehaviorTree;

import java.util.function.Supplier;

public class NodoAccion extends Nodo {
    protected Supplier<Integer> accion;

    public NodoAccion(Supplier<Integer> accion) {
        this.accion = accion;
    }

    @Override
    public Status tick() {
        if (accion.get() == 1) 
            return Status.SUCCES;
        if (accion.get() == 0) 
            return Status.RUNNING;
        else 
            return Status.FAILURE;
    }
    
}
