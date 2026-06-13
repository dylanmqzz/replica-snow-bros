package GameElements.NoEstaticos.Enemigos.Animales;

import GameElements.NoEstaticos.Proyectiles.Proyectil;
import Grafica.Sprite;
import Grafica.AnimationManagers.AnimationManagerRF;
import Interface.InterfacesGameElement.Lanzador;
import Movimiento.MovimientoEnemigos.IARanaDeFuego;
import Utils.ConstantesSonidos;
import Utils.ReproductorSonido;
import Utils.Vector2;
import Enumerados.*;

public class RanaDeFuego extends Animal implements Lanzador {
    protected final int COOLDOWN_ATAQUE = 30;
    protected ReproductorSonido sonido = ReproductorSonido.getInstancia();
    protected int cooldownAtaque;
    protected boolean puedeAtacar;

    public RanaDeFuego(Vector2 pos) {
        super(pos);
        cooldownAtaque = 0;
        puedeAtacar = true;
        puntos = 300;
        puntosCongelado = 150;
    }

    public void lanzar(Vector2 direccion) {
        if (!detenido) {
            cooldownAtaque = COOLDOWN_ATAQUE;
            puedeAtacar = false;
            Proyectil bola = miJuego.getFabricaGameElements().crearProyectil(TipoElementos.BOLA_DE_FUEGO, posicion, direccion);
            sonido.reproducirSonido(ConstantesSonidos.FuegoEnemigo);
            if (miJuego.getModoDeJuego().getNivel() != null)
                miJuego.getModoDeJuego().getNivel().agregarElemento(bola);
        }
    }

    public boolean puedeAtacar() {
        return puedeAtacar;
    }

    @Override
    public void accionarComportamiento() {
        if (ia == null)
            ia = new IARanaDeFuego(this);
        ia.ejecutar();
    }

    @Override
    public void setSprite(Sprite s) {
        super.setSprite(s);
        animaciones = new AnimationManagerRF(sprite, this);
    }

    public void tick() {
        accionarComportamiento();
        animar();
        if (tiempoDescongelar > 0)
            descongelar();
            
        if (cooldownAtaque > 0)
            cooldownAtaque--;
        else
            puedeAtacar = true;
        enSuelo = false;
        enEscalera = false;
        notify(Eventos.ACTUALIZACION_GRAFICA);
        notify(Eventos.COLISION);
        if (eliminado)
            eliminar();
    }

}
