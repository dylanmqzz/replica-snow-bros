package Grafica.Paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Grafica.ConstantesGraficas;
import Interface.InterfaceControladores.ControladorVistas;
import Utils.ConstantesSonidos;
import Utils.ReproductorSonido;

public class PanelNombre extends PanelVista {
    protected JTextField campoNombre;
    protected JButton botonConfirmar;
    protected JLabel labelTitulo;
    protected JLabel labelInstruccion;
    protected ReproductorSonido sonido = ReproductorSonido.getInstancia();
    protected String nombreJugador;

    public PanelNombre(ControladorVistas cv) {
        super(cv);
        setLayout(null);
        setBackground(Color.BLACK);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        int anchoPanel = ConstantesGraficas.PANEL_ANCHO;
        
        labelTitulo = new JLabel("SNOW BROS");
        labelTitulo.setForeground(Color.WHITE);
        labelTitulo.setFont(new Font("Press Start 2P", Font.BOLD, 35));
        labelTitulo.setBounds((anchoPanel / 2) - 200, 140, 400, 60);
        labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelTitulo);

        labelInstruccion = new JLabel("Ingresa tu nombre:");
        labelInstruccion.setForeground(Color.WHITE);
        labelInstruccion.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
        labelInstruccion.setBounds((anchoPanel / 2) - 200, 265, 400, 30);
        labelInstruccion.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelInstruccion);

        campoNombre = new JTextField();
        campoNombre.setBounds((anchoPanel / 2) - 150, 300, 300, 40);
        campoNombre.setFont(new Font("Press Start 2P", Font.PLAIN, 20));
        campoNombre.setHorizontalAlignment(JTextField.CENTER);
        campoNombre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarNombre();
            }
        });
        add(campoNombre);

        botonConfirmar = new JButton("CONFIRMAR");
        botonConfirmar.setBounds((anchoPanel / 2) - 100, 370, 200, 50);
        botonConfirmar.setFont(new Font("Press Start 2P", Font.BOLD, 12));
        botonConfirmar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarNombre();
            }
        });
        add(botonConfirmar);
    }

    private void confirmarNombre() {
        nombreJugador = campoNombre.getText().trim();
        if (!nombreJugador.isEmpty()) {
            controladorVista.setNombreJugadorTemporal(nombreJugador);
            controladorVista.accionarPanelMenu();
            sonido.reproducirSonido(ConstantesSonidos.NombreConfirmado); 
        } else {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingresa un nombre", 
                "Nombre requerido", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    public void limpiarCampo() {
        campoNombre.setText("");
    }

}
