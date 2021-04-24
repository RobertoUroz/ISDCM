/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp.modelo;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import org.json.JSONArray;
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
    private int id;
    private String date; //TODO: Change to date time format
    private String duracionString;
    private int reproducciones;
    private String username;
    
    /**
     *
     * @param titulo
     * @param autor
     * @param duracionH
     * @param duracionMin
     * @param duracionS
     * @param descripcion
     * @param formato
     * @param url
     * @param username
     */
    public Video(String titulo, String autor, int duracionH, int duracionMin, int duracionS, String descripcion, String formato, String url, String username) {
        this.titulo = titulo.toLowerCase();
        this.autor = autor.toLowerCase();
        this.duracionH = duracionH;
        this.duracionMin = duracionMin;
        this.duracionS = duracionS;
        this.descripcion = descripcion.toLowerCase();
        this.formato = formato.toLowerCase();
        this.url = url;
        this.username = username;			   
    }
    
    public Video(JSONObject item) {
        System.out.println(item.toString());
        this.id = item.getInt("id");
        this.titulo = item.getString("titulo").toLowerCase();
        this.autor = item.getString("autor").toLowerCase();
        this.date = item.get("fechacreacion").toString();
        this.duracionString = item.get("duracion").toString();
        this.reproducciones = item.getInt("reproducciones");
        this.descripcion = item.getString("descripcion").toLowerCase();
        this.formato = item.getString("formato").toLowerCase();
        this.url = item.getString("url");
        this.username = item.getString("username");
    }

    /**
     *
     */
    public Video() {
    }
    
    /**
     *
     * @param id
     */
    public Video(int id){
        this.id = id;
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
     *
     * @return
     */
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
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     *
     * @return
     */
    public String getURLFromVideo() {
        if (this.url == null){
            DatabaseService db = DatabaseService.getInstance();
            JSONObject video_json = db.getPSQLQuery("SELECT URL FROM VIDEOS WHERE ID=?",this.id);
            if (video_json.getInt("count") != 1)
                throw new RuntimeException("Error in video::gettingURLFromVideo() : video selected is not unique or does not exist");
            return video_json.getJSONArray("items").getJSONObject(0).getString("URL");
        } else {
            return this.url;
        }
    }

    public JSONObject getAllVideos() {
        DatabaseService db = DatabaseService.getInstance();
        return db.getSQLQuery("SELECT * FROM VIDEOS");
    }
    
    /*
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