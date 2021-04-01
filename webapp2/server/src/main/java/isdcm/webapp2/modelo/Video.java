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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private String username;
    
    private String date; //TODO: Change to date time format
    private String duracionString;
    private int reproducciones;
    
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
     * @return the duracionH
     */
    public int getDuracionH() {
        return duracionH;
    }

    /**
     * @param duracionH the duracionH to set
     */
    public void setDuracionH(int duracionH) {
        this.duracionH = duracionH;
    }

    /**
     * @return the duracionMin
     */
    public int getDuracionMin() {
        return duracionMin;
    }

    /**
     * @param duracionMin the duracionMin to set
     */
    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }

    /**
     * @return the duracionS
     */
    public int getDuracionS() {
        return duracionS;
    }

    /**
     * @param duracionS the duracionS to set
     */
    public void setDuracionS(int duracionS) {
        this.duracionS = duracionS;
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
    
    /**
     *
     * @return
     */
    public boolean registerVideo() {
        DatabaseService db = DatabaseService.getInstance();
        JSONObject id = db.getSQLQuery("VALUES NEXT VALUE FOR VIDEOS_SEQ");
        int int_id = id.getJSONArray("items").getJSONObject(0).getInt("1");
        //Time
        Time sqlTime = new Time(this.getDuracionH(), this.getDuracionMin(), this.getDuracionS());
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
                + "','"
                + this.getUsername()
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
        JSONObject video_json = db.getSQLQuery("SELECT URL FROM VIDEOS WHERE ID='" + this.getId() + "'");
        if (video_json.getString("count") != "1")
            throw new RuntimeException("Error in video::gettingURLFromVideo() : video selected is not unique or does not exist");
        return video_json.getJSONArray("items").getJSONObject(0).getString("URL");
    }
    
    public List<Video> searchMyVideos(String username){
        DatabaseService db = DatabaseService.getInstance();
        JSONObject videos_obj = db.getSQLQuery("SELECT * FROM VIDEOS WHERE USERNAME='" + username + "'");
        return transformJSONToListVideos(videos_obj);
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
    
    public List<Video> searchVideo(String titulo, String autor, String year, String mes, String dia) {
        DatabaseService db = DatabaseService.getInstance();
        String sql = "SELECT * FROM VIDEOS";
        List<String> where_sql = new ArrayList<>();
        if (autor != null)
            where_sql.add("AUTOR = '" + autor + "'");
        if (titulo != null)
            where_sql.add("TITULO = '" + titulo + "'");
        if (year != null){
            String[] dates;
            Integer I_dia = null, I_mes = null;
            int i_year = Integer.parseInt(year);
            if (dia != null)
                I_dia = Integer.valueOf(dia);
            if (mes != null)
                I_mes = Integer.valueOf(mes);
            dates = getDates(I_dia, I_mes, i_year);
            if (dates[1] != null)
                where_sql.add("FECHACREACION >= " + dates[0] + " AND FECHACREACION < " + dates[1]);
            else
                where_sql.add("FECHACREACION = " + dates[0]);
        }
        if (!where_sql.isEmpty()){
            sql += " WHERE ";
            for (int i = 0; i < where_sql.size(); i++){
                sql += where_sql.get(i);
                if (i != where_sql.size() - 1)
                    sql += " AND ";
            }   
        }
        System.out.println(sql);
        JSONObject videos_db = db.getSQLQuery(sql);
        return transformJSONToListVideos(videos_db);
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

    private List<Video> transformJSONToListVideos(JSONObject videos_db) {
        JSONArray videos_array = videos_db.getJSONArray("items");
        List<Video> result = new ArrayList<>();
        for (int i = 0; i < videos_array.length(); i++){
            result.add(new Video(videos_array.getJSONObject(i)));
        }
        return result;
    }
}
