package GameElements.Estaticos.PowerUp;

import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;
import Enumerados.Efectos;

public class PowerUpRoja extends PowerUp {
    public PowerUpRoja(Vector2 pos) {
        super(pos);
        efecto = Efectos.MATA_RAPIDO;
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

}