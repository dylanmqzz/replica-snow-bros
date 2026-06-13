package Utils.Sensor;

import GameElements.GameElement;
import GameElements.HitBox;
import GameElements.Estaticos.Decoracion.Obstaculo.Escalera;
import GameElements.Estaticos.Decoracion.Obstaculo.Pared;
import GameElements.Estaticos.Decoracion.Obstaculo.ParedDestructible;
import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Obstaculo.Trampa;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import GameElements.Estaticos.PowerUp.PowerUp;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import GameElements.NoEstaticos.Proyectiles.BolaDeFuego;
import GameElements.NoEstaticos.Proyectiles.BolaDeNieve;
import GameElements.NoEstaticos.Proyectiles.Bomba;
import GameElements.NoEstaticos.Proyectiles.Fuego;
import Interface.InterfacesDeColision.Colisionador;
import Juego.ControladorColisiones;
import Utils.Vector2;

public abstract class Sensor implements Colisionador {
    protected GameElement propietario;
    protected ControladorColisiones controladorColisiones;
    protected HitBox hitbox;
    protected Vector2 posicion;
    protected Vector2 centroPropietario;

    public HitBox getHitbox() {
        return hitbox;
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
    public void colisionar(Fuego fuego) {

    }

    @Override
    public void colisionar(PowerUp P) {

    }

    @Override
    public void colisionar(Plataforma p) {

    }

    @Override
    public void colisionar(SueloResbaladizo pr) {

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
    public void colisionar(Enemigo e) {

    }

    public abstract void update();

}
