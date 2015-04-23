package com.daniel.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

public class DbUtil {
	private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
            	Properties prop = new Properties();
                InputStream inputStream = DbUtil.class.getClassLoader().getResourceAsStream("/db.properties");
                prop.load(inputStream);
                System.out.println("JNDI DS: " + prop.getProperty("jndi.ds"));
                if (prop.getProperty("jndi.ds").isEmpty()) {
                    String driver = prop.getProperty("driver");
                    String url = prop.getProperty("url");
                    String user = prop.getProperty("user");
                    String password = prop.getProperty("password");
                    Class.forName(driver);
                    connection = DriverManager.getConnection(url, user, password);                	
                } else {
                	System.out.println("Get connection from datasource");
                	String jndiPath = prop.getProperty("jndi.ds"); 
                	Context ctx = new InitialContext();
                	DataSource ds = (DataSource) ctx.lookup(jndiPath);
                	connection = ds.getConnection();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NamingException e) {
				e.printStackTrace();
			}
            return connection;
        }

    }
    
    public static void close(Connection con, Statement stmt, ResultSet rs) {
    	try {
			if (con != null) con.close();
			if (stmt != null) stmt.close();
			if (rs != null) rs.close();
		} catch (Throwable e) {
		}
    	
    }
}
