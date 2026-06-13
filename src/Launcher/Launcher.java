package Launcher;

import Grafica.GUI;
import Juego.Juego;

public class Launcher {
	public static void main(String[] args) {
		GUI grafica = new GUI();
		Juego juego = new Juego(grafica);
		grafica.registraControladorJuego(juego);
	}
	
}
