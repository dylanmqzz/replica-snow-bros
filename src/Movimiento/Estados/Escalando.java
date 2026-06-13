package Movimiento.Estados;

import GameElements.NoEstaticos.NoEstatico;
import Movimiento.Estrategias.*;

public class Escalando extends Estado {
    public Escalando(NoEstatico e) {
        super(e);
        estrategia = new EstrategiaEscalando(e);
    }

}
