package Juego;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Fabricas.FabricaGameElement;
import Fabricas.FabricaSprites;
import Fabricas.FabricasSprites.FabricaClasica;
import Fabricas.FabricasSprites.FabricaPersonalizada;
import GameElements.NoEstaticos.Jugador.Jugador;
import Hilo.*;
import Interface.InterfaceControladores.*;
import Parser.CreadorDeNiveles;
import Utils.ConstantesSonidos;
import Utils.ReproductorSonido;

public class Juego implements ControladorJuego {
	protected ModoDeJuego modo;
	protected int numeralModo;
	protected FabricaGameElement fabricaGE;
	protected ControladorGraficas controlGrafica;
	protected ControladorColisiones controladorColisiones;
	protected HiloJuego hiloJuego;
	protected int puntaje;
	protected Ranking rankingClasico;
	protected Ranking rankingContrarreloj;
	protected Ranking rankingSupervivencia;
	protected ReproductorSonido sonido = ReproductorSonido.getInstancia();
	protected boolean esFabricaClasica;
	protected String nombreTemporal;

	public Juego(ControladorGraficas cg) {
		controlGrafica = cg;
		controladorColisiones = new ControladorColisiones();
		ElementsRegister registers = new ElementsRegister(controlGrafica, controladorColisiones);
		FabricaClasica fsC = new FabricaClasica();
		fabricaGE = new FabricaGameElement(fsC, this, registers);
		esFabricaClasica = true;
		cargarRankings();
	}

	public void accionarDominioClasico() {
		fabricaGE.setFabricaSprites(new FabricaClasica());
		esFabricaClasica = true;
	}

	public void accionarDominioPersonalizado() {
		fabricaGE.setFabricaSprites(new FabricaPersonalizada());
		esFabricaClasica = false;
	}

	public Ranking getRankingPorModo(int modo) {
		switch (modo) {
			case 1:
				return rankingClasico;
			case 2:
				return rankingContrarreloj;
			case 3:
				return rankingSupervivencia;
			default:
				return rankingClasico;
		}
	}

	public void iniciar() {
		modo.iniciarNivel();
		controlGrafica.mostrarNivel();
		sonido.reproducirMusicaLoop(ConstantesSonidos.MusicaFondo);
		sonido.setVolumenMusica(0.3f);
	}

	public void setNombreJugador(String nombre) {
		this.nombreTemporal = nombre;
	}

	public String getNombreJugador() {
		return nombreTemporal;
	}

	public void reiniciarHilo() {
		detenerHilo();
		controlGrafica.reiniciarHelperDeInput();
		hiloJuego = new HiloJuego(modo, controlGrafica.getHelper());
		hiloJuego.start();
		modo.actualizarUI();
	}

	public void finalizar() {
		detenerHilo();
		sonido.detenerMusica();
		agregarJugadorAlRanking();
		guardarRankings();
		sonido.reproducirSonido(ConstantesSonidos.GameOver);
		controlGrafica.gameOver();
	}

	public void ganaste() {
		detenerHilo();
		sonido.detenerMusica();
		sonido.reproducirSonido(ConstantesSonidos.Victoria);
		agregarJugadorAlRanking();
		guardarRankings();
		controlGrafica.victoria();
	}

	protected void detenerHilo() {
		if (hiloJuego != null) {
			hiloJuego.detener();
			try {
				hiloJuego.join(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		hiloJuego = null;
	}

	public void configurarModo(int m) {
		switch (m) {
			case 1:
				modo = new ModoClasico(new CreadorDeNiveles(fabricaGE));
				numeralModo = 1;
				break;
			case 2:
				modo = new ModoContrarreloj(new CreadorDeNiveles(fabricaGE));
				numeralModo = 2;
				break;
			case 3:
				modo = new ModoSupervivencia(new CreadorDeNiveles(fabricaGE));
				numeralModo = 3;
				break;
			default:
				break;
		}
	}

	public void reiniciarModo() {
		FabricaSprites fs;
		if (esFabricaClasica)
			fs = new FabricaClasica();
		else
			fs = new FabricaPersonalizada();
		controladorColisiones = new ControladorColisiones();
		ElementsRegister registers = new ElementsRegister(controlGrafica, controladorColisiones);
		fabricaGE = new FabricaGameElement(fs, this, registers);
		configurarModo(numeralModo);
	}

	public ModoDeJuego getModoDeJuego() {
		return modo;
	}

	public ControladorGraficas getControladorGrafica() {
		return controlGrafica;
	}

	public ControladorColisiones getControladorColisiones() {
		return controladorColisiones;
	}

	public boolean esClasica() {
		return esFabricaClasica;
	}

	public FabricaGameElement getFabricaGameElements() {
		return fabricaGE;
	}

	public Jugador getJugador() {
		return modo.getNivel().getJugador();
	}

	public int getPuntaje() {
		return getJugador().getPuntaje();
	}

	public void agregarJugadorAlRanking() {
		if (modo != null && modo.getNivel() != null) {
			RegistroJugador jugador = modo.getNivel().getJugador().getRegistro();
			switch (numeralModo) {
				case 1:
					rankingClasico.agregarJugador(jugador);
					break;
				case 2:
					rankingContrarreloj.agregarJugador(jugador);
					break;
				case 3:
					rankingSupervivencia.agregarJugador(jugador);
					break;
			}
		}
	}

	public void guardarRankings() {
		try {
			FileOutputStream fos1 = new FileOutputStream("./RANKING_CLASICO.tdp");
			ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
			oos1.writeObject(getRankingPorModo(1).getTopJugadores());
			oos1.flush();
			oos1.close();

			FileOutputStream fos2 = new FileOutputStream("./RANKING_CONTRARRELOJ.tdp");
			ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
			oos2.writeObject(getRankingPorModo(2).getTopJugadores());
			oos2.flush();
			oos2.close();

			FileOutputStream fos3 = new FileOutputStream("./RANKING_SUPERVIVENCIA.tdp");
			ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
			oos3.writeObject(getRankingPorModo(3).getTopJugadores());
			oos3.flush();
			oos3.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void cargarRankings() {
		try {
			FileInputStream fis = new FileInputStream("./RANKING_CLASICO.tdp");
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<RegistroJugador> top = (ArrayList<RegistroJugador>) ois.readObject();
			rankingClasico = new Ranking();
			for (RegistroJugador j : top) {
				rankingClasico.agregarJugador(j);
			}
			ois.close();
		} catch (FileNotFoundException e) {
			rankingClasico = new Ranking();
		} catch (Exception e) {
			e.printStackTrace();
			rankingClasico = new Ranking();
		}

		try {
			FileInputStream fis = new FileInputStream("./RANKING_CONTRARRELOJ.tdp");
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<RegistroJugador> top = (ArrayList<RegistroJugador>) ois.readObject();
			rankingContrarreloj = new Ranking();
			for (RegistroJugador j : top) {
				rankingContrarreloj.agregarJugador(j);
			}
			ois.close();
		} catch (FileNotFoundException e) {
			rankingContrarreloj = new Ranking();
		} catch (Exception e) {
			e.printStackTrace();
			rankingContrarreloj = new Ranking();
		}

		try {
			FileInputStream fis = new FileInputStream("./RANKING_SUPERVIVENCIA.tdp");
			ObjectInputStream ois = new ObjectInputStream(fis);
			ArrayList<RegistroJugador> top = (ArrayList<RegistroJugador>) ois.readObject();
			rankingSupervivencia = new Ranking();
			for (RegistroJugador j : top) {
				rankingSupervivencia.agregarJugador(j);
			}
			ois.close();
		} catch (FileNotFoundException e) {
			rankingSupervivencia = new Ranking();
		} catch (Exception e) {
			e.printStackTrace();
			rankingSupervivencia = new Ranking();
		}
	}

	public Ranking getRankingClasico() {
		return rankingClasico;
	}

	public Ranking getRankingContraReloj() {
		return rankingContrarreloj;
	}

	public Ranking getRankingSupervivencia() {
		return rankingSupervivencia;
	}

}
