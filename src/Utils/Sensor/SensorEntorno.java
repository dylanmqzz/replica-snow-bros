package Utils.Sensor;

import GameElements.GameElement;
import GameElements.HitBox;
import GameElements.Estaticos.Decoracion.Obstaculo.Escalera;
import GameElements.Estaticos.Decoracion.Obstaculo.Pared;
import GameElements.Estaticos.Decoracion.Obstaculo.ParedDestructible;
import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Obstaculo.Trampa;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import GameElements.NoEstaticos.Jugador.Jugador;
import Utils.Vector2;

public class SensorEntorno extends Sensor {
    protected Jugador jugador;
    protected boolean colisionaConPiso = false;
    protected boolean colisionaConPared = false;
    protected boolean escaleraParaBajar = false;

    public SensorEntorno(GameElement propietario) {
        this.propietario = propietario;
        controladorColisiones = propietario.getMiJuego().getControladorColisiones();
        centroPropietario = calcularCentro(propietario);
        posicion = new Vector2(0, 0);
        hitbox = new HitBox(12.5f, 60, posicion.clone());
        actualizarPosicion();
    }

    @Override
    public void update() {
        colisionaConPiso = false;
        colisionaConPared = false;
        escaleraParaBajar = false;
        actualizarPosicion();
        controladorColisiones.buscarColisiones(this);
    }

    protected void actualizarPosicion() {
        float anchoP = propietario.getWidth() / 2;
        Vector2 centroAnterior = centroPropietario;
        centroPropietario = calcularCentro(propietario);
        int direccionMov = centroPropietario.getX() - centroAnterior.getX() <= 0 ? -1 : 1;
        if (direccionMov == -1)
            anchoP += hitbox.getBase();
        posicion.setX(centroPropietario.getX() + anchoP * direccionMov);
        posicion.setY(centroPropietario.getY());
        hitbox.setPosition(posicion);
    }

    public boolean detectaPiso() {
        return colisionaConPiso;
    }

    public boolean detectaPared() {
        return colisionaConPared;
    }

    public boolean detectaEscaleraParaBajar() {
        return escaleraParaBajar;
    }

    public HitBox getHitbox() {
        return hitbox;
    }

    @Override
    public void colisionar(Plataforma p) {
        colisionaConPiso = true;
        if (propietario.getHitbox().diferenciaAltura(p.getHitbox()) == 0)
            colisionaConPared = true;
    }

    @Override
    public void colisionar(SueloResbaladizo pr) {
        colisionaConPiso = true;
    }

    @Override
    public void colisionar(PlataformaQuebradiza pq) {
        colisionaConPiso = true;
    }

    @Override
    public void colisionar(Escalera e) {
        if (propietario.getHitbox().diferenciaAltura(e.getHitbox()) == -1) {
            colisionaConPiso = true;
            escaleraParaBajar = true;
        }
    }

    @Override
    public void colisionar(Trampa t) {
    }

    @Override
    public void colisionar(Pared p) {
        if (propietario.getHitbox().diferenciaAltura(p.getHitbox()) != -1)
            colisionaConPared = true;
    }

    @Override
    public void colisionar(ParedDestructible p) {
        colisionaConPared = true;
    }

    protected Vector2 calcularCentro(GameElement e) {
        Vector2 centro = new Vector2(0, 0);
        centro.setX(e.getPosicion().getX() + e.getWidth() / 2);
        centro.setY(e.getPosicion().getY() + e.getHeight() / 2);
        return centro;
    }

}
