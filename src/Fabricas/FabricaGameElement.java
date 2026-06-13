package Fabricas;

import Enumerados.TipoElementos;
import GameElements.*;
import GameElements.Estaticos.Decoracion.Obstaculo.Escalera;
import GameElements.Estaticos.Decoracion.Obstaculo.Pared;
import GameElements.Estaticos.Decoracion.Obstaculo.ParedDestructible;
import GameElements.Estaticos.Decoracion.Obstaculo.SueloResbaladizo;
import GameElements.Estaticos.Decoracion.Obstaculo.Trampa;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaEstatica;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaQuebradiza;
import GameElements.Estaticos.PowerUp.PowerUpAzul;
import GameElements.Estaticos.PowerUp.PowerUpDoblePuntaje;
import GameElements.Estaticos.PowerUp.PowerUpFruta;
import GameElements.Estaticos.PowerUp.PowerUpVidaExtra;
import GameElements.Estaticos.PowerUp.PowerUpRoja;
import GameElements.Estaticos.PowerUp.PowerUpVerde;
import GameElements.NoEstaticos.Enemigos.Animales.DemonioRojo;
import GameElements.NoEstaticos.Enemigos.Animales.RanaDeFuego;
import GameElements.NoEstaticos.Enemigos.Animales.TrollAmarillo;
import GameElements.NoEstaticos.Enemigos.Animales.Indestructibles.Calabaza;
import GameElements.NoEstaticos.Enemigos.Animales.Indestructibles.Fantasma;
import GameElements.NoEstaticos.Enemigos.Jefes.Kamakichi;
import GameElements.NoEstaticos.Enemigos.Jefes.Moghera;
import GameElements.NoEstaticos.Jugador.*;
import GameElements.NoEstaticos.Proyectiles.*;
import Utils.Vector2;
import Juego.ElementsRegister;
import Juego.Juego;

public class FabricaGameElement {
    protected FabricaSprites fabricaSprites;
    protected ElementsRegister registrador;
    protected Juego miJuego;

    public FabricaGameElement(FabricaSprites fs, Juego j, ElementsRegister registrador) {
        miJuego = j;
        this.registrador = registrador;
        fabricaSprites = fs;
    }

    public void setFabricaSprites(FabricaSprites fs) {
        fabricaSprites = fs;
    }

    public GameElement crearElemento(TipoElementos tipo, Vector2 posicion) {
        GameElement elemento;
        switch (tipo) {
            case PLATAFORMA_ESTATICA:
                elemento = new PlataformaEstatica(posicion);
                break;
            case PLATAFORMA_QUEBRADIZA:
                elemento = new PlataformaQuebradiza(posicion);
                break;
            case PLATAFORMA_MOVIL:
                elemento = new PlataformaMovil(posicion);
                break;
            case SUELO_RESBALADIZO:
                elemento = new SueloResbaladizo(posicion);
                break;
            case ESCALERA:
                elemento = new Escalera(posicion);
                break;
            case TRAMPA:
                elemento = new Trampa(posicion);
                break;
            case PARED:
                elemento = new Pared(posicion);
                break;
            case PARED_DESTRUCTIBLE:
                elemento = new ParedDestructible(posicion);
                break;
            case DEMONIO_ROJO:
                elemento = new DemonioRojo(posicion);
                break;
            case TROLL_AMARILLO:
                elemento = new TrollAmarillo(posicion);
                break;
            case RANA_DE_FUEGO:
                elemento = new RanaDeFuego(posicion);
                break;
            case JUGADOR:
                elemento = new Jugador(posicion);
                break;
            case POWER_UP_AZUL:
                elemento = new PowerUpAzul(posicion);
                break;
            case POWER_UP_VIDAEXTRA:
                elemento = new PowerUpVidaExtra(posicion);
                registrador.registrarElementoColisionable(elemento);
                break;
            case POWER_UP_ROJA:
                elemento = new PowerUpRoja(posicion);
                break;
            case POWER_UP_FRUTA:
                elemento = new PowerUpFruta(posicion);
                break;
            case POWER_UP_VERDE:
                elemento = new PowerUpVerde(posicion);
                break;
            case CALABAZA:
                elemento = new Calabaza(posicion);
                break;
            case FANTASMA:
                elemento = new Fantasma(posicion);
                break;
            case POWER_UP_DOBLE_PUNTAJE:
                elemento = new PowerUpDoblePuntaje(posicion);
                break;
            case MOGHERA:
                elemento = new Moghera(posicion);
                break;
            case KAMAKICHI:
                elemento = new Kamakichi(posicion);
                break;
            default:
                elemento = new PlataformaEstatica(posicion);
        }
        elemento.setSprite(fabricaSprites.obtenerSprite(tipo));
        elemento.setJuego(miJuego);
        registrador.registrarElemento(elemento);
        return elemento;
    }

    public Proyectil crearProyectil(TipoElementos tipo, Vector2 pos, Vector2 dir) {
        Proyectil elemento;
        switch (tipo) {
            case BOLA_DE_NIEVE:
                elemento = new BolaDeNieve(pos, dir);
                break;
            case BOLA_DE_FUEGO:
                elemento = new BolaDeFuego(pos, dir);
                registrador.registrarElementoColisionable(elemento);
                break;
            case FUEGO:
                elemento = new Fuego(pos, dir, 950);
                registrador.registrarElementoColisionable(elemento);
                break;
            case BOMBA:
                elemento = new Bomba(pos, dir);
                registrador.registrarElementoColisionable(elemento);
                break;
            default:
                elemento = new BolaDeNieve(pos, dir);
        }
        elemento.setSprite(fabricaSprites.obtenerSprite(tipo));
        elemento.setJuego(miJuego);
        registrador.registrarElemento(elemento);
        return elemento;
    }

    public Juego getMiJuego() {
        return miJuego;
    }

}
