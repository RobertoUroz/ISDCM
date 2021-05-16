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
    
    protected SunXACMLAuthorizer(String pathRequest, String pathPolicy) {
        this.pathRequest = pathRequest;
        this.pathPolicy = pathPolicy;
        System.out.println(pathRequest);
    }
    
    protected SunXACMLAuthorizer(String pathRequest, String pathConfig, Boolean dummy) {
        this.pathRequest = pathRequest;
        this.pathConfig = pathConfig;
        System.out.println(pathRequest);
    }
    
    protected void execute() {
        try {
            String[] requestsPath = getAllXMLFilesPath(this.pathRequest);
            String[] policiesPath = getAllXMLFilesPath(this.pathPolicy);
            if (policiesPath.length == 0){
                throw new Exception("No se ha podido encontrar ninguna policy, abortando proceso Authorizer");
            }
            PDPBasic pdp = new PDPBasic(policiesPath, requestsPath, null);
            for (String requestPath : requestsPath) {
                if (policiesPath.length > 1)
                    System.out.println("Verificando request con las policies seleccionadas " + requestPath);
                else if (policiesPath.length == 1)
                    System.out.println("Verificando request con la policy seleccionada " + requestPath);
                ResponseCtx response = pdp.evaluateRequest(requestPath);
                writeResult(response, policiesPath, requestPath, true);
                //ResponseCtx response = pdp.evaluate(RequestCtx.getInstance(new FileInputStream(requestPath)));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    
    private PDP setupPDP(String configPath) {
        /*ResourceFinder resourceFinder = new ResourceFinder();
        List resourcesModules = new ArrayList();
        policyModules.add(filePolicyModule);
        policyFinder.setModules(policyModules);
        AttributeFinder attrFinder = new AttributeFinder();
        SelectorModule selectorAttributeModule = new SelectorModule();
        attrModules.add(selectorAttributeModule);
        CurrentEnvModule envAttributeModule = new CurrentEnvModule();
        attrModules.add(envAttributeModule);
        attrFinder.setModules(attrModules);
        return new PDP(new PDPConfig(attrFinder, null, configFinder));*/
        return null;
    }

    private void printResult(OutputStream response, Indenter indenter, Set results) {
        // Make a PrintStream for a nicer printing interface
        PrintStream out = new PrintStream(response);

        // Prepare the indentation string
        String indent = indenter.makeString();

        // Now write the XML...
        out.println(indent + "<Response>");

        // Go through all results
        Iterator it = results.iterator();
        indenter.in();
        while (it.hasNext()) {
            Result result = (Result)(it.next());
            result.encode(out, indenter);
        }
        indenter.out();

        // Finish the XML for a response
        out.println(indent + "</Response>");
    }

    private void writeResult(ResponseCtx response, String[] paths, String requestPath, boolean policy) throws FileNotFoundException {
        String baseDir = System.getProperty("user.dir");
        String pathString = "";
        for (String s : paths)
            pathString += new File(s).getName() + "_";
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
