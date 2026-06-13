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
import GameElements.NoEstaticos.Proyectiles.Proyectil;
import Interface.InterfacesDeColision.Colisionador;
import Movimiento.MovimientoEnemigos.IACalabaza;
import Utils.Vector2;
import Enumerados.EstadoSprite;
import Enumerados.TipoElementos;
import GameElements.GameElement;
import GameElements.HitBox;

public class Calabaza extends Indestructible {
    protected boolean puedeColisionar;

    public Calabaza(Vector2 pos) {
        super(pos);
        HEIGHT = 36;
        WIDGHT = 24;
        hitbox = new HitBox(WIDGHT, HEIGHT, pos);
        puedeColisionar = true;
    }

    public void invocarFantasma() {
        GameElement fantasma = miJuego.getFabricaGameElements().crearElemento(TipoElementos.FANTASMA, posicion.clone());
        miJuego.getModoDeJuego().getNivel().agregarElemento(fantasma);
    }

    @Override
    public void accionarComportamiento() {
        if (ia == null)
            ia = new IACalabaza(this);
        ia.ejecutar();
    }

    @Override
    public void animar() {
        if (direccion.getX() == 1)
            sprite.setEstadoActual(EstadoSprite.DERECHA);
        else
            sprite.setEstadoActual(EstadoSprite.IZQUIERDA);
    }

    @Override
    public void empujar(float dir) {

    }

    public void setPuedeColisionar(boolean value) {
        puedeColisionar = value;
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

    @Override
    public void colisionar(Proyectil p) {

    }

    @Override
    public void colisionar(PowerUp P) {

    }

    @Override
    public void colisionar(Enemigo e) {

    }

    @Override
    public void colisionar(Plataforma p) {
        if (puedeColisionar) 
            super.colisionar(p);
    }
    
    @Override
    public void colisionar(SueloResbaladizo pr) {
        if (puedeColisionar) 
            super.colisionar(pr);
    }

    @Override
    public void colisionar(Escalera e) {

    }

    @Override
    public void colisionar(Trampa t) {

    }

    @Override
    public void colisionar(Pared p) {
        if (puedeColisionar) 
            super.colisionar(p);
    }

    @Override
    public void colisionar(ParedDestructible p) {
        if (puedeColisionar) 
            super.colisionar(p);
    }

    @Override
    public void colisionar(BolaDeFuego bolaf) {
    }

    @Override
    public void colisionar(BolaDeNieve bolan) {
        restarResistencia();
        if (bolan.dobleDaño())
            restarResistencia();
        bolan.eliminar();
    }

    @Override
    public void colisionar(Bomba bomba) {

    }
    
    @Override
    public void colisionar(Fuego fuego) {

    }

    @Override
    public void colisionar(PlataformaQuebradiza pq) {
        if (puedeColisionar) 
            super.colisionar(pq);
    }

    @Override
    public void colisionar(PlataformaMovil pm) {
        if (puedeColisionar) 
            super.colisionar(pm);  
    }
    
}
