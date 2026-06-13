package GameElements.Estaticos.PowerUp;

import Enumerados.Efectos;
import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;

public class PowerUpVidaExtra extends PowerUp {
    public PowerUpVidaExtra(Vector2 pos) {
        super(pos);
        efecto = Efectos.VIDA_EXTRA;
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

}
