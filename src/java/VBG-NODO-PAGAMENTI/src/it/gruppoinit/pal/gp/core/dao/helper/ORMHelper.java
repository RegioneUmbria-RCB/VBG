package it.gruppoinit.pal.gp.core.dao.helper;

/**
 * threadlocal utilizzato per contenere la proprietà idcomune e software da
 * collegare ad una sessione di navigazione utente
 * 
 * @author fabrizioc
 * 
 */
public class ORMHelper {

    private static ThreadLocal<String> helperIdComune = new ThreadLocal<String>();
    private static ThreadLocal<String> helperIdComuneAlias = new ThreadLocal<String>();
    private static ThreadLocal<String> helperSoftware = new ThreadLocal<String>();
    private static ThreadLocal<String> helperCodiceComune = new ThreadLocal<String>();
    private static ThreadLocal<String> helperHibernateSFKey = new ThreadLocal<String>();
    private static ThreadLocal<String> helperToken = new ThreadLocal<String>();

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

    public static String getCodiceComune() {

	return helperCodiceComune.get();
    }

    public static void setCodiceComune(String cCom) {

	helperCodiceComune.set(cCom);
    }

    /*
     * public static String getHibernateKeyFromProps(Properties props) {
     * 
     * return props.getProperty(WebConstants.HIBERNATE_CONN_URL) + "@" +
     * props.getProperty(WebConstants.HIBERNATE_DEFAULT_SCHEMA); }
     */
    
    public static void destroyORMHelper() {

	ORMHelper.setIdcomune(null);
	ORMHelper.setIdcomuneAlias(null);
	ORMHelper.setSoftware(null);
	ORMHelper.setHibernateSFKey(null);
	ORMHelper.setToken(null);
	ORMHelper.setCodiceComune(null);
    }
}
