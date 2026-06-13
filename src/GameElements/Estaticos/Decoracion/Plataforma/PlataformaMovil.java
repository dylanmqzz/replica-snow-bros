package GameElements.Estaticos.Decoracion.Plataforma;

import Enumerados.Eventos;
import GameElements.HitBox;
import GameElements.Estaticos.Decoracion.Obstaculo.Escalera;
import GameElements.Estaticos.Decoracion.Obstaculo.Pared;
import GameElements.Estaticos.Decoracion.Obstaculo.ParedDestructible;
import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Obstaculo.Trampa;
import GameElements.Estaticos.PowerUp.PowerUp;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import GameElements.NoEstaticos.Proyectiles.BolaDeFuego;
import GameElements.NoEstaticos.Proyectiles.BolaDeNieve;
import GameElements.NoEstaticos.Proyectiles.Bomba;
import GameElements.NoEstaticos.Proyectiles.Fuego;
import Interface.InterfacesDeColision.Colisionador;
import Interface.Visitors.Registrador;
import Utils.Vector2;

public class PlataformaMovil extends Plataforma implements Colisionador {
    protected Vector2 direccion;
    protected boolean fueColisionada;

    public PlataformaMovil(Vector2 pos) {
        super(pos);
        hitbox = new HitBox(36, 36, pos);
        direccion = new Vector2(1, 0);
        fueColisionada = false;
        puntos = 200;
    }

    public void aceptarRegistro(Registrador r) {
        r.registrar(this);
    }

    public boolean yaColisiono() {
        return fueColisionada;
    }

    public void setFueColisionada(boolean b) {
        fueColisionada = b;
        notify(Eventos.PUNTOS);
    }

    public void desplazar() {
        posicion.setX(posicion.getX() + direccion.getX());
        notify(Eventos.ACTUALIZACION_GRAFICA);
        notify(Eventos.COLISION);
        hitbox.setPosition(posicion);
    }

    public void setDireccion(Vector2 v) {
        direccion = v;
    }

    public Vector2 getDireccion() {
        return direccion;
    }

    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

    public void colisionar(ParedDestructible p) {
        direccion.setX(direccion.getX() * (-1));
    }

    public void colisionar(Pared p) {
        direccion.setX(direccion.getX() * (-1));
    }

    public void colisionar(Trampa t) {

    }

    public void colisionar(Escalera e) {
        direccion.setX(direccion.getX() * (-1));
    }

    public void colisionar(SueloResbaladizo pr) {
        direccion.setX(direccion.getX() * (-1));
    }

    public void colisionar(PlataformaQuebradiza pq) {
        direccion.setX(direccion.getX() * (-1));
    }

    public void colisionar(Plataforma p) {
        direccion.setX(direccion.getX() * (-1));
    }

    public void colisionar(Enemigo e) {

    }

    public void colisionar(PowerUp p) {

    }

    public void colisionar(PlataformaMovil pm) {

    }

    public void colisionar(BolaDeFuego bf) {

    }

    public void colisionar(Bomba b) {

    }

    public void colisionar(BolaDeNieve b) {

    }

    public void colisionar(Fuego f) {

    }
    
}
