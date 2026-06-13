package Utils.Sensor;

import GameElements.GameElement;
import GameElements.HitBox;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import GameElements.NoEstaticos.Proyectiles.BolaDeNieve;
import Utils.Vector2;

public class CapturadorGolpes extends Sensor {
    protected Vector2 posicionReferencia;

    public CapturadorGolpes(GameElement p, Vector2 posReferencia, float width, float height) {
        this.propietario = p;
        controladorColisiones = propietario.getMiJuego().getControladorColisiones();
        posicionReferencia = posReferencia;
        hitbox = new HitBox(width, height, posReferencia, new Vector2(-width / 2, -height / 2));
        actualizarPosicion();
    }

    protected void actualizarPosicion() {
        Vector2 temporal = propietario.getPosicion().clone();
        temporal.sumar(posicionReferencia);
        hitbox.setPosition(temporal.clone());
    }

    @Override
    public void colisionar(BolaDeNieve bolan) {
        Enemigo enemigo = ((Enemigo) propietario);
        enemigo.restarResistencia();
        if (bolan.dobleDaño())
            enemigo.restarResistencia();
        bolan.eliminar();
    }

    @Override
    public void update() {
        actualizarPosicion();
        controladorColisiones.buscarColisiones(this);
    }

}
