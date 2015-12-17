/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airportcodes;

/**
 *
 * @author alyssamcintyre
 */
import java.sql.*;
public class DBConnection {
    static final String JDBC_DRIVER="jdbc:postgresql://simon:5432/accounting";
        static final String DB_URL = "jdbc:mysql://simon.bitbucketacademy.com";
        
        static final String USER = "almcinty";
        static final String PASS = "e7c5S82E";
    public static void getConnection(){
       Connection conn = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch(SQLException se){
            se.printStackTrace();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
              
    }
    

