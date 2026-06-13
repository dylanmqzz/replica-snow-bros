package GameElements.NoEstaticos.Enemigos.Animales.Indestructibles;

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
import Movimiento.MovimientoEnemigos.IAFantasma;
import Utils.Vector2;
import Enumerados.EstadoSprite;
import GameElements.HitBox;

public class Fantasma extends Indestructible {
    public Fantasma(Vector2 pos) {
        super(pos);
        HEIGHT = 24;
        WIDGHT = 36;
        hitbox = new HitBox(WIDGHT, HEIGHT, pos);
    }

    @Override
    public void accionarComportamiento() {
        if (ia == null)
            ia = new IAFantasma(this);
        ia.ejecutar();
    }

    @Override
    public void animar() {
        if (direccionMirada.getX() == 1)
            sprite.setEstadoActual(EstadoSprite.DERECHA);
        else
            sprite.setEstadoActual(EstadoSprite.IZQUIERDA);
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
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

    public void colisionar(PlataformaMovil pm){

    }

    public void colisionar(PlataformaQuebradiza pq){

    }

    @Override
    public void colisionar(SueloResbaladizo pr) {

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
    public void colisionar(BolaDeFuego bolaf) {

    }

    @Override
    public void colisionar(BolaDeNieve bolan) {

    }
    
    @Override
    public void colisionar(Bomba bomba) {

    }

    @Override
    public void colisionar(Fuego fuego) {
        
    }
    
}
