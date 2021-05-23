/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.app5.SunXACMLAuthorizer;

import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import isdcm.app5.Menu;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Resis
 */
public class MenuSunXACMLAuthorizer {

    private final BufferedReader reader;
    private final String[] options = {
        "[1] xACML con versión de prueba 1",
        "[2] xACML con versión de prueba 2",
        "[3] xACML de un archivo del disco duro",
        "[4] Volver a la página principal"
    };
    private String[][] paths;
    private final int nbOfTests = 2;
    private Menu m;

    public MenuSunXACMLAuthorizer(Menu m, BufferedReader reader) {
        this.m = m;
        this.reader = reader;
        String baseDir = System.getProperty("user.dir");
        paths = new String[nbOfTests][3];
        for (int i = 0; i < nbOfTests; i++){
            if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0){
                paths[i][0] = baseDir + "\\src\\main\\java\\res\\xACML" + (i+1) + "\\support" + "\\requests";
                paths[i][1] = baseDir + "\\src\\main\\java\\res\\xACML" + (i+1) + "\\support" + "\\policy";
                paths[i][2] = baseDir + "\\src\\main\\java\\res\\xACML" + (i+1) + "\\support" + "\\config\\config_rbac.xml";
            } else {
                paths[i][0] = baseDir + "/src/main/java/res/xACML" + (i+1) + "/support" + "/requests";
                paths[i][1] = baseDir + "/src/main/java/res/xACML" + (i+1) + "/support" + "/policy";
                paths[i][2] = baseDir + "/src/main/java/res/xACML" + (i+1) + "/support" + "/config/config_rbac.xml";
            }
        }
        System.out.println(paths[0]);
    }

    public void paginaPrincipal() {
        System.out.println("Bienvenido a SunXACMLAuthorizer, ¿qué desea hacer?");
        optionsPaginaPrincipal();
    }
    
     private void optionsPaginaPrincipal() {
        try {
            while (true) {
                for (String s : options)
                System.out.println(s);
                String option = reader.readLine();
                switch (option){
                    case "1":
                        optionsConfigFile(0, false);
                        break;
                    case "2":
                        optionsConfigFile(1, false);
                        break;
                    case "3":
                        optionsConfigFile(0, true);
                        break;
                    case "4":
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
        }
    }
     
    private void optionsConfigFile(int i, boolean extra) {
        SunXACMLAuthorizer authorizer;
        String[][] local_paths;
        try {
            System.out.println("¿Quiere utilizar el config file?");
            System.out.println("[1] Sí");
            System.out.println("[2] No");
            String optionConfigFile = reader.readLine();
            switch (optionConfigFile) {
                case "1":
                    if (!extra)
                        local_paths = paths;
                    else
                        local_paths = retrievePaths(optionConfigFile);
                    authorizer = new SunXACMLAuthorizer(local_paths[i][0], local_paths[i][2], false);
                    authorizer.executeWithConfigFile();
                    break;
                case "2":
                    if (!extra)
                        local_paths = paths;
                    else
                        local_paths = retrievePaths(optionConfigFile);
                    if (local_paths == null)
                        return;
                    authorizer = new SunXACMLAuthorizer(local_paths[i][0], local_paths[i][1]);
                    authorizer.execute(0);
                    break;
                default:
                    System.out.println("No se ha entendido la opción");
            }
        } catch (IOException ex) {
            System.out.println("Ha habido un error al leer la opción indicada, por favor, vuélvala a introducir");
            optionsConfigFile(i, extra);
        }
    }

    private String[][] retrievePaths(String optionConfigFile) {
        String[][] paths = new String[1][3];
        String path;
        File f;
        try {
            System.out.println("Recuerde que los archivos deben de estar aislados en su carpeta contenedora, por lo tanto, las policies, requests y config deben de estar aislados en sus carpetas correspondientes");
            if (optionConfigFile.equals("2")){
                System.out.println("Por favor, especifique ruta de la carpeta contenedora de las policies");
                path = reader.readLine();
                f = new File(path);
                if (!f.exists())
                    throw new IOException("");
                paths[0][1] = path;
            }
            System.out.println("Por favor, especifique ruta de la carpeta contenedora de los requests");
            path = reader.readLine();
            f = new File(path);
            if (!f.exists())
                    throw new IOException("");
            paths[0][0] = path;
            if (optionConfigFile.equals("1")){
                System.out.println("Por favor, especifique ruta del archivo config");
                path = reader.readLine();
                f = new File(path);
                if (!f.exists())
                    throw new IOException("");
                paths[0][2] = path;
            }
            return paths;
        } catch (IOException ex) {
            System.out.println("Ha habido un problema mientras insertaba las rutas de las carpetas contenedoras, por favor, inténtelo de nuevo");
            return null;
        }
    }
    
}
