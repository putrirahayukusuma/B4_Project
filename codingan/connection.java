package codingan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class connection {
    private static Connection mysqlconfig;
    public static Connection configDB()throws SQLException{
    try {
    Class.forName("com.mysql.jdbc.Driver");
    System.out.println("Driver Loaded");
    mysqlconfig = DriverManager.getConnection("jdbc:mysql://localhost:3306/hijab","root","");
    System.out.println("Terhubung...");
} catch(ClassNotFoundException e) {
    throw new IllegalStateException(null, e); 
}
    return mysqlconfig; 
}
}

