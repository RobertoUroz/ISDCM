/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp1.modelo;

import org.json.JSONObject;

/**
 *
 * @author ruroz
 */
public class Usuario {

    private String nombre;
    private String apellidos;
    private String email;
    private String username;
    private String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean loginUser() {
        
        JSONObject user_json = null;
        DatabaseService db = DatabaseService.getInstance();
        user_json = db.getSQLQuery("SELECT * FROM USUARIOS WHERE USERNAME=" + this.getUsername());
        String password_tmp = "null";
        if (user_json.getInt("count") > 0)
            password_tmp = user_json.getJSONArray("items").getJSONObject(0).getString("password");
        return this.getPassword().equals(password_tmp);
        
    }
    
    public boolean registerUser(){
        
        return true;
    }
    
}
