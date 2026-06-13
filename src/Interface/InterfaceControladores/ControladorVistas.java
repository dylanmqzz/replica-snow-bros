package Interface.InterfaceControladores;

public interface ControladorVistas {
    public void accionarInicioJuego();

	public void accionarPanelPuntajes();

	public void accionarPanelModoDeJuego();

	public void accionarPanelMenu();

	public void cambiarModoDeJuego(int modo);

	public ControladorJuego getControladorJuego();

	public void setNombreJugadorTemporal(String nombre);
	
}
