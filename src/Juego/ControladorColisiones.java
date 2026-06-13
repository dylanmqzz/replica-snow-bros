package Juego;

import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import GameElements.GameElement;
import Interface.InterfacesDeColision.Colisionable;
import Interface.InterfacesDeColision.Colisionador;
import Utils.Sensor.Sensor;

public class ControladorColisiones {
    protected List<GameElement> listaColisionables;
    protected final ReadWriteLock lock = new ReentrantReadWriteLock();

    public ControladorColisiones() {
        listaColisionables = new LinkedList<GameElement>();
    }

    public void buscarColisiones(GameElement elemento) {
        lock.readLock().lock();
        try {
            for (GameElement colisionable : listaColisionables) {
                if (elemento != colisionable)
                    if (elemento.getHitbox() != null && colisionable.getHitbox() != null)
                        if (elemento.getHitbox().chequearColision(colisionable.getHitbox()))
                            resolverColision((Colisionador) elemento, (Colisionable) colisionable);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public void buscarColisiones(Sensor sensor) {
        lock.readLock().lock();
        try {
            for (GameElement colisionable : listaColisionables) {
                if (sensor.getHitbox().chequearColision(colisionable.getHitbox()))
                    resolverColision(sensor, (Colisionable) colisionable);
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    protected void resolverColision(Colisionador colisionador, Colisionable colisionable) {
        colisionable.aceptarColision(colisionador);
    }

    public void cargarElementoColisionable(GameElement elemento) {
        lock.writeLock().lock();
        try {
            listaColisionables.add(elemento);
        } finally {
            lock.writeLock().unlock();
        }
        
    }

    public void eliminarElementoColisionable(GameElement elemento) {
        lock.writeLock().lock();
        try {
            listaColisionables.remove(elemento);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void limpiarColisionables() {
        lock.writeLock().lock();
        try {
            listaColisionables.clear();
        } finally {
            lock.writeLock().unlock();
        }
    }
    
}
