package GameElements.NoEstaticos.Proyectiles;

import GameElements.Estaticos.Decoracion.Obstaculo.Escalera;
import GameElements.Estaticos.Decoracion.Obstaculo.Pared;
import GameElements.Estaticos.Decoracion.Obstaculo.ParedDestructible;
import GameElements.Estaticos.Decoracion.Obstaculo.Trampa;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.PowerUp.PowerUp;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;
import GameElements.HitBox;

public class Fuego extends Proyectil {
    public Fuego(Vector2 pos, Vector2 direccion, int largo) {
        super(pos, direccion);
        WIDGHT = largo;
        hitbox = new HitBox(WIDGHT, 36, pos);
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

    @Override
    public void colisionar(BolaDeFuego bolaf) {

    }

    @Override
    public void colisionar(Bomba bomba) {

    }

    @Override
    public void colisionar(BolaDeNieve bolan) {

    }

    @Override
    public void colisionar(PowerUp P) {

    }

    @Override
    public void colisionar(Enemigo e) {

    }

    @Override
    public void colisionar(Plataforma p) {

    }

    @Override
    public void colisionar(SueloResbaladizo sr) {

    }

    @Override
    public void colisionar(PlataformaQuebradiza pq) {

    }

    @Override
    public void colisionar(PlataformaMovil pm) {

    }

    @Override
    public void colisionar(Escalera e) {

    }

    @Override
    public void colisionar(Trampa t) {

    }

    @Override
    public void colisionar(Pared p) {

    }

    @Override
    public void colisionar(ParedDestructible p) {

    }

    @Override
    public void colisionar(Fuego fuego) {

    }
    
}
