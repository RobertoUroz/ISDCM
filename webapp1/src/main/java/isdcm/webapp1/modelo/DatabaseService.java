/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isdcm.webapp1.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    private DatabaseService() {
        this.db_URL = "jdbc:derby://localhost:1527/webapp1";
        try {
            connection = DriverManager.getConnection(db_URL);
            System.out.println("Connected to DB");
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
    
    private JSONArray convertToJSONArray(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
        JSONObject obj = new JSONObject();
            int total_rows = resultSet.getMetaData().getColumnCount();
            for (int i = 0; i < total_rows; i++) {
                obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), resultSet.getObject(i + 1));

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
}
