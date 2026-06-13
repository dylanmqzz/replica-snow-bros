package Grafica.Paneles;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.*;

import Interface.InterfaceControladores.*;
import Grafica.ConstantesGraficas;

public class PanelVista extends JPanel {
    protected ControladorVistas controladorVista;
    protected Image fondo;

    public PanelVista(ControladorVistas controlador) {
        controladorVista = controlador;
    }

    protected void agregarImagenFondo(String ruta) {
        ImageIcon icono = new ImageIcon(ruta);
        this.fondo = icono.getImage().getScaledInstance(ConstantesGraficas.PANEL_ANCHO, ConstantesGraficas.PANEL_ALTO, Image.SCALE_SMOOTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (fondo != null)
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }

    protected void transparentarBoton(JButton boton) {
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
    }

    protected JButton crearBotonRetro(String texto, Color colorBase) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Press Start 2P", Font.PLAIN, 9)); 
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);

        boton.setBackground(colorBase);
        boton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 3), BorderFactory.createLineBorder(Color.WHITE, 2)));
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBase.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBase);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBase.darker());
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorBase);
            }
        });
        return boton;
    }

}
