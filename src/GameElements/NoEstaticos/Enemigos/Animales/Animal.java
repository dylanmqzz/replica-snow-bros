package GameElements.NoEstaticos.Enemigos.Animales;

import GameElements.HitBox;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import Utils.Vector2;

public abstract class Animal extends Enemigo {
    public Animal(Vector2 posicion) {
        super(posicion);
        WIDGHT=36;
        HEIGHT=36;
        hitbox = new HitBox(WIDGHT, HEIGHT, posicion);
        direccionMirada = new Vector2(1, 0);
        resistenciaMaxima = 3;
        resistencia = resistenciaMaxima;
    }

}
