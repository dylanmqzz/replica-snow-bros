package Juego;

import Observers.ObserverColision;
import Observers.ObserverEfecto;
import Observers.ObserverMuerte;
import Observers.ObserverPuntaje;
import GameElements.GameElement;
import GameElements.Estaticos.Estatica;
import GameElements.Estaticos.Decoracion.Plataforma.PlataformaMovil;
import GameElements.NoEstaticos.Enemigos.Enemigo;
import GameElements.NoEstaticos.Jugador.Jugador;
import GameElements.NoEstaticos.Proyectiles.Proyectil;
import Interface.InterfaceControladores.ControladorGraficas;
import Interface.InterfacesGameElement.LogicElement;
import Interface.Visitors.Registrador;

public class ElementsRegister implements Registrador {
    protected ControladorGraficas controlGrafica;
    protected ControladorColisiones controladorColisiones;

    public ElementsRegister(ControladorGraficas cg, ControladorColisiones cc) {
        controlGrafica = cg;
        controladorColisiones = cc;
    }

    public void registrarObserverGrafico(GameElement ge) {
        ge.suscribe(controlGrafica.registrarGameElement((LogicElement) ge));
    }

    public void registrarObserverColision(GameElement ge) {
        ObserverColision oc = new ObserverColision(controladorColisiones, ge);
        ge.suscribe(oc);
    }

    public void registrarObserverEfecto(Jugador j) {
        ObserverEfecto oe = new ObserverEfecto(j);
        controlGrafica.getPanelNivel().add(oe);
        j.suscribe(oe);
    }

    public void registarObserverMuerte(Enemigo e) {
        ObserverMuerte om = new ObserverMuerte(e, controlGrafica.getPanelNivel());
        controlGrafica.getPanelNivel().add(om);
        controlGrafica.getPanelNivel().setComponentZOrder(om, 0);
        e.suscribe(om);
    }

    public void registrarObserverPuntaje(GameElement g) {
        ObserverPuntaje op = new ObserverPuntaje(g);
        controlGrafica.getPanelNivel().add(op);
        g.suscribe(op);
    }

    public void registrarElementoColisionable(GameElement ge) {
        controladorColisiones.cargarElementoColisionable(ge);
    }

    public void registrarElemento(GameElement e) {
        e.aceptarRegistro(this);
    }

    @Override
    public void registrar(Estatica e) {
        registrarElementoColisionable(e);
        registrarObserverGrafico(e);
        registrarObserverPuntaje(e);
    }

    @Override
    public void registrar(Enemigo e) {
        registrarElementoColisionable(e);
        registrarObserverColision(e);
        registrarObserverGrafico(e);
        registarObserverMuerte(e);
        registrarObserverPuntaje(e);
    }

    @Override
    public void registrar(Proyectil e) {
        registrarObserverColision(e);
        registrarObserverGrafico(e);
        registrarElementoColisionable(e);
    }

    @Override
    public void registrar(Jugador e) {
        registrarObserverColision(e);
        registrarObserverGrafico(e);
        registrarObserverEfecto(e);
    }

    public void registrar(PlataformaMovil p) {
        registrarObserverColision(p);
        registrarObserverGrafico(p);
        registrarElementoColisionable(p);
        registrarObserverPuntaje(p);
    }
    
}
