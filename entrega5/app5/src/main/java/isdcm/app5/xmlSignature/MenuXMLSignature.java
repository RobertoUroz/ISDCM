/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.app5.xmlSignature;

import isdcm.app5.Menu;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author Resis
 */
public class MenuXMLSignature {

    private final BufferedReader reader;
    //firmar politicas siguiendo el ejemplo de sunXACML (todas las policies de una carpeta), coger un xml signature de una carpeta por defecto o sino una personalizada (que esté en una carpeta solo)
    private final String signaturePath;
    private final String policyPath;
    private final String[] options = {
        "[1] Firma XML",
        "[2] Verificar XML personalizado según base de datos interna",
        "[3] Volver a la página principal"
    };
    private String[] optionsPolicies = {
        "[1] demo",
        "[2] personalizada"
    };
    private Menu m;
    
    public MenuXMLSignature (Menu m, BufferedReader reader) {
        this.m = m;
        this.reader = reader;
        String baseDir = System.getProperty("user.dir");
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0){
            this.signaturePath  = baseDir + "\\src\\main\\java\\res\\xmlSignature";
            this.policyPath = baseDir + "\\src\\main\\java\\res\\xACML" + 1 + "\\support" + "\\policy\\XACMLPolicy1.xml";
        } else {
            this.signaturePath  = baseDir + "/src/main/java/res/xmlSignature";
            this.policyPath = baseDir + "/src/main/java/res/xACML" + 1 + "/support" + "/policy/XACMLPolicy1.xml";
        }
    }

    public void paginaPrincipal() {
            System.out.println("Bienvenido a xmlSignature, ¿qué desea hacer?");
            optionsPaginaPrincipal();
    }    

    private void optionsPaginaPrincipal() {
        XMLSigner signer;
        try {
            while (true) {
                for (String s : options)
                    System.out.println(s);
                String option = reader.readLine();
                switch (option){
                    case "1":
                        optionsPolicies(false);
                        break;
                    case "2":
                        System.out.println("Por favor, indique la ruta absoluta del archivo XML a verificar.");
                        String policyLocalPath = reader.readLine();
                        signer = new XMLSigner(policyLocalPath, null);
                        signer.verifyXML();
                        break;
                    case "3":
                        m.paginaPrincipal();
                        break;
                    default:
                        System.out.println("No se ha entendido la opción, ¿Podría volver a escribirla?");
                        optionsPaginaPrincipal();   
                }    
            }
        } catch (IOException ex) {
            System.out.println("Ha habido un error al leer la opción indicada, por favor, vuélvala a introducir");
            optionsPaginaPrincipal();
        } catch (SAXException ex) {
            Logger.getLogger(MenuXMLSignature.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MenuXMLSignature.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void optionsPolicies(boolean customXML) {
        XMLSigner signer;
        String xmlLocalPath = this.signaturePath; 
        String policyLocalPath = this.policyPath;
        try {
            System.out.println("¿Quiere firmar una policy demo o su propia policy?");
            for (String s : optionsPolicies)
                System.out.println(s);
            String option = reader.readLine();
            switch (option){
                case "1":
                    signer = new XMLSigner(policyLocalPath, xmlLocalPath);
                    signer.signXML();
                    break;
                case "2":
                    System.out.println("Por favor, introduzca la ruta absoluta del archivo policy.");
                    policyLocalPath = reader.readLine();
                    signer = new XMLSigner(policyLocalPath, xmlLocalPath);
                    signer.signXML();
                    break;
                default:
                    System.out.println("No se ha entendido la opción, ¿Podría volver a escribirla?");
                    optionsPaginaPrincipal();
            }
        } catch (IOException ex) {
            System.out.println("Ha habido un error al leer la opción indicada, por favor, vuélvala a introducir");
            optionsPaginaPrincipal();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(MenuXMLSignature.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(MenuXMLSignature.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MenuXMLSignature.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(MenuXMLSignature.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (KeyException ex) {
            Logger.getLogger(MenuXMLSignature.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MarshalException ex) {
            Logger.getLogger(MenuXMLSignature.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLSignatureException ex) {
            Logger.getLogger(MenuXMLSignature.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (TransformerException ex) {
            Logger.getLogger(MenuXMLSignature.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
