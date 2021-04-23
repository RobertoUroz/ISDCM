/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp.modelo;

/**
 *
 * @author alejandro.adan.navarro
 */
public class PreferenciasUsuario {
    private String username;
    private int reproductor; // 1 VideoJS, 2 <video> HTML5
    private int listavideos; // 1 Práctica 1, 2 Práctica 2
    private int color; // 1 claro, 2 oscuro
    
    public PreferenciasUsuario(String username) {
        this.username = username;
        this.reproductor = 2;
        this.listavideos = 2;
        this.color = 1;
    }
    
    public PreferenciasUsuario(String username, int reproductor, int listavideos, int color) {
        this.username = username;
        this.reproductor = reproductor;
        this.listavideos = listavideos;
        this.color = color;
    }
}
