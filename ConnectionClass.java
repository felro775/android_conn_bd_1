package com.decodexs.conexion01;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
public class ConnectionClass {
    protected static String db = "u254451192_Liga2425";
    protected static String ip = "193.203.166.19";
    protected static String port = "3306";
    protected static String username = "u254451192_felro775";
    protected static String password = "Es55143976$";



    public Connection CONN(){
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String conexionString = "jdbc:mysql://"+ip+":"+port+"/"+db;
            conn = DriverManager.getConnection(conexionString,username,password);

        } catch (Exception e) {
            Log.e("Error Conexion SQL", Objects.requireNonNull(e.getMessage()));
        }
        return conn;
    }
}
