/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp1.modelo;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author cubometa
 */
public class CifradoVideos {
    private SecretKey createkey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES/CBC/PKCS5Padding");
            return kg.generateKey();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    private byte[] encryptfile(byte[] file, SecretKey sk) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,sk);
            return cipher.doFinal(file);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println(e);
            return "".getBytes();
        }
    }
    
    /*
    private byte[] decryptfile(byte[] file, SecretKey sk) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,sk);
            return cipher.doFinal(file);
        } catch (Exception e) {
            System.out.println(e);
            return "".getBytes();
        }
    }
    */
    
    private String encodekey(SecretKey sk) {
        return Base64.getEncoder().encodeToString(sk.getEncoded());
    }
    
    private SecretKey decodekey(String s) {
        byte[] dk = Base64.getDecoder().decode(s);
        return new SecretKeySpec(dk, 0, dk.length, "AES/CBC/PKCS5Padding");
    }
}
