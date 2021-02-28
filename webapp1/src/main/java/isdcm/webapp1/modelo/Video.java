/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp1.modelo;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;

/**
 *
 * @author ruroz
 */
public class Video {

    private String titulo;
    private String autor;
    private String duracion;
    private String descripcion;
    private String formato;
    private String url;
    
    private String date; //TODO: Change to date time format
    
    public Video(String titulo, String autor, String duracion, String descripcion, String formato, String url) {
        this.titulo = titulo;
        this.autor = autor;
        this.duracion = duracion;
        this.descripcion = descripcion;
        this.formato = formato;
        this.url = url;
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
     * @return the duracion
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
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
    
    public boolean registerVideo() {
        DatabaseService db = DatabaseService.getInstance();
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
        //LocalDateTime now = LocalDateTime.now();
        JSONObject id = db.getSQLQuery("VALUES NEXT VALUE FOR VIDEOS_SEQ");
        int int_id = id.getJSONArray("items").getJSONObject(0).getInt("1");
        //Time
        Time sqlTime = new Time(1, 1, 1);
        //Date
        Date sqlDate = new Date(System.currentTimeMillis());
        String sql = "INSERT INTO VIDEOS VALUES("
                + int_id
                + ",'"
                + this.titulo
                + "','"
                + this.autor
                + "','"
                + sqlDate
                + "','"
                + sqlTime
                + "',"
                + 0
                + ",'"
                + this.descripcion
                + "','"
                + this.formato
                + "','"
                + this.url
                + "')";
        System.out.println(sql);
        int rows = db.insertSQLQuery(sql);
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
}
