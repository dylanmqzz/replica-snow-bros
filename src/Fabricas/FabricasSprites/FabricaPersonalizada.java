package Fabricas.FabricasSprites;

import Enumerados.TipoElementos;
import Fabricas.FabricaSprites;
import Grafica.Sprite;

public class FabricaPersonalizada extends FabricaSprites{
    protected final String RUTA_SPRITES = "src//Assets//Sprites//DominioPersonalizado";

    public FabricaPersonalizada() {

    }

    public Sprite obtenerSprite(TipoElementos tipo){
        String nombreTipo = tipo.toString();
        String ruta = RUTA_SPRITES + "//" + nombreTipo; 
        return new Sprite(ruta);
    }

}
