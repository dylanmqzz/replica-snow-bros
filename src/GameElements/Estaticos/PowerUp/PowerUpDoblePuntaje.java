package GameElements.Estaticos.PowerUp;

import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;
import Enumerados.Efectos;

public class PowerUpDoblePuntaje extends PowerUp {
    public PowerUpDoblePuntaje(Vector2 pos) {
        super(pos);
        efecto = Efectos.DOBLE_PUNTOS;
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

}