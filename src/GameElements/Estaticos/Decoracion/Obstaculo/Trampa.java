package GameElements.Estaticos.Decoracion.Obstaculo;

import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;
import GameElements.HitBox;

public class Trampa extends Obstaculo {
    public Trampa(Vector2 pos) {
        super(pos);
        hitbox = new HitBox(20, 15, pos, new Vector2(7.5f, 20));
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

}
