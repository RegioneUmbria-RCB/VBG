package it.gruppoinit.pdd.ri.interceptors;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class ConfigurazioneLoader {

    private Properties props = null;
    private String DEPLOY_PROPERTIES_CLASSPATH = "";

    public ConfigurazioneLoader() {

	super();
    }

    public ConfigurazioneLoader(String deployPropertiesFile) {

	this();
	this.DEPLOY_PROPERTIES_CLASSPATH = deployPropertiesFile;
    }

    private Properties loadDeployProperties() {

	if (props == null) {
	    props = new Properties();
	    InputStream is = null;
	    try {
		URL resource = getClass().getClassLoader().getResource(DEPLOY_PROPERTIES_CLASSPATH);
		if (resource != null) {
		    is = getClass().getClassLoader().getResource(DEPLOY_PROPERTIES_CLASSPATH).openStream();
		    if (is != null) {
			props.load(is);
		    } else {
			throw new RuntimeException("Impossibile trovare il file di configurazione " + DEPLOY_PROPERTIES_CLASSPATH);
		    }
		}
	    } catch (Exception e) {
		throw new RuntimeException("Errore nel caricamento del file " + DEPLOY_PROPERTIES_CLASSPATH, e);
	    } finally {
		try {
		    if (is != null) {
			is.close();
		    }
		} catch (Exception e) {
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
