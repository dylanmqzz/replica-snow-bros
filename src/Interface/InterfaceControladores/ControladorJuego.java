package Interface.InterfaceControladores;

import GameElements.NoEstaticos.Jugador.*;
import Juego.ModoDeJuego;
import Juego.Ranking;

public interface ControladorJuego {
    public void iniciar();

    public void finalizar();

    public Jugador getJugador();

    public void reiniciarModo();

    public void configurarModo(int m);

    public ModoDeJuego getModoDeJuego();

    public void agregarJugadorAlRanking();

    public Ranking getRankingClasico();

    public Ranking getRankingContraReloj();

    public Ranking getRankingSupervivencia();

    public void guardarRankings();

    public void accionarDominioPersonalizado();

    public void accionarDominioClasico();

    public void setNombreJugador(String s);
    
}
