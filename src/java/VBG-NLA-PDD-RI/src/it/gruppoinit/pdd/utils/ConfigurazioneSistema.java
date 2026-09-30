package it.gruppoinit.pdd.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ConfigurazioneSistema {

    private static final String DEPLOY_PROPERTIES_CLASSPATH = "deploy.properties";
    private Properties props = null;

    private Properties loadDeployProperties() {

	if (props == null) {
	    props = new Properties();
	    InputStream is = null;
	    try {
		is = getClass().getClassLoader().getResource(DEPLOY_PROPERTIES_CLASSPATH).openStream();
		if (is != null) {
		    props.load(is);
		} else {
		    throw new RuntimeException("Impossibile trovare il file di configurazione " + DEPLOY_PROPERTIES_CLASSPATH);
		}
	    } catch (IOException e) {
		throw new RuntimeException("Errore nel caricamento del file " + DEPLOY_PROPERTIES_CLASSPATH, e);
	    } finally {
		try {
		    is.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	return props;
    }

    protected String getPropertyValueForIdComuneAlias(String propertyname, String idcomunealias) {

	String retVal = null;
	Properties deployProps = loadDeployProperties();
	if (StringUtils.isNotBlank(idcomunealias)) {
	    String propName = propertyname.concat(".").concat(idcomunealias);
	    if (StringUtils.isNotBlank(idcomunealias)) {
		retVal = deployProps.getProperty(propName);
	    }
	}
	if (retVal == null) {
	    retVal = deployProps.getProperty(propertyname);
	}
	return retVal;
    }

    protected boolean StringToBoolean(String value) {

	if (StringUtils.defaultIfEmpty(value, "false").equals("true")) {
	    return true;
	}
	return false;
    }
}
