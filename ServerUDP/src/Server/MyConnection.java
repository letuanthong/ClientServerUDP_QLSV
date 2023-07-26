package Server;
import java.sql.*;
import javax.swing.*;

public class MyConnection {
    
    Connection con = null;
    private String database;
    private String username;
    private String password;
    
    public Connection getConnection(){
        
        database = ServerCtr.database;
        username = ServerCtr.username;
        password = ServerCtr.password;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String URL = "jdbc:mysql://localhost/"+database+"?useTimezone=true&serverTimezone=UTC";
            Connection con = DriverManager.getConnection(URL, username, password);
            return con;
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString(), "Loi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
}
