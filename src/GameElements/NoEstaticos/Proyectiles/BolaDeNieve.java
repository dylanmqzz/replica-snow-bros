package GameElements.NoEstaticos.Proyectiles;

import Enumerados.EstadoSprite;
import Enumerados.Eventos;
import GameElements.Estaticos.Decoracion.Obstaculo.Pared;
import GameElements.Estaticos.Decoracion.Obstaculo.ParedDestructible;
import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Obstaculo.Trampa;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import Utils.ConstantesSonidos;
import Utils.ReproductorSonido;
import Utils.Vector2;
import Interface.InterfacesDeColision.Colisionador;
import Grafica.Sprite;

public class BolaDeNieve extends Proyectil {
    protected ReproductorSonido sonido = ReproductorSonido.getInstancia();
    protected boolean doble;

    public BolaDeNieve(Vector2 posInicial, Vector2 direccion) {
        super(posInicial, direccion);
        WIDGHT = 30;
        HEIGHT = 30;
        doble = false;
        zOrder = 0;
    }

    public void setSprite(Sprite s) {
        super.setSprite(s);
    }

    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

    public boolean dobleDaño() {
        return doble;
    }

    @Override
    public void colisionar(Plataforma p) {
        cambiarEstadoSprite(EstadoSprite.EXPLOSION);
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
        p.RestarResistencia();
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

    public void activarDoble() {
        doble = true;
    }

    public void eliminar() {
        miJuego.getModoDeJuego().getNivel().eliminarProyectil(this);
        notify(Eventos.ELIMINACION);
        observers.clear();
        notify(Eventos.ACTUALIZACION_GRAFICA);
        activa = false;
        sonido.reproducirSonido(ConstantesSonidos.BolaDeNieveDesaparece);
        hitbox = null;
    }

}
