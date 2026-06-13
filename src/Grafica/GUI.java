package Grafica;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import Grafica.Paneles.*;
import Interface.InterfaceControladores.*;
import Interface.InterfacesGameElement.LogicElement;
import Movimiento.HelperLecturaMovimiento;
import Observers.Observer;
import Utils.ConstantesSonidos;
import Utils.ReproductorSonido;

public class GUI implements ControladorGraficas, ControladorVistas {
    protected PanelMenu panelMenu;
    protected PanelModos panelModos;
    protected PanelRankings panelRanking;
    protected PanelNivel panelNivel;
    protected PanelNombre panelNombre;
    protected PanelGameOver panelGameOver;
    protected PanelVictoria panelVictoria;
    protected JFrame pantalla;
    protected ControladorJuego controladorJuego;
    protected HelperLecturaMovimiento helper;
    protected String nombreJugadorTemporal;
    protected ReproductorSonido sonido = ReproductorSonido.getInstancia();

    public GUI() {
        registrarFuentePersonalizada();
        panelMenu = new PanelMenu(this);
        panelModos = new PanelModos(this);
        panelRanking = new PanelRankings(this);
        panelNivel = new PanelNivel(this);
        panelNombre = new PanelNombre(this);
        panelGameOver = new PanelGameOver(this);
        panelVictoria = new PanelVictoria(this);
        configurarPantalla();
    }

    private void registrarFuentePersonalizada() {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src//Assets//Fonts//PressStart2P//PressStart2P-Regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            System.err.println("ADVERTENCIA: No se pudo cargar la fuente 'Press Start 2P'. Se usará la fuente por defecto.");
            e.printStackTrace();
        }
    }

    public void inicializarHelper() {
        helper = new HelperLecturaMovimiento(pantalla, controladorJuego.getJugador());
        helper.registrarTeclas();
    }

    @Override
    public void reiniciarHelperDeInput() {
        if (helper != null)
            helper.reiniciarHelper();
        inicializarHelper();
    }

    protected void configurarPantalla() {
        pantalla = new JFrame("Snow Bros TDP");
        pantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pantalla.setResizable(false);
        pantalla.setSize(ConstantesGraficas.PANEL_ANCHO, ConstantesGraficas.PANEL_ALTO);
        pantalla.setLocationRelativeTo(null);
        pantalla.setFocusable(true);
        mostrarPanelNombre();
        pantalla.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controladorJuego.agregarJugadorAlRanking();
                controladorJuego.guardarRankings();
            }
        });
        pantalla.setVisible(true);
    }

    @Override
    public void registraControladorJuego(ControladorJuego cj) {
        controladorJuego = cj;
    }

    public void mostrarPantallaMenu() {
        pantalla.setContentPane(panelMenu);
        refrescar();
    }

    @Override
    public void mostrarNivel() {
        SwingUtilities.invokeLater(() -> {
            pantalla.setContentPane(panelNivel);
            refrescar();
        });
    }

    public void mostrarPantallaRanking() {
        panelRanking.actualizar();
        pantalla.setContentPane(panelRanking);
        refrescar();
    }

    public void mostrarPanelModos() {
        pantalla.setContentPane(panelModos);
        refrescar();
    }

    @Override
    public Observer registrarGameElement(LogicElement entidad) {
        Observer observer = panelNivel.agregarElemento(entidad);
        refrescar();
        return observer;
    }

    public void setNombreJugadorTemporal(String nombre) {
        this.nombreJugadorTemporal = nombre;
        controladorJuego.setNombreJugador(nombre);
    }

    public void accionarInicioJuego() {
        if (nombreJugadorTemporal != null && !nombreJugadorTemporal.isEmpty())
            controladorJuego.setNombreJugador(nombreJugadorTemporal);
        controladorJuego.iniciar();
        mostrarNivel();
        panelNivel.inicializarVidas(controladorJuego.getJugador().getVidas());
    }

    public void accionarPanelPuntajes() {
        mostrarPantallaRanking();
        sonido.reproducirSonido(ConstantesSonidos.SonidoBoton);
    }

    public void accionarPanelModoDeJuego() {
        mostrarPanelModos();
        sonido.reproducirSonido(ConstantesSonidos.SonidoBoton);
    }

    public void cambiarModoDeJuego(int modo) {
        controladorJuego.configurarModo(modo);
    }

    public void accionarPanelMenu() {
        mostrarPantallaMenu();
        sonido.reproducirSonido(ConstantesSonidos.SonidoBoton);
    }

    public ControladorJuego getControladorJuego() {
        return controladorJuego;
    }

    public void refrescar() {
        pantalla.revalidate();
        pantalla.repaint();
    }

    public PanelNivel getPanelNivel() {
        return panelNivel;
    }

    public void gameOver() {
        panelNivel.limpiarElementos();
        panelGameOver.setPuntaje(controladorJuego.getJugador().getPuntaje());
        helper.reiniciarHelper();
        controladorJuego.reiniciarModo();
        mostrarPanelGameOver();

        panelGameOver.setListenerJugarDeNuevo(e -> {
            controladorJuego.agregarJugadorAlRanking();
            controladorJuego.guardarRankings();
            helper.reiniciarHelper();
            controladorJuego.reiniciarModo();
            accionarInicioJuego();
            sonido.reproducirSonido(ConstantesSonidos.SonidoBoton);
        });
        panelGameOver.setListenerVerRanking(e -> {
            controladorJuego.agregarJugadorAlRanking();
            controladorJuego.guardarRankings();
            helper.reiniciarHelper();
            controladorJuego.reiniciarModo();
            accionarPanelPuntajes();
            sonido.reproducirSonido(ConstantesSonidos.SonidoBoton);
        });
        panelGameOver.setListenerVolverMenu(e -> {
            controladorJuego.agregarJugadorAlRanking();
            controladorJuego.guardarRankings();
            helper.reiniciarHelper();
            controladorJuego.reiniciarModo();
            mostrarPantallaMenu();
            sonido.reproducirSonido(ConstantesSonidos.SonidoBoton);
        });
    }

    public void victoria() {
        panelNivel.limpiarElementos();
        panelVictoria.setPuntaje(controladorJuego.getJugador().getPuntaje());
        helper.reiniciarHelper();
        controladorJuego.reiniciarModo();
        mostrarPanelVictoria();

        panelVictoria.setListenerJugarDeNuevo(e -> {
            controladorJuego.agregarJugadorAlRanking();
            controladorJuego.guardarRankings();
            helper.reiniciarHelper();
            controladorJuego.reiniciarModo();
            accionarInicioJuego();
            sonido.reproducirSonido(ConstantesSonidos.SonidoBoton);
        });
        panelVictoria.setListenerVerRanking(e -> {
            controladorJuego.agregarJugadorAlRanking();
            controladorJuego.guardarRankings();
            helper.reiniciarHelper();
            controladorJuego.reiniciarModo();
            accionarPanelPuntajes();
            sonido.reproducirSonido(ConstantesSonidos.SonidoBoton);
        });
        panelVictoria.setListenerVolverMenu(e -> {
            controladorJuego.agregarJugadorAlRanking();
            controladorJuego.guardarRankings();
            helper.reiniciarHelper();
            controladorJuego.reiniciarModo();
            mostrarPantallaMenu();
            sonido.reproducirSonido(ConstantesSonidos.SonidoBoton);
        });
    }

    public void mostrarPanelVictoria() {
        pantalla.setContentPane(panelVictoria);
        refrescar();
    }

    public void mostrarPanelGameOver() {
        pantalla.setContentPane(panelGameOver);
        refrescar();
    }

    @Override
    public void limpiarGraficaNivelActual() {
        // Define la tarea del metodo
        Runnable tareaLimpieza = () -> {
            panelNivel.limpiarElementos();
            panelNivel.inicializarVidas(controladorJuego.getJugador().getVidas());
        };
        // Chequea cual hilo esta llamando al metodo (EventDispatchThread o HiloJuego)
        if (SwingUtilities.isEventDispatchThread())
            tareaLimpieza.run();
        else {
            try {
                SwingUtilities.invokeAndWait(tareaLimpieza);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void mostrarPanelNombre() {
        panelNombre.limpiarCampo();
        pantalla.setContentPane(panelNombre);
        refrescar();
    }

    public HelperLecturaMovimiento getHelper() {
        return helper;
    }

}
