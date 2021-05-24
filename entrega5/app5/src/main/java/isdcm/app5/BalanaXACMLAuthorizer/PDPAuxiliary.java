/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.app5.BalanaXACMLAuthorizer;

import java.io.ByteArrayInputStream;
import org.wso2.balana.*;
import org.wso2.balana.ctx.AbstractResult;
import org.wso2.balana.ctx.Attribute;
import org.wso2.balana.ctx.ResponseCtx;
import org.wso2.balana.ctx.xacml3.Result;
import org.wso2.balana.finder.AttributeFinder;
import org.wso2.balana.finder.AttributeFinderModule;
import org.wso2.balana.finder.PolicyFinder;
import org.wso2.balana.finder.PolicyFinderModule;
import org.wso2.balana.finder.impl.FileBasedPolicyFinderModule;
import org.wso2.balana.xacml3.Attributes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.wso2.balana.finder.impl.CurrentEnvModule;
import org.wso2.balana.finder.impl.SelectorModule;

/**
 *
 * @author Resis
 */
public class PDPAuxiliary {
    
    PDP pdp;
    Balana balana;
    
    PDPAuxiliary(String[] policyPath) {
        this.balana = Balana.getInstance();
        this.pdp = setupWithPolicies(policyPath);
    }

    ResponseCtx evaluateRequest(String request) throws FileNotFoundException, ParsingException {
        System.err.println("++"+request+"++");
        String rr = "";
        try {
            rr = new String(Files.readAllBytes(Paths.get(request)), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(PDPAuxiliary.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.err.println("++"+rr+"++");
        String response = pdp.evaluate(rr);
        ResponseCtx responseCtx = ResponseCtx.getInstance(getXacmlResponse(response));
        return responseCtx;
    }
    
    public static Element getXacmlResponse(String response) {
        ByteArrayInputStream inputStream;
        DocumentBuilderFactory dbf;
        Document doc;

        inputStream = new ByteArrayInputStream(response.getBytes());
        dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);

        try {
            doc = dbf.newDocumentBuilder().parse(inputStream);
        } catch (Exception e) {
            System.err.println("DOM of request element can not be created from String");
            return null;
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
               System.err.println("Error in closing input stream of XACML response");
            }
        }
        return doc.getDocumentElement();
    }

    private PDP setupWithPolicies(String[] policyPath) {        
        PolicyFinder pf = new PolicyFinder();
        Set paths = new HashSet<String>(Arrays.asList(policyPath));
        PolicyFinderModule pfm = new FileBasedPolicyFinderModule(paths);
        Set<PolicyFinderModule> spfm = new HashSet<PolicyFinderModule>();
        spfm.add(pfm);
        pf.setModules(spfm);
        
        AttributeFinder attrFinder = new AttributeFinder();
        SelectorModule selectorAttributeModule = new SelectorModule();
        List attrModules = new ArrayList();
        attrModules.add(selectorAttributeModule);
        CurrentEnvModule envAttributeModule = new CurrentEnvModule();
        attrModules.add(envAttributeModule);
        attrFinder.setModules(attrModules);
        return new PDP(new PDPConfig(attrFinder, pf, null));
    }
    
}
