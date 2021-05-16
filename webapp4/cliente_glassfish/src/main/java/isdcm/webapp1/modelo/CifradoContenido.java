/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp1.modelo;

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
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CifradoContenido {
    
    static private SecretKey key;
    
    public CifradoContenido() {
        if (key == null){
            try {
                KeyStore ks = KeyStore.getInstance("JCEKS");
                ks.load(new FileInputStream(new File(CifradoContenido.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceAll("/([^/]+)$","/") + "../../../../../res/aes.keystore")), "asdf1234".toCharArray());
                
                key = (SecretKeySpec) ks.getKey("aes128", "asdf1234".toCharArray());
                
            } catch (KeyStoreException ex) {
                Logger.getLogger(CifradoContenido.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CifradoContenido.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CifradoContenido.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(CifradoContenido.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CertificateException ex) {
                Logger.getLogger(CifradoContenido.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnrecoverableKeyException ex) {
                Logger.getLogger(CifradoContenido.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public byte[] encrypt(byte[] file){
        byte[] efile = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key,new IvParameterSpec(new byte[16]));
            efile = cipher.doFinal(file);
        } catch (Exception ex) {
            Logger.getLogger(CifradoContenido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return efile;
  }

    public byte[] decrypt(byte[] file) {
        byte[] dfile = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,key,new IvParameterSpec(new byte[16]));
            dfile = cipher.doFinal(file);
        }   catch (Exception ex) {
            Logger.getLogger(CifradoContenido.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dfile;
    }
}
