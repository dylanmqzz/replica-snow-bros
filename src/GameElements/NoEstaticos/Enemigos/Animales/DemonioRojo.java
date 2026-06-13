package GameElements.NoEstaticos.Enemigos.Animales;

import GameElements.Estaticos.Decoracion.Obstaculo.Escalera;
import Grafica.Sprite;
import Grafica.AnimationManagers.AnimationManagerDR;
import Movimiento.MovimientoEnemigos.IADemonioRojo;
import Utils.Vector2;

public class DemonioRojo extends Animal {
    public DemonioRojo(Vector2 pos) {
        super(pos);
        puntos = 300;
        puntosCongelado = 150;
    }

    public void accionarComportamiento() {
        if (ia == null)
            ia = new IADemonioRojo(this);
        ia.ejecutar();
    }

    @Override
    public void setSprite(Sprite s) {
        super.setSprite(s);
        animaciones = new AnimationManagerDR(sprite, this);
    }

    @Override
    public void colisionar(Escalera e) {
        enEscalera = true;
        if (hitbox.diferenciaAltura(e.getHitbox()) == (-1))
            resolverColision(e);
    }

}
