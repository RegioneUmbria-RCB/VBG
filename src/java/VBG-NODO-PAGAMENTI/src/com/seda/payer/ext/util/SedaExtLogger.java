/**
 * 
 */
package com.seda.payer.ext.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Hierarchy;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.RootLogger;

public class SedaExtLogger {

	/*public static final String BASE_LOG_MESSAGES="com.seda.j2ee5.maf.util.LogMessages";
	public static final String MDC_CTX="ctx"; 
	public static final String MDC_APP="app";*/
	
	private static Hierarchy hierarchy;	
    //private static ResourceBundle bundle=ResourceBundle.getBundle(BASE_LOG_MESSAGES);	
	
    static {
    	Properties log4jConfiguration = new Properties();
    	try {
			log4jConfiguration.load(SedaExtLogger.class.getResourceAsStream("log4j.properties"));
			hierarchy = new Hierarchy(new RootLogger(Level.DEBUG));
			// Load log4j property from external properties configuration object
	    	new PropertyConfigurator().doConfigure(log4jConfiguration, hierarchy);
	    	
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static Logger getLogger(String name) {
        return hierarchy.getLogger(name);
    }
}
