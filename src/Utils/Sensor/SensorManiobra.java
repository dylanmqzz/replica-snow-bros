package Utils.Sensor;

import GameElements.GameElement;
import GameElements.HitBox;
import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import Utils.Vector2;

public class SensorManiobra extends Sensor {
    protected boolean detectaPisoArriba;
    protected boolean detectaPisoAbajo;
    protected float alturaPisoArriba, alturaPisoAbajo;
    protected float margen;

    public SensorManiobra(GameElement propietario, float altura, float margen) {
        this.propietario = propietario;
        controladorColisiones = propietario.getMiJuego().getControladorColisiones();
        centroPropietario = calcularCentro(propietario);
        posicion = new Vector2(0, 0);
        hitbox = new HitBox(5, altura * 2, posicion.clone(), new Vector2(0, -altura));
        this.margen = margen;
        actualizarPosicion();
    }

    @SuppressWarnings("unused")
    protected void actualizarPosicion() {
        float anchoP = propietario.getWidth() / 2;
        Vector2 centroAnterior = centroPropietario;
        centroPropietario = calcularCentro(propietario);
        int direccionMov = centroPropietario.getX() - centroAnterior.getX() <= 0 ? -1 : 1;
        if (direccionMov == -1)
            anchoP += hitbox.getBase();
        posicion.setX(centroPropietario.getX());
        posicion.setY(centroPropietario.getY());
        hitbox.setPosition(posicion.clone());
    }

    @Override
    public void update() {
        detectaPisoArriba = false;
        detectaPisoAbajo = false;
        alturaPisoAbajo = Float.MAX_VALUE;
        alturaPisoArriba = Float.MAX_VALUE;
        actualizarPosicion();
        controladorColisiones.buscarColisiones(this);
    }

    public boolean detectarPisoArriba() {
        return detectaPisoArriba;
    }

    public boolean detectarPisoAbajo() {
        return detectaPisoAbajo;
    }

    public float getAlturaPisoArriba() {
        return alturaPisoArriba;
    }

    public float getAlturaPisoAbajo() {
        return alturaPisoAbajo;
    }

    @Override
    public void colisionar(Plataforma p) {
        float difAltura = Math.abs(p.getPosicion().getY() - posicion.getY());
        if (p.getPosicion().getY() < posicion.getY() && difAltura >= margen && p.getPosicion().getY() > 40) {
            detectaPisoArriba = true;
            if (p.getPosicion().getY() < alturaPisoArriba)
                alturaPisoArriba = p.getPosicion().getY();
        }
        if (p.getPosicion().getY() > posicion.getY() && difAltura >= margen) {
            detectaPisoAbajo = true;
            if (p.getPosicion().getY() < alturaPisoAbajo)
                alturaPisoAbajo = p.getPosicion().getY();
        }
    }

    @Override
    public void colisionar(SueloResbaladizo pr) {
        float difAltura = Math.abs(pr.getPosicion().getY() - posicion.getY());
        if (pr.getPosicion().getY() < posicion.getY() && difAltura >= margen) {
            detectaPisoArriba = true;
            alturaPisoArriba = pr.getPosicion().getY();
        }
        if (pr.getPosicion().getY() > posicion.getY() && difAltura >= margen) {
            detectaPisoAbajo = true;
            alturaPisoAbajo = pr.getPosicion().getY();
        }
    }

    @Override
    public void colisionar(PlataformaQuebradiza pq) {
        float difAltura = Math.abs(pq.getPosicion().getY() - posicion.getY());
        if (pq.getPosicion().getY() < posicion.getY() && difAltura >= margen) {
            detectaPisoArriba = true;
            alturaPisoArriba = pq.getPosicion().getY();
        }
        if (pq.getPosicion().getY() > posicion.getY() && difAltura >= margen) {
            detectaPisoAbajo = true;
            alturaPisoAbajo = pq.getPosicion().getY();
        }
    }

    protected Vector2 calcularCentro(GameElement e) {
        Vector2 centro = new Vector2(0, 0);
        centro.setX(e.getPosicion().getX() + e.getWidth() / 2);
        centro.setY(e.getPosicion().getY() + e.getHeight() / 2);
        return centro;
    }
    
}
