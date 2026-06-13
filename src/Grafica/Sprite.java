package Grafica;

import java.io.File;

import Enumerados.EstadoSprite;

public class Sprite {
    protected String rutaSprites;
    protected EstadoSprite estadoActual;

    public Sprite(String ruta) {
        rutaSprites = ruta;
        estadoActual = EstadoSprite.BASE;
    }

    public String getRutaImagenActual() {
        return getRutaImagen(estadoActual);
    }

    public String getRutaImagen(EstadoSprite estado) {
        return rutaSprites + File.separator + estado.toString() + ".gif";
    }

    public EstadoSprite getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(EstadoSprite estado) {
        estadoActual = estado;
    }
    
}
