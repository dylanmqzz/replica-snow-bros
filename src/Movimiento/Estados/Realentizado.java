package Movimiento.Estados;

import GameElements.NoEstaticos.NoEstatico;
import Movimiento.Estrategias.*;

public class Realentizado extends Estado {
    public Realentizado(NoEstatico e) {
        super(e);
        estrategia = new EstrategiaRealentizado(e);
    }

}
