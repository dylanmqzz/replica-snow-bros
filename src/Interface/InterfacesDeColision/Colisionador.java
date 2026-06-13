package Interface.InterfacesDeColision;

import GameElements.Estaticos.PowerUp.PowerUp;
import GameElements.Estaticos.Decoracion.Obstaculo.*;
import GameElements.Estaticos.Decoracion.Plataforma.Plataforma;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import GameElements.NoEstaticos.Proyectiles.BolaDeFuego;
import GameElements.NoEstaticos.Proyectiles.BolaDeNieve;
import GameElements.NoEstaticos.Proyectiles.Bomba;
import GameElements.NoEstaticos.Proyectiles.Fuego;

public interface Colisionador {
    public void colisionar(BolaDeFuego bolaf);

    public void colisionar(Bomba bomba);

    public void colisionar(BolaDeNieve bolan);

    public void colisionar(Fuego fuego);

    public void colisionar(PowerUp P);

    public void colisionar(Enemigo e);

    public void colisionar(Plataforma p);

    public void colisionar(SueloResbaladizo pr);

    public void colisionar(PlataformaQuebradiza pq);

    public void colisionar(PlataformaMovil pm);

    public void colisionar(Escalera e);

    public void colisionar(Trampa t);

    public void colisionar(Pared p);

    public void colisionar(ParedDestructible p);
    
}
