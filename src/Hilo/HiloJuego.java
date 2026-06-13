package Hilo;

import GameElements.Estaticos.Estatica;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import GameElements.NoEstaticos.Proyectiles.Proyectil;
import Juego.ModoDeJuego;
import Juego.Nivel;
import Movimiento.HelperLecturaMovimiento;

public class HiloJuego extends Thread {
    protected final double NS_POR_TICK = 1_000_000_000.0 / 30.0;
    protected volatile boolean running = true;
    protected ModoDeJuego modo;
    protected HelperLecturaMovimiento helper;
    protected long timer;
    protected int ticks = 0;

    public HiloJuego(ModoDeJuego modo, HelperLecturaMovimiento helper) {
        this.modo = modo;
        this.helper = helper;
    }

    public void detener() {
        running = false;
        this.interrupt();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double delta = 0;
        timer = System.currentTimeMillis();
        ticks = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / NS_POR_TICK;
            lastTime = now;

            while (delta >= 1) {
                tick();
                ticks++;
                delta -= 1;
            }
            if (System.currentTimeMillis() - timer >= 1000 && running) {
                timer += 1000;
                if (helper.primerTeclaPresionada())
                    modo.tickTimer(); 
                ticks = 0;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                running = false;
                Thread.currentThread().interrupt();
            }
        }
    }

    private void tick() {
        Nivel nivel = modo.getNivel();
        if (nivel == null) 
            return;

        helper.informarJugador();
        if(nivel != null && nivel.getJugador() != null)
            nivel.getJugador().tick();
        if (helper.primerTeclaPresionada()) {
            for (Enemigo e : nivel.obtenerEnemigos()) {
                e.tick();
            }
        }
        for (Proyectil p : nivel.obtenerProyectiles()) {
            if (p.estaActiva())
                p.mover();
        }
        for (Estatica e : nivel.obtenerEstaticos()) {
            e.decrementarTiempoDeVida();
            e.desplazar();
        }

        if(helper.primerTeclaPresionada())
            nivel.tickNivel();
    }

}
