package Juego;

import Parser.CreadorDeNiveles;

public class ModoContrarreloj extends ModoDeJuego {
    protected static final int TIEMPO_LIMITE = 300;

    public ModoContrarreloj(CreadorDeNiveles creador) {
        super(creador);
		tiempoPartida = TIEMPO_LIMITE;
        rutasNiveles.add("src//Parser//ArchivosNiveles//Nivel_1.txt");
        rutasNiveles.add("src//Parser//ArchivosNiveles//Nivel_2.txt");
        rutasNiveles.add("src//Parser//ArchivosNiveles//Nivel_3.txt");
        rutasNiveles.add("src//Parser//ArchivosNiveles//Nivel_4.txt");
        rutasNiveles.add("src//Parser//ArchivosNiveles//Nivel_5.txt");
        rutasNiveles.add("src//Parser//ArchivosNiveles//Nivel_6.txt");
    }
    
	@Override
    public void avanzarNivel() {
        int proximoIndice = nivelActualIndex + 1;
        if (proximoIndice < rutasNiveles.size())
            cargarNivel(proximoIndice);
        else
            juego.ganaste();
    }

	@Override
    public void actualizarUI() {
        juego.getControladorGrafica().getPanelNivel().actualizarPuntaje(juego.getJugador().getPuntaje());
        juego.getControladorGrafica().getPanelNivel().actualizarOleada(0);
        juego.getControladorGrafica().getPanelNivel().actualizarNivel(nivelActualIndex + 1);
	}
    
    @Override
    public void tickTimer() {
        tiempoPartida--;
        juego.getControladorGrafica().getPanelNivel().actualizarTiempo(tiempoPartida);
        if (tiempoPartida <= 0)
            juego.finalizar();
    }

    @Override
    public boolean permiteCalabaza() {
        return true;
    }

}
