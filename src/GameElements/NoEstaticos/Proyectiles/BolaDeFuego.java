package GameElements.NoEstaticos.Proyectiles;

import Utils.Vector2;
import Interface.InterfacesDeColision.Colisionador;

public class BolaDeFuego extends Proyectil {
    public BolaDeFuego(Vector2 posInicial, Vector2 direccion) {
        super(posInicial, direccion);
        WIDGHT = 30;
        HEIGHT = 30;
    }

    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

}
