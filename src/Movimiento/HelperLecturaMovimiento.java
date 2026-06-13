package Movimiento;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JFrame;

import GameElements.NoEstaticos.Jugador.Jugador;
import Utils.Vector2;

public class HelperLecturaMovimiento {
    protected JFrame pantalla;
    protected Jugador jugador;
    protected KeyAdapter keyAdapter;
    protected Vector2 direccion;
    protected boolean atacar;
    protected boolean salto;
    protected boolean primerTeclaPresionada;
    protected CopyOnWriteArrayList<Tecla> teclasPresionadas;
    protected enum Tecla {
        W, A, D, SPACE
    };

    public HelperLecturaMovimiento(JFrame p, Jugador j) {
        pantalla = p;
        jugador = j;
        teclasPresionadas = new CopyOnWriteArrayList<>();
        direccion = new Vector2(0, 0);
        atacar = false;
        primerTeclaPresionada = false;
    }

    protected void procesarInput() {
        direccion.setX(0);
        direccion.setY(0);
        atacar = false;
        salto = false;
        for (Tecla t : teclasPresionadas) {
            switch (t) {
                case A:
                    direccion.setX(-1);
                    jugador.movio();
                    break;
                case D:
                    direccion.setX(1);
                    jugador.movio();
                    break;
                case W:
                    salto = true;
                    jugador.movio();
                    break;
                case SPACE:
                    atacar = true;
                    jugador.movio();
                    break;
                default:
                    break;
            }
        }
    }

    public void informarJugador() {
        procesarInput();
        jugador.procesarInputMovimiento(direccion, salto);
        if (atacar)
            jugador.atacar();
    }

    public void registrarTeclas() {
        keyAdapter = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                primerTeclaPresionada = true;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        cargarTecla(Tecla.A);
                        break;
                    case KeyEvent.VK_D:
                        cargarTecla(Tecla.D);
                        break;
                    case KeyEvent.VK_W:
                        cargarTecla(Tecla.W);
                        break;
                    case KeyEvent.VK_SPACE:
                        cargarTecla(Tecla.SPACE);
                        break;
                    default:
                        break;
                }
            }
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        eliminarTecla(Tecla.A);
                        break;
                    case KeyEvent.VK_D:
                        eliminarTecla(Tecla.D);
                        break;
                    case KeyEvent.VK_W:
                        eliminarTecla(Tecla.W);
                        break;
                    case KeyEvent.VK_SPACE:
                        eliminarTecla(Tecla.SPACE);
                        break;
                    default:
                        break;
                }
            }
        };
        pantalla.addKeyListener(keyAdapter);
    }

    protected void cargarTecla(Tecla t) {
        switch (t) {
            case A:
                if (teclasPresionadas.contains(Tecla.D))
                    teclasPresionadas.remove(Tecla.D);
                if (!teclasPresionadas.contains(Tecla.A))
                    teclasPresionadas.add(Tecla.A);
                break;
            case D:
                if (teclasPresionadas.contains(Tecla.A))
                    teclasPresionadas.remove(Tecla.A);
                if (!teclasPresionadas.contains(Tecla.D))
                    teclasPresionadas.add(Tecla.D);
                break;
            case W:
                if (!teclasPresionadas.contains(Tecla.W))
                    teclasPresionadas.add(Tecla.W);
                break;
            case SPACE:
                if (!teclasPresionadas.contains(Tecla.SPACE))
                    teclasPresionadas.add(Tecla.SPACE);
                break;
            default:
                break;
        }
    }

    protected void eliminarTecla(Tecla t) {
        teclasPresionadas.remove(t);
    }

    public void reiniciarHelper() {
        pantalla.removeKeyListener(keyAdapter);
    }

    public boolean primerTeclaPresionada() {
        return primerTeclaPresionada;
    }
    
}
