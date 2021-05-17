/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.app5.SunXACMLAuthorizer;

import com.sun.xacml.Indenter;
import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.ctx.Result;
import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.ResourceFinder;
import com.sun.xacml.finder.impl.CurrentEnvModule;
import com.sun.xacml.finder.impl.FilePolicyModule;
import com.sun.xacml.finder.impl.SelectorModule;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Resis
 */
class SunXACMLAuthorizer {

    private String pathRequest;
    private String pathPolicy;
    private String pathConfig;
    private String[] requestsPath;
    private String[] policiesPath;
    
    protected SunXACMLAuthorizer(String pathRequest, String pathPolicy) {
        this.pathRequest = pathRequest;
        this.pathPolicy = pathPolicy;
        System.out.println(pathRequest);
        this.requestsPath = getAllXMLFilesPath(this.pathRequest);
        this.policiesPath = getAllXMLFilesPath(this.pathPolicy);
    }
    
    protected SunXACMLAuthorizer(String pathRequest, String pathConfig, Boolean dummy) {
        this.pathRequest = pathRequest;
        this.pathConfig = pathConfig;
        System.out.println(pathRequest);
    }
    
    protected void execute(int i) {
        try {
            if (i == requestsPath.length)
                return;
            if (policiesPath.length == 0){
                System.out.println("No se ha podido encontrar ninguna policy, abortando proceso Authorizer");
                return;
            }
            PDPBasic pdp = new PDPBasic(policiesPath, requestsPath, null);
            if (policiesPath.length > 1)
                System.out.println("Verificando request con las policies seleccionadas " + requestsPath[i]);
            else if (policiesPath.length == 1)
                System.out.println("Verificando request con la policy seleccionada " + requestsPath[i]);
            ResponseCtx response = pdp.evaluateRequest(requestsPath[i]);
            writeResult(response, policiesPath, requestsPath[i], true);
        } catch (Exception e) {
            System.out.println("La request seleccionada no ha podido ser procesada, saltando request...");
            System.out.println(i);
            execute(++i);
        }
    }
    
    protected void executeWithConfigFile() {
        try {
            String[] requestsPath = getAllXMLFilesPath(this.pathRequest);
            String[] configsPath = getAllXMLFilesPath(this.pathConfig);
            if (configsPath.length == 0)
                throw new Exception("No se ha podido encontrar ningun archivo de configuración, abortando proceso Authorizer");
            else if (configsPath.length > 1)
                throw new Exception("Demasiados archivos de configuración, únicamente se requiere uno");
            String configPath = configsPath[0];
            PDPBasic pdp = new PDPBasic(null, requestsPath, configPath);
            for (String requestPath : requestsPath) {
                ResponseCtx response = pdp.evaluateRequest(requestPath);
                writeResult(response, configsPath, requestPath, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private String[] getAllXMLFilesPath(String path) {
        File f = new File(path);
        for (String s : f.list())
            System.out.println(s);
        File[] files = f.listFiles(filter);
        String[] result = new String[files.length];
        for (int i = 0; i < files.length; i++)
            result[i] = files[i].getAbsolutePath();
        return result;
    }
    
    FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".xml");
        }
    };

    private void printResult(OutputStream response, Indenter indenter, Set results) {
        PrintStream out = new PrintStream(response);
        String indent = indenter.makeString();
        out.println(indent + "<Response>");
        Iterator it = results.iterator();
        indenter.in();
        while (it.hasNext()) {
            Result result = (Result)(it.next());
            result.encode(out, indenter);
        }
        indenter.out();
        out.println(indent + "</Response>");
    }

    private void writeResult(ResponseCtx response, String[] paths, String requestPath, boolean policy) throws FileNotFoundException {
        String baseDir = System.getProperty("user.dir");
        String pathString = "";
        for (String s : paths){
            String fileName = new File(s).getName().split("\\.")[0];
            pathString += fileName + "_";
        }
        String pathResponse;
        String typeResult;
        if (policy)
            typeResult = "Policy_";
        else
            typeResult = "Config_";
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0){
            pathResponse = baseDir + "\\outputs\\" + System.currentTimeMillis() + "__" + typeResult + pathString + "Request_" + new File(requestPath).getName();
        } else {
            pathResponse = baseDir + "/outputs/" + System.currentTimeMillis() + "__" +typeResult + pathString + "Request_" + new File(requestPath).getName();
        }
        System.out.println("Escribiendo resultado en: " + pathResponse);
        OutputStream out = new FileOutputStream(pathResponse);
        printResult(out, new Indenter(), response.getResults());
        System.out.println("Resultado guardado exitosamente en: " + pathResponse);
    }
    
}
