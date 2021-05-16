/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.cliente_tomcat.modelo;

import java.sql.Date;
import java.sql.Time;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.json.JSONObject;

/**
 *
 * @author ruroz
 */
public class Video {

    private String titulo;
    private String autor;
    private int duracionH;
    private int duracionMin;
    private int duracionS;
    private String descripcion;
    private String formato;
    private String url;
    private String username;
    private String date; //fecha creacion
    private String duracionString;
    private int id;
    private int reproducciones;
    
    public Video() {
        
    }
    
    public Video(String titulo, String autor, int duracionH, int duracionMin, int duracionS, String descripcion, String formato, String url, String username) {
        this.titulo = titulo;
        this.autor = autor;
        this.duracionH = duracionH;
        this.duracionMin = duracionMin;
        this.duracionS = duracionS;
        this.descripcion = descripcion;
        this.formato = formato;
        this.url = url;
        this.username = username;
    }

    public Video(JSONObject item) {
        System.out.println(item.toString());
        this.id = item.getInt("id");
        this.titulo = item.getString("titulo");
        this.autor = item.getString("autor");
        this.date = item.get("fechacreacion").toString();
        this.duracionString = item.get("duracion").toString();
        this.reproducciones = item.getInt("reproducciones");
        this.descripcion = item.getString("descripcion");
        this.formato = item.getString("formato");
        this.url = item.getString("url");
        this.username = item.getString("username");
    }
    
    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the formato
     */
    public String getFormato() {
        return formato;
    }

    /**
     * @param formato the formato to set
     */
    public void setFormato(String formato) {
        this.formato = formato;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    public JSONObject getAllVideos() {
        DatabaseService db = DatabaseService.getInstance();
        return db.getSQLQuery("SELECT * FROM app.VIDEOS");
    }
    
    public boolean registerVideo() {
        DatabaseService db = DatabaseService.getInstance();
        JSONObject ni_id = db.getSQLQuery("VALUES NEXT VALUE FOR VIDEOS_SEQ");
        int int_id = ni_id.getJSONArray("items").getJSONObject(0).getInt("1");
        //Time
        Time sqlTime = new Time(this.duracionH, this.duracionMin, this.duracionS);
        //Date
        Date sqlDate = new Date(System.currentTimeMillis());
        int rows = db.insertPSQLQuery("INSERT INTO VIDEOS VALUES(?,?,?,?,?,?,?,?,?,?)",
                int_id,
                this.titulo,
                this.autor,
                sqlDate,
                sqlTime,
                0,
                this.descripcion,
                this.formato,
                this.url,
                this.username);
        switch (rows){
            case 1:
                return true;
            case 0:
                return false;
            default:
                System.out.println("VIDEOS::registerVideo()  :  There has been another number instead of 1 or 0 : " + rows);
                return false;
        }
    }
    
    public String getUsuario() {
        return username;
    }

    public void setUsuario(String username) {
        this.username = username;
    }
    
    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the duracionString
     */
    public String getDuracionString() {
        return duracionString;
    }

    /**
     * @param duracionString the duracionString to set
     */
    public void setDuracionString(String duracionString) {
        this.duracionString = duracionString;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the reproducciones
     */
    public int getReproducciones() {
        return reproducciones;
    }

    /**
     * @param reproducciones the reproducciones to set
     */
    public void setReproducciones(int reproducciones) {
        this.reproducciones = reproducciones;
    }
}
