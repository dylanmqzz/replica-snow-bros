package Utils.BehaviorTree;

public class NodoSecuencia extends NodoCompuesto {
    protected Nodo hijoEnEjecucion;
    
    public NodoSecuencia() {
        super();
        hijoEnEjecucion = null;
    }

    @Override
    public Status tick() {
        Status resultado = Status.FAILURE;
        for (Nodo h : hijos) {
            if (hijoEnEjecucion != null) {
                if (hijoEnEjecucion == h) {
                    resultado = h.tick();
                    hijoEnEjecucion = null;
                } else 
                    resultado = Status.SUCCES;
            } else 
                resultado = h.tick();

            if (resultado == Status.RUNNING) {
                hijoEnEjecucion = h;
                break;
            }
            if (resultado == Status.FAILURE) 
                break;
        }
        return resultado;
    }

}
