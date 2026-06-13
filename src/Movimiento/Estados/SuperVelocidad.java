package Movimiento.Estados;

import GameElements.NoEstaticos.NoEstatico;
import Movimiento.Estrategias.EstrategiaSuperVelocidad;

public class SuperVelocidad extends Estado {
    public SuperVelocidad(NoEstatico e) {
        super(e);
        estrategia = new EstrategiaSuperVelocidad(e);
    }
    
}
