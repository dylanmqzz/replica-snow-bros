package Observers;

import javax.swing.JLabel;
import java.io.File;

import Enumerados.Eventos;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import Grafica.Paneles.PanelNivel;

public class ObserverMuerte extends JLabel implements Observer {
    protected PanelNivel panelNivel;
    protected Enemigo animal;
    protected int muerte;

    public ObserverMuerte(Enemigo enemigo, PanelNivel panel) {
        super();
        animal = enemigo;
        setVisible(false);
        panelNivel = panel;
    }

    public void update(Eventos e) {
        switch (e) {
            case CAMBIO_RESISTENCIA:
                if (animal.getResistencia() == animal.resistenciaMaxima())
                    setVisible(false);
                else {
                    if (animal.getResistencia() >= 2)
                        muerte = 1;
                    else
                        muerte = 2;
                    mostrarBola();
                }
                break;
            case MUERTE:
                muerte = 3;
                mostrarBola();
                break;
            case ACTUALIZACION_GRAFICA:
                if (isVisible())
                    mostrarBola();
                break;
            case ELIMINACION:
                panelNivel.remove(this);
                panelNivel.repaint();
                break;
            case EMPUJAR:
                if (animal.getDireccionEmpuje() > 0)
                    muerte = 4;
                else
                    muerte = 5;
            default:
                break;
        }
    }

    private void mostrarBola() {
        String rutaBola;
        if (animal.getMiJuego().esClasica()) {
            rutaBola = "src/Assets" + File.separator + "Sprites" + File.separator + "DominioClasico" + File.separator + "ANIMAL_CONGELADO" + File.separator + "MUERTE" + muerte + ".gif";
            setBounds((int) animal.getPosicion().getX() - 8, (int) animal.getPosicion().getY() + 35, 45, 45);
        } else {
            rutaBola = "src/Assets" + File.separator + "Sprites" + File.separator + "DominioPersonalizado" + File.separator + "ANIMAL_CONGELADO" + File.separator + "MUERTE" + muerte + ".gif";
            setBounds((int) animal.getPosicion().getX() - 8, (int) animal.getPosicion().getY() + 35, 45, 45);
        }
        this.setText("<html><img src='file:" + rutaBola + "' width='50' height='50'></html>");
        setVisible(true);
    }

}
