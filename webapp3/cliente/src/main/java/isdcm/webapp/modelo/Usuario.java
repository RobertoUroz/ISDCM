/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp.modelo;

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
    
    public Usuario(String nombre, String apellidos, String email, String username, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
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
        user_json = db.getPSQLQuery("SELECT * FROM USUARIOS WHERE USERNAME=?",this.getUsername());
        //user_json = db.getSQLQuery("SELECT * FROM USUARIOS WHERE USERNAME='"+this.getUsername()+"'");
        String password_tmp = "null";
        System.out.print("-->"+user_json.toString());
        if (user_json.getInt("count") > 0){
            System.out.println(user_json.toString());
            password_tmp = user_json.getJSONArray("items").getJSONObject(0).getString("password");
        }
        return this.getPassword().equals(password_tmp);
        
    }
    
    public boolean registerUser(){
        JSONObject user_json = null;
        DatabaseService db = DatabaseService.getInstance();
        user_json = db.getPSQLQuery("SELECT * FROM USUARIOS WHERE USERNAME=?",this.getUsername());
        //user_json = db.getSQLQuery("SELECT * FROM USUARIOS WHERE USERNAME='" + this.getUsername() + "'");
        if (user_json.getInt("count") > 0) {
            return false;
        }
        /*int rows = db.insertSQLQuery("INSERT INTO USUARIOS VALUES('"
                + this.nombre 
                + "','"
                + this.apellidos
                + "','"
                + this.email
                + "','"
                + this.username
                + "','"
                + this.password
                + "')");*/
        int rows = db.insertPSQLQuery("INSERT INTO USUARIOS VALUES(?,?,?,?,?)",
                this.nombre,
                this.apellidos,
                this.email,
                this.username,
                this.password);
        switch (rows){
            case 1:
                return true;
            case 0:
                return false;
            default:
                System.out.println("USUARIO::registerUser()  :  There has been another number instead of 1 or 0 : " + rows);
                return false;
        }
    }
    
}
