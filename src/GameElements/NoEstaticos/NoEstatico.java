package GameElements.NoEstaticos;

import Enumerados.EstadoSprite;
import GameElements.GameElement;
import Grafica.Sprite;
import Interface.InterfacesDeColision.Colisionador;
import Interface.InterfacesGameElement.Movible;
import Movimiento.Estados.Estado;
import Utils.Vector2;

public abstract class NoEstatico extends GameElement implements Colisionador, Movible {
    protected boolean enSuelo;
    protected Estado estado;
    protected Vector2 direccionMirada;
    protected Vector2 direccion = new Vector2(0, 0);
    protected Vector2 velocidad = new Vector2(0, 0);

    public NoEstatico(Vector2 posicion) {
        super(posicion);
        zOrder = 1;
    }

    public void setSprite(Sprite s) {
        super.setSprite(s);
        cambiarEstadoSprite(EstadoSprite.BASE_DERECHA);
    }
    
    public boolean enSuelo() {
        return enSuelo;
    }

    public void setEnSuelo(boolean b) {
        enSuelo = b;
    }

    public void setEstado(Estado e) {
        estado = e;
    }

    public Vector2 getDireccion() {
        return direccion;
    }

    public Vector2 getDireccionMirada() {
        return direccionMirada;
    }

    public Vector2 getVelocidad() {
        return velocidad;
    }
    
    public void mover(){

    }
    
}
