package it.gruppoinit.pal.gp.backoffice.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.features.sistema.ITipologiaFunionalitaService;
import it.gruppoinit.pal.gp.core.features.sistema.IVerticalizzazioneTipoInstallazioneService;
import it.gruppoinit.pal.gp.core.features.sistema.TecnologiaPaginaEnum;

public class ApplicationLinkUtil {

    private static ITipologiaFunionalitaService tipologiaFunionalitaService = null;

    public static String getLinkSchede(HttpServletRequest request, String uriBack, String software, boolean isPopup, Integer codiceIstanza) {

	return getLinkSchede(request, uriBack, software, isPopup, codiceIstanza, null);
    }

    public static String getLinkSchede(HttpServletRequest request, String uriBack, String software, boolean isPopup, Integer codiceIstanza,
	    Integer codiceMovimento) {

	StringBuilder sbJava = new StringBuilder("../istanzedyn2dati/viewModelliIstanza.htm?codiceIstanza=").append(codiceIstanza);
	StringBuilder sbNet = new StringBuilder(BackofficeNETConstants.getURL_ISTANZE_DYN2_MODELLI()).append("?CodiceIstanza=").append(codiceIstanza);
	if (codiceMovimento != null) {
	    sbJava.append("&codiceMovimento=").append(codiceMovimento);
	    sbNet.append("&CodiceMovimento=").append(codiceMovimento);
	}
	if (isExternalPage(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_SCHEDEDINAMICHE)) {
	    return BackofficeNETConstants.getUrlTo(request, sbNet.toString(), uriBack, software, isPopup);
	} else {
	    return getUrl(request, sbJava.toString(), uriBack, software, isPopup);
	}
    }

    public static String getLinkSchedeAttivita(HttpServletRequest request, String uriBack, String software, boolean isPopup, Integer codiceAttivita) {

	StringBuilder sbJava = new StringBuilder("../iattivitadyn2dati/viewModelliAttivita.htm?codiceIstanza=").append(codiceAttivita);
	StringBuilder sbNet = new StringBuilder(BackofficeNETConstants.getURL_ATTIVITA_DYN2_MODELLI()).append("?CodiceAttivita=")
		.append(codiceAttivita);
	if (isExternalPage(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_SCHEDEDINAMICHE)) {
	    return BackofficeNETConstants.getUrlTo(request, sbNet.toString(), uriBack, software, isPopup);
	} else {
	    return getUrl(request, sbJava.toString(), uriBack, software, isPopup);
	}
    }

    public static String getLinkSchedeMercati(HttpServletRequest request, String uriBack, String software, boolean isPopup, Integer codiceMercato) {

	StringBuilder sbNet = new StringBuilder(BackofficeNETConstants.getURL_MERCATI_DYN2_MODELLI()).append("?CodiceMercato=").append(codiceMercato);
	//.append("&Software=").append(software);
	return BackofficeNETConstants.getUrlTo(request, sbNet.toString(), uriBack, software, isPopup);
    }

    public static String getLinkSchedePosteggi(HttpServletRequest request, String uriBack, String software, boolean isPopup,
	    Integer codicePosteggio) {

	StringBuilder sbNet = new StringBuilder(BackofficeNETConstants.getURL_MERCATO_D_DYN2_MODELLI()).append("?IdPosteggio=")
		.append(codicePosteggio);
	//.append("&Software=").append(software);
	return BackofficeNETConstants.getUrlTo(request, sbNet.toString(), uriBack, software, isPopup);
    }

    public static String getLinkStampeIstanza(HttpServletRequest request, String uriBack, String software, boolean isPopup, Integer codiceIstanza,
	    Integer codiceMovimento) {

	String paginaJAVA = "../report/createReportIstanze.htm?codiceIstanza=" + codiceIstanza;
	String paginaASP = BackofficeNETConstants.getURL_ISTANZESTAMPE() + "?Codice=" + codiceIstanza;
	if (codiceMovimento != null) {
	    paginaASP = BackofficeNETConstants.getURL_MOV_DOC_TIPO() + "?CodiceIstanza=" + codiceIstanza + "&CodiceMovimento=" + codiceMovimento;
	    paginaJAVA = "../istanze/stampe.htm?codiceIstanza=" + codiceIstanza + "&codiceMovimento=" + codiceMovimento;
	}
	if (isExternalPage(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_STAMPE)) {
	    return BackofficeNETConstants.getUrlTo(request, paginaASP, uriBack, software, isPopup);
	} else {
	    return getUrl(request, paginaJAVA, uriBack, software, isPopup);
	}
    }
    // NUOVI ONERI

    public static String getLinkIstanzeoneriCanoni(HttpServletRequest request, String uriBack, String software, boolean isPopup,
	    Integer codiceIstanza) {

	return BackofficeNETConstants.getUrlTo(request, BackofficeNETConstants.getURL_ISTANZEONERICANONI() + "?CodiceIstanza=" + codiceIstanza,
		uriBack, software, isPopup);
    }

    public static String getLinkIstanzeoneriCostoUrbanizzazione(HttpServletRequest request, String uriBack, String software, boolean isPopup,
	    Integer codiceIstanza) {

	return BackofficeNETConstants.getUrlTo(request,
		BackofficeNETConstants.getURL_ISTANZEONERICOSTOURBANIZZAZIONE() + "?codiceistanza=" + codiceIstanza, uriBack, software, isPopup);
    }

    public static String getLinkIstanzeoneriCostoCostruzione(HttpServletRequest request, String uriBack, String software, boolean isPopup,
	    Integer codiceIstanza) {

	return BackofficeNETConstants.getUrlTo(request,
		BackofficeNETConstants.getURL_ISTANZEONERICOSTOCOSTRUZIONE() + "?codiceistanza=" + codiceIstanza, uriBack, software, isPopup);
    }

    public static String getLinkIstanzeoneriFidejussioni(HttpServletRequest request, String uriBack, String software, boolean isPopup,
	    Integer codiceIstanza) {

	return BackofficeNETConstants.getUrlTo(request, BackofficeNETConstants.getURL_ISTANZEONERIFIDEJUSSIONI() + "?CodiceIstanza=" + codiceIstanza,
		uriBack, software, isPopup);
    }
    // NUOVI ONERI

    public static String getLinkIstanzeoneri(HttpServletRequest request, String uriBack, String software, boolean isPopup, Integer codiceIstanza) {

	if (isExternalPage(IVerticalizzazioneTipoInstallazioneService.PAR_PAGINA_ONERI)) {
	    return BackofficeNETConstants.getUrlTo(request, BackofficeNETConstants.getURL_ISTANZEONERILISTA() + "?Codice=" + codiceIstanza, uriBack,
		    software, isPopup);
	} else {
	    return getUrl(request, "../istanzeoneri/list.htm?codiceIstanza=" + codiceIstanza, uriBack, software, isPopup);
	}
    }

    private static String getUrl(HttpServletRequest request, String uriTo, String uriBack, String software, boolean isPopup) {

	String urlTo = "";
	String qm = "?";
	if (uriTo.indexOf(qm) > -1) {
	    qm = "&";
	}
	if (StringUtils.isBlank(software)) {
	    software = (String) request.getSession(false).getAttribute(WebConstants.SOFTWARE);
	}
	try {
	    if (!isPopup) {
		// url che passo al sistema esterno con la quale richiamare sigepro2
		String historyBackUrl = request.getContextPath() + "/history/back.htm?" + WebConstants.GOTO + "=%2F";
		String historyBackUrlEncoded = URLEncoder.encode(historyBackUrl, "UTF-8");
		historyBackUrlEncoded = URLEncoder.encode(historyBackUrlEncoded, "UTF-8");
		// String returnTo = RETURNTO + "=" + historyBackUrlEncoded;
		// accodo alla url passata al metodo che corrisponde alla funzionalità da richiamare, la return_to e il
		// software
		uriTo += qm + /*returnTo +*/"&" + WebConstants.SOFTWARE + "=" + software;
		// eseguo l'encoding perchè l'url deve transitare per l'ExternalResourceController
		String uriToEncoded = URLEncoder.encode(uriTo, "UTF-8");
		// externalResourceUrlEncoded = URLEncoder.encode(externalResourceUrlEncoded, "UTF-8");
		// eseguo l'encoding della uriBack che devo inserire nell'history back
		String uriBackEncoded = "%2F";
		if (StringUtils.isNotBlank(uriBack)) {
		    uriBackEncoded = URLEncoder.encode(uriBack, "UTF-8");
		}
		uriBackEncoded = URLEncoder.encode(uriBackEncoded, "UTF-8");
		String historySetUrl = "../history/set.htm?ReturnTo=" + uriBackEncoded + "&" + WebConstants.GOTO + "=" + uriToEncoded;
		urlTo = historySetUrl;
	    } else {
		// se la chiamata è in popup non passo per l'history controller e non accodo in query string la
		// return_to
		uriTo += qm + WebConstants.SOFTWARE + "=" + software;
		String uriToEncoded = URLEncoder.encode(uriTo, "UTF-8");
		urlTo = uriToEncoded;
	    }
	} catch (UnsupportedEncodingException e) {
	    System.err.println("ApplicationLinkUtil getUrl error: " + e.getMessage());
	}
	return urlTo;
    }

    public static boolean isExternalPage(String pageVertParam) {

	if (tipologiaFunionalitaService == null) {
	    tipologiaFunionalitaService = (ITipologiaFunionalitaService) ContextLoader.getCurrentWebApplicationContext()
		    .getBean("tipologiaFunzionalitaServiceImpl", ITipologiaFunionalitaService.class);
	}
	return TecnologiaPaginaEnum.MICROSOFT.equals(tipologiaFunionalitaService.getTecnologiaPagina(pageVertParam));
    }
}
