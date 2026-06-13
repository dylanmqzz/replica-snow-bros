package Grafica.Paneles;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Interface.InterfaceControladores.ControladorVistas;

public class PanelGameOver extends PanelVista {
    protected JButton btnJugarDeNuevo;
    protected JButton btnVerRanking;
    protected JButton btnVolverMenu;
    protected JLabel lblPuntaje;

    public PanelGameOver(ControladorVistas cv) {
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

        JLabel lblGameOver = new JLabel("¡GAME OVER!");
        lblGameOver.setFont(new Font("Press Start 2P", Font.BOLD, 36));
        lblGameOver.setForeground(Color.RED);
        lblGameOver.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        add(lblGameOver, gbc);

        gbc.gridy = 1;
        add(Box.createVerticalStrut(20), gbc);

        lblPuntaje = new JLabel("Puntaje: 0");
        lblPuntaje.setFont(new Font("Press Start 2P", Font.PLAIN, 20));
        lblPuntaje.setForeground(Color.WHITE);
        lblPuntaje.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        add(lblPuntaje, gbc);

        gbc.gridy = 3;
        add(Box.createVerticalStrut(30), gbc);

        btnJugarDeNuevo = crearBoton("JUGAR DE NUEVO");
        gbc.gridy = 4;
        add(btnJugarDeNuevo, gbc);

        btnVerRanking = crearBoton("VER RANKING");
        gbc.gridy = 5;
        add(btnVerRanking, gbc);

        btnVolverMenu = crearBoton("VOLVER AL MENU");
        gbc.gridy = 6;
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
