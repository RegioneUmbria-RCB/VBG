package it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Layouttesti;
import it.gruppoinit.pal.gp.core.domain.Layouttestibase;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;

/**
 * Classe per il caricamento delle label dalle tabelle LAYOUTTESTI e LAYOUTTESTIBASE
 * 
 */
public class LayoutTestiMessageSource extends AbstractMessageSource implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(LayoutTestiMessageSource.class);
    private LayouttestibaseService layouttestibaseService;
    private LayouttestiService layouttestiService;

    @Autowired
    public void setLayouttestibaseService(LayouttestibaseService layouttestibaseService) {

	this.layouttestibaseService = layouttestibaseService;
    }

    @Autowired
    public void setLayouttestiService(LayouttestiService layouttestiService) {

	this.layouttestiService = layouttestiService;
    }

    /**
     * how long the messages will be cached (before timestamps will be checked) <br/>
     * a value below zero means "cache forever"
     */
    private long cacheMillis = 0;
    /** timestamp: when messages were loaded for the last time */
    private long loadTimestamp = -1;
    /** timestamp: when messages were last updated according to messageReader */
    private long lastUpdate = -1;
    /**
     * Cache holding already generated MessageFormats per message code and Locale <br/>
     * Map <String, Map <Locale, MessageFormat&gt;&gt;
     */
    private final Map<String, Map<Locale, MessageFormat>> cachedMessageFormats = new HashMap<String, Map<Locale, MessageFormat>>();
    /**
     * all messages (for all basenames) per locale <br/>
     * Map <Locale, properties&gt;
     */
    private Map<Locale, Properties> cachedMergedProperties = new HashMap<Locale, Properties>();
    private Map<String, Properties> cachedOverridedProperties = new HashMap<String, Properties>();
    private Locale fallbackLocale = Locale.ITALIAN;

    public void init() {

	readFromDB();
	// refreshIfNecessary();
    }

    public void afterPropertiesSet() throws Exception {

    }

    /**
     * set the Locale to fallback to when no match is found <br/>
     * <br/>
     * set to null if you do not want a fallback Locale <br/>
     * default is Locale.ITALIAN
     * 
     * @param fallbackLocale
     *            the locale to fallback to when no match is found
     */
    public void setFallbackLocale(Locale fallbackLocale) {

	this.fallbackLocale = fallbackLocale;
    }

    /**
     * set number of seconds to cache the messages
     * <ul>
     * <li>Default is "-1", indicating to cache forever
     * <li>A positive number will cache loaded messages for the given number of seconds. This is essentially the
     * interval between refresh attempts. Note that a refresh attempt will first check the last-modified timestamp using
     * getLastUpdate
     * <li>A value of "0" will check the last-modified timestamp on every message access. <b>Do not use this in a
     * production environment!</b>
     * </ul>
     * 
     * @see #getLastUpdate
     */
    public void setCacheSeconds(int cacheSeconds) {

	this.cacheMillis = cacheSeconds * 1000;
    }

    /**
     * This method should return the timestamp when messages were last updated. <br/>
     * The default implementation always returns -1, which will prevent refreshing. <br/>
     * sub-classes should override this method when refreshing is desired
     * 
     * @return -1
     */
    public long getLastUpdate() {

	return this.lastUpdate;
    }

    protected synchronized MessageFormat resolveCode(String code, Locale locale) {

	refreshIfNecessary();
	String _code = code + "_" + ORMHelper.getSoftware();
	MessageFormat messageFormat = getMessageFormat(_code, locale);
	if (messageFormat != null) {
	    if (logger.isInfoEnabled()) {
		logger.info("resolved [" + _code + "] for locale [" + locale + "]");
	    }
	    return messageFormat;
	}
	_code = code + "_" + WebConstants.SOFTWARE_TT;
	messageFormat = getMessageFormat(_code, locale);
	if (messageFormat != null) {
	    if (logger.isInfoEnabled()) {
		logger.info("resolved [" + _code + "] for locale [" + locale + "]");
	    }
	    return messageFormat;
	}
	Locale[] locales = getAlternativeLocales(locale);
	for (int i = 0; i < locales.length; i++) {
	    _code = code + "_" + ORMHelper.getSoftware();
	    messageFormat = getMessageFormat(_code, locales[i]);
	    if (messageFormat != null) {
		if (logger.isInfoEnabled()) {
		    logger.info("resolved [" + _code + "] for locale [" + locales[i] + "]");
		}
		return messageFormat;
	    }
	    _code = code + "_" + WebConstants.SOFTWARE_TT;
	    messageFormat = getMessageFormat(_code, locales[i]);
	    if (messageFormat != null) {
		if (logger.isInfoEnabled()) {
		    logger.info("resolved [" + _code + "] for locale [" + locales[i] + "]");
		}
		return messageFormat;
	    }
	}
	if (logger.isInfoEnabled()) {
	    logger.info("could not resolve [" + _code + "] for locale [" + locale + "]");
	}
	return null;
    }

    protected synchronized String resolveCodeWithoutArguments(String code, Locale locale) {

	refreshIfNecessary();
	String msg = internalResolveCodeWithoutArguments(code, locale);
	// MOD
	String extMsg = internalResolveOverridedCodeWithoutArguments(code, locale);
	if (extMsg != null) {
	    msg = extMsg;
	}
	return msg;
    }

    private String internalResolveCodeWithoutArguments(String code, Locale locale) {

	refreshIfNecessary();
	String _code = code + "_" + ORMHelper.getSoftware();
	String msg = getMessages(locale).getProperty(_code);
	if (msg != null) {
	    if (logger.isInfoEnabled()) {
		logger.info("resolved [" + _code + "] without arguments for locale [" + locale + "] => [" + msg + "]");
	    }
	    return msg;
	} else {
	    _code = code + "_" + WebConstants.SOFTWARE_TT;
	    msg = getMessages(locale).getProperty(_code);
	    if (msg != null) {
		if (logger.isInfoEnabled()) {
		    logger.info("resolved [" + _code + "] without arguments for locale [" + locale + "] => [" + msg + "]");
		}
		return msg;
	    }
	    Locale[] locales = getAlternativeLocales(locale);
	    _code = code + "_" + ORMHelper.getSoftware();
	    for (int i = 0; i < locales.length; i++) {
		msg = getMessages(locales[i]).getProperty(_code);
		if (msg != null) {
		    if (logger.isInfoEnabled()) {
			logger.info("resolved [" + _code + "] without arguments for locale [" + locale + "] => [" + msg + "]");
		    }
		    return msg;
		}
	    }
	    _code = code + "_" + WebConstants.SOFTWARE_TT;
	    for (int i = 0; i < locales.length; i++) {
		msg = getMessages(locales[i]).getProperty(_code);
		if (msg != null) {
		    if (logger.isInfoEnabled()) {
			logger.info("resolved [" + _code + "] without arguments for locale [" + locale + "] => [" + msg + "]");
		    }
		    return msg;
		}
	    }
	}
	if (msg == null) {
	    if (logger.isInfoEnabled()) {
		_code = code + "_" + ORMHelper.getSoftware();
		logger.info("could not resolve [" + _code + "] without arguments for locale [" + locale + "]");
	    }
	}
	return null;
    }

    private String internalResolveOverridedCodeWithoutArguments(String code, Locale locale) {

	String key = code + "_" + ORMHelper.getIdcomune() + "_" + ORMHelper.getSoftware() + "_" + locale.toString();
	String msg = null;
	if (cachedOverridedProperties.get(ORMHelper.getIdcomune()) != null) {
	    msg = ((Properties) cachedOverridedProperties.get(ORMHelper.getIdcomune())).getProperty(key);
	    if (msg == null) {
		key = code + "_" + ORMHelper.getIdcomune() + "_" + WebConstants.SOFTWARE_TT + "_" + locale.toString();
		msg = ((Properties) cachedOverridedProperties.get(ORMHelper.getIdcomune())).getProperty(key);
	    }
	}
	if (msg != null) {
	    if (logger.isInfoEnabled()) {
		logger.info("override code [" + code + "] with [" + key + "] => [" + msg + "]");
	    }
	}
	return msg;
    }

    public void refreshIfNecessary() {

	long now = System.currentTimeMillis();
	if (loadTimestamp < 0) {
	    if (logger.isDebugEnabled()) {
		logger.debug("read messages for the first time, call readFromDB().");
	    }
	    // loadMessages for the first time
	    readFromDB();
	    this.loadTimestamp = now;
	    this.lastUpdate = now;
	    return;
	}
	if (cacheMillis < 0) {
	    if (logger.isDebugEnabled()) {
		logger.debug("cacheMillis is set to " + cacheMillis + " so never refresh from DB.");
	    }
	    return;
	}
	if (now > this.lastUpdate + this.cacheMillis) {
	    if (logger.isDebugEnabled()) {
		logger.debug("refresh messages from DB, call readFromDB().");
	    }
	    this.lastUpdate = now;// lastUpdate;
	    readFromDB();
	}
    }

    /**
     * create a locale from given values, supporting null-values for country and variant
     * 
     * @param language
     *            language to construct Locale for
     * @param country
     *            country to construct Locale for
     * @param variant
     *            variant to construct Locale for
     * @return a Locale object
     * @throws NullPointerException
     *             if language is null
     */
    private Locale createLocale(String language, String country, String variant) {

	if (country == null)
	    return new Locale(language);
	if (variant == null)
	    return new Locale(language, country);
	return new Locale(language, country, variant);
    }

    /**
     * build an array of alternative locales for the given locale <br/>
     * result does not contain original locale
     * 
     * @param locale
     *            the locale to find alternatives for
     * @return an array of alternative locales
     */
    private Locale[] getAlternativeLocales(Locale locale) {

	Locale[] locales = new Locale[3];
	int count = 0;
	if (locale.getVariant().length() > 0) {
	    // add a locale without the variant
	    locales[count] = new Locale(locale.getLanguage(), locale.getCountry());
	    count++;
	}
	if (locale.getCountry().length() > 0) {
	    // add a locale without the country
	    locales[count] = new Locale(locale.getLanguage());
	    count++;
	}
	if (fallbackLocale != null) {
	    locales[count] = fallbackLocale;
	}
	return locales;
    }

    /**
     * get the messages for the given locale, creating a new Properties object if necessary
     * 
     * @param locale
     *            the locale to find messages for
     * @return a Properties object
     */
    private Properties getMessages(Locale locale) {

	Properties messages = (Properties) cachedMergedProperties.get(locale);
	if (messages == null) {
	    messages = new Properties();
	    cachedMergedProperties.put(locale, messages);
	}
	return messages;
    }

    /**
     * stores a message in our internal data structures
     * 
     * @param code
     *            the code of the message to store
     * @param language
     *            the language of the message (required)
     * @param country
     *            the country of the message (optional, may be null)
     * @param variant
     *            the variant of the message (optional, may be null)
     * @param message
     *            the actual message
     */
    private void mapMessage(String code, String language, String country, String variant, String message) {

	Locale locale = createLocale(language, country, variant);
	Properties messages = getMessages(locale);
	if (code == null || code.trim() == "") {
	    logger.error("mapMessage: CODICETESTO IS EMPTY, layouttestibase TESTO=[" + message + "]  CODICETESTO=[" + code + "] and locale ["
		    + locale + "]");
	}
	if (message == null) {
	    logger.warn("mapMessage: TESTO IS NULL, layouttestibase TESTO=[" + message + "] CODICETESTO=[" + code + "] and locale [" + locale + "]");
	    message = "";
	}
	if (logger.isDebugEnabled()) {
	    logger.debug("adding layouttestibase TESTO=[" + message + "] CODICETESTO=[" + code + "] and locale [" + locale + "]");
	}
	messages.setProperty(code, message);
    }

    private MessageFormat getMessageFormat(String code, Locale locale) {

	Map<Locale, MessageFormat> localeMap = (Map<Locale, MessageFormat>) this.cachedMessageFormats.get(code);
	if (localeMap != null) {
	    MessageFormat result = (MessageFormat) localeMap.get(locale);
	    if (result != null) {
		return result;
	    }
	}
	String msg = getMessages(locale).getProperty(code);
	if (msg != null) {
	    if (localeMap == null) {
		localeMap = new HashMap<Locale, MessageFormat>();
		this.cachedMessageFormats.put(code, localeMap);
	    }
	    MessageFormat result = createMessageFormat(msg, locale);
	    localeMap.put(locale, result);
	    return result;
	}
	return null;
    }

    protected final void readFromDB() {

	readMessages();
	readOverridedMessage();
    }

    private void readMessages() {

	if (logger.isDebugEnabled()) {
	    logger.debug("read layouttestibase from DB");
	}
	long startTime = System.currentTimeMillis();
	cachedMergedProperties.clear();
	cachedMessageFormats.clear();
	List<Layouttestibase> msgs = layouttestibaseService.findAll(null, null);
	for (Layouttestibase msg : msgs) {
	    mapMessage(msg.getId().getCodicetesto() + "_" + msg.getId().getSoftware(), "it", "", "", msg.getTesto());
	}
	long millis = System.currentTimeMillis() - startTime;
	if (logger.isDebugEnabled()) {
	    logger.debug("read layouttestibase took " + millis + " millis");
	}
    }

    private void readOverridedMessage() {

	if (logger.isDebugEnabled()) {
	    logger.debug("read layouttesti from DB");
	}
	long startTime = System.currentTimeMillis();
	cachedOverridedProperties.remove((String) ORMHelper.getIdcomune());
	Properties extMessages = new Properties();
	List<Layouttesti> msgsExt = layouttestiService.findAll(null, null);
	for (Layouttesti msgExt : msgsExt) {
	    Locale locale = createLocale("it", "", "");
	    if (logger.isDebugEnabled()) {
		logger.debug("adding layouttesti [" + msgExt.getNuovotesto() + "] for code [" + msgExt.getId().getCodicetesto() + "_"
			+ msgExt.getId().getIdcomune() + "_" + msgExt.getId().getSoftware() + "_" + locale.toString() + "]");
	    }
	    if (msgExt.getNuovotesto() != null) {
		extMessages.setProperty(msgExt.getId().getCodicetesto() + "_" + msgExt.getId().getIdcomune() + "_" + msgExt.getId().getSoftware()
			+ "_" + locale.toString(), msgExt.getNuovotesto());
	    }
	}
	cachedOverridedProperties.put(ORMHelper.getIdcomune(), extMessages);
	long millis = System.currentTimeMillis() - startTime;
	if (logger.isDebugEnabled()) {
	    logger.debug("read layouttesti took " + millis + " millis");
	}
    }
}
