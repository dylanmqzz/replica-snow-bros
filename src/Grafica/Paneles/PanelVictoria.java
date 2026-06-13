package Grafica.Paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import Interface.InterfaceControladores.ControladorVistas;

public class PanelVictoria extends PanelVista {
    protected JButton btnJugarDeNuevo;
    protected JButton btnVerRanking;
    protected JButton btnVolverMenu;
    protected JLabel lblPuntaje;

    public PanelVictoria(ControladorVistas cv) {
        super(cv);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblVictoria = new JLabel("¡VICTORIA!");
        lblVictoria.setFont(new Font("Press Start 2P", Font.BOLD, 36));
        lblVictoria.setForeground(new Color(255, 215, 0));
        lblVictoria.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        add(lblVictoria, gbc);

        JLabel lblFelicitaciones = new JLabel("¡Felicitaciones!");
        lblFelicitaciones.setFont(new Font("Press Start 2P", Font.PLAIN, 18));
        lblFelicitaciones.setForeground(Color.YELLOW);
        lblFelicitaciones.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        add(lblFelicitaciones, gbc);

        gbc.gridy = 2;
        add(Box.createVerticalStrut(20), gbc);

        lblPuntaje = new JLabel("Puntaje: 0");
        lblPuntaje.setFont(new Font("Press Start 2P", Font.PLAIN, 20));
        lblPuntaje.setForeground(Color.WHITE);
        lblPuntaje.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 3;
        add(lblPuntaje, gbc);

        gbc.gridy = 4;
        add(Box.createVerticalStrut(30), gbc);

        btnJugarDeNuevo = crearBoton("JUGAR DE NUEVO");
        gbc.gridy = 5;
        add(btnJugarDeNuevo, gbc);

        btnVerRanking = crearBoton("VER RANKING");
        gbc.gridy = 6;
        add(btnVerRanking, gbc);

        btnVolverMenu = crearBoton("VOLVER AL MENU");
        gbc.gridy = 7;
        add(btnVolverMenu, gbc);
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Press Start 2P", Font.PLAIN, 14));
        boton.setPreferredSize(new Dimension(300, 50));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(50, 50, 50));
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(100, 100, 100));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(50, 50, 50));
            }
        });
        return boton;
    }

    public void setPuntaje(int puntaje) {
        lblPuntaje.setText("Puntaje: " + puntaje);
    }

    public void setListenerJugarDeNuevo(ActionListener listener) {
        for (ActionListener al : btnJugarDeNuevo.getActionListeners()) {
            btnJugarDeNuevo.removeActionListener(al);
        }
        btnJugarDeNuevo.addActionListener(listener);
    }

    public void setListenerVerRanking(ActionListener listener) {
        for (ActionListener al : btnVerRanking.getActionListeners()) {
            btnVerRanking.removeActionListener(al);
        }
        btnVerRanking.addActionListener(listener);
    }

    public void setListenerVolverMenu(ActionListener listener) {
        for (ActionListener al : btnVolverMenu.getActionListeners()) {
            btnVolverMenu.removeActionListener(al);
        }
        btnVolverMenu.addActionListener(listener);
    }

}
