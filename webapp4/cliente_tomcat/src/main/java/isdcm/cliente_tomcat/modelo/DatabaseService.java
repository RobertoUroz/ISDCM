/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.cliente_tomcat.modelo;

import java.io.File;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Time;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ruroz
 */
public class DatabaseService {
    
    private static DatabaseService SINGLE_INSTANCE = null;
    private String db_URL = null;
    private Connection connection = null;
    private static boolean PREPAREDB = true;
    private static boolean DROPDB = false;
    private String[] tables = {
        "USUARIOS", 
        "VIDEOS", 
        "PREFERENCIASUSUARIO"
    };
    private String[] drop_tables = {
        "VIDEOS", 
        "PREFERENCIASUSUARIO",
        "USUARIOS"
    };
    private String[] columns = {
        "NOMBRE VARCHAR(2048), APELLIDOS VARCHAR(2048), EMAIL VARCHAR(2048), USERNAME VARCHAR(256) PRIMARY KEY, PASSWORD VARCHAR(2048)",
        "ID NUMERIC PRIMARY KEY, TITULO VARCHAR(100), AUTOR VARCHAR(100), FECHACREACION DATE, DURACION TIME, REPRODUCCIONES NUMERIC, DESCRIPCION VARCHAR(255), FORMATO VARCHAR(5), URL VARCHAR(2048), USERNAME VARCHAR(256), FOREIGN KEY (USERNAME) REFERENCES USUARIOS(USERNAME)",
        "USERNAME VARCHAR(256) PRIMARY KEY, REPRODUCTOR SMALLINT, LISTAVIDEOS SMALLINT, COLOR SMALLINT"
    };
    
    private DatabaseService() {
        String filePath = new File("").getAbsolutePath();
        System.out.println(filePath);
        this.db_URL = "jdbc:derby:" + filePath + "..\\..\\tomcat_db;create=true;";
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connection = DriverManager.getConnection(db_URL, "app", "admin");
            System.out.println("Connected to DB");
            if (DROPDB)
                dropDB(0);
            if (PREPAREDB)
                prepareDB(0);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public JSONObject getSQLQuery(String sql) {
        JSONObject result = new JSONObject();
        result.put("items", new JSONArray());
        result.put("count", 0);
        try {
            Statement statement = connection.createStatement();
            ResultSet rows = statement.executeQuery(sql);
            JSONArray rows_array = convertToJSONArray(rows);
            result.put("items", rows_array);
            result.put("count", rows_array.length());
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
    public JSONObject getPSQLQuery(String sql, Object... params) {
        JSONObject result = new JSONObject();
        result.put("items", new JSONArray());
        result.put("count", 0);
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            parametros_en_sentencia(params, statement);
            ResultSet rows = statement.executeQuery();
            JSONArray rows_array = convertToJSONArray(rows);
            result.put("items", rows_array);
            result.put("count", rows_array.length());
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    private void parametros_en_sentencia(Object[] params, PreparedStatement statement) throws SQLException {
        for (int i=0; i<params.length; ++i) {
            if (params[i] instanceof String) {
                statement.setString(i+1, (String) params[i]);
            } else if (params[i] instanceof Time) {
                statement.setTime(i+1, (Time) params[i]);
            } else if (params[i] instanceof Date) {
                statement.setDate(i+1, (Date) params[i]);
            } else if (params[i] instanceof Clob) {
                statement.setClob(i+1, (Clob) params[i]);
            } else {
                try {
                    statement.setInt(i+1, (int) params[i]);
                } catch (NullPointerException e) {
                    System.out.print(params[i].getClass().getName());
                }
            }
        }
    }
    
    int insertSQLQuery(String sql) {
        int rows = 0;
        try {
            Statement statement = connection.createStatement();
            rows = statement.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }
    
    int insertPSQLQuery(String sql, Object... params) {
        int rows = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            parametros_en_sentencia(params, statement);
            rows = statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rows;
    }
    
    private JSONArray convertToJSONArray(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            JSONObject obj = new JSONObject();
            int total_rows = resultSet.getMetaData().getColumnCount();
            for (int i = 0; i < total_rows; i++) {
                Object value = resultSet.getObject(i + 1);
                if (value.getClass().getName().equals("org.apache.derby.client.am.ClientClob")){
                    Clob value_clob = (Clob)value;
                    value = value_clob.getSubString(1, (int) value_clob.length());
                }
                String label = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                obj.put(label, value);
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }
    
    public static DatabaseService getInstance() {
        if (SINGLE_INSTANCE == null) {  
          synchronized(DatabaseService.class) {
          SINGLE_INSTANCE = new DatabaseService();
          }
        }
        return SINGLE_INSTANCE;
    }

    private void prepareDB(int i) {
        String sql = "";
        try {
            for (; i < tables.length; i++){
                sql = "CREATE TABLE app." + tables[i] + "(" + columns[i] + ")";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.execute();
                System.out.println("CREATE");
            }
            PreparedStatement statement = connection.prepareStatement("CREATE SEQUENCE VIDEOS_SEQ AS BIGINT START WITH 0");
            statement.execute();
        } catch (SQLException e) {
            switch (e.getSQLState()){
                case "X0Y32":
                    prepareDB(i + 1);
                    break;
                case "X0Y68":
                    break;
                default:
                    Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    private void dropDB(int i) {
        String sql = "";
        try {
            for (; i < drop_tables.length; i++){
                sql = "DROP TABLE app." + drop_tables[i];
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.execute();
                System.out.println("drop");
            }
        } catch (SQLException e) {
            switch (e.getSQLState()){
                case "X0Y32.S":
                    dropDB(i+1);
                    break;
                case "42Y55":
                    dropDB(i+1);
                    break;
                default:
                    Logger.getLogger(DatabaseService.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
