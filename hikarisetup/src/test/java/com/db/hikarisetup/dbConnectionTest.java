package com.db.hikarisetup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.db.hikarisetup.dbManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
public class dbConnectionTest {

	private static final Logger LOG = Logger.getLogger(dbConnectionTest.class);
	
	private static void postProcess(ResultSet rs) throws SQLException {
		while(rs.next()){
			printRecord(rs);
		}
		
	}
	private static void printRecord(ResultSet rs) throws SQLException {
		System.out.println(rs.getString("id").toString());
		System.out.println(rs.getString("Name").toString());
		
	}
	public static void main(String args[]) throws SQLException
	{
		
		 String configFile = "src/main/resources/dbp-database.properties";
	        
	        HikariConfig cfg = new HikariConfig(configFile);
	        HikariDataSource ds = new HikariDataSource(cfg);
		 Connection con = null;
		 PreparedStatement pst = null;
	        ResultSet rs = null;
		 try {

	            con = ds.getConnection();
	            pst = con.prepareStatement("SELECT * FROM alerttype;");
	            rs = pst.executeQuery();

	            while (rs.next()) {

	                System.out.format("%d %s %d %n", rs.getInt(1), rs.getString(2), 
	                        rs.getInt(3));
	            }

	        } catch (SQLException ex) {

	        	LOG.error("error=",ex);

	        } finally {

	            try {
	            
	                if (rs != null) {
	                    rs.close();
	                }
	                
	                if (pst != null) {
	                    pst.close();
	                }
	                
	                if (con != null) {
	                    con.close();
	                }
	                ds.close();
	            } catch (SQLException ex) {

	            }
	        }
	}
}
