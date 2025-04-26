package com.qlbv.model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectDB {
    private static ConnectDB instance = new ConnectDB();

    public static ConnectDB getInstance() {
        return instance;
    
    }

    public static Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            String url = "jdbc:sqlserver://localhost:1433;database=QLBanVe;encrypt=true;trustServerCertificate=true;useUnicode=true;characterEncoding=UTF-8";
            String user = "sa";
            String password = "sapassword";
            con = DriverManager.getConnection(url, user, password);
            
        } catch (SQLException e) {
            System.out.println("Ket Noi That Bai .Error:"+e.getMessage());
        }

        return con;
    }

    public static void disConnect(Connection c){
        try {
            if(c!=null){
                c.close();
            }
        } catch (SQLException e) {
            System.out.println("Ngat Ket Noi That Bai .Error: "+e.getMessage());
        }
    }

    

}