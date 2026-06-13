package GameElements.Estaticos.Decoracion.Obstaculo;

import GameElements.HitBox;
import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;

public class SueloResbaladizo extends Obstaculo {
    public SueloResbaladizo(Vector2 pos) {
        super(pos);
        hitbox = new HitBox(36, 30, posicion.clone(), new Vector2(2,0));
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }
    
}
