package Interface.InterfacesGameElement;

import Utils.Vector2;
import Grafica.Sprite;
import Observers.*;

public interface LogicElement extends Observable {
    public Vector2 getPosicion();

    public Sprite getSprite();

    public int getWidth();

    public int getHeight();

    public int getZOrder();

}
