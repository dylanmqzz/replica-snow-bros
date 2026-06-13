package GameElements.NoEstaticos.Enemigos.Jefes;

import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import GameElements.NoEstaticos.Proyectiles.BolaDeNieve;
import GameElements.NoEstaticos.Proyectiles.Proyectil;
import Grafica.Sprite;
import Grafica.AnimationManagers.AnimationManagerMO;
import Movimiento.MovimientoEnemigos.IAMoghera;
import Utils.Vector2;
import Enumerados.Eventos;
import Enumerados.TipoElementos;
import GameElements.HitBox;

public class Moghera extends Jefe {
    protected HitBox areaExcepcionColision = new HitBox(0, 0, posicion.clone());
    protected HitBox hitboxGolpe;
    protected boolean puedeAtacar;
    protected boolean muerto;
    protected int cooldownAtaque;
    protected final int COOLDOWN_ATAQUE = 120;

    public Moghera(Vector2 pos) {
        super(pos);
        HEIGHT = 160;
        WIDGHT = 120;
        hitbox = new HitBox(110, 125, pos);
        hitboxGolpe = new HitBox(120, 60, pos);
        direccionMirada = new Vector2(-1, 0);
        puedeColisionar = true;
        muerto = false;
        cooldownAtaque = COOLDOWN_ATAQUE;
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
    }

    @Override
    public void colisionar(SueloResbaladizo pr) {
        boolean realizarChequeo = puedeColisionar || !areaExcepcionColision.chequearColision(pr.getHitbox());
        if (realizarChequeo) {
            resolverColision(pr);
            velocidad.setY(0);
            enSuelo = true;
        }
    }

    @Override
    public void colisionar(PlataformaQuebradiza pq) {
        boolean realizarChequeo = puedeColisionar || !areaExcepcionColision.chequearColision(pq.getHitbox());
        if (realizarChequeo) {
            resolverColision(pq);
            velocidad.setY(0);
            enSuelo = true;
        }
    }

    public void colisionar(PlataformaMovil pm) {
        boolean realizarChequeo = puedeColisionar || !areaExcepcionColision.chequearColision(pm.getHitbox());
        if (realizarChequeo) {
            resolverColision(pm);
            velocidad.setY(0);
            enSuelo = true;
        }
    }

    public void lanzar(Vector2 direccion) {
        Vector2 posFuego = posicion.clone();
        posFuego.sumar(new Vector2(-900, 40));
        if (puedeAtacar) {
            Proyectil bola = miJuego.getFabricaGameElements().crearProyectil(TipoElementos.FUEGO, posFuego, direccion);
            cooldownAtaque = COOLDOWN_ATAQUE;
            puedeAtacar = false;
            if (miJuego.getModoDeJuego().getNivel() != null)
                miJuego.getModoDeJuego().getNivel().agregarElemento(bola);
        }
    }

    public void setAreaSinColision(float altura) {
        Vector2 pos = new Vector2(posicion.getX(), altura);
        areaExcepcionColision = new HitBox(300, 80, pos, new Vector2(-150, -20));
    }

    public void accionarComportamiento() {
        if (ia == null)
            ia = new IAMoghera(this);
        ia.ejecutar();
    }

    public void setSprite(Sprite s) {
        super.setSprite(s);
        animaciones = new AnimationManagerMO(sprite, this);
        animaciones.animar();
    }

    public void colisionar(BolaDeNieve bolan) {
        if (hitboxGolpe.chequearColision(bolan.getHitbox()))
            restarResistencia();
        if (bolan.dobleDaño())
            restarResistencia();
        bolan.eliminar();
    }

    public void morir() {
        muerto = true;
        velocidad.setX(0);
        velocidad.setY(0);
    }

    public boolean estaMuerto() {
        return muerto;
    }

    public void tick() {
        accionarComportamiento();
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
