package Observers;

import javax.swing.JLabel;
import javax.swing.SwingUtilities; 

import Interface.InterfacesGameElement.LogicElement;
import Enumerados.EstadoSprite;
import Enumerados.Eventos;
import Grafica.Paneles.PanelNivel;
import GameElements.NoEstaticos.Jugador.Jugador;

public class ObserverGrafico extends JLabel implements Observer {
	protected LogicElement elementoObservado;
	protected PanelNivel panelNivel;

	public ObserverGrafico(PanelNivel panel, LogicElement element) {
		super();
		panelNivel = panel;
		elementoObservado = element;
		update(Eventos.ACTUALIZACION_GRAFICA); 
	}

	public void update(Eventos e) {
		SwingUtilities.invokeLater(() -> {
			switch (e) {
				case ACTUALIZACION_GRAFICA:
					updateSprite();
					updatePosicionTamaño(); 
					break;
				case ELIMINACION:
					panelNivel.remove(ObserverGrafico.this); 
					panelNivel.repaint(); 
					break;
				case SUMA_VIDA:
					panelNivel.agregarVida(); 
					break;
				case PERDIDAVIDA:
					panelNivel.eliminarVida(); 
					break;
				case MUERTE:
					break;
				case SUMA_PUNTOS:
					panelNivel.actualizarPuntaje(((Jugador)elementoObservado).getPuntaje()); 
					break;
				default:
					break; 
			}
		});
	}

	protected void updateSprite() {
		EstadoSprite estadoActual = elementoObservado.getSprite().getEstadoActual();
		String rutaImagen = elementoObservado.getSprite().getRutaImagen(estadoActual);
		this.setText("<html><img src='file:" + rutaImagen + "' width='" + elementoObservado.getWidth() + "' height='" + elementoObservado.getHeight() + "'></html>");
		this.setIcon(null);
	}

	protected void updatePosicionTamaño() {
		int x = Math.round(elementoObservado.getPosicion().getX());
		int y = Math.round(elementoObservado.getPosicion().getY());
		setBounds(x, y + 40, elementoObservado.getWidth(), elementoObservado.getHeight());
	}

}
