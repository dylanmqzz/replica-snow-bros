package GameElements.NoEstaticos.Jugador;

import GameElements.HitBox;
import GameElements.GameElement;
import Interface.InterfacesGameElement.*;
import Interface.Visitors.Registrador;
import Juego.Juego;
import Juego.RegistroJugador;
import Movimiento.Estados.*;
import Observers.Observer;
import Enumerados.Efectos;
import Enumerados.Eventos;
import Enumerados.TipoElementos;
import Utils.*;
import Utils.Sensor.SensorAtaqueJugador;
import GameElements.Estaticos.Decoracion.Obstaculo.Escalera;
import GameElements.Estaticos.Decoracion.Obstaculo.Pared;
import GameElements.Estaticos.Decoracion.Obstaculo.ParedDestructible;
import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Obstaculo.Trampa;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import GameElements.Estaticos.PowerUp.PowerUp;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import GameElements.NoEstaticos.Proyectiles.BolaDeFuego;
import GameElements.NoEstaticos.Proyectiles.BolaDeNieve;
import GameElements.NoEstaticos.Proyectiles.Bomba;
import GameElements.NoEstaticos.Proyectiles.Fuego;
import GameElements.NoEstaticos.Proyectiles.Proyectil;
import Grafica.AnimationManagers.AnimationManagerJugador;
import Grafica.Sprite;
import GameElements.NoEstaticos.*;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Jugador extends NoEstatico implements Lanzador {
    public enum Ataque {
        LANZAR, PATEAR
    }
    protected final ReadWriteLock lock = new ReentrantReadWriteLock();
    protected final int COOLDOWN = 10;
    protected final int TIEMPO_PU = 300;
    protected final int TIEMPO_INVENCIBLE = 45;
    protected final int ESPERA_MUERTE = 60;
    protected final int SONIDO_ESCALERA = 8;
    protected int vidas;
    protected int tiempoInvencible;
    protected int esperaMuerte;
    protected int esperaSonidoEscalera;
    protected Vector2 posicionInicial;
    protected boolean puedeAtacar;
    protected boolean powRoja;
    protected int puntaje;
    protected boolean enEscalera;
    protected RegistroJugador registro;
    protected int tiempoPU;
    protected boolean velocidadActivada;
    protected ReproductorSonido sonido = ReproductorSonido.getInstancia();
    protected int cooldownDisparo;
    protected boolean doblePuntaje;
    protected boolean reaparece;
    protected Ataque tipoAtaque;
    protected AnimationManagerJugador animaciones;
    protected SensorAtaqueJugador sensorAtaque;
    protected boolean detencionActivada;
    protected boolean escalando;
    protected boolean saltando;
    protected boolean puedeSonarEscalera;

    public Jugador(Vector2 posicion) {
        super(posicion);
        saltando = false;
        zOrder = 0;
        posicionInicial = posicion.clone();
        enSuelo = true;
        puntaje = 0;
        vidas = 3;
        hitbox = new HitBox(30, 40, posicion, new Vector2(5, 0));
        direccion = new Vector2(0, 0);
        estado = new Normal(this);
        direccionMirada = new Vector2(1, 0);
        puedeAtacar = true;
        powRoja = false;
        enEscalera = false;
        velocidadActivada = false;
        cooldownDisparo = 0;
        esperaSonidoEscalera = 0;
        puedeSonarEscalera = true;
        tiempoInvencible = TIEMPO_INVENCIBLE;
        esperaMuerte = -1;
        tipoAtaque = Ataque.LANZAR;
        doblePuntaje = false;
        reaparece = true;
        detencionActivada = false;
    }

    public RegistroJugador getRegistro() {
        return new RegistroJugador(miJuego.getNombreJugador(), puntaje);
    }

    public void sumarPuntaje(int p) {
        sonido.reproducirSonido(ConstantesSonidos.SumarPuntos);
        if (doblePuntaje)
            puntaje += p * 2;
        else
            puntaje += p;
        notify(Eventos.SUMA_PUNTOS);
    }

    public boolean getDoblePuntaje() {
        return doblePuntaje;
    }

    @Override
    public void setSprite(Sprite s) {
        super.setSprite(s);
        animaciones = new AnimationManagerJugador(sprite, this);
    }

    @Override
    public void setJuego(Juego j) {
        super.setJuego(j);
        sensorAtaque = new SensorAtaqueJugador(this);
    }

    public boolean getPuedeAtacar() {
        return puedeAtacar;
    }

    public void mover() {
        if (vidas > 0)
            estado.getEstrategiaMovimiento().efectuarMovimiento();
    }

    public void procesarInputMovimiento(Vector2 dir, boolean saltoSolicitado) {
        if (dir.getX() != 0)
            direccionMirada.setX(dir.getX());
        direccion.setX(dir.getX());
        if (saltoSolicitado) {
            direccion.setY(dir.getY());
            if (!saltando && !escalando)
                sonido.reproducirSonido(ConstantesSonidos.Salto);
            saltando = true;
        }
        estado.getEstrategiaMovimiento().actualizarMovimiento(dir, saltoSolicitado);
    }

    public boolean reaparece() {
        return reaparece;
    }

    public void movio() {
        reaparece = false;
    }

    public boolean estaSaltando() {
        return saltando;
    }

    public boolean escalando() {
        return escalando;
    }

    public boolean enEscalera() {
        return enEscalera;
    }

    public boolean esInvencible() {
        return tiempoInvencible > 0;
    }

    public void atacar() {
        if (puedeAtacar && vidas > 0) {
            puedeAtacar = false;
            cooldownDisparo = COOLDOWN;
            definirTipoAtaque();
        }
    }

    protected void definirTipoAtaque() {
        sensorAtaque.update();
        if (sensorAtaque.detectaEnemigoCongelado()) {
            tipoAtaque = Ataque.PATEAR;
            patear(sensorAtaque.enemigoEnRango(), direccionMirada.getX());
        } else {
            tipoAtaque = Ataque.LANZAR;
            lanzar(direccionMirada);
        }
    }

    public Ataque getAtaque() {
        return tipoAtaque;
    }

    public void lanzar(Vector2 direccion) {
        sonido.reproducirSonido(ConstantesSonidos.Disparo);
        Proyectil bola = miJuego.getFabricaGameElements().crearProyectil(TipoElementos.BOLA_DE_NIEVE, posicion, direccion);
        miJuego.getModoDeJuego().getNivel().agregarElemento(bola);
        if (powRoja)
            ((BolaDeNieve) bola).activarDoble();
    }

    public void patear(Enemigo target, float dir) {
        target.empujar(dir);
    }

    public void perderVida() {
        if (vidas > 0) {
            vidas--;
            sonido.reproducirSonido(ConstantesSonidos.MuerteJugador);
            tiempoInvencible = TIEMPO_INVENCIBLE;
            notify(Eventos.PERDIDAVIDA);
            reiniciarEfectos();
        }
        if (vidas > 0) reiniciarPosicion();
        else if (vidas == 0) {
            esperaMuerte = ESPERA_MUERTE;
            vidas--;
        }
    }

    public void reiniciarPosicion() {
        posicion = posicionInicial.clone();
        hitbox.setPosition(posicion);
        saltando = false;
        reaparece = true;
        velocidad.setX(0);
        velocidad.setY(0);
    }

    public void reiniciarEfectos() {
        estado = new Normal(this);
        velocidadActivada = false;
        doblePuntaje = false;
        powRoja = false;
        detencionActivada = false;
        for (Enemigo enem : miJuego.getModoDeJuego().getNivel().obtenerEnemigos()) {
            if (enem.getResistencia() == enem.resistenciaMaxima())
                enem.setDetenido(false);
        }
        notify(Eventos.SIN_EFECTOS);
    }

    public boolean detencionActivada() {
        return detencionActivada;
    }

    public void aplicarPowerUp(Efectos e) {
        tiempoPU = TIEMPO_PU;
        ReproductorSonido sonido = ReproductorSonido.getInstancia();
        sonido.reproducirSonido(ConstantesSonidos.PowerUp);
        switch (e) {
            case VELOCIDAD:
                reiniciarEfectos();
                estado = new SuperVelocidad(this);
                velocidadActivada = true;
                tiempoPU = TIEMPO_PU;
                notify(Eventos.POW_AZUL);
                sonido.reproducirSonido(ConstantesSonidos.Aceleracion);
                break;
            case VIDA_EXTRA:
                if (vidas < 3) {
                    vidas++;
                    notify(Eventos.SUMA_VIDA);
                }
                break;
            case MATA_RAPIDO:
                reiniciarEfectos();
                powRoja = true;
                notify(Eventos.POW_ROJA);
                tiempoPU = TIEMPO_PU;
                break;
            case DETENER_ENEMIGOS:
                reiniciarEfectos();
                detencionActivada = true;
                for (Enemigo enem : miJuego.getModoDeJuego().getNivel().obtenerEnemigos()) {
                    enem.setDetenido(true);
                }
                notify(Eventos.POW_VERDE);
                tiempoPU = TIEMPO_PU;
                break;
            case FRUTA:
                break;
            case DOBLE_PUNTOS:
                reiniciarEfectos();
                tiempoPU = TIEMPO_PU;
                doblePuntaje = true;
                notify(Eventos.DOBLE_PUNTOS);
                break;
            default:
                break;
        }
    }

    protected void colisionConSuelo(GameElement suelo) {
        enSuelo = true;
        velocidad.setY(0);
        saltando = false;
        escalando = false;
        if (!velocidadActivada)
            estado = new Normal(this);
        else
            estado = new SuperVelocidad(this);
    }

    public void colisionar(PowerUp p) {
        aplicarPowerUp(p.getEfecto());
        p.eliminar();
    }

    public void colisionar(Enemigo e) {
        if (e.estaCongelado()) {
            resolverColision(e);
            if (hitbox.diferenciaAltura(e.getHitbox()) == -1)
                colisionConSuelo(e);
        } else if (!esInvencible())
            perderVida();
    }

    public void colisionar(Plataforma p) {
        resolverColision(p);
        if (hitbox.diferenciaAltura(p.getHitbox()) == -1)
            colisionConSuelo(p);
    }

    public void colisionar(SueloResbaladizo pr) {
        resolverColision(pr);
        if (hitbox.diferenciaAltura(pr.getHitbox()) == -1) {
            velocidad.setY(0);
            escalando = false;
            enSuelo = true;
            saltando = false;
            if (!velocidadActivada)
                estado = new Realentizado(this);
            else
                estado = new Normal(this);
        }
    }

    public void colisionar(PlataformaQuebradiza pq) {
        resolverColision(pq);
        if (hitbox.diferenciaAltura(pq.getHitbox()) == -1) {
            colisionConSuelo(pq);
            pq.pisoJugador();
        }
    }

    public void colisionar(PlataformaMovil pm) {
        resolverColision(pm);
        if (hitbox.diferenciaAltura(pm.getHitbox()) == -1) {
            enSuelo = true;
            saltando = false;
            enEscalera = false;
            escalando = false;
            velocidad.setY(0);
            if (!velocidadActivada)
                estado = new Normal(this);
            else
                estado = new SuperVelocidad(this);
            if (pm.yaColisiono() == false) {
                sumarPuntaje(200);
                pm.setFueColisionada(true);
            }
            posicion.sumar(pm.getDireccion().getX(), 0);
        }
    }

    public void colisionar(Escalera e) {
        saltando = false;
        estado = new Escalando(this);
        if (!enSuelo && velocidad.getY() != 0)
            escalando = true;
        if (escalando)
            velocidad.setY(0);
        if (puedeSonarEscalera) {
            puedeSonarEscalera = false;
            esperaSonidoEscalera = SONIDO_ESCALERA;
            sonido.reproducirSonido(ConstantesSonidos.Escalera);
        }
    }

    public void colisionar(Trampa t) {
        perderVida();
    }

    public void colisionar(Pared p) {
        resolverColision(p);
        if (hitbox.diferenciaAltura(p.getHitbox()) == -1)
            colisionConSuelo(p);
    }

    public void colisionar(ParedDestructible p) {
        resolverColision(p);
        if (hitbox.diferenciaAltura(p.getHitbox()) == -1)
            colisionConSuelo(p);
    }

    public void colisionar(BolaDeFuego bolaf) {
        bolaf.eliminar();
        if (!esInvencible())
            perderVida();
    }

    public void colisionar(Bomba bomba) {
        bomba.eliminar();
        if (!esInvencible())
            perderVida();
    }

    public void colisionar(BolaDeNieve bolan) {

    }

    public void colisionar(Fuego fuego) {
        fuego.eliminar();
        if (!esInvencible())
            perderVida();
    }

    private void resolverColision(GameElement elemento) {
        Vector2 correccion = hitbox.calcularCorreccion(elemento.getHitbox());
        posicion.sumar(correccion);
        hitbox.getPosition().sumar(correccion);
    }

    public int getVidas() {
        return vidas;
    }

    @Override
    public void aceptarRegistro(Registrador r) {
        r.registrar(this);
    }

    @Override
    public void eliminar() {
        miJuego.getModoDeJuego().getNivel().setJugador(null);
        notify(Eventos.ELIMINACION);
        hitbox = null;
        observers.clear();
    }

    public String getNombre() {
        return miJuego.getNombreJugador();
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public void suscribe(Observer suscriptor) {
        lock.writeLock().lock();
        try {
            observers.add(suscriptor);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void notify(Eventos e) {
        lock.readLock().lock();
        try {
            for (Observer o : observers) {
                o.update(e);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    protected void timerTiempoInvencible() {
        if (tiempoInvencible > 0)
            tiempoInvencible--;
    }

    protected void timerAtaque() {
        if (cooldownDisparo > 0) {
            puedeAtacar = false;
            cooldownDisparo--;
        } else
            puedeAtacar = true;
    }

    protected void timerEscalera() {
        if (esperaSonidoEscalera > 0) {
            puedeSonarEscalera = false;
            esperaSonidoEscalera--;
        } else
            puedeSonarEscalera = escalando;
    }

    protected void timerPowerUp() {
        if (tiempoPU > 0)
            tiempoPU--;
        else {
            reiniciarEfectos();
            tiempoPU = TIEMPO_PU;
        }
    }

    protected void timerEsperaMuerte() {
        if (esperaMuerte > 0)
            esperaMuerte--;
        else if (esperaMuerte == 0)
            miJuego.finalizar();
    }

    protected void resetearInfoColision() {
        enSuelo = false;
        enEscalera = false;
        escalando = false;
    }

    public void tick() {
        mover();
        animaciones.animar();
        notify(Eventos.ACTUALIZACION_GRAFICA);
        timerTiempoInvencible();
        timerAtaque();
        timerPowerUp();
        timerEsperaMuerte();
        timerEscalera();
        resetearInfoColision();
        notify(Eventos.COLISION);
    }

}