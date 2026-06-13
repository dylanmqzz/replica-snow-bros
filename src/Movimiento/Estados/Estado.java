package Movimiento.Estados;

import GameElements.NoEstaticos.NoEstatico;
import Movimiento.Estrategias.*;

public abstract class Estado {
    protected EstrategiaMovimiento estrategia;
    protected NoEstatico entidad;

    public Estado(NoEstatico e) {
        entidad = e;
    }

    public EstrategiaMovimiento getEstrategiaMovimiento() {
        return estrategia;
    }

}
