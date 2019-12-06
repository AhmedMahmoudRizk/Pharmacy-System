/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBManagementLayer;

/**
 *
 * @author power
 */
import java.sql.*;

public class myconnector {
	public Connection con;
	public Statement stmt;
	public myconnector() throws Exception {
		try{
		 	String userName = "root";
	   		String password = "admin";
	        	String url ="jdbc:mysql://localhost/mydb";//?useUnicode=true&characterEncoding=UTF-8
		        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        		con = DriverManager.getConnection (url, userName, password);

			//DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
        	//stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt = con.createStatement();
			//stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch(Exception e) {
			System.err.println(">_< MySQL connection is broken!!! The error is as follows,\n");
            System.err.println(e.getMessage());
			throw(e);
		}
	}

	public void closeConnection() throws Exception{
		con.close();
	}
}