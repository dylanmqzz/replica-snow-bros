package GameElements.Estaticos.PowerUp;

import Enumerados.Efectos;
import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;

public class PowerUpFruta extends PowerUp {
    public PowerUpFruta(Vector2 pos) {
        super(pos);  
        efecto = Efectos.FRUTA; 
        puntos = 500;  
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

}
