package Parser;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Enumerados.TipoElementos;
import Utils.Vector2;
import Fabricas.FabricaGameElement;
import GameElements.GameElement;
import Juego.Juego;
import Juego.Nivel;
import GameElements.NoEstaticos.Jugador.*;

public class CreadorDeNiveles {
    protected FabricaGameElement fabrica;
    protected Random rand;
    protected List<TipoElementos> enemigosPosibles;

    public CreadorDeNiveles(FabricaGameElement fabrica) {
        this.fabrica = fabrica;
        this.rand = new Random();
        this.enemigosPosibles = new ArrayList<>();
        enemigosPosibles.add(TipoElementos.DEMONIO_ROJO);
        enemigosPosibles.add(TipoElementos.TROLL_AMARILLO);
        enemigosPosibles.add(TipoElementos.RANA_DE_FUEGO);
    }

    public Nivel cargarDesdeArchivo(String rutaArchivo) {
        Nivel nivel = new Nivel();
        nivel.setModo(fabrica.getMiJuego().getModoDeJuego());
        cargarMetadatosNivel(nivel, obtenerLinea(rutaArchivo, 0));
        cargarGameElementsEstaticos(nivel, obtenerLinea(rutaArchivo, 1));
        cargarGameElementsNoEstaticos(nivel, obtenerLinea(rutaArchivo, 1));
        cargarJugador(nivel);
        return nivel;
    }

    public void cargarOleadaSupervivencia(Nivel nivel, String rutaArchivo) {
        cargarMetadatosNivel(nivel, obtenerLinea(rutaArchivo, 0));
        cargarGameElementsNoEstaticos(nivel, obtenerLinea(rutaArchivo, 1));
    }

    protected void cargarMetadatosNivel(Nivel nivel, String linea) {
        Map<String, Integer> datos = extraerDatos(linea);
        nivel.setEnemigosRestantes(datos.getOrDefault("ENEMIGOS", 0));
    }

    protected Map<String, Integer> extraerDatos(String linea) {
        String[] partes = linea.split(";");
        Map<String, Integer> mapeoDatos = new HashMap<>();
        for (String parte : partes) {
            String[] subPartes = parte.split("=");
            mapeoDatos.put(subPartes[0], Integer.parseInt(subPartes[1]));
        }
        return mapeoDatos;
    }

    protected void cargarGameElementsEstaticos(Nivel nivel, String lineaElemento) {
        int x = 0;
        int y = 0;

        for (int i = 0; i < lineaElemento.length(); i += 3) { 
            String codigoElemento = lineaElemento.substring(i, i + 2); 
            TipoElementos tipo = TipoElementos.fromCodigo(codigoElemento); 
            if (x >= nivel.getColumnas()) { 
                x = 0; 
                y++;
            }
            GameElement elemento = null;
            Vector2 pos = new Vector2(x * 40, y * 40);
            
            switch (tipo) {
                case PLATAFORMA_ESTATICA:
                case PLATAFORMA_QUEBRADIZA:
                case PLATAFORMA_MOVIL:
                case SUELO_RESBALADIZO:
                case ESCALERA:
                case TRAMPA:
                case PARED:
                case PARED_DESTRUCTIBLE:
                case POWER_UP_AZUL:
                case POWER_UP_VIDAEXTRA:
                case POWER_UP_ROJA:
                    elemento = fabrica.crearElemento(tipo, pos); 
                    break;
                default:
                    break;
            }
            if (elemento != null)
                nivel.agregarElemento(elemento); 
            x++;
        }
    }

    protected void cargarGameElementsNoEstaticos(Nivel nivel, String lineaElemento) {
        int x = 0;
        int y = 0;

        for (int i = 0; i < lineaElemento.length(); i += 3) { 
            String codigoElemento = lineaElemento.substring(i, i + 2); 
            TipoElementos tipo = TipoElementos.fromCodigo(codigoElemento); 
            if (x >= nivel.getColumnas()) { 
                x = 0; 
                y++;
            }
            GameElement elemento = null;
            Vector2 pos = new Vector2(x * 40, y * 40);
            TipoElementos tipoACrear = null;

            switch (tipo) {
                case DEMONIO_ROJO:
                case MOGHERA:
                case KAMAKICHI:
                case TROLL_AMARILLO:
                case RANA_DE_FUEGO:
                    tipoACrear = tipo;
                    break;
                case ENEMIGO_ALEATORIO:
                    tipoACrear = enemigosPosibles.get(rand.nextInt(enemigosPosibles.size()));
                    break;
                default:
                    break;
            }
            if (tipoACrear != null) {
                elemento = fabrica.crearElemento(tipoACrear, pos);
                nivel.agregarElemento(elemento);
            }
            x++; 
        }
    }

    public String obtenerLinea(String archivo, int linea) {
        String resultado = "";
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String lineaLeida;
            int contador = 0;
            while ((lineaLeida = br.readLine()) != null) {
                if (contador == linea) {
                    resultado = lineaLeida;
                    break;
                }
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    private void cargarJugador(Nivel n) {
        GameElement jugador = fabrica.crearElemento(TipoElementos.JUGADOR, new Vector2(6 * 40, 11 * 40));
        n.setJugador((Jugador) jugador);
    }

    public Juego getJuego() {
        return fabrica.getMiJuego();
    }

}
