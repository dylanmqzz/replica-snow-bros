package Movimiento.Estrategias;

import GameElements.NoEstaticos.NoEstatico;

public class EstrategiaSuperVelocidad extends EstrategiaMovimiento {
    public EstrategiaSuperVelocidad(NoEstatico e) {
        super(e);
        velocidadCaminar = 5;
        velocidadSalto = -7;
    }

}
