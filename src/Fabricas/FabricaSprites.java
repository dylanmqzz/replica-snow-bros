package Fabricas;

import Enumerados.TipoElementos;
import Grafica.Sprite;

public abstract class FabricaSprites {
    public abstract Sprite obtenerSprite(TipoElementos tipo);

}
