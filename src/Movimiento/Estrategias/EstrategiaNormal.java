package Movimiento.Estrategias;

import GameElements.NoEstaticos.*;

public class EstrategiaNormal extends EstrategiaMovimiento {
    public EstrategiaNormal(NoEstatico e) {
        super(e);
        velocidadCaminar = 3;
        velocidadSalto = -7;
    }

}
