/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author user
 */
public class New_ConnectDB {
    private Connection koneksi;
    public Connection connect(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Berhasil Koneksi");
        }catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        String url = "jdbc:mysql://localhost:3306/ms_rumahazza";
        try {
            koneksi = DriverManager.getConnection(url,"root","");
            System.out.println("Berhasil koneksi database");
        }catch (SQLException ex){
            System.out.println(ex);
        }
        return koneksi;
    }
    
}
