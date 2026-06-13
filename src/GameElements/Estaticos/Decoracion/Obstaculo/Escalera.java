package GameElements.Estaticos.Decoracion.Obstaculo;

import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;
import GameElements.HitBox;

public class Escalera extends Obstaculo {
    public Escalera(Vector2 pos) {
        super(pos);
        hitbox = new HitBox(18, 36, pos.clone(), new Vector2(11f, 0f));
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

}
