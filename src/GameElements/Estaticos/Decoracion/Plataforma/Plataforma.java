package GameElements.Estaticos.Decoracion.Plataforma;

import GameElements.HitBox;
import GameElements.Estaticos.Decoracion.Decoracion;
import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;

public abstract class Plataforma extends Decoracion {
    public Plataforma(Vector2 posicion) {
        super(posicion);
        hitbox = new HitBox(36, 30, posicion.clone(), new Vector2(2, 0));
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

}
