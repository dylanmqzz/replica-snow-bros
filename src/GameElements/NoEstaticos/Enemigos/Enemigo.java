package GameElements.NoEstaticos.Enemigos;

import Enumerados.Eventos;
import Enumerados.TipoElementos;
import GameElements.GameElement;
import GameElements.Estaticos.Decoracion.Obstaculo.Escalera;
import GameElements.Estaticos.Decoracion.Obstaculo.Pared;
import GameElements.Estaticos.Decoracion.Obstaculo.ParedDestructible;
import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Obstaculo.Trampa;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import GameElements.Estaticos.PowerUp.PowerUp;
import GameElements.NoEstaticos.NoEstatico;
import GameElements.NoEstaticos.Proyectiles.BolaDeFuego;
import GameElements.NoEstaticos.Proyectiles.BolaDeNieve;
import GameElements.NoEstaticos.Proyectiles.Bomba;
import GameElements.NoEstaticos.Proyectiles.Fuego;
import Grafica.AnimationManagers.AnimationManager;
import Interface.InterfacesDeColision.Colisionable;
import Interface.InterfacesDeColision.Colisionador;
import Interface.Visitors.Registrador;
import Movimiento.Estados.Estado;
import Movimiento.Estados.Normal;
import Utils.Vector2;
import Movimiento.MovimientoEnemigos.IAEnemigo;

public abstract class Enemigo extends NoEstatico implements Colisionable {
    protected static final int TIEMPO_DESCONGELAR = 50;
    protected int resistenciaMaxima;
    protected int resistencia;
    protected Estado estado;
    protected IAEnemigo ia;
    protected boolean detenido;
    protected AnimationManager animaciones;
    protected int tiempoDescongelar;
    protected int puntosCongelado;
    protected boolean enEscalera;
    protected boolean eliminado;
    protected float dirEmpuje;
    protected boolean fueCongelado;

    public Enemigo(Vector2 posicion) {
        super(posicion);
        setJuego(miJuego);
        estado = new Normal(this);
        direccion = new Vector2(0, 0);
        direccionMirada = new Vector2(-1, 0);
        velocidad = new Vector2(0, 0);
        enSuelo = true;
        detenido = false;
        tiempoDescongelar = 0;
        puntosCongelado = 0;
        eliminado = false;
        fueCongelado = false;
    }

    public void restarResistencia() {
        if (resistencia > 0)
            resistencia--;
        detenido = true;
        if (resistencia == 0) {
            notify(Eventos.MUERTE);
            if (!fueCongelado) {
                notify(Eventos.PUNTOS);
                miJuego.getJugador().sumarPuntaje(puntosCongelado);
                fueCongelado = true;
            }
        } else
            notify(Eventos.CAMBIO_RESISTENCIA);
        tiempoDescongelar = TIEMPO_DESCONGELAR;
    }

    @Override
    public void aceptarRegistro(Registrador r) {
        r.registrar(this);
    }

    public void mover() {
        estado.getEstrategiaMovimiento().efectuarMovimiento();
    }

    public void procesarSolicitudMovimiento(Vector2 dir) {
        if (dir.getX() != 0)
            direccionMirada.setX(dir.getX());
        direccion.setX(dir.getX());
        estado.getEstrategiaMovimiento().actualizarMovimiento(dir, false);
    }

    public boolean estaEnEscalera() {
        return enEscalera;
    }

    public boolean estaCongelado() {
        return resistencia == 0;
    }

    public void descongelar() {
        tiempoDescongelar--;
        if (tiempoDescongelar == 0) {
            if (resistencia < resistenciaMaxima) {
                resistencia++;
                tiempoDescongelar = TIEMPO_DESCONGELAR;
            }
            if (resistencia == resistenciaMaxima && !miJuego.getJugador().detencionActivada())
                detenido = false;
            notify(Eventos.CAMBIO_RESISTENCIA);
        }
    }

    public void empujar(float dir) {
        detenido = false;
        tiempoDescongelar = -1;
        dirEmpuje = dir;
        notify(Eventos.EMPUJAR);
    }

    public void eliminar() {
        aparecerPowerUp();
        notify(Eventos.PUNTOS);
        miJuego.getJugador().sumarPuntaje(puntos);
        miJuego.getModoDeJuego().getNivel().eliminarEnemigo(this);
        notify(Eventos.ACTUALIZACION_GRAFICA);
        notify(Eventos.ELIMINACION);
        observers.clear();
        hitbox = null;
    }

    public int resistenciaMaxima() {
        return resistenciaMaxima;
    }

    public int getResistencia() {
        return resistencia;
    }

    public float getDireccionEmpuje() {
        return dirEmpuje;
    }

    public void setDetenido(boolean d) {
        detenido = d;
    }

    public boolean estaDetenido() {
        return detenido;
    }

    protected void aparecerPowerUp() {
        int aparecePower = (int) (Math.random() * 100);
        TipoElementos tipo;
        if (aparecePower < 30) {
            switch (aparecePower % 6) {
                case 1:
                    tipo = TipoElementos.POWER_UP_AZUL;
                    break;
                case 2:
                    tipo = TipoElementos.POWER_UP_FRUTA;
                    break;
                case 3:
                    tipo = TipoElementos.POWER_UP_ROJA;
                    break;
                case 4:
                    tipo = TipoElementos.POWER_UP_VERDE;
                    break;
                case 5:
                    tipo = TipoElementos.POWER_UP_DOBLE_PUNTAJE;
                    break;
                default:
                    tipo = TipoElementos.POWER_UP_VIDAEXTRA;
                    break;
            }
            GameElement powerUp = miJuego.getFabricaGameElements().crearElemento(tipo, posicion.clone());
            miJuego.getModoDeJuego().getNivel().agregarElemento(powerUp);
        }
    }

    protected void comporbarChoque(GameElement e) {
        boolean empujado = !detenido && estaCongelado();
        if (empujado && hitbox.diferenciaAltura(e.getHitbox()) != -1)
            eliminado = true;
    }

    @Override
    public void colisionar(Enemigo e) {
        boolean empujado = !e.detenido && e.estaCongelado();
        if (empujado)
            eliminado = true;
    }

    public void animar() {
        animaciones.animar();
    }

    @Override
    public void colisionar(PowerUp P) {

    }

    @Override
    public void colisionar(Plataforma p) {
        resolverColision(p);
        velocidad.setY(0);
        enSuelo = true;
        comporbarChoque(p);
    }

    @Override
    public void colisionar(SueloResbaladizo pr) {
        resolverColision(pr);
        velocidad.setY(0);
        enSuelo = true;
        comporbarChoque(pr);
    }

    @Override
    public void colisionar(PlataformaQuebradiza pq) {
        resolverColision(pq);
        velocidad.setY(0);
        enSuelo = true;
        comporbarChoque(pq);
    }

    public void colisionar(PlataformaMovil pm) {
        resolverColision(pm);
        velocidad.setY(0);
        enSuelo = true;
        comporbarChoque(pm);
    }

    @Override
    public void colisionar(Escalera e) {
        enEscalera = true;
        if (hitbox.diferenciaAltura(e.getHitbox()) == -1) {
            enSuelo = true;
            resolverColision(e);
        }
    }

    @Override
    public void colisionar(Trampa t) {
        comporbarChoque(t);
    }

    @Override
    public void colisionar(Pared p) {
        comporbarChoque(p);
    }

    @Override
    public void colisionar(ParedDestructible p) {
        comporbarChoque(p);
    }

    @Override
    public void colisionar(BolaDeFuego bolaf) {

    }

    @Override
    public void colisionar(BolaDeNieve bolan) {
        restarResistencia();
        if (bolan.dobleDaño())
            restarResistencia();
        bolan.eliminar();
    }

    @Override
    public void colisionar(Bomba bomba) {

    }

    @Override
    public void colisionar(Fuego fuego) {

    }

    public int getPuntos() {
        if (miJuego.getJugador().getDoblePuntaje()) {
            if (estaDetenido())
                return puntosCongelado * 2;
            else
                return puntos * 2;
        } else {
            if (estaDetenido())
                return puntosCongelado;
            else
                return puntos;
        }
    }

    protected void resolverColision(GameElement elemento) {
        Vector2 correccion = hitbox.calcularCorreccion(elemento.getHitbox());
        posicion.sumar(correccion);
        hitbox.getPosition().sumar(correccion);
    }

    public void tick() {
        accionarComportamiento();
        animar();
        enSuelo = false;
        enEscalera = false;
        if (tiempoDescongelar > 0)
            descongelar();
        notify(Eventos.ACTUALIZACION_GRAFICA);
        notify(Eventos.COLISION);
        if (eliminado)
            eliminar();
    }

    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }

    public abstract void accionarComportamiento();

}
