/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.app5;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author Resis
 */
public class Main {
        
    public static void main(String[] args) {
        System.out.println("Bienvenido/a a la entrega nº 5 de Alejandro Adan Navarro y Roberto Uroz Rivas");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        Menu mp = new Menu(reader);
        mp.paginaPrincipal();
    }
}
