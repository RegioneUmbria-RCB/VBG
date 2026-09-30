/**
 * 
 */
package it.gruppoinit.pal.gp.pay.web;

import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author francol
 *
 */
@Controller
//@RequestMapping(path = "/esitoSessionePagamento")
public class EsitoSessionePagamentoController {

    private static final Logger log = LoggerFactory.getLogger(EsitoSessionePagamentoController.class);
    @Autowired
    private PayConnectorService payConnectorService;
    @Autowired
    private ConfigurazionePagamentiService configurazionePagamentiService;

    @RequestMapping(path = "/{profile}")
    public String handleRedirect(@PathVariable(name = "profile") String connector, Model model, HttpServletRequest request,
	    HttpServletResponse response) {

	//connector ids are uppercase
	connector = connector.toUpperCase();
	String defaultPage = "defaultEsitoSessione";
	//View goToPage = null;
	String goToPage = null;
	try {
	    if (log.isInfoEnabled()) {
		log.info("handleRedirect - Richiesta di esito pagamento {}", request.getParameterMap());
	    }
	    PayProfiliEntiCreditori ctorCfg = this.configurazionePagamentiService.configuraRequestPerEnteCreditore(connector);
	    if (ctorCfg != null) {
		IPayConnector ctor = this.payConnectorService.getPayConnectorInstance(ctorCfg.getPayConnector());
		PaySessioniPagamento paySex = ctor.gestisciEsitoSessione(request.getParameterMap());
		if (paySex != null && StringUtils.isNotBlank(paySex.getUrlRedirectEsito())) {
		    log.debug("Sessione trovata redirigo l'utente a {}", paySex.getUrlRedirectEsito());
		    //goToPage = new RedirectView(paySex.getUrlRedirectEsito(), false);
		    goToPage = "redirect:" + paySex.getUrlRedirectEsito();
		}
	    }
	} catch (Exception e) {
	    log.error("handleRedirect - errore nella gestione dell'esito della sessione di pagamento da parte del connettore " + connector
		    + " l'utente sarà reindirizzato alla pagina di default", e);
	}
	if (goToPage == null) {
	    goToPage = defaultPage;
	}
	return goToPage;
    }
}
