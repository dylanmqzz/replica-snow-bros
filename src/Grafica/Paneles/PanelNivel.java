package Grafica.Paneles;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import java.util.concurrent.CopyOnWriteArrayList;

import Grafica.ConstantesGraficas;
import Interface.InterfaceControladores.ControladorVistas;
import Interface.InterfacesGameElement.LogicElement;
import Observers.ObserverGrafico;
import Observers.Observer;

public class PanelNivel extends PanelVista {
    protected CopyOnWriteArrayList<JLabel> corazones;
    protected JLabel labelPuntaje;
    protected JLabel labelOleada;
    protected JLabel labelTiempo;
    protected JLabel labelNivel;

    public PanelNivel(ControladorVistas controlador) {
        super(controlador);
        setPreferredSize(new Dimension(ConstantesGraficas.PANEL_ANCHO, ConstantesGraficas.PANEL_ALTO));
        setLayout(null);
        setDoubleBuffered(true);
        agregarImagenFondo("src//Assets//Sprites//FONDO_NIVEL.png");
        corazones = new CopyOnWriteArrayList<>();
        inicializarLabelPuntaje();
        inicializarLabelOleada();
        inicializarLabelTiempo();
        inicializarLabelNivel();
    }

    public Observer agregarElemento(LogicElement elemento) {
        ObserverGrafico observer = new ObserverGrafico(this, elemento);
        this.add(observer);
        this.setComponentZOrder(observer, elemento.getZOrder());
        repaint();
        return observer;
    }

    public void limpiarElementos() {
        this.removeAll();
        inicializarLabelPuntaje();
        inicializarLabelOleada();
        inicializarLabelTiempo();
        inicializarLabelNivel();
        corazones.clear();
        revalidate();
        repaint();
    }

    public void agregarVida() {
        String ruta = "src//Assets//Sprites//VIDA.png";
        ImageIcon icono = new ImageIcon(ruta);
        Image img = icono.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        JLabel vida = new JLabel(new ImageIcon(img));
        int indice = corazones.size();
        corazones.addLast(vida);
        int x = 5 + (indice * 37); 
        int y = 5; 
        vida.setBounds(x, y, 32, 32);
        this.add(vida);
        setComponentZOrder(vida, 0);
        revalidate();
        repaint();
    }

    public void inicializarVidas(int cantVidas) {
        for (int i = 0; i < cantVidas; i++) {
            agregarVida();
        }
        revalidate();
        repaint();
    }

    public void eliminarVida() {
        if (!corazones.isEmpty()){
            JLabel ultimaVida = corazones.getLast();
            ultimaVida.setVisible(false);
            remove(ultimaVida);
            corazones.removeLast();
        }
    }

    private void inicializarLabelTiempo() {
        labelTiempo = new JLabel("| 00:00");
        labelTiempo.setFont(new Font("Press Start 2P", Font.BOLD, 14));
        labelTiempo.setForeground(Color.WHITE);
        int anchoLabel = 120;
        int altoLabel = 30; 
        int x = ConstantesGraficas.PANEL_ANCHO - anchoLabel - 390; 
        int y = 10;
        labelTiempo.setBounds(x, y, anchoLabel, altoLabel);      
        this.add(labelTiempo);
        setComponentZOrder(labelTiempo, 0);
    }

    private void inicializarLabelOleada() {
        labelOleada = new JLabel("");
        labelOleada.setFont(new Font("Press Start 2P", Font.BOLD, 14));
        labelOleada.setForeground(Color.WHITE);
        int anchoLabel = 180;
        int altoLabel = 30;
        int x = ConstantesGraficas.PANEL_ANCHO - anchoLabel - 200;
        int y = 10;
        labelOleada.setBounds(x, y, anchoLabel, altoLabel);
        this.add(labelOleada);
        setComponentZOrder(labelOleada, 0);
        labelOleada.setVisible(false); 
    }

    private void inicializarLabelNivel() {
        labelNivel = new JLabel("");
        labelNivel.setFont(new Font("Press Start 2P", Font.BOLD, 14)); 
        labelNivel.setForeground(Color.WHITE); 
        int anchoLabel = 180;
        int altoLabel = 30;
        int x = ConstantesGraficas.PANEL_ANCHO - anchoLabel - 200; 
        int y = 10; 
        labelNivel.setBounds(x, y, anchoLabel, altoLabel);
        this.add(labelNivel);
        setComponentZOrder(labelNivel, 0);
        labelNivel.setVisible(false); 
    }

    private void inicializarLabelPuntaje() {
        labelPuntaje = new JLabel("");
        labelPuntaje.setFont(new Font("Press Start 2P", Font.BOLD, 14));
        labelPuntaje.setForeground(Color.WHITE);
        int anchoLabel = 200;
        int altoLabel = 30;
        int x = ConstantesGraficas.PANEL_ANCHO - anchoLabel - 10; 
        int y = 10; 
        labelPuntaje.setBounds(x, y, anchoLabel, altoLabel);
        this.add(labelPuntaje);
        setComponentZOrder(labelPuntaje, 0);
    }

    public void actualizarTiempo(int segundosTotales) {
        int minutos = segundosTotales / 60;
        int segundos = segundosTotales % 60;
        labelTiempo.setText(String.format("| %02d:%02d", minutos, segundos));
        repaint();
    }

    public void actualizarOleada(int oleada) {
        if (oleada > 0) {
            labelOleada.setText("| WAVE:" + oleada); 
            labelOleada.setVisible(true);
        } else {
            labelOleada.setText("");
            labelOleada.setVisible(false);
        }
        repaint(); 
    }

    public void actualizarNivel(int numNivel) {
        if (numNivel > 0) {
            labelNivel.setText("| LEVEL:" + numNivel);
            labelNivel.setVisible(true);
        } else {
            labelNivel.setText("");
            labelNivel.setVisible(false);
        }
        repaint();
    }

    public void actualizarPuntaje(int puntaje) {
        labelPuntaje.setText("| SCORE:" + puntaje);
        repaint();
    }

}
