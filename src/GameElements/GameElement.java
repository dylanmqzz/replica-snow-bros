package GameElements;

import java.util.LinkedList;
import Observers.*;
import Utils.Vector2;
import Enumerados.EstadoSprite;
import Enumerados.Eventos;
import Grafica.Sprite;
import Interface.InterfacesGameElement.LogicElement;
import Interface.Visitors.Registrable;
import Juego.Juego;

public abstract class GameElement implements LogicElement, Registrable {
    protected LinkedList<Observer> observers;
    protected int WIDGHT;
    protected int HEIGHT;
    protected HitBox hitbox;
    protected Sprite sprite;
    protected Vector2 posicion;
    protected Juego miJuego;
    protected int puntos;
    protected int zOrder;

    public GameElement(Vector2 v) {
        if (v != null)
            posicion = v;
        else
            posicion = new Vector2(0, 0);

        observers = new LinkedList<Observer>();
        sprite = null;
        hitbox = new HitBox(0, 0, v);
        WIDGHT = 40;
        HEIGHT = 40;
        puntos = 0;
        zOrder = 0;
    }

    public int getWidth() {
        return WIDGHT;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public void cambiarEstadoSprite(EstadoSprite nuevoEstado) {
        if (sprite != null) {
            sprite.setEstadoActual(nuevoEstado);
            notify(Eventos.ACTUALIZACION_GRAFICA);
        }
    }

    public LinkedList<Observer> observers() {
        return observers;
    }

    public void suscribe(Observer suscriptor) {
        observers.add(suscriptor);
    }

    public void unsuscribe(Observer suscriptor) {
        observers.remove(suscriptor);
    }

    public void notify(Eventos e) {
        for (Observer o : observers) {
            o.update(e);
        }
    }

    public void setSprite(Sprite s) {
        sprite = s;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public Juego getMiJuego() {
        return miJuego;
    }

    public void setJuego(Juego j) {
        miJuego = j;
    }

    public HitBox getHitbox() {
        return hitbox;
    }

    @Override
    public int getZOrder() {
        return zOrder;
    }

    public int getPuntos(){
        if (miJuego.getJugador().getDoblePuntaje())
            return puntos * 2;
        else
            return puntos;
    }

    public abstract void eliminar();

}
