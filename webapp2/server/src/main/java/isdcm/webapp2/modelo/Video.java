/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp2.modelo;

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
     */
    public Video(String titulo, String autor, int duracionH, int duracionMin, int duracionS, String descripcion, String formato, String url) {
        this.titulo = titulo;
        this.autor = autor;
        this.duracionH = duracionH;
        this.duracionMin = duracionMin;
        this.duracionS = duracionS;
        this.descripcion = descripcion;
        this.formato = formato;
        this.url = url;
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
        JSONObject id = db.getSQLQuery("VALUES NEXT VALUE FOR VIDEOS_SEQ");
        int int_id = id.getJSONArray("items").getJSONObject(0).getInt("1");
        //Time
        Time sqlTime = new Time(this.duracionH, this.duracionMin, this.duracionS);
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
     *
     * @return
     */
    public String getURLFromVideo() {
        DatabaseService db = DatabaseService.getInstance();
        JSONObject video_json = db.getSQLQuery("SELECT URL FROM VIDEOS WHERE ID='" + this.id + "'");
        if (video_json.getString("count") != "1")
            throw new RuntimeException("Error in video::gettingURLFromVideo() : video selected is not unique or does not exist");
        return video_json.getJSONArray("items").getJSONObject(0).getString("URL");
    }
    
    /**
     *
     * @param titulo
     * @return
     */
    public JSONObject searchByName(String titulo) {
        DatabaseService db = DatabaseService.getInstance();
        return db.getSQLQuery("SELECT * FROM VIDEOS WHERE TITULO='" + titulo + "'");
    }

    /**
     *
     * @param autor
     * @return
     */
    public JSONObject searchByAutor(String autor) {
        DatabaseService db = DatabaseService.getInstance();
        return db.getSQLQuery("SELECT * FROM VIDEOS WHERE AUTOR='" + autor + "'");
    }

    /**
     *
     * @param dia
     * @param mes
     * @param year
     * @return
     */
    public JSONObject searchByFechaCreacion(Integer dia, Integer mes, int year) {
        DatabaseService db = DatabaseService.getInstance();
        String sql;
        String [] dates;
        dates = getDates(dia, mes, year);
        if (dates[1] != null)
            sql = "SELECT * FROM VIDEOS WHERE FECHACREACION >= " + dates[0] + " AND FECHACREACION < " + dates[1];
        else
            sql = "SELECT * FROM VIDEOS WHERE FECHACREACION = " + dates[0];
        System.out.println("DATES : " + dates[0] + " : " + dates[1]);
        System.out.println(sql);
        return db.getSQLQuery(sql);
    }
    
    public JSONObject searchVideo(String titulo, String autor, String year, String mes, String dia) {
        DatabaseService db = DatabaseService.getInstance();
        String sql = "SELECT * FROM VIDEOS WHERE ";
        if (autor != "")
            sql += "AUTOR = '" + autor + "' AND ";
        if (titulo != "")
            sql += "TITULO = '" + titulo + "' AND ";
        if (year != ""){
            String[] dates;
            dates = getDates(Integer.parseInt(dia), Integer.parseInt(mes), Integer.parseInt(year));
            if (dates[1] != null)
                sql += "FECHACREACION >= " + dates[0] + " AND FECHACREACION < " + dates[1];
            else
                sql += "FECHACREACION = " + dates[0];
        }
        System.out.println(sql);
        return db.getSQLQuery(sql);
    }

    private String[] getDates(Integer dia, Integer mes, int year) {
        String first_date = null;
        String second_date = null;
        if (mes == null){
            first_date = "'" + year + "-01-01'";
            second_date = "'" + (year+1) + "-01-01'";
        } else {
            String mes_correct = mes < 10 ? "0" + mes : mes.toString();
            if (dia == null){
                first_date = "'" + year + "-" + mes_correct + "-01'";
                if (mes == 12) {
                    second_date = "'" + (year+1) + "-" + "01" + "-01'";
                } else {
                    Integer mes_t = mes + 1;
                    System.out.println(mes + " : " + mes_t);
                    mes_correct = mes_t < 10 ? "0" + mes_t : mes_t.toString();
                    second_date = "'" + year + "-" + mes_correct + "-01'";
                }
            } else {
                String dia_correct = dia < 10 ? "0" + dia : dia.toString();
                first_date = "'" + year + "-" + mes_correct + "-" + dia_correct + "'";
            } 
        }
        String[] dates = {first_date, second_date};
        return dates;
    }
}
