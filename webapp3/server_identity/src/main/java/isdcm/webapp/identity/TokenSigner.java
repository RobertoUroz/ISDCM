/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp.identity;

/**
 *
 * @author Resis
 */
public class TokenSigner {

    private String token;
    
    public boolean sign(String username, String password) {
        //verify user exists
        //if user exists, sign token with 1 hour and return true, if user does not exist, return false
        //store token
        return true;
    }
    
    public String getToken(){
        return token;
    }
    
}
