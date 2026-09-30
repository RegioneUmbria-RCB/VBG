package com.paevolution.appiogateway.utils;

public class WebConstants {

    // TODO: URI dei servizi esposti da IO Italia, se in futuro li cambiano
    // si può pensare di spostarli nel file di configurazione application.yml
    public static final String PROFILES_URI = "/profiles";
    public static final String MESSAGES_URI = "/messages";
    public static final String SUBSCRIPTIONS_FEED_URI = "/subscriptions-feed";
    public static final String FISCAL_CODE_PARAM = "/{fiscal_code}";
    public static final String FISCAL_CODE_ID_TRANSAZIONE_PARAMS = "/{fiscal_code}/{id}";
    public static final String DATE_PARAM = "/{date}";
    //
    // URI con parametri
    public static final String PROFILES_URI_WITH_FISCAL_CODE_PARAM = PROFILES_URI + FISCAL_CODE_PARAM;
    public static final String MESSAGES_URI_WITH_FISCAL_CODE_PARAM = MESSAGES_URI + FISCAL_CODE_PARAM;
    public static final String MESSAGES_URI_WITH_FISCAL_CODE_AND_ID_PARAMS = MESSAGES_URI + FISCAL_CODE_ID_TRANSAZIONE_PARAMS;
    public static final String SUBSCRIPTIONS_FEED_URI_WITH_DATE_PARAM = SUBSCRIPTIONS_FEED_URI + DATE_PARAM;
    //
    public static final String DEFAULT_CLIENT_REGISTRATON_ID = "ioproxy";
    public static final String BASIC_AUTHENTICATION_HEADER = "Basic ";
    public static final String BEARER_AUTHENTICATION_HEADER = "Bearer ";
    public static final String AUTHORIZATION_KEY_HEADER = "Authorization";
    public static final String OCP_APIM_SUBSCRIPTION_KEY_HEADER = "Ocp-Apim-Subscription-Key";
    /// IOPROXY: URL ENDPOINT GATEWAY ESTERNO (AMBIENTE DI SANDBOX)
    public static final String IOAPI_UD_SANDBOX_BASE_URL = "https://api.regione.umbria.it:443/ioproxy/1.0.0";
    public static final String IOAPI_MESSAGES_SERVICE_URL_VIA_IOPROXY_SANDBOX = IOAPI_UD_SANDBOX_BASE_URL + "/messages";
    public static final String IOAPI_PROFILES_SERVICE_URL_VIA_IOPROXY_SANDBOX = IOAPI_UD_SANDBOX_BASE_URL + "/profiles";
    public static final String IOAPI_SUBSCRIPTIONS_FEED_SERVICE_URL_VIA_IOPROXY_SANDBOX = IOAPI_UD_SANDBOX_BASE_URL + "/subscriptions-feed";
    /// https://api.regione.umbria.it:443/ioproxy/1.0.0
    public static final String UD_CONNECTOR = "UD";
    public static final String IO_CONNECTOR = "IOITALIA";
}
