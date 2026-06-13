package GameElements.Estaticos.Decoracion.Plataforma;

import Interface.InterfacesDeColision.Colisionador;
import Utils.Vector2;

public class PlataformaEstatica extends Plataforma {
    public PlataformaEstatica(Vector2 pos) {
        super(pos);
    }

    @Override
    public void aceptarColision(Colisionador c) {
        c.colisionar(this);
    }
   
}
