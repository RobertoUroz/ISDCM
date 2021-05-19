/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.app5.SunXACMLAuthorizer;

import com.sun.xacml.ConfigurationStore;
import com.sun.xacml.PDP;
import com.sun.xacml.PDPConfig;
import com.sun.xacml.ParsingException;
import com.sun.xacml.UnknownIdentifierException;
import com.sun.xacml.ctx.RequestCtx;
import com.sun.xacml.ctx.ResponseCtx;
import com.sun.xacml.finder.AttributeFinder;
import com.sun.xacml.finder.PolicyFinder;
import com.sun.xacml.finder.impl.CurrentEnvModule;
import com.sun.xacml.finder.impl.FilePolicyModule;
import com.sun.xacml.finder.impl.SelectorModule;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Resis
 */
public class PDPAuxiliary {
    
    PDP pdp;
    
    PDPAuxiliary(String[] policiesPath) {
        this.pdp = setupWithPolicies(policiesPath);
    }
    
    PDPAuxiliary (String configPath) {
        this.pdp = setupWithConfigFile(configPath);
    }

    ResponseCtx evaluateRequest(String request) throws FileNotFoundException, ParsingException {
        return pdp.evaluate(RequestCtx.getInstance(new FileInputStream(request)));
    }

    private PDP setupWithConfigFile(String configPath) {
        PDP pdp = null;
        try {
            ConfigurationStore cnfStore = new ConfigurationStore(new File(configPath));
            pdp = new PDP(cnfStore.getDefaultPDPConfig());
        } catch (ParsingException ex) {
            Logger.getLogger(PDPAuxiliary.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownIdentifierException ex) {
            Logger.getLogger(PDPAuxiliary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pdp;
    }

    private PDP setupWithPolicies(String[] policiesPath) {
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
