package Juego;

import javax.swing.SwingUtilities;

import Parser.CreadorDeNiveles;

public class ModoSupervivencia extends ModoDeJuego {
    protected static final int PUNTAJE_OBJETIVO = 10000;
    protected static final int TIEMPO_SPAWN = 20;
    protected int oleadaActual = 1;
    protected int spawnTimer;

    public ModoSupervivencia(CreadorDeNiveles creador) {
        super(creador);
        tiempoPartida = 0;
        spawnTimer = 0;
        rutasNiveles.add("src//Parser//ArchivosNiveles//Supervivencia_Arena.txt");
    }

    @Override
    public void avanzarNivel() {
        spawnTimer = 0; 
        oleadaActual++; 
        String rutaNivelActual = rutasNiveles.get(nivelActualIndex);
        creadorNiveles.cargarOleadaSupervivencia(nivelActual, rutaNivelActual);
        SwingUtilities.invokeLater(() -> {
            juego.getControladorGrafica().getPanelNivel().actualizarOleada(oleadaActual);
        });
    }
    
    @Override
    public void actualizarUI() {
        juego.getControladorGrafica().getPanelNivel().actualizarPuntaje(juego.getJugador().getPuntaje());
        juego.getControladorGrafica().getPanelNivel().actualizarOleada(oleadaActual);
        juego.getControladorGrafica().getPanelNivel().actualizarNivel(0);
    }

    @Override
    public void tickTimer() {
        tiempoPartida++;
        spawnTimer++;
        
        SwingUtilities.invokeLater(() -> {
            juego.getControladorGrafica().getPanelNivel().actualizarTiempo(tiempoPartida);
        });
        
        if (nivelActual != null && juego.getJugador().getPuntaje() >= PUNTAJE_OBJETIVO)
            juego.ganaste();
        if (spawnTimer >= TIEMPO_SPAWN)
            avanzarNivel();
    }

    @Override
    public boolean permiteCalabaza() {
        return false;
    }

}
