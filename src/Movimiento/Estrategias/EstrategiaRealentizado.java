package Movimiento.Estrategias;

import GameElements.NoEstaticos.NoEstatico;

public class EstrategiaRealentizado extends EstrategiaMovimiento {
    public EstrategiaRealentizado(NoEstatico e) {
        super(e);
        velocidadCaminar = 1.5f;
        velocidadSalto = -7;
    }

}
