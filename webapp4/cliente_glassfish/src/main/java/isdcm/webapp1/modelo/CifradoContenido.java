/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp1.modelo;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

/**
 *
 * @author cubometa
 */
public class CifradoContenido {
    public SecretKey createkey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            return kg.generateKey();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    public byte[] encryptfile(byte[] file, SecretKey sk) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,sk);
            return cipher.doFinal(file);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println(e);
            return "".getBytes();
        }
    }
    
    public byte[] decryptfile(byte[] file, SecretKey sk) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,sk);
            return cipher.doFinal(file);
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            System.out.println(e);
            return "".getBytes();
        }
    }
    
    public byte[] encodekey(SecretKey sk) {
        try {
        SecretKeyFactory aesFactory = SecretKeyFactory.getInstance("AES");
        SecretKeySpec aesSpec = (SecretKeySpec)
                aesFactory.getKeySpec(sk, javax.crypto.spec.SecretKeySpec.class);
        byte[] rawAesKey = aesSpec.getEncoded();
        return Base64.getEncoder().encode(rawAesKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.println(e);
            return "".getBytes();
        }
        //return Base64.getEncoder().encode(sk.getEncoded());
        // return Base64.getEncoder().encodeToString(sk.getEncoded());
    }
    
    public SecretKey decodekey(byte[] s) {
        byte[] dk = Base64.getDecoder().decode(s);
        return new SecretKeySpec(dk, 0, dk.length, "AES");
    }
    
    public byte[] getuserkey(String usuario) {
        JSONObject user_json = null;
        DatabaseService db = DatabaseService.getInstance();
        user_json = db.getPSQLQuery("SELECT * FROM CLAVESUSUARIO WHERE USERNAME=?",usuario);
        byte[] key = new byte[0];
        System.out.print("-->"+user_json.toString());
        if (user_json.getInt("count") > 0){
            System.out.println(user_json.toString());
            key = (byte[]) user_json.getJSONArray("items").getJSONObject(0).get("clave");
        }
        return key;
    }
}
