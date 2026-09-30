package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * threadlocal utilizzato per contenere la proprietà idcomune e software da collegare ad una sessione di navigazione
 * utente
 * 
 * @author fabrizioc
 * 
 */
public class ORMHelper {

    private static ThreadLocal<String> helperIdente = new ThreadLocal<String>();
    private static ThreadLocal<String> helperIdComune = new ThreadLocal<String>();
    private static ThreadLocal<String> helperIdComuneAlias = new ThreadLocal<String>();
    private static ThreadLocal<String> helperIdComunebase = new ThreadLocal<String>();
    private static ThreadLocal<String> helperSoftware = new ThreadLocal<String>();
    private static ThreadLocal<String> helperHibernateSFKey = new ThreadLocal<String>();
    private static ThreadLocal<String> helperToken = new ThreadLocal<String>();

    public static String getIdente() {

	return helperIdente.get();
    }

    public static void setIdente(String idente) {

	helperIdente.set(idente);
    }

    public static String getIdcomune() {

	return helperIdComune.get();
    }

    public static void setIdcomune(String idcomune) {

	helperIdComune.set(idcomune);
    }

    public static String getIdcomuneAlias() {

	return helperIdComuneAlias.get();
    }

    public static void setIdcomuneAlias(String idcomuneAlias) {

	helperIdComuneAlias.set(idcomuneAlias);
    }

    public static String getSoftware() {

	return helperSoftware.get();
    }

    public static void setSoftware(String software) {

	helperSoftware.set(software);
    }

    public static String getHibernateSFKeyUrl() {

	return helperHibernateSFKey.get();
    }

    public static void setHibernateSFKey(String hibernateSFKey) {

	helperHibernateSFKey.set(hibernateSFKey);
    }

    public static String getToken() {

	return helperToken.get();
    }

    public static void setToken(String token) {

	helperToken.set(token);
    }

    public static String getIdcomunebase() {

	return helperIdComunebase.get();
    }

    public static void setIdcomunebase(String idcomunebase) {

	helperIdComunebase.set(idcomunebase);
    }

    public static String getHibernateKeyFromProps(Properties props) {

	return props.getProperty(WebConstants.HIBERNATE_CONN_URL) + "@" + props.getProperty(WebConstants.HIBERNATE_DEFAULT_SCHEMA);
    }

    public static boolean isConsoleRegionale() {

	String idcomune = StringUtils.defaultString(getIdcomune());
	String idcomunebase = StringUtils.defaultString(getIdcomunebase());
	return idcomune.equalsIgnoreCase(idcomunebase);
    }

    public static boolean isConsoleLocale() {

	return isConsoleRegionale() ? false : true;
    }

    public static void destroyORMHelper() {

	ORMHelper.setIdcomune(null);
	ORMHelper.setIdcomuneAlias(null);
	ORMHelper.setSoftware(null);
	ORMHelper.setHibernateSFKey(null);
	ORMHelper.setToken(null);
	ORMHelper.setIdente(null);
	ORMHelper.setIdcomunebase(null);
    }
}
