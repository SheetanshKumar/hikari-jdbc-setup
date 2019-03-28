package com.db.hikarisetup;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.konylabs.middleware.controller.DataControllerRequest;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class dbManager {

	private static final Logger LOG = Logger.getLogger(dbManager.class);

    private static HikariDataSource DATASOURCE;
    
    private static synchronized void createDataSource() throws Exception
    {
    	try {
    	if (DATASOURCE == null || DATASOURCE.isClosed()) {
			String configFile = "src/main/resources/dbp-database.properties";
			HikariConfig config = new HikariConfig(configFile);
			DATASOURCE = new HikariDataSource(config);
         }
    	}
    	catch (Exception e) {
    		LOG.error("error=",e);
		}
    }
    
    public static synchronized void closeDataSource() {
        if (DATASOURCE != null) {
            try{
            	DATASOURCE.close();
            }catch(Exception e){
            	LOG.error("DBUtil - Failed to release resources of datasource", e);
            }
        }
    }
	
	public static DataSource getDataSource() {
		if (DATASOURCE == null || DATASOURCE.isClosed()) {
			try {
				createDataSource();
			} catch (Exception e) {
				closeDataSource();
			}
		}
		return DATASOURCE;
	}
	
	public static Connection getConnection() {

		try {
			if (DATASOURCE == null || DATASOURCE.isClosed()) {
				getDataSource();
			}
			return DATASOURCE.getConnection();
		} catch (Exception e) {
			LOG.error("DBUtil - Failed to get connection", e);
		}
		return null;
	}
}

