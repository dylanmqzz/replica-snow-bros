package Juego;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.swing.SwingUtilities;

import GameElements.GameElement;
import GameElements.Estaticos.Estatica;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import GameElements.NoEstaticos.Jugador.Jugador;
import GameElements.NoEstaticos.Proyectiles.Proyectil;
import Interface.Visitors.Registrador;
import Utils.Vector2;
import Enumerados.TipoElementos;

public class Nivel implements Registrador {
    protected final ReadWriteLock lock = new ReentrantReadWriteLock();
    protected final int CANTIDAD_MAX_ELEMENTOS = 208;
    protected final int FILAS = 13;
    protected final int COLUMNAS = 16;
    protected CopyOnWriteArrayList<Estatica> listaEstaticos;
    protected CopyOnWriteArrayList<Enemigo> listaEnemigos;
    protected CopyOnWriteArrayList<Proyectil> listaProyectiles;
    protected Jugador jugador;
    protected int enemigosRestantes;
    protected ModoDeJuego modo;
    protected int tiempoTranscurrido;
    protected boolean calabazaAparecida;
    protected int tiempoAparicionCalabaza;

    public Nivel() {
        this.listaEstaticos = new CopyOnWriteArrayList<Estatica>();
        this.listaEnemigos = new CopyOnWriteArrayList<Enemigo>();
        this.listaProyectiles = new CopyOnWriteArrayList<Proyectil>();
        this.enemigosRestantes = 0;
        this.calabazaAparecida = false;
        this.tiempoAparicionCalabaza = 60 * 30;
    }

    public void setModo(ModoDeJuego modo) {
        this.modo = modo;
    }

    public int getFilas() {
        return FILAS;
    }

    public int getColumnas() {
        return COLUMNAS;
    }

    public CopyOnWriteArrayList<Estatica> obtenerEstaticos() {
        return listaEstaticos;
    }

    public CopyOnWriteArrayList<Enemigo> obtenerEnemigos() {
        return listaEnemigos;
    }

    public CopyOnWriteArrayList<Proyectil> obtenerProyectiles() {
        return listaProyectiles;
    }

    public void eliminarEstatico(Estatica elemento) {
        lock.writeLock().lock();
        try {
            listaEstaticos.remove(elemento);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void eliminarEnemigo(Enemigo enemigo) {
        lock.writeLock().lock();
        try {
            listaEnemigos.remove(enemigo);
            enemigosRestantes--;
            if (enemigosRestantes == 0) {
                SwingUtilities.invokeLater(() -> {
                    modo.avanzarNivel();
                });
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void eliminarProyectil(Proyectil proyectil) {
        lock.writeLock().lock();
        try {
            listaProyectiles.remove(proyectil);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setEnemigosRestantes(int enemigos) {
        this.enemigosRestantes = enemigos;
    }

    public void agregarEnemigosRestantes(int enemigos) {
        this.enemigosRestantes += enemigos;
    }

    public int getEnemigosRestantes() {
        return enemigosRestantes;
    }

    public void agregarElemento(GameElement e) {
        e.aceptarRegistro(this);
    }

    @Override
    public void registrar(Estatica e) {
        listaEstaticos.add(e);
    }

    @Override
    public void registrar(Enemigo e) {
        listaEnemigos.add(e);
    }

    @Override
    public void registrar(Proyectil e) {
        listaProyectiles.add(e);
    }

    @Override
    public void registrar(Jugador e) {
    }

    public void registrar(PlataformaMovil p) {
        listaEstaticos.add(p);
    }

    public void reiniciarNivel() {
        if (!listaEnemigos.isEmpty()) {
            for (Enemigo e : listaEnemigos) {
                e.eliminar();
            }
            listaEnemigos.clear();
        }
        if (!listaEstaticos.isEmpty()) {
            for (Estatica e : listaEstaticos) {
                e.eliminar();
            }
            listaEstaticos.clear();
        }
        if (!listaProyectiles.isEmpty()) {
            for (Proyectil p : listaProyectiles) {
                p.eliminar();
            }
            listaProyectiles.clear();
        }
        if (jugador != null) {
            jugador.eliminar();
            jugador = null;
        }
    }

    private void aparecerCalabaza() {
        Enemigo calabaza = (Enemigo) modo.getJuego().getFabricaGameElements().crearElemento(TipoElementos.CALABAZA, new Vector2(45, 45));
        agregarElemento(calabaza);
    }

    public void tickNivel() {
        if (modo.permiteCalabaza()) {
            if (!calabazaAparecida) {
                tiempoAparicionCalabaza--;
                if (tiempoAparicionCalabaza <= 0) {
                    calabazaAparecida = true;
                    aparecerCalabaza();
                }
            }
        }
    }

}
