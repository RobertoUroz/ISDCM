/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.app5;

import isdcm.app5.SunXACMLAuthorizer.MenuSunXACMLAuthorizer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Resis
 */
public class Menu {

    private BufferedReader reader;
    private static String[] options = {
        "[1] Uso de la herramienta de Autorización de Sun",
        "[2] [Alejandro]",
        "[3] XML Signature"
    };
    
    public Menu() {
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        reader = new BufferedReader(inputStreamReader);
    }
    
    public void paginaPrincipal() {
        System.out.println("Página Principal de la entrega 5.");
        optionsPaginaPrincipal();
    }

    private void optionsPaginaPrincipal() {
        try {
            System.out.println("¿Qué desea hacer?");
            for (String s : options)
                System.out.println(s);
            String option = reader.readLine();
            switch (option){
                case "1":
                    MenuSunXACMLAuthorizer menu1 = new MenuSunXACMLAuthorizer(reader);
                    menu1.paginaPrincipal();
                    break;
                case "2":
                    break;
                case "3":
                    break;
                default:
                    System.out.println("No se ha entendido la opción, ¿Podría volver a escribirla?");
                    optionsPaginaPrincipal();
            }
        } catch (IOException ex) {
            System.out.println("Ha habido un error al leer la opción indicada, por favor, vuélvala a introducir");
            optionsPaginaPrincipal();
        }
    }
    
}
