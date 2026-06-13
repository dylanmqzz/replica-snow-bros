package Movimiento.Estrategias;

import GameElements.NoEstaticos.NoEstatico;
import Movimiento.Estados.Normal;
import Utils.Vector2;

public class EstrategiaEscalando extends EstrategiaMovimiento {
    protected float velocidadEscalar = -3f;
    protected float velocidadBajar = 2f;
    protected float velocidadCaminar = 3f;

    public EstrategiaEscalando(NoEstatico e) {
        super(e);
    }

    public void actualizarMovimiento(Vector2 dir, boolean salto) {
        entidad.getVelocidad().setX(velocidadCaminar*dir.getX());
        
        if (!salto && !entidad.enSuelo())
            entidad.getVelocidad().setY(velocidadBajar);
        else if (salto && !entidad.enSuelo())
            entidad.getVelocidad().setY(velocidadEscalar);
    }

    public void efectuarMovimiento() {
        entidad.getPosicion().sumar(entidad.getVelocidad());
        entidad.setEstado(new Normal(entidad));
    }

}
