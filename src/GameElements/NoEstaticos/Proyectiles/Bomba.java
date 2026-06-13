package GameElements.NoEstaticos.Proyectiles;

import Enumerados.Eventos;
import GameElements.HitBox;
import GameElements.Estaticos.Decoracion.Obstaculo.Pared;
import GameElements.Estaticos.Decoracion.Obstaculo.ParedDestructible;
import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Obstaculo.Trampa;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import Utils.Vector2;
import Interface.InterfacesDeColision.Colisionador;
import Movimiento.Estados.Estado;
import Movimiento.Estados.Normal;

public class Bomba extends Proyectil {
    protected Vector2 direccion;
    protected Estado estado;

    public Bomba(final Vector2 pos, Vector2 dire) {
        super(pos, dire);
        HEIGHT = 30;
        WIDGHT = 30;
        hitbox = new HitBox(28, 28, pos, new Vector2(1, 1));
        direccion = dire;
        estado = new Normal(this);
        enSuelo = true;
    }

    public void mover() {
        estado.getEstrategiaMovimiento().actualizarMovimiento(direccion, true);
        estado.getEstrategiaMovimiento().efectuarMovimiento();
        hitbox.setPosition(posicion.clone());
        enSuelo = false;
        notify(Eventos.ACTUALIZACION_GRAFICA);
        notify(Eventos.COLISION);
    }

    @Override
    public void colisionar(Plataforma p) {
        eliminar();
    }

    @Override
    public void colisionar(SueloResbaladizo pr) {
        eliminar();
    }

    @Override
    public void colisionar(PlataformaQuebradiza pq) {
        eliminar();
    }

    public void colisionar(PlataformaMovil pm) {
        eliminar();
    }

    @Override
    public void colisionar(Trampa t) {
        eliminar();
    }

    @Override
    public void colisionar(Pared p) {
        eliminar();
    }

    @Override
    public void colisionar(ParedDestructible p) {
        eliminar();
    }

    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

}
