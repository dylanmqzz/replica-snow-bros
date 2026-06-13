package Movimiento.Estados;

import Movimiento.Estrategias.*;
import GameElements.NoEstaticos.NoEstatico;

public class Normal extends Estado {
    public Normal(NoEstatico e) {
        super(e);
        estrategia = new EstrategiaNormal(e);
    }

}
