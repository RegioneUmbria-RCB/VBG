package it.gruppoinit.pal.gp.core.constants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * Classe delle costanti e dei metodi da utilizzare per costruire le chiamate alle funzionalità di SigeproMS
 * 
 * @author fabrizioc
 * 
 */
public class BackofficeNETConstants {

    /**
     * ReturnTo<br />
     * (Costante da utilizzare per la composizione delle url per le chiamate alle funzionalità di SigeproMS.)
     */
    public static final String RETURNTO = "ReturnTo";
    /**
     * Software<br />
     * (Costante da utilizzare per la composizione delle url per le chiamate alle funzionalità di SigeproMS.)
     */
    public static final String SOFTWARE = "Software";

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASPNET()} + /cl_Istanzeonerilista.asp
     */
    public static String getURL_ISTANZEONERICOSTOCOSTRUZIONE() {

	return getURL_APP_ASPNET() + "/Istanze/CalcoloOneri/CostoCostruzione/CCICalcoliTot.aspx";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASPNET()} + /cl_Istanzeonerilista.asp
     */
    public static String getURL_ISTANZEONERICOSTOURBANIZZAZIONE() {

	return getURL_APP_ASPNET() + "/Istanze/CalcoloOneri/Urbanizzazione/OICalcoloTot.aspx";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASPNET()} + /cl_Istanzeonerilista.asp
     */
    public static String getURL_ISTANZEONERICANONI() {

	return getURL_APP_ASPNET() + "/Istanze/CalcoloCanoni/IstanzeCalcoloCanoni.aspx";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_Istanzeonerilista.asp
     */
    public static String getURL_ISTANZEONERIFIDEJUSSIONI() {

	return getURL_APP_ASP() + "/cl_IstanzeFidejussioniLista.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_Istanzeonerilista.asp
     */
    public static String getURL_ISTANZEONERILISTA() {

	return getURL_APP_ASP() + "/cl_Istanzeonerilista.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_IstanzeStampe.asp
     */
    public static String getURL_ISTANZESTAMPE() {

	return getURL_APP_ASP() + "/cl_IstanzeStampe.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_documentiTipoSelezione.asp
     */
    public static String getURL_MOV_DOC_TIPO() {

	return getURL_APP_ASP() + "/cl_documentiTipoSelezione.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_stampaletteretipo.asp
     */
    public static String getURL_STAMPA_LETTERE_TIPO() {

	return getURL_APP_ASP() + "/cl_stampaletteretipo.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_stampagraduatoria.asp
     */
    public static String getURL_STAMPA_GRADUATORIA() {

	return getURL_APP_ASP() + "/cl_stampagraduatoria.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_stampamassivamov.asp
     */
    public static String getURL_STAMPA_MASSIVA_MOV() {

	return getURL_APP_ASP() + "/cl_stampamassivamov.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /RTF_stampe/base.doc
     */
    public static String getURL_RTF_BASE_DOC() {

	return getURL_APP_ASP() + "/RTF_stampe/base.doc";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /Istanze/DatiDinamici/IstanzeDyn2Modelli.aspx
     */
    public static String getURL_ISTANZE_DYN2_MODELLI() {

	return getURL_APP_ASPNET() + "/Istanze/DatiDinamici/IstanzeDyn2Modelli.aspx";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /Istanze/DatiDinamici/AttivitaDyn2Modelli.aspx
     */
    public static String getURL_ATTIVITA_DYN2_MODELLI() {

	return getURL_APP_ASPNET() + "/Istanze/DatiDinamici/AttivitaDyn2Modelli.aspx";
    }

    /**
     * 
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /DatiDinamici/Mercati/mercati-dyn2-modelli.aspx
     */
    public static String getURL_MERCATI_DYN2_MODELLI() {

	return getURL_APP_ASPNET() + "/DatiDinamici/Mercati/mercati-dyn2-modelli.aspx";
    }

    /**
     * 
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /DatiDinamici/Mercati/posteggi-dyn2-modelli.aspx
     */
    public static String getURL_MERCATO_D_DYN2_MODELLI() {

	return getURL_APP_ASPNET() + "/DatiDinamici/Mercati/posteggi-dyn2-modelli.aspx";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_StampaMercati.asp
     */
    public static String getURL_STAMPA_MERCATI() {

	return getURL_APP_ASP() + "/cl_StampaMercati.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_ContabilitaStampaRagioneria.asp
     */
    public static String getURL_STAMPA_CONTABILITA_RAGIONERIA() {

	return getURL_APP_ASP() + "/cl_ContabilitaStampaRagioneria.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_ResponsabiliAooLista.asp
     */
    public static String getURL_RESP_AOO_LISTA() {

	return getURL_APP_ASP() + "/cl_ResponsabiliAooLista.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_TipiDocumentoStampe.asp
     */
    public static String getURL_STAMPE_TIPI_DOC() {

	return getURL_APP_ASP() + "/cl_TipiDocumentoStampe.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_movimentiAllegati.asp
     */
    public static String getURL_STAMPE_MOVIMENTO_CREA_ALLEGATO() {

	return getURL_APP_ASP() + "/cl_movimentiAllegati.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_sorteggiDocTipo.asp
     */
    public static String getURL_STAMPA_DOC_TIPO_SORTEGGIO() {

	return getURL_APP_ASP() + "/cl_sorteggiDocTipo.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_stampaIAttivita.asp
     */
    public static String getURL_STAMPA_IATTIVITA() {

	return getURL_APP_ASP() + "/cl_stampaIAttivita.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_IstanzeLavoriStampa.asp
     */
    public static String getURL_STAMPA_ISTANZELAVORIT() {

	return getURL_APP_ASP() + "/cl_IstanzeLavoriStampa.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /Archivi/DatiDinamici/Dyn2TipiMovimentoModelli.aspx
     */
    public static String getURL_DYN2_TIPIMOV_MODELLI() {

	return getURL_APP_ASPNET() + "/Archivi/DatiDinamici/Dyn2TipiMovimentoModelli.aspx";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /Istanze/DatiDinamici/AnagrafeDyn2Modelli.aspx
     */
    public static String getURL_SCHEDE_ANAGRAFE() {

	return getURL_APP_ASPNET() + "/Istanze/DatiDinamici/AnagrafeDyn2Modelli.aspx";
    }

    /**
     * {@link BackofficeNETConstants#getAPP_ASPNET()} + /Archivi/CalcoloOneri/TipiRateizzazione.aspx
     * 
     * @return
     */
    public static String getURL_ONERITIPIRATEIZZAZIONI() {

	return getURL_APP_ASPNET() + "/Archivi/CalcoloOneri/TipiRateizzazione.aspx";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_DocumentiTipoSelezione.asp
     */
    public static String getURL_STAMPA_COMMISSIONI_EDILIZIE_T() {

	return getURL_APP_ASP() + "/cl_DocumentiTipoSelezione.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_APP_ASP()} + /cl_StampaDocumentiTipoPerAnagrafe.asp
     */
    public static String getURL_STAMPA_DOCUMENTI_TIPO_PER_ANAGRAFE() {

	return getURL_APP_ASP() + "/cl_StampaDocumentiTipoPerAnagrafe.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_STAMPA_RICHIEDENTI_DOCUMENTI_TIPO()} + /cl_RichiedentiDocumentiTipo.asp
     */
    public static String getURL_STAMPA_RICHIEDENTI_DOCUMENTI_TIPO() {

	return getURL_APP_ASP() + "/cl_RichiedentiDocumentiTipo.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_STAMPA_LETTERE_TIPO_AUTORIZZAZIONI()} + /cl_LettereTipoElaborazione2.asp
     */
    public static String getURL_STAMPA_LETTERE_TIPO_AUTORIZZAZIONI() {

	return getURL_APP_ASP() + "/cl_LettereTipoElaborazione2.asp";
    }

    /**
     * {@link BackofficeNETConstants#getURL_STAMPA_LETTERE_TIPO_AUTORIZZAZIONI()} + /cl_LettereTipoElaborazione2.asp
     */
    public static String getURL_STAMPA_PROVVEDIMENTI_AUTORIZZATIVI() {

	return getURL_APP_ASP() + "/cl_ProvvedimentiStampa.asp";
    }

    /*---------------web service-------------*/
    /**
     * 
     * URI del web service per la gestione delle anagrafiche
     */
    public static String getURL_WS_ANAGRAFE() {

	return WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_ASPNET) +
		"/webServices/wssigeproanagrafe/WsAnagrafe2.asmx?wsdl";
    }

    /**
     * URI del web service per elaborare le formule delle schede istanza<b>getWsHostUrl() +
     * "/WebServices/WsSIGePro/Wcf/ElaborazioneMassiva/ElaborazioneMassivaSchedeIstanza.svc?wsdl"</b>
     * 
     * @return
     */
    public static String getUrlWsElaborazioneMassiva() {

	return WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_ASPNET) +
		"/WebServices/WsSIGePro/Wcf/ElaborazioneMassiva/ElaborazioneMassivaSchedeIstanza.svc?wsdl";
    }

    /**
     * Uri del web service per il recupero della lista delle stampanti
     * 
     * @return
     */
    public static String getURL_WS_LISTASTAMPANTI() {

	return WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_ASPNET) + "/WebServices/WsSIGePro/ListaStampanti.asmx";
    }

    /**
     * 
     * URI del web service per la gestione delle formule schede dinamiche
     */
    public static String getURL_WS_SCHEDE_DINAMICHE() {

	return WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_ASPNET) +
		"/WebServices/WsSIGePro/SchedeDinamicheWebService.asmx";
    }

    /**
     * {@link BackofficeNETConstants#getURL_GENERA_STAMPA()} + /generastampa.asp
     */
    public static String getURL_GENERA_STAMPA() {

	return getURL_APP_ASP() + "/generastampa.asp";
    }

    /**
     * Metodo per il recupero dell'url dell'host per le chiamate ai web service di SigeproMS.<br />
     * es. http://devel3/aspnet/ <br />
     * 
     * Il metodo recupera il valore contattando il RegistryService. La chiamata è eseguita solamente la prima volta per
     * popolare la variabile statica interna {@link BackofficeNETConstants#WS_HOST_URL}.<br />
     * Per le chiamate successive è restituito il valore di tale variabile
     * 
     * @return
     */
    /*
    public  static String getWsHostUrl() {
    
    return WebConstants.getSecurityParamValue(WebConstants.SecurityParams.WSHOSTURL_ASPNET);
    }
    */
    public static String getBASE_URL() {

	return WebConstants.getSecurityParamValue(WebConstants.SecurityParams.BASE_URL);
    }

    private static String getAPP_ASPNET() {

	return WebConstants.getSecurityParamValue(WebConstants.SecurityParams.APP_ASPNET);
    }

    public static String getURL_APP_ASPNET() {

	if (StringUtils.isNotBlank(getAPP_ASPNET())) {
	    return getBASE_URL() + "/" + getAPP_ASPNET();
	} else {
	    return getBASE_URL();
	}
    }

    public static String getURL_APP_ASP() {

	String appAsp = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.APP_ASP);
	if (StringUtils.isNotBlank(appAsp)) {
	    return getBASE_URL() + "/" + appAsp;
	} else {
	    return getBASE_URL();
	}
    }

    /**
     * Metodo per la creazione dell'url per le chiamate a funzionalità di SigeproMS da pagine JSP.<br />
     * <b>UTILIZZATO COME TAG: il tld si trova in WEB-INF/init_tags/custom.tld</b>
     * 
     * @param request
     *            request http
     * @param uriTo
     *            funzione di SigeproMS da richiamare comprensiva di context path.<br />
     *            es. String urlIstanza = BackofficeNETConstants.getURL_ISTANZE();
     * @param uriBack
     *            link di ritorno (utilizzare l'attributo della request _urlback se settato dalla pagina
     *            history.jsp).<br />
     *            se null o stringa vuota allora è settato a "/"
     * @param software
     *            software della funzione di destinazione, se null o stringa vuota si utilizza quello della session
     * 
     * @param isPopup
     *            se true allora non utilizza il decorator, serve per le chiamate window.open
     * @return restituisce un url da utilizzare come valore per attributi href dei link
     * 
     */
    public static String getUrlTo(HttpServletRequest request, String uriTo, String uriBack, String software, boolean isPopup) {

	return getUrlToInternal(request, uriTo, uriBack, software, isPopup, false, false);
    }

    /**
     * Funzione per usare il normale URLto e forzare il popup decorator
     * 
     * @param request
     * @param uriTo
     * @param uriBack
     * @param software
     * @param isPopup
     * @return
     */
    public static String getUrlToPopupdecorator(HttpServletRequest request, String uriTo, String uriBack, String software) {

	return getUrlToInternal(request, uriTo, uriBack, software, false, false, true);
    }

    /**
     * @param request
     * @param uriTo
     * @param uriBack
     * @param software
     * @param isPopup
     * @return
     */
    private static String getUrlToInternal(HttpServletRequest request, String uriTo, String uriBack, String software, boolean isPopup, boolean isPost,
	    boolean forcePopupdecorator) {

	// FIXME recuperare la BASE_URL da sigeprosecurity per comporre la url a sigeproMS
	String urlTo = "";
	String externalResourceUrl = "../externalresource/goTo.htm?url=";
	if (isPopup) {
	    externalResourceUrl = "../externalresource/goToPopup.htm?url=";
	}
	if (forcePopupdecorator) {
	    externalResourceUrl = "../externalresource/goToPopup.htm?url=";
	}
	String qm = "?";
	if (uriTo.indexOf(qm) > -1) {
	    qm = "&";
	}
	if (StringUtils.isBlank(software)) {
	    software = (String) request.getSession(false).getAttribute(WebConstants.SOFTWARE);
	}
	String suffix = "&" + WebConstants.IS_POST_ACTION + "=true";
	try {
	    if (!isPopup) {
		// url che passo al sistema esterno con la quale richiamare sigepro2
		String historyBackUrl = getBASE_URL() + request.getContextPath() + "/history/back.htm?" + WebConstants.GOTO + "=%2F";
		String historyBackUrlEncoded = URLEncoder.encode(historyBackUrl, "UTF-8");
		historyBackUrlEncoded = URLEncoder.encode(historyBackUrlEncoded, "UTF-8");
		String returnTo = RETURNTO + "=" + historyBackUrlEncoded;
		// accodo alla url passata al metodo che corrisponde alla funzionalità da richiamare, la return_to e il
		// software
		uriTo += qm + returnTo + "&" + SOFTWARE + "=" + software;
		// eseguo l'encoding perchè l'url deve transitare per l'ExternalResourceController
		String uriToEncoded = URLEncoder.encode(uriTo, "UTF-8");
		externalResourceUrl += uriToEncoded;
		if (isPost) { // accodo il suffisso per mandare in POST
		    externalResourceUrl += suffix;
		}
		String externalResourceUrlEncoded = URLEncoder.encode(externalResourceUrl, "UTF-8");
		// externalResourceUrlEncoded = URLEncoder.encode(externalResourceUrlEncoded, "UTF-8");
		// eseguo l'encoding della uriBack che devo inserire nell'history back
		String uriBackEncoded = "%2F";
		if (StringUtils.isNotBlank(uriBack)) {
		    uriBackEncoded = URLEncoder.encode(uriBack, "UTF-8");
		}
		uriBackEncoded = URLEncoder.encode(uriBackEncoded, "UTF-8");
		String historySetUrl = "../history/set.htm?ReturnTo=" + uriBackEncoded + "&" + WebConstants.GOTO + "=" + externalResourceUrlEncoded;
		urlTo = historySetUrl;
	    } else {
		// se la chiamata è in popup non passo per l'history controller e non accodo in query string la
		// return_to		
		uriTo += qm + SOFTWARE + "=" + software;
		String uriToEncoded = URLEncoder.encode(uriTo, "UTF-8");
		externalResourceUrl += uriToEncoded;
		if (isPost) { // accodo il suffisso per mandare in POST
		    externalResourceUrl += suffix;
		}
		urlTo = externalResourceUrl;
	    }
	} catch (UnsupportedEncodingException e) {
	    System.err.println("BackofficeNETConstants getUrlTo error: " + e.getMessage());
	}
	return urlTo;
    }

    /**
     * Metodo per la creazione dell'url per le chiamate a funzionalità di SigeproMS da pagine JSP. Come comportamento
     * aggiuntivo prende i parametri dalla request ed esegue una autopost sulla url tornata.<br />
     * Il metodo usa {@link BackofficeNETConstants}.
     * {@link #getUrlTo(HttpServletRequest, String, String, String, boolean)} ed accoda il parametro che permette ad
     * ExternalResourceController di scegliere la pagina che fa il post piuttosto che quella della normale esecuzione.
     * 
     */
    public static String getUrlToPost(HttpServletRequest request, String uriTo, String uriBack, String software, boolean isPopup) {

	return getUrlToInternal(request, uriTo, uriBack, software, isPopup, true, false);
    }
}
