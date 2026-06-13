package Grafica.Paneles;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Juego.Ranking;
import Juego.RegistroJugador;
import Grafica.ConstantesGraficas;
import Interface.InterfaceControladores.ControladorVistas;

public class PanelRankings extends PanelVista {
    protected JPanel panelRankings;
    protected JButton botonVolver;
    protected Ranking rankingClasico;
    protected Ranking rankingContraReloj;
    protected Ranking rankingSupervivencia;

    public PanelRankings(ControladorVistas controlador) {
        super(controlador);
        setPreferredSize(new Dimension(ConstantesGraficas.PANEL_ANCHO, ConstantesGraficas.PANEL_ALTO));
        setLayout(null);
        setDoubleBuffered(true);
        setBackground(Color.BLACK);
        agregarBotonVolver();
    }

    protected void agregarBotonVolver() {
        botonVolver = crearBotonRetro("MENU", new Color(200, 50, 50));
        botonVolver.setBounds((ConstantesGraficas.PANEL_ANCHO / 2) - 75, ConstantesGraficas.PANEL_ALTO - 100, 150, 40);
        registrarOyenteBotonVolver();
        add(botonVolver);
    }

    protected void registrarOyenteBotonVolver() {
        botonVolver.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controladorVista.accionarPanelMenu();
            }
        });
    }

    public void agregarPanelRankings() {
        if (panelRankings != null)
            remove(panelRankings);
        setRanking();

        panelRankings = new JPanel();
        panelRankings.setOpaque(false);
        panelRankings.setLayout(new GridLayout(1, 3, 20, 0));
        panelRankings.setBounds(50, 60, ConstantesGraficas.PANEL_ANCHO - 100, ConstantesGraficas.PANEL_ALTO - 200);

        Font fuenteTitulo = new Font("Press Start 2P", Font.BOLD, 18);
        Font fuenteJugador = new Font("Press Start 2P", Font.PLAIN, 8);
        Color colorTexto = Color.WHITE;

        JPanel columnaClasico = crearColumnaRanking("CLASICO", rankingClasico, fuenteTitulo, fuenteJugador, colorTexto, new Color(100, 200, 100));
        JPanel columnaContraReloj = crearColumnaRanking("CONTRARRELOJ", rankingContraReloj, fuenteTitulo, fuenteJugador, colorTexto, new Color(200, 150, 50));
        JPanel columnaSupervivencia = crearColumnaRanking("SUPERVIVENCIA", rankingSupervivencia, fuenteTitulo, fuenteJugador, colorTexto, new Color(80, 180, 255));

        panelRankings.add(columnaClasico);
        panelRankings.add(columnaContraReloj);
        panelRankings.add(columnaSupervivencia);

        add(panelRankings);
        revalidate();
        repaint();
    }

    private JPanel crearColumnaRanking(String titulo, Ranking ranking, Font fuenteTitulo, Font fuenteJugador, Color colorTexto, Color colorTitulo) {
        JPanel columna = new JPanel();
        columna.setOpaque(false);
        columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
        columna.setBorder(BorderFactory.createLineBorder(colorTitulo, 2));

        JLabel tituloLabel = new JLabel("TOP 5");
        tituloLabel.setFont(fuenteTitulo);
        tituloLabel.setForeground(colorTitulo);
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        columna.add(Box.createVerticalStrut(10));
        columna.add(tituloLabel);

        JLabel subtituloLabel = new JLabel(titulo);
        subtituloLabel.setFont(new Font("Press Start 2P", Font.BOLD, 8));
        subtituloLabel.setForeground(colorTitulo);
        subtituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        columna.add(subtituloLabel);
        columna.add(Box.createVerticalStrut(15));

        if (ranking == null || ranking.getTopJugadores().isEmpty()) {
            JLabel vacio = new JLabel("Sin jugadores");
            vacio.setFont(fuenteJugador);
            vacio.setForeground(Color.GRAY);
            vacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            columna.add(vacio);
        } else {
            int pos = 1;
            for (RegistroJugador jugador : ranking.getTopJugadores()) {
                if (pos > 5)
                    break;

                JPanel jugadorPanel = new JPanel();
                jugadorPanel.setOpaque(false);
                jugadorPanel.setLayout(new BorderLayout());
                jugadorPanel.setMaximumSize(new Dimension(500, 30));

                JLabel posLabel = new JLabel(pos + " :");
                posLabel.setFont(new Font("Press Start 2P", Font.BOLD, 12));
                posLabel.setForeground(colorTitulo);
                posLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 5));

                JLabel nombreLabel = new JLabel(jugador.getNombre());
                nombreLabel.setFont(fuenteJugador);
                nombreLabel.setForeground(colorTexto);

                JLabel puntajeLabel = new JLabel(jugador.getPuntaje() + "");
                puntajeLabel.setFont(new Font("Press Start 2P", Font.BOLD, 8));
                puntajeLabel.setForeground(colorTitulo);
                puntajeLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 10));

                jugadorPanel.add(posLabel, BorderLayout.WEST);
                jugadorPanel.add(nombreLabel, BorderLayout.CENTER);
                jugadorPanel.add(puntajeLabel, BorderLayout.EAST);

                columna.add(jugadorPanel);
                columna.add(Box.createVerticalStrut(5));
                pos++;
            }
        }
        columna.add(Box.createVerticalGlue());
        return columna;
    }

    public Ranking getRankingClasico() {
        return rankingClasico;
    }

    public void setRanking() {
        rankingClasico = controladorVista.getControladorJuego().getRankingClasico();
        rankingContraReloj = controladorVista.getControladorJuego().getRankingContraReloj();
        rankingSupervivencia = controladorVista.getControladorJuego().getRankingSupervivencia();
    }

    public void actualizar() {
        removeAll();
        setBackground(Color.BLACK);
        agregarPanelRankings();
        agregarBotonVolver();
        revalidate();
        repaint();
    }

}
