package com.kwon.smb;

import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBase {
    public DBase() {
    }
     
    public Connection connect(String db_connect_str, 
            String db_userid, String db_password) {
        Connection conn;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = (Connection) DriverManager.getConnection(db_connect_str,
                    db_userid, db_password);
             
        } catch(Exception e) {
            e.printStackTrace();
            conn = null;
        }
        return conn;
    }
     
    public void exportData(Connection conn,String filename) {
        Statement stmt;
        String query;
        try {
            stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
             
            //For comma separated file
            query = "SELECT userid,movieid,rating into OUTFILE '"+filename+
                    "' FIELDS TERMINATED BY ',' FROM rating";
            stmt.executeQuery(query);
             System.out.println("csv 파일 생성 완료");
        } catch(Exception e) {
            e.printStackTrace();
            stmt = null;
        }
    }
    
    
}