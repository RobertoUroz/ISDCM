/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.app5.SunXACMLAuthorizer;

import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.ParsingException;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.impl.CurrentEnvModule;
import com.sun.xacml.finder.impl.FilePolicyModule;
import com.sun.xacml.finder.impl.SelectorModule;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Resis
 */
public class PDPBasic {
    
    PDP pdp;
    
    PDPBasic(String[] policiesPath, String[] requestsPath, String configPath) {
        if (policiesPath == null)
            this.pdp = setupWithConfigFile(requestsPath, configPath);
        else if (configPath == null)
            this.pdp = setupWithPolicies(policiesPath, requestsPath);
    }

    ResponseCtx evaluateRequest(String request) throws FileNotFoundException, ParsingException {
        return pdp.evaluate(RequestCtx.getInstance(new FileInputStream(request)));
    }

    private PDP setupWithConfigFile(String[] requestsPath, String configPath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private PDP setupWithPolicies(String[] policiesPath, String[] requestsPath) {
        FilePolicyModule filePolicyModule = new FilePolicyModule();
        List attrModules = new ArrayList();
        for (int i = 0; i < policiesPath.length; i++)
            filePolicyModule.addPolicy(policiesPath[i]);
        PolicyFinder policyFinder = new PolicyFinder();
        Set policyModules = new HashSet();
        policyModules.add(filePolicyModule);
        policyFinder.setModules(policyModules);
        AttributeFinder attrFinder = new AttributeFinder();
        SelectorModule selectorAttributeModule = new SelectorModule();
        attrModules.add(selectorAttributeModule);
        CurrentEnvModule envAttributeModule = new CurrentEnvModule();
        attrModules.add(envAttributeModule);
        attrFinder.setModules(attrModules);
        return new PDP(new PDPConfig(attrFinder, policyFinder, null));
    }
    
}
