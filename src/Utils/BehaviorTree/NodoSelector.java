package Utils.BehaviorTree;

public class NodoSelector extends NodoCompuesto {

    public NodoSelector() {
        super();
    }

    @Override
    public Status tick() {
        Status resultado = Status.FAILURE;
        for (Nodo h : hijos) {
            resultado = h.tick();
            if (resultado == Status.SUCCES)
                break;
        }
        return resultado;
    }

}
