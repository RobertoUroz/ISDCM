/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.app5.xmlSignature;

import java.io.ByteArrayInputStream;
import java.io.File;
import javax.xml.crypto.*;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dom.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.*;
import javax.xml.crypto.dsig.spec.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Resis
 */
class XMLSigner {

    private final String pathPolicy;
    private final String pathXML;
    private final String baseDir = System.getProperty("user.dir");
    private final String outputPath;
    private final String publicKeysPath;
    private final long timestamp = System.currentTimeMillis();
    
    XMLSigner(String pathPolicy, String pathXML) {
        this.pathPolicy = pathPolicy;
        this.pathXML = pathXML;
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0){
            this.outputPath  = baseDir + "\\outputs\\" + "Signed_" + timestamp + ".xml";
            this.publicKeysPath = baseDir + "\\src\\main\\java\\res\\publicKeys";
        } else {
            this.outputPath  = baseDir + "/outputs/" + "Signed_" + timestamp + ".xml";
            this.publicKeysPath = baseDir + "/src/main/java/res/publicKeys";
        }
    }

    void signXML() throws NoSuchAlgorithmException, FileNotFoundException, SAXException, IOException, ParserConfigurationException, InvalidAlgorithmParameterException, KeyException, MarshalException, XMLSignatureException, TransformerConfigurationException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true); 
        DocumentBuilder builder = dbf.newDocumentBuilder();  
        Document doc = builder.parse(new FileInputStream(getAllXMLFilesPath(pathPolicy)[0]));
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        DOMSignContext dsc = new DOMSignContext
          (kp.getPrivate(), doc.getDocumentElement());
        XMLSignatureFactory fac =
          XMLSignatureFactory.getInstance("DOM");
        Reference ref = fac.newReference
          ("", fac.newDigestMethod(DigestMethod.SHA1, null),
            Collections.singletonList
              (fac.newTransform(Transform.ENVELOPED,
                (TransformParameterSpec) null)), null, null); 
        SignedInfo si = fac.newSignedInfo
        (fac.newCanonicalizationMethod
          (CanonicalizationMethod.INCLUSIVE_WITH_COMMENTS,
            (C14NMethodParameterSpec) null),
          fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
          Collections.singletonList(ref)); 
        KeyInfoFactory kif = fac.getKeyInfoFactory();
        KeyValue kv = kif.newKeyValue(kp.getPublic());
        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(kv));
        XMLSignature signature = fac.newXMLSignature(si, ki);
        signature.sign(dsc);
        OutputStream os;
        os = new FileOutputStream(outputPath);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer trans = tf.newTransformer();
        trans.transform(new DOMSource(doc), new StreamResult(os));
        storePublicKey(kp.getPublic());
    }
    
    void verifyXML() throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, Exception {
        // Instantiate the document to be validated
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc =
            dbf.newDocumentBuilder().parse(new FileInputStream(this.pathPolicy));

        // Find Signature element
        NodeList nl =
            doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        if (nl.getLength() == 0) {
            throw new Exception("Cannot find Signature element");
        }

        // Create a DOM XMLSignatureFactory that will be used to unmarshal the
        // document containing the XMLSignature
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

        
        // Create a DOMValidateContext and specify a KeyValue KeySelector
        // and document context
        String[] keyPaths = retrieveAllKeyFiles(this.publicKeysPath);
        for (int i = 0; i < nl.getLength(); i++){
            if (verifyWithKeys(keyPaths, 0, fac, nl.item(i))){
                System.out.println("Signature passed core validation");
                return;
            }
        }
        System.out.println("Esta policy no es admitida en el sistema");
    }
    
    private boolean verifyWithKeys(String[] keyPaths, int i, XMLSignatureFactory fac, Node n) {
        try {
            
            RSAPublicKey pKey = readPublicKey(keyPaths[i]);
            
            DOMValidateContext valContext = new DOMValidateContext
                (pKey, n);

            // unmarshal the XMLSignature
            XMLSignature signature = fac.unmarshalXMLSignature(valContext);

            // Validate the XMLSignature (generated above)
            boolean coreValidity = signature.validate(valContext);

            // Check core validation status
            if (coreValidity == false) {
                System.err.println("Signature failed core validation");
                boolean sv = signature.getSignatureValue().validate(valContext);
                System.out.println("signature validation status: " + sv);
                // check the validation status of each Reference
                Iterator it = signature.getSignedInfo().getReferences().iterator();
                for (int j=0; it.hasNext(); j++) {
                    boolean refValid =
                            ((Reference) it.next()).validate(valContext);
                    System.out.println("ref["+j+"] validity status: " + refValid);
                    if (refValid)
                        return true;
                }
                return false;
            } else {
                return true;
            }
        } catch (Exception ex) {
            if (i == keyPaths.length - 1) {
                return false;
            } else {
                return verifyWithKeys(keyPaths, ++i, fac, n);
            }
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
    
    private String[] retrieveAllKeyFiles(String path) {
        File f = new File(path);
        File[] files = f.listFiles(filterKeys);
        String[] result = new String[files.length];
        for (int i = 0; i < files.length; i++)
            result[i] = files[i].getAbsolutePath();
        return result;
    }
    
    FilenameFilter filterKeys = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return true;
        }
    };

    private void storePublicKey(PublicKey pKey) throws FileNotFoundException, IOException {
        PemObject pemObject = new PemObject("PUBLIC KEY", pKey.getEncoded());
        PemWriter pemWriter;
        if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0){
            pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(baseDir + "\\src\\main\\java\\res\\publicKeys\\" + "PublicKey_" + timestamp + ".pem")));
        } else {
            pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(baseDir + "/src/main/java/res/publicKeys/" + "PublicKey_" + timestamp + ".pem")));
        }
        try {
            pemWriter.writeObject(pemObject);
        } finally {
            pemWriter.close();
            System.out.println("Clave pública guardada exitosamente para futuras validaciones");
        }
    }
    
    private RSAPublicKey readPublicKey(String path) throws IOException {
        System.out.println(path);
        Security.addProvider(new BouncyCastleProvider());
        byte[] keyBytes = Files.readAllBytes(Paths.get(path));
        PEMReader reader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(keyBytes)));
        return (RSAPublicKey) reader.readObject();
    }
   
}
