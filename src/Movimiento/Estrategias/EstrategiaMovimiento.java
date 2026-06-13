package Movimiento.Estrategias;

import Utils.Vector2;
import GameElements.NoEstaticos.NoEstatico;

public abstract class EstrategiaMovimiento {
    protected NoEstatico entidad;
    protected float velocidadCaminar;
    protected float velocidadSalto;
    protected final float GRAVEDAD = 0.5f;

    public EstrategiaMovimiento(NoEstatico e) {
        entidad = e;
    }

    public void actualizarMovimiento(Vector2 dir, boolean salto) {
        entidad.getVelocidad().setX(dir.getX() * velocidadCaminar);

        if (salto && entidad.enSuelo())
            entidad.getVelocidad().setY(velocidadSalto);
        if (!entidad.enSuelo())
            entidad.getVelocidad().sumar(0, GRAVEDAD);
    }

    public void efectuarMovimiento() {
        entidad.getPosicion().sumar(entidad.getVelocidad());
    }

    public float getVelocidadCaminar() {
        return velocidadCaminar;
    }

    public float getVelocidadSalto() {
        return velocidadSalto;
    }

}
