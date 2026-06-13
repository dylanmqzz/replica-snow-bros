package GameElements.Estaticos.Decoracion.Obstaculo;

import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;

public class Pared extends Obstaculo {
    public Pared(Vector2 pos) {
        super(pos);  
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }
    
}
