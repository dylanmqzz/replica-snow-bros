package GameElements.NoEstaticos.Enemigos.Animales.Indestructibles;

import GameElements.NoEstaticos.Enemigos.Animales.Animal;
import Utils.Vector2;
import GameElements.NoEstaticos.Proyectiles.Proyectil;

public abstract class Indestructible extends Animal {
    public Indestructible(Vector2 posicion) {
        super(posicion);
        puntos = 400;
        puntosCongelado = 200;
    }

    public void colisionar(Proyectil p) {
        
    }
    
}
