package GameElements.NoEstaticos.Enemigos.Animales;

import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import Grafica.Sprite;
import Grafica.AnimationManagers.AnimationManagerTA;
import Movimiento.MovimientoEnemigos.IATrollAmarillo;
import Utils.Vector2;
import GameElements.HitBox;

public class TrollAmarillo extends Animal {
    protected boolean saltando;
    protected boolean cayendo;
    protected boolean puedeColisionar = true;
    protected HitBox areaExcepcionColision = new HitBox(0, 0, posicion.clone());

    public TrollAmarillo(Vector2 pos) {
        super(pos);
        puedeColisionar = true;
        saltando = false;
        cayendo = false;
        puntos = 500;
        puntosCongelado = 300;
    }

    @Override
    public void accionarComportamiento() {
        if (ia == null)
            ia = new IATrollAmarillo(this);
        ia.ejecutar();
    }

    @Override
    public void setSprite(Sprite s) {
        super.setSprite(s);
        animaciones = new AnimationManagerTA(sprite, this);
    }

    public void setSaltando(boolean value) {
        saltando = value;
    }

    public void setCayendo(boolean value) {
        cayendo = value;
    }

    public boolean estaSaltando() {
        return saltando;
    }

    public boolean estaCayendo() {
        return cayendo;
    }

    public void setAreaSinColision(float altura) {
        float offsetAltura = altura - posicion.getY();
        areaExcepcionColision = new HitBox(160, 80, posicion.clone(), new Vector2(-80, offsetAltura));
    }

    public void setPuedeColisionar(boolean value) {
        puedeColisionar = value;
    }

    public void colisionar(Plataforma p) {
        boolean realizarChequeo = puedeColisionar || !areaExcepcionColision.chequearColision(p.getHitbox());
        if (realizarChequeo) {
            resolverColision(p);
            if (hitbox.diferenciaAltura(p.getHitbox()) == -1) {
                velocidad.setY(0);
                enSuelo = true;
            }
        }
        comporbarChoque(p);
    }

    @Override
    public void colisionar(SueloResbaladizo pr) {
        boolean realizarChequeo = puedeColisionar || !areaExcepcionColision.chequearColision(pr.getHitbox());
        if (realizarChequeo) {
            resolverColision(pr);
            velocidad.setY(0);
            enSuelo = true;
        }
        comporbarChoque(pr);
    }

    @Override
    public void colisionar(PlataformaQuebradiza pq) {
        boolean realizarChequeo = puedeColisionar || !areaExcepcionColision.chequearColision(pq.getHitbox());
        if (realizarChequeo) {
            resolverColision(pq);
            velocidad.setY(0);
            enSuelo = true;
        }
        comporbarChoque(pq);
    }

    public void colisionar(PlataformaMovil pm) {
        boolean realizarChequeo = puedeColisionar || !areaExcepcionColision.chequearColision(pm.getHitbox());
        if (realizarChequeo) {
            resolverColision(pm);
            velocidad.setY(0);
            enSuelo = true;
        }
        comporbarChoque(pm);
    }

}
