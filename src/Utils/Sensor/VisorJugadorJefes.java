package Utils.Sensor;

import GameElements.NoEstaticos.Enemigos.Enemigo;
import Utils.Vector2;

public class VisorJugadorJefes extends VisorJugador {
    protected final float toleranciaVertical = 150;

    public VisorJugadorJefes(Enemigo propietario, float rango) {
        super(propietario, rango);
    }

    public boolean objetivoALaVista() {
        if (!objetivoEnRango())
            return false;
        Vector2 dirObjetivo = propietario.getPosicion().direccionHacia(objetivo.getPosicion());
        if (dirObjetivo.getX() * propietario.getDireccionMirada().getX() > 0) {
            if (Math.abs(dirObjetivo.getY()) <= toleranciaVertical)
                return true;
        }
        return false;
    }

}
