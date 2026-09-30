package it.gruppoinit.pal.gp.backoffice.web.util;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;

public class ApplicationLinkUtil {

    private static VerticalizzazioniService verticalizzazioniService = null;

    public static String getLinkSchede(HttpServletRequest request, String uriTo, String uriBack, String software, boolean isPopup,
	    Integer codiceIstanza) {

	if (isEnterprise()) {
	    return BackofficeNETConstants.getUrlTo(request, uriTo, uriBack, software, isPopup);
	} else {
	    return getUrl(request, "../istanzedyn2dati/viewModelliIstanza.htm?codiceIstanza=" + codiceIstanza, uriBack, software, isPopup);
	}
    }
    
    
    public static String  getLinkStampeIstanza(HttpServletRequest request, String uriTo, String uriBack, String software, boolean isPopup,
	    Integer codiceIstanza) {

	if (isEnterprise()) {
	    return BackofficeNETConstants.getUrlTo(request, uriTo, uriBack, software, isPopup);
	} else {
	    return getUrl(request, "../report/createReportIstanze.htm?codiceIstanza=" + codiceIstanza, uriBack, software, isPopup);
	}
    }

    public static String getLinkIstanzeoneri(HttpServletRequest request, String uriTo, String uriBack, String software, boolean isPopup,
	    Integer codiceIstanza) {

	if (isEnterprise()) {
	    return BackofficeNETConstants.getUrlTo(request, uriTo, uriBack, software, isPopup);
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

    public static boolean isEnterprise() {

	if (verticalizzazioniService == null) {
	    verticalizzazioniService = (VerticalizzazioniService) ContextLoader.getCurrentWebApplicationContext().getBean(
		    "verticalizzazioniServiceImpl", VerticalizzazioniService.class);
	}
	if (verticalizzazioniService != null) {
	    return verticalizzazioniService.isInstallazioneEnterprise();
	} else {
	    return true;
	}
    }
}
