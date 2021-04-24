/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp.modelo;

import org.json.JSONObject;

/**
 *
 * @author alejandro.adan.navarro
 */
public class PreferenciasUsuario {
    private String username;
    private int reproductor; // 1 VideoJS, 2 <video> HTML5
    static public final int REPRODUCTOR_VIDEOJS = 1;
    static public final int REPRODUCTOR_HTML5 = 2;
    private int listavideos; // 1 Práctica 1, 2 Práctica 2
    static public final int LISTAVIDEOS_BD = 1;
    static public final int LISTAVIDEOS_SOAP = 2;
    private int color; // 1 claro, 2 oscuro
    static public final int COLOR_CLARO = 1;
    static public final int COLOR_OSCURO = 2;
    
    public PreferenciasUsuario(String username) {
        this.username = username;
        this.reproductor = REPRODUCTOR_HTML5;
        this.listavideos = LISTAVIDEOS_SOAP;
        this.color = COLOR_CLARO;
    }
    
    public PreferenciasUsuario(String username, int reproductor, int listavideos, int color) {
        this.username = username;
        this.reproductor = reproductor;
        this.listavideos = listavideos;
        this.color = color;
    }
    
    /**
     * Recibir las preferencias de un usuario desde la BD.
     * @param username El nombre de usuario del usuario.
     * @return Las preferencias de dicho usuario.
     */
    public static PreferenciasUsuario get(String username) {
        JSONObject user_json = null;
        DatabaseService db = DatabaseService.getInstance();
        user_json = db.getPSQLQuery("SELECT * FROM PREFERENCIASUSUARIO WHERE USERNAME=?",username);
        System.out.print("-->"+user_json.toString());
        if (user_json.getInt("count") > 0){
            System.out.println(user_json.toString());
            JSONObject usu = user_json.getJSONArray("items").getJSONObject(0);
            return new PreferenciasUsuario(username,
                    usu.getInt("reproductor"),
                    usu.getInt("listavideos"),
                    usu.getInt("color"));
        }
        return new PreferenciasUsuario(username);
    }
    
    public int reproductor() {
        return reproductor;
    }
    
    public int listavideos() {
        return listavideos;
    }
    
    public int color() {
        return color;
    }
    
    /**
     * Guardar las preferencias en la BD.
     * @return Si el guardado ha tenido éxito o no.
     */
    public boolean save() {
        JSONObject user_json;
        DatabaseService db = DatabaseService.getInstance();
        user_json = db.getPSQLQuery("SELECT USERNAME FROM PREFERENCIASUSUARIO WHERE USERNAME=?",this.username);
        
        int rows;
        if (user_json.getInt("count") == 0) {
            rows = db.insertPSQLQuery("INSERT INTO PREFERENCIASUSUARIO VALUES(?,?,?,?)",
                this.username,
                this.reproductor,
                this.listavideos,
                this.color);
        } else {
            rows = db.insertPSQLQuery("UPDATE PREFERENCIASUSUARIO SET REPRODUCTOR=?, LISTAVIDEOS=?, COLOR=? WHERE USERNAME=?",
                this.reproductor,
                this.listavideos,
                this.color,
                this.username);
        }
        
        switch (rows){
            case 1:
                return true;
            case 0:
                return false;
            default:
                System.out.println("PREFERENCIASUSUARIO::save()  :  There has been another number instead of 1 or 0 : " + rows);
                return false;
        }
    }
}
