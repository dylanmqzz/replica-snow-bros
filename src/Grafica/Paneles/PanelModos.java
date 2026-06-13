package Grafica.Paneles;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import Grafica.ConstantesGraficas;
import Interface.InterfaceControladores.ControladorVistas;

public class PanelModos extends PanelVista {
    protected JButton botonClasico;
    protected JButton botonContrarreloj;
    protected JButton botonSuperviviencia;
    protected JButton botonVolver;

    public PanelModos(ControladorVistas controlador) {
        super(controlador);
        agregarImagenFondo("src//Assets//Sprites//MENU_MODOS.jpg");
        setLayout(null);
        agregarBotonesModo();
        agregarBotonVolver();
    }

    protected void agregarBotonesModo() {
        int anchoBoton = 180;
        int altoBoton = 60;
        int separacion = 15;
        int totalAncho = (3 * anchoBoton) + (2 * separacion);
        int posX = (ConstantesGraficas.PANEL_ANCHO - totalAncho) / 2;
        int posY = 350; 

        botonClasico = crearBotonRetro("CLASICO", new Color(100, 200, 100));
        botonContrarreloj = crearBotonRetro("CONTRARRELOJ", new Color(200, 150, 50));
        botonSuperviviencia = crearBotonRetro("SUPERVIVENCIA", new Color(80, 180, 255));
        
        botonClasico.setBounds(posX, posY, anchoBoton, altoBoton);
        botonContrarreloj.setBounds(posX + anchoBoton + separacion, posY, anchoBoton, altoBoton);
        botonSuperviviencia.setBounds(posX + 2 * (anchoBoton + separacion), posY, anchoBoton, altoBoton);
        
        registrarOyenteBotonClasico();
        registrarOyenteBotonContrareloj();
        registrarOyenteBotonSupervivencia();
        
        add(botonClasico);
        add(botonContrarreloj);
        add(botonSuperviviencia);
    }

    protected void agregarBotonVolver() {
        botonVolver = crearBotonRetro("MENU", new Color(200, 50, 50));
        botonVolver.setBounds((ConstantesGraficas.PANEL_ANCHO / 2) - 75, ConstantesGraficas.PANEL_ALTO - 100, 150, 40);
        registrarOyenteBotonVolver();
        add(botonVolver);
    }

    protected void registrarOyenteBotonClasico() {
        botonClasico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controladorVista.cambiarModoDeJuego(1);
                controladorVista.accionarInicioJuego();
            }
        });
    }

    protected void registrarOyenteBotonContrareloj() {
        botonContrarreloj.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controladorVista.cambiarModoDeJuego(2);
                controladorVista.accionarInicioJuego();
            }
        });
    }

    protected void registrarOyenteBotonSupervivencia() {
        botonSuperviviencia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controladorVista.cambiarModoDeJuego(3);
                controladorVista.accionarInicioJuego();
            }
        });
    }

    protected void registrarOyenteBotonVolver() {
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controladorVista.accionarPanelMenu();
            }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(new Color(0, 0, 0, 120));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setFont(new Font("Press Start 2P", Font.PLAIN, 25));
        g2d.setColor(Color.WHITE);
        String titulo = "SELECCIONAR MODO";
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(titulo)) / 2;
        int y = (getHeight() / 2) + 50;
        g2d.drawString(titulo, x, y);
        g2d.dispose();
    }

}
