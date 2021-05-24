/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.app5;

import isdcm.app5.SunXACMLAuthorizer.MenuSunXACMLAuthorizer;
import isdcm.app5.BalanaXACMLAuthorizer.MenuBalanaXACMLAuthorizer;
import isdcm.app5.xmlSignature.MenuXMLSignature;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.exit;
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
        "[2] Uso de la herramienta de autorización Balana",
        "[3] XML Signature",
        "[4] Salir"
    };
    
    public Menu(BufferedReader reader) {
        this.reader = reader;
    }
    
    public void paginaPrincipal() {
        System.out.println("Página Principal de la entrega 5.");
        optionsPaginaPrincipal();
    }

    private void optionsPaginaPrincipal() {
        try {
            while(true) {
                System.out.println("¿Qué desea hacer?");
                for (String s : options)
                    System.out.println(s);
                String option = reader.readLine();
                switch (option){
                    case "1":
                        MenuSunXACMLAuthorizer menu1 = new MenuSunXACMLAuthorizer(this, reader);
                        menu1.paginaPrincipal();
                        break;
                    case "2":
                        MenuBalanaXACMLAuthorizer menu2 = new MenuBalanaXACMLAuthorizer(this, reader);
                        menu2.paginaPrincipal();
                        break;
                    case "3":
                        MenuXMLSignature menu3 = new MenuXMLSignature(this, reader);
                        menu3.paginaPrincipal();
                        break;
                    case "4":
                        exit(0);
                        break;
                    default:
                        System.out.println("No se ha entendido la opción, ¿Podría volver a escribirla?");
                        optionsPaginaPrincipal();
                }
            }
        } catch (IOException ex) {
            System.out.println("Ha habido un error al leer la opción indicada, por favor, vuélvala a introducir");
            optionsPaginaPrincipal();
        }
    }
    
}
