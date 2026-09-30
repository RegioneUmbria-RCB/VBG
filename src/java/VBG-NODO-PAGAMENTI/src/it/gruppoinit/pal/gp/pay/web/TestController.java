/**
 * 
 */
package it.gruppoinit.pal.gp.pay.web;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gruppoinit.pal.gp.pay.connector.IPayConnector;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.service.AttivazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoResponseType;
import it.gruppoinit.pal.gp.pay.ws.schema.AttivaSessionePagamentoType;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParamType;
import it.gruppoinit.pal.gp.pay.ws.schema.RiferimentoPosizioneDebitoriaType;

/**
 * @author francol
 *
 */
@Controller
//@RequestMapping(path = "/esitoSessionePagamento")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private PayConnectorService payConnectorService;
    @Autowired
    private ConfigurazionePagamentiService configurazionePagamentiService;
    @Autowired
    private AttivazionePagamentiService attivazionePagamentiService;

    @RequestMapping
    public String attivaSessione(@ModelAttribute(name = "profile") String ente, @ModelAttribute(name = "idpos") Integer idPos,
	    @ModelAttribute(name = "urlback") String urlRitorno, Model model, HttpServletRequest request, HttpServletResponse response) {

	try {
	    if (log.isInfoEnabled()) {
		log.info("handleRedirect - Richiesta di esito pagamento {}", request.getParameterMap());
	    }
	    PayProfiliEntiCreditori ctorCfg = this.configurazionePagamentiService.configuraRequestPerEnteCreditore(ente);
	    if (ctorCfg != null) {
		boolean post = true;
		IPayConnector ctor = this.payConnectorService.getPayConnectorInstance(ctorCfg.getPayConnector());
		AttivaSessionePagamentoType attivaSesReq = new AttivaSessionePagamentoType();
		attivaSesReq.setCfEnteCreditore(ente);
		RiferimentoPosizioneDebitoriaType refPos = new RiferimentoPosizioneDebitoriaType();
		refPos.setIdPosizione(BigInteger.valueOf(idPos));
		attivaSesReq.setRiferimentoPosizione(refPos);
		attivaSesReq.setUrlRedirectEsito(urlRitorno);
		AttivaSessionePagamentoResponseType sesResp = this.attivazionePagamentiService.attivaSessionePagamento(attivaSesReq);
		if (sesResp.isEsito()) {
		    StringBuilder sbParams = new StringBuilder();
		    if (sesResp.getFormParams() != null) {
			for (FormParamType param : sesResp.getFormParams().getParam()) {
			    if (sbParams.length() > 0) {
				sbParams.append("&");
			    }
			    sbParams.append(param.getParamName()).append("=").append(param.getValue());
			}
		    }
		    model.addAttribute("datiSessione", sesResp);
		    StringBuilder sbGet = new StringBuilder(sesResp.getPayUrl());
		    if (sbParams.length() > 0) {
			sbGet.append("?").append(sbParams.toString());
		    }
		    model.addAttribute("urlget", sbGet.toString());
		    if (post) {
			return "test/attivaSessione";
		    } else {
			response.sendRedirect(sbGet.toString());
			return null;
		    } 
		}
		else {
		    model.addAttribute("message", sesResp.getDescEsito());
		}
	    }
	} catch (Exception e) {
	    log.error("handleRedirect - errore nella gestione dell'esito della sessione di pagamento da parte del connettore " +
		    ente +
		    " l'utente sarà reindirizzato alla pagina di default", e);
	}
	return "test/attivaSessione";
    }
}
