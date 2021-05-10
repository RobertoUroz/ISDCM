/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.cliente_tomcat.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.apache.xml.security.encryption.XMLCipher;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Resis
 */
public class Cifrado {
    
    static private SecretKey key;
    
    public Cifrado() {
        if (!org.apache.xml.security.Init.isInitialized())
            org.apache.xml.security.Init.init();
        if (key == null){
            try {
                KeyStore ks = KeyStore.getInstance("JCEKS");
                ks.load(new FileInputStream(new File(Cifrado.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "../../res/aes.keystore")), "asdf1234".toCharArray());
                
                key = (SecretKeySpec) ks.getKey("aes128", "asdf1234".toCharArray());
                
            } catch (KeyStoreException ex) {
                Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CertificateException ex) {
                Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnrecoverableKeyException ex) {
                Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Document encrypt(Document doc, Element el, Boolean encryptContentsOnly){
        Document document = null;
        try {
            XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.AES_128);
            keyCipher.init(XMLCipher.ENCRYPT_MODE, key);
            document = keyCipher.doFinal(doc, doc.getDocumentElement(), encryptContentsOnly);
        } catch (Exception ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return document;
  }

    public Document decrypt(Document doc) {
        Document document = null;
        try {
            XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.AES_128);
            keyCipher.init(XMLCipher.DECRYPT_MODE, key);
            document = keyCipher.doFinal(doc, doc.getDocumentElement());
        }   catch (Exception ex) {
            Logger.getLogger(Cifrado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return document;
    }
}
