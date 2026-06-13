package GameElements.Estaticos.PowerUp;

import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;
import Enumerados.Efectos;

public class PowerUpAzul extends PowerUp {
    public PowerUpAzul(Vector2 pos) {
        super(pos);
        efecto = Efectos.VELOCIDAD;
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

}
