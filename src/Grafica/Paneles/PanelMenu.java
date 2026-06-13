package Grafica.Paneles;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import Grafica.ConstantesGraficas;
import Interface.InterfaceControladores.ControladorVistas;

public class PanelMenu extends PanelVista {
    protected JButton botonIniciar;
    protected JButton botonPuntajes;
    protected JButton botonClasico;
    protected JButton botonPersonalizado;
    protected String dominioSeleccionado = "CLASICO";

    public PanelMenu(ControladorVistas controlador) {
        super(controlador);
        setPreferredSize(new Dimension(ConstantesGraficas.PANEL_ANCHO, ConstantesGraficas.PANEL_ALTO));
        setLayout(null);
        agregarImagenFondo("src//Assets//Sprites//MENU_INICIO.jpg");
        agregarBotonIniciar();
        agregarBotonClasico();
        agregarBotonPersonalizado();
        agregarBotonPuntaje();
        actualizarEstadoBotones();
    }

    protected void agregarBotonIniciar() {
        botonIniciar = crearBotonRetro("INICIAR", new Color(200, 50, 50));
        botonIniciar.setBounds((ConstantesGraficas.PANEL_ANCHO / 2) - 100, 320, 200, 40);
        
        registrarOyenteBotonIniciar();
        add(botonIniciar);
    }

    protected void agregarBotonClasico() {
        botonClasico = crearBotonRetro("DOMINIO CLASICO", new Color(100, 200, 100)); 
        botonClasico.setBounds((ConstantesGraficas.PANEL_ANCHO - 420) / 2, 420, 200, 40);
        
        registrarOyenteBotonClasico();
        add(botonClasico);
    }

    protected void agregarBotonPersonalizado() {
        botonPersonalizado = crearBotonRetro("DOMINIO PERSONALIZADO", new Color(200, 150, 50)); 
        int x_clasico = (ConstantesGraficas.PANEL_ANCHO - 420) / 2;
        botonPersonalizado.setBounds(x_clasico + 220, 420, 200, 40);
        
        registrarOyenteBotonPersonalizado();
        add(botonPersonalizado);
    }

    protected void agregarBotonPuntaje() {
        botonPuntajes = crearBotonRetro("RANKING", new Color(50, 150, 200));
        botonPuntajes.setBounds((ConstantesGraficas.PANEL_ANCHO / 2) - 100, 370, 200, 40);
        
        registrarOyenteBotonPuntajes();
        add(botonPuntajes);
    }

    protected void registrarOyenteBotonIniciar() {
        botonIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (dominioSeleccionado.equals("CLASICO"))
                    controladorVista.getControladorJuego().accionarDominioClasico();
                else
                    controladorVista.getControladorJuego().accionarDominioPersonalizado();
                controladorVista.accionarPanelModoDeJuego();
            }
        });
    }

    protected void registrarOyenteBotonClasico() {
        botonClasico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dominioSeleccionado = "CLASICO";
                actualizarEstadoBotones();
            }
        });
    }

    protected void registrarOyenteBotonPersonalizado() {
        botonPersonalizado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dominioSeleccionado = "PERSONALIZADO";
                actualizarEstadoBotones(); 
            }
        });
    }

    protected void registrarOyenteBotonPuntajes() {
        botonPuntajes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controladorVista.accionarPanelPuntajes();
            }
        });
    }

    private void actualizarEstadoBotones() {
        if (dominioSeleccionado.equals("CLASICO")) {
            botonClasico.setBackground(new Color(100, 200, 100));
            botonClasico.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
            botonPersonalizado.setBackground(new Color(100, 75, 25));
            botonPersonalizado.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        } else {
            botonPersonalizado.setBackground(new Color(200, 150, 50));
            botonPersonalizado.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
            botonClasico.setBackground(new Color(50, 100, 50));
            botonClasico.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        }
        botonClasico.repaint();
        botonPersonalizado.repaint();
    }

}
