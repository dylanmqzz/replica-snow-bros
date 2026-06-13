package GameElements.Estaticos.PowerUp;

import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;
import Enumerados.Efectos;

public class PowerUpVerde extends PowerUp {
    public PowerUpVerde(Vector2 pos) {
        super(pos);
        efecto = Efectos.DETENER_ENEMIGOS;
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }
    
}
