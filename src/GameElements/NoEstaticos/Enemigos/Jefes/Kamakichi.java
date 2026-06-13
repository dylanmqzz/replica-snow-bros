package GameElements.NoEstaticos.Enemigos.Jefes;

import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import GameElements.NoEstaticos.Proyectiles.BolaDeNieve;
import GameElements.NoEstaticos.Proyectiles.Proyectil;
import Grafica.Sprite;
import Grafica.AnimationManagers.AnimationManagerKM;
import Movimiento.MovimientoEnemigos.IAKamakichi;
import Utils.Vector2;
import Enumerados.Eventos;
import Enumerados.TipoElementos;
import GameElements.HitBox;

public class Kamakichi extends Jefe {
    protected int cooldownAtaque;
    protected final int COOLDOWN_ATAQUE = 60;
    protected final Vector2 CANON_SUPERIOR_IZQUIERDA = new Vector2(40, -20);
    protected final Vector2 CANON_SUPERIOR_DERECHA = new Vector2(220, -20);
    protected final Vector2 CANON_LATERAL_IZQUIERDA = new Vector2(-10, 50);
    protected final Vector2 CANON_LATERAL_DERECHA = new Vector2(260, 50);

    public Kamakichi(Vector2 pos) {
        super(pos);
        HEIGHT = 120;
        WIDGHT = 240;
        hitbox = new HitBox(WIDGHT, HEIGHT, pos);
        direccionMirada = new Vector2(-1, 0);
        puedeColisionar = true;
        cooldownAtaque = 0;
        puedeAtacar = true;
    }

    public void mover() {
        estado.getEstrategiaMovimiento().efectuarMovimiento();
    }

    public void lanzar() {
        cooldownAtaque = COOLDOWN_ATAQUE;
        puedeAtacar = false;
        if (!detenido) {
            Vector2 base = posicion.clone();

            Vector2 pSi = base.clone();
            pSi.sumar(CANON_SUPERIOR_IZQUIERDA.clone());
            Vector2 pSd = base.clone();
            pSd.sumar(CANON_SUPERIOR_DERECHA.clone());
            Vector2 pLi = base.clone();
            pLi.sumar(CANON_LATERAL_IZQUIERDA.clone());
            Vector2 pLd = base.clone();
            pLd.sumar(CANON_LATERAL_DERECHA.clone());

            Proyectil superiorIzquierda = miJuego.getFabricaGameElements().crearProyectil(TipoElementos.BOMBA, pSi, new Vector2(-1, -2));
            Proyectil superiorDerecha = miJuego.getFabricaGameElements().crearProyectil(TipoElementos.BOMBA, pSd, new Vector2(1, -2));
            Proyectil lateralIzquierda = miJuego.getFabricaGameElements().crearProyectil(TipoElementos.BOMBA, pLi, new Vector2(-1, -1));
            Proyectil lateralDerecha = miJuego.getFabricaGameElements().crearProyectil(TipoElementos.BOMBA, pLd, new Vector2(1, -1));

            var nivel = miJuego.getModoDeJuego().getNivel();
            if (nivel != null) {
                nivel.agregarElemento(superiorIzquierda);
                nivel.agregarElemento(superiorDerecha);
                nivel.agregarElemento(lateralIzquierda);
                nivel.agregarElemento(lateralDerecha);
            }
        }
    }

    @Override
    public void accionarComportamiento() {
        if (ia == null)
            ia = new IAKamakichi(this);
        ia.ejecutar();
    }

    public void setSprite(Sprite s) {
        super.setSprite(s);
        animaciones = new AnimationManagerKM(sprite, this);
        animaciones.animar();
    }

    public void colisionar(BolaDeNieve bolan) {

    }

    public void colisionar(Plataforma p) {
        if (puedeColisionar) {
            resolverColision(p);
            if (hitbox.diferenciaAltura(p.getHitbox()) == -1) {
                velocidad.setY(0);
                enSuelo = true;
            }
        }
    }

    @Override
    public void colisionar(SueloResbaladizo pr) {
        if (puedeColisionar) {
            resolverColision(pr);
            velocidad.setY(0);
            enSuelo = true;
        }
    }

    @Override
    public void colisionar(PlataformaQuebradiza pq) {
        if (puedeColisionar) {
            resolverColision(pq);
            velocidad.setY(0);
            enSuelo = true;
        }
    }

    public void colisionar(PlataformaMovil pm) {
        if (puedeColisionar) {
            resolverColision(pm);
            velocidad.setY(0);
            enSuelo = true;
        }
    }

    public void tick() {
        accionarComportamiento();
        mover();
        animar();
        if (sufrioGolpe > 0)
            sufrioGolpe--;
            
        if (cooldownAtaque > 0)
            cooldownAtaque--;
        else
            puedeAtacar = true;
        enSuelo = false;
        notify(Eventos.ACTUALIZACION_GRAFICA);
        notify(Eventos.COLISION);
        if (eliminado)
            eliminar();
    }
}
