package GameElements.NoEstaticos.Proyectiles;

import Enumerados.EstadoSprite;
import Enumerados.Eventos;
import GameElements.NoEstaticos.NoEstatico;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import Grafica.Sprite;
import Interface.Visitors.Registrador;
import Utils.Vector2;
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
import Interface.InterfacesDeColision.Colisionable;

public abstract class Proyectil extends NoEstatico implements Colisionable {
    protected static final float GRAVEDAD = 0.005f;
    protected static final float VELOCIDAD_BASE = 7.0f;
    protected boolean activa;
    protected boolean aplicarGravedad;

    public Proyectil(Vector2 posInicial, Vector2 direccion) {
        this(posInicial, direccion, true);
    }

    public Proyectil(Vector2 posInicial, Vector2 direccion, boolean conGravedad) {
        super(new Vector2(posInicial.getX(), posInicial.getY()));
        this.aplicarGravedad = conGravedad;

        float magnitud = (float) Math.sqrt(direccion.getX() * direccion.getX() + direccion.getY() * direccion.getY());
        if (magnitud > 0) {
            float dirNormX = direccion.getX() / magnitud;
            float dirNormY = direccion.getY() / magnitud;
            velocidad = new Vector2(dirNormX * VELOCIDAD_BASE, dirNormY * VELOCIDAD_BASE);
        } else
            velocidad = new Vector2(VELOCIDAD_BASE, 0);

        activa = true;
        hitbox = new HitBox(16, 16, posicion);
    }

    public boolean estaActiva() {
        return activa;
    }

    public void desactivar() {
        eliminar();
    }

    public void setSprite(Sprite s) {
        super.setSprite(s);
        if (velocidad.getX() > 0)
            cambiarEstadoSprite(EstadoSprite.DERECHA);
        else
            cambiarEstadoSprite(EstadoSprite.IZQUIERDA);
    }

    public void mover() {
        posicion.setX(posicion.getX() + velocidad.getX());
        posicion.setY(posicion.getY() + 0.12f);
        notify(Eventos.ACTUALIZACION_GRAFICA);
        notify(Eventos.COLISION);
        if (hitbox != null)
            hitbox.setPosition(new Vector2(posicion.getX() + velocidad.getX(), posicion.getY() + 0.12f));
    }

    @Override
    public void aceptarRegistro(Registrador r) {
        r.registrar(this);
    }

    @Override
    public void eliminar() {
        if (miJuego.getModoDeJuego().getNivel() != null)
            miJuego.getModoDeJuego().getNivel().eliminarProyectil(this);

        notify(Eventos.ELIMINACION);
        observers.clear();
        notify(Eventos.ACTUALIZACION_GRAFICA);
        activa = false;
        hitbox = null;
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
    public void colisionar(SueloResbaladizo pr) {

    }

    @Override
    public void colisionar(PlataformaQuebradiza pq) {

    }

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
        eliminar();
    }

    @Override
    public void colisionar(ParedDestructible p) {
        eliminar();
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
