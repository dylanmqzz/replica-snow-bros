package Interface.Visitors;

import GameElements.Estaticos.Estatica;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import GameElements.NoEstaticos.Jugador.Jugador;
import GameElements.NoEstaticos.Proyectiles.Proyectil;

public interface Registrador {
    public void registrar(Estatica e);

    public void registrar(Enemigo e);

    public void registrar(Proyectil e);

    public void registrar(Jugador e);
    
    public void registrar(PlataformaMovil p);

}
