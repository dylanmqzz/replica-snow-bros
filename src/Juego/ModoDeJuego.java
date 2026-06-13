package Juego;

import java.util.LinkedList;

import Parser.CreadorDeNiveles;

public abstract class ModoDeJuego {
    protected Juego juego;
    protected CreadorDeNiveles creadorNiveles;
    protected LinkedList<String> rutasNiveles;
    protected Nivel nivelActual;
    protected int nivelActualIndex;
    protected int tiempoPartida;

    public ModoDeJuego(CreadorDeNiveles creador) {
        this.juego = creador.getJuego();
        this.creadorNiveles = creador;
        this.rutasNiveles = new LinkedList<String>();
        this.nivelActualIndex = 0;
    }

    public void iniciarNivel() {
        cargarNivel(nivelActualIndex);
    }

    protected void cargarNivel(int indice) {
        int puntajeAcumulado = 0;
        int vidasAcumuladas = 3;
        if (nivelActual != null) {
            if (nivelActual.getJugador() != null) {
                puntajeAcumulado = nivelActual.getJugador().getPuntaje();
                vidasAcumuladas = nivelActual.getJugador().getVidas();
            }
            juego.getControladorGrafica().limpiarGraficaNivelActual();
            juego.getControladorColisiones().limpiarColisionables();
            nivelActual.reiniciarNivel();
        }
        nivelActualIndex = indice;
        String rutaNivelActual = rutasNiveles.get(nivelActualIndex);
        nivelActual = creadorNiveles.cargarDesdeArchivo(rutaNivelActual);

        if (nivelActual.getJugador() != null) {
            nivelActual.getJugador().setPuntaje(puntajeAcumulado);
            nivelActual.getJugador().setVidas(vidasAcumuladas);
        }
        juego.reiniciarHilo(); 
    }

    public Nivel getNivel() {
        return nivelActual;
    }

    public Juego getJuego() {
        return juego;
    }

    public abstract void avanzarNivel();

    public abstract void actualizarUI();

    public abstract void tickTimer();

    public abstract boolean permiteCalabaza();

}
