package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

	private static final String PROPERTIES_FILE = "mysql.properties";
	
    public static Connection connectDB() {
    	try (InputStream input = Database.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
    		Properties prop = new Properties();
    		
    		if (input == null) {
                System.out.println("Unable to find files" + PROPERTIES_FILE);
                return null;
            }
    		
    		prop.load(input);
    		
    		String url = prop.getProperty("db.url");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
            
    	} catch (IOException |  SQLException | ClassNotFoundException e) {e.printStackTrace();}
    	return null;
    }
}
