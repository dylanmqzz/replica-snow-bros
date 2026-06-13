package GameElements.Estaticos.Decoracion.Obstaculo;

import GameElements.HitBox;
import GameElements.Estaticos.Decoracion.Decoracion;
import Utils.Vector2;

public abstract class Obstaculo extends Decoracion {
    public Obstaculo(Vector2 posicion) {
        super(posicion);
        hitbox = new HitBox(36, 36, posicion.clone());
    }
    
}
