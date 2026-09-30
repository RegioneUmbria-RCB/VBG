package it.gruppoinit.pal.gp.core.features.sistema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class SecurityUtils {

    private static final String CHARSET_UTF_8 = "UTF-8";

    private SecurityUtils() {

	initSecurityAppUrl();
    }

    private static final Logger logger = LoggerFactory.getLogger(SecurityUtils.class);
    private static final List<String> whiteList = Arrays.asList("windowed", "windowName", "WindowName");
    private static List<String> blacklist;
    private static Set<String> u;
    static {
	//
	blacklist = new ArrayList<String>();
	blacklist.add("<script");
	blacklist.add("</script");
	//
	blacklist.add("<iframe");
	blacklist.add("\\x3C\\x69\\x66\\x72\\x61\\x6d\\x65");
	blacklist.add("&#x3C;&#x69;&#x66;&#x72;&#x61;&#x6d;&#x65");
	//
	blacklist.add("</iframe");
	blacklist.add("\\x3C\\x2f\\x69\\x66\\x72\\x61\\x6d\\x65");
	blacklist.add("&#x3C;&#x2f;&#x69;&#x66;&#x72;&#x61;&#x6d;&#x65");
	//
	blacklist.add("<textarea");
	blacklist.add("<select");
	blacklist.add("<form");
	blacklist.add("<svg");
	blacklist.add("xss:");
	blacklist.add("expression");
	blacklist.add("alert(");
	blacklist.add("alert");
	blacklist.add("alert&#x28");
	blacklist.add("<input");
	blacklist.add("<img");
	blacklist.add("mouse");
	blacklist.add("eval(");
	blacklist.add("eval&#x28");
	blacklist.add("location");
	blacklist.add("window");
	blacklist.add("source");
	//
	blacklist.add("@import");
	blacklist.add("\\x40\\x69\\x6d\\x70\\x6f\\x72\\x74");
	blacklist.add("&#x40;&#x69;&#x6d;&#x70;&#x6f;&#x72;&#x74");
	blacklist.add("%3E%40import");
	blacklist.add("@import");
	//
	blacklist.add("<style");
	blacklist.add("\\x3C\\x73\\x74\\x79\\x6C\\x65");
	blacklist.add("&#x3c;&#x73;&#x74;&#x79;&#x6c;&#x65"); //</style
	//
	blacklist.add("</style");
	blacklist.add("\\x3C\\x2f\\x73\\x74\\x79\\x6C\\x65"); //</style
	blacklist.add("&#x3c;&#x2f;&#x73;&#x74;&#x79;&#x6c;&#x65"); //</style	
	//
	// blacklist.add("style"); non lo devo aggiungere in questo modo 
	blacklist.add("\\x73\\x74\\x79\\x6C\\x65"); //style
	blacklist.add("&#x73;&#x74;&#x79;&#x6c;&#x65"); //style
	//
	blacklist.add("%3E%3Cstyle");
	blacklist.add("%3C%2Fstyle");
	blacklist.add("%3C%00style");
	blacklist.add("\\x6a\\x61\\x76\\x61\\x73\\x63\\x72\\x69\\x70\\x74"); //javascript
	blacklist.add("&#x6a;&#x61;&#x76;&#x61;&#x73;&#x63;&#x72;&#x69;&#x70;&#x74"); //javascript
	blacklist.add("\\x61\\x6c\\x65\\x72\\x74"); // alert
	blacklist.add("&#x61;&#x6c;&#x65;&#x72;&#x74"); // alert
	blacklist.add("\\x65\\x76\\x61\\x6c"); // eval
	blacklist.add("&#x65;&#x76;&#x61;&#x6c"); // eval
	blacklist.add("\\x73\\x63\\x72\\x69\\x70\\x74"); // script
	blacklist.add("&#x73;&#x63;&#x72;&#x69;&#x70;&#x74"); // script
	blacklist.add("\\x73\\x6f\\x75\\x72\\x63\\x65"); // source
	blacklist.add("&#x73;&#x6f;&#x75;&#x72;&#x63;&#x65"); // source
	blacklist.add("\\x6d\\x6f\\x75\\x73\\x65"); //mouse
	blacklist.add("&#x6d;&#x6f;&#x75;&#x73;&#x65"); //mouse
	blacklist.add("&#x6d;&#x6f;&#x75;&#x73;&#x65"); //mouse
	// valueOf
	blacklist.add("valueOf");
	blacklist.add("\\x76\\X61\\X6c\\x75\\x65\\x4f\\x66");
	blacklist.add("valueof");
	blacklist.add("\\x76\\x61\\x6C\\x75\\x65\\x6F\\x66");
	// family
	// blacklist.add("family"); // non lo devo aggiungere in questo modo
	blacklist.add("-family");
	//
	List<String> blacklists = aggiungiABlackList();
	blacklist.addAll(blacklists);
    }

    public static List<String> getblackList() {

	return blacklist;
    }

    public static boolean isAttivaSicurezza() {

	boolean attivo = false;
	if (ORMHelper.getIdcomuneAlias() != null) {
	    IVerticalizzazioneParametriSistemaService verticalizzazioneParametriSistemaService = (IVerticalizzazioneParametriSistemaService) ContextLoader
		    .getCurrentWebApplicationContext()
		    .getBean("verticalizzazioneParametriSistemaServiceImpl", IVerticalizzazioneParametriSistemaService.class);
	    attivo = verticalizzazioneParametriSistemaService.attivaComportamentiSicurezza();
	}
	return attivo;
    }

    public static boolean verificaUrl(String url) {

	if (url.indexOf(".htm") < 0) {
	    logger.debug("url==>false: {} ", url);
	    return false;
	}
	if (u == null) {
	    initSecurityAppUrl();
	}
	for (String urlDaNonConsiderare : u) {
	    if (url.indexOf(urlDaNonConsiderare) >= 0) {
		logger.debug("url==>false: {} ", url);
		return false;
	    }
	}
	logger.debug("url==>true: {}", url);
	return true;
    }

    public static String decodificaParametro(String v) {

	String name = v.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
	String decode = URLDecoder.decode(name).toLowerCase();
	contieneEspressioniRegolari(decode);
	return decode.replace("\\", "");
    }

    /**
     * Accoda alla url il token e verifica
     * 
     * @param url
     * @param request
     * @return
     */
    public static String elaboraUrl(String url, HttpServletRequest request, boolean isAttivaSicurezza) {

	String lclurl = "";
	try {
	    lclurl = URLDecoder.decode(url, CHARSET_UTF_8);
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	String token = (String) request.getSession().getAttribute(WebConstants.TOKEN);
	if (lclurl.indexOf("?") >= 0) {
	    lclurl += "&";
	} else {
	    lclurl += "?";
	}
	lclurl += WebConstants.TOKEN + "=" + token;
	checkUrlEsterni(lclurl, isAttivaSicurezza);
	return lclurl;
    }

    public static void verificaParametroIdComuneAlias(String valore) {

	verificaParametro(WebConstants.IDCOMUNE_ALIAS, valore, 20);
    }

    private static void verificaParametroIdComuneAliasDaRequest(HttpServletRequest httpServletRequest) {

	verificaParametro(WebConstants.IDCOMUNE_ALIAS, httpServletRequest, 20);
    }

    private static void verificaParametro(String nomeParametro, String valore, int length) {

	if ((StringUtils.isNotBlank(valore) && !Utilities.validaTestoAlfanumerico(valore)) || StringUtils.defaultString(valore).length() > length) {
	    logger.error("ERRORE SICUREZZA: Parametro: {}, valore: {}", nomeParametro, valore);
	    throw new SecurityException("Intercettato parametro di input non corretto");
	}
    }

    private static void verificaParametro(String nomeParametro, HttpServletRequest httpServletRequest, int length) {

	String valore = httpServletRequest.getParameter(nomeParametro);
	verificaParametro(nomeParametro, valore, length);
    }

    public static void checkUrlEsterni(String url, boolean isAttivaSicurezza) {

	if (isAttivaSicurezza && StringUtils.defaultString(url).toLowerCase().startsWith("http")) {
	    String baseUrl = WebConstants.getSecurityParamValue(SecurityParams.BASE_URL);
	    if (StringUtils.isNotBlank(baseUrl)) {
		if (StringUtils.isNotBlank(url) && !url.toLowerCase().startsWith(baseUrl.toLowerCase())) {
		    throw new SecurityException("Non è possibile eseguire link esterni");
		}
	    } else {
		throw new SecurityException("Non è possibile eseguire link esterni");
	    }
	}
    }

    public static void sanificaRequest(HttpServletRequest httpServletRequest) {

	String url = StringUtils.defaultString(httpServletRequest.getRequestURI());
	verificaParametro(WebConstants.SOFTWARE, httpServletRequest, 2);
	verificaParametroIdComuneAliasDaRequest(httpServletRequest);
	verificaParametro("_timestamp", httpServletRequest, 30);
	verificaParametro(WebConstants.STATUS_MSG_PARAM_NAME, httpServletRequest, 6);
	if (!SecurityUtils.verificaUrl(url)) {
	    return;
	}
	if (isAttivaSicurezza()) {
	    Map params = httpServletRequest.getParameterMap();
	    Iterator i = params.keySet().iterator();
	    while (i.hasNext()) {
		String key = (String) i.next();
		String[] value = ((String[]) params.get(key));
		for (String v : value) {
		    if (StringUtils.isNotBlank(v)) {
			String v1 = decodificaParametro(v);
			//1. Verifico se in whitelist
			for (String w : whiteList) {
			    if (v1.indexOf(w) >= 0) {
				return;
			    }
			}
			//2. Verifico se in blackList
			for (String w : getblackList()) {
			    if (v1.indexOf(w) >= 0) {
				logger.debug("Parametro: {}, valore: {}", key, v1);
				if (!v1.equalsIgnoreCase(IVerticalizzazioneParametriSistemaService.PAR_NASCONDI_SCRIPT_LOCATION)
					&& v1.indexOf("externalresource") < 0) {
				    logger.error("Intercettato parametro di input non corretto {}, {}, {}",
					    new Object[] { value, url, httpServletRequest.getQueryString() });
				    throw new SecurityException("Intercettato parametro di input non corretto");
				}
			    }
			}
		    }
		}
	    }
	}
    }

    private static void contieneEspressioniRegolari(String url) {

	// Verifica se la url che ci viene passata contiene una codifica in caratteri ASCII
	Pattern bslash = Pattern.compile("\\\\[Xx][a-f0-9]{2}");
	Pattern eCom = Pattern.compile("\\&\\#[Xx][a-f0-9]{2}");
	Matcher matcherbS = bslash.matcher(url);
	Matcher matchereC = eCom.matcher(url);
	while (matcherbS.find() || matchereC.find()) {
	    String valore = "";
	    if (matchereC.find()) {
		valore = matchereC.group();
	    } else {
		valore = matcherbS.group();
	    }
	    logger.error("ERRORE SICUREZZA: carattere non valido {}", valore);
	    throw new SecurityException("Intercettato parametro di input non corretto.");
	}
    }

    public static void initSecurityAppUrl() {

	u = new HashSet<String>();
	try {
	    securityAppFilter();
	} catch (Exception e) {
	    logger.error("initSecurityAppUrl()", e);
	    u = getDefaultUrlDaNonConsiderare();
	}
    }

    private static void securityAppFilter() {

	InputStream in = null;
	try {
	    in = SecurityUtils.class.getClassLoader().getResourceAsStream("security.app.filter.url.txt");
	    BufferedReader br = new BufferedReader(new InputStreamReader(in, CHARSET_UTF_8));
	    String line = br.readLine();
	    while (line != null) {
		line = br.readLine();
		if (!StringUtils.defaultString(line).trim().isEmpty()) {
		    u.add(line);
		}
	    }
	} catch (Exception e) {
	    logger.error("initSecurityAppUrl# errore nella lettura del file security.app.filter.url.txt " + e.getMessage(), e);
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		    logger.error("initSecurityAppUrl# errore nella lettura del file security.app.filter.url.txt " + e.getMessage(), e);
		}
	    }
	}
    }

    // Legge dal file altri parametri non permessi nella url e li aggiunge alla blacklist
    private static List<String> aggiungiABlackList() {

	List<String> toBlacklist = new ArrayList<String>();
	InputStream blackList = null;
	try {
	    blackList = SecurityUtils.class.getClassLoader().getResourceAsStream("security.app.filter.blacklist.txt");
	    if (blackList != null) {
		BufferedReader br = new BufferedReader(new InputStreamReader(blackList, CHARSET_UTF_8));
		String line = br.readLine();
		while (line != null) {
		    line = br.readLine();
		    if (!StringUtils.defaultString(line).trim().isEmpty()) {
			toBlacklist.add(line);
		    }
		}
	    }
	} catch (Exception e) {
	    logger.error("initSecurityAppUrl# errore nella lettura del file security.app.filter.blacklist.txt " + e.getMessage(), e);
	} finally {
	    if (blackList != null) {
		try {
		    blackList.close();
		} catch (IOException e) {
		    logger.error("initSecurityAppUrl# errore nella lettura del file security.app.filter.blacklist.txt " + e.getMessage(), e);
		}
	    }
	}
	return toBlacklist;
    }

    private static Set<String> getDefaultUrlDaNonConsiderare() {

	Set<String> u = new HashSet<String>();
	u.add("/alberoproc/");
	u.add("/bachecalavoroconfigurazione/");
	u.add("/foarjsteps/");
	u.add("/history/");
	u.add("/import/");
	u.add("/info/");
	u.add("/infosuap/");
	u.add("/inventarioprocedimenti/");
	u.add("/mailtipo/");
	u.add("/modelli/");
	u.add("/movimentimail/");
	u.add("/news/");
	u.add("/normegenerali/");
	u.add("/pecinbox/");
	u.add("/protocollo/");
	u.add("/quesiti/");
	u.add("/tipiprocedure/");
	u.add("/upgrade/");
	u.add("/verticalizzazionibase/");
	return u;
    }
}
