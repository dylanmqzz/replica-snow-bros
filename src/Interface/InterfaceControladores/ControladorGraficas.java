package Interface.InterfaceControladores;

import Observers.*;
import Interface.InterfacesGameElement.*;
import Movimiento.HelperLecturaMovimiento;
import Grafica.Paneles.PanelNivel;

public interface ControladorGraficas {
	public void registraControladorJuego(ControladorJuego controladorJuego);

	public Observer registrarGameElement(LogicElement entidad);

	public void mostrarPantallaMenu();

	public void mostrarNivel();

	public void gameOver();

	public PanelNivel getPanelNivel();

	public void limpiarGraficaNivelActual();

	public void reiniciarHelperDeInput();

	public HelperLecturaMovimiento getHelper();

	public void victoria();
	
}