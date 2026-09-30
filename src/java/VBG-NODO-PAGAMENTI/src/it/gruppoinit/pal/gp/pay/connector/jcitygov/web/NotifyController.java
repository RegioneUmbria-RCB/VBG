package it.gruppoinit.pal.gp.pay.connector.jcitygov.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.TipiEvento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayPosizioniDebitorieService;
import it.gruppoinit.pal.gp.pay.service.PayProfiliEntiCreditoriService;
import it.gruppoinit.pal.gp.pay.service.PayRichiesteService;

@Controller
public class NotifyController {

    private static final Logger log = LoggerFactory.getLogger(NotifyController.class);
    @Autowired
    private PayProfiliEntiCreditoriService payProfiliEntiCreditoriService;
    @Autowired
    private PayPosizioniDebitorieService payPosizioniDebitorieService;
    @Autowired
    private ConfigurazionePagamentiService configurazionePagamentiService;
    @Autowired
    private PayRichiesteService payRichiesteService;

    @RequestMapping("/jcity/notificaPagamento")
    public void notificaPagamento(@RequestParam("idPayPos") String[] idPayPos, @RequestParam("idProfilo") String idProfilo,
	    @RequestParam("idSessionePagamento") String idSessionePagamento, Model model, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	String operation = "ricezione notifica di pagamento da JCity-GOV in corso" + request.getQueryString();
	log.info("notificaPagamento - {}", operation);
	PayProfiliEntiCreditori profilo = null;
	try {
	    profilo = this.payProfiliEntiCreditoriService.findByCfCodiceProfilo(idProfilo);
	    this.configurazionePagamentiService.configuraRequestPerEnteCreditore(profilo);
	    for (String idPosS : idPayPos) {
		log.info("idPayPos {}", idPosS);
		try {
		    PayPosizioniDebitorie payPos = payPosizioniDebitorieService.findById(new PkId(Integer.parseInt(idPosS)));
		    payRichiesteService.registraRichiestaPerPosizioneDebitoria(payPos, TipiEvento.VERIFICA_STATO_PAGAMENTO_PSP);
		} catch (PayException e) {
		    log.error("Errore nella registrazione dell'aggiornamento di stato per la posizione debitoria {}-{}", idPosS, idProfilo);
		}
	    }
	} catch (PayConfigurationException e1) {
	    log.error(operation, e1);
	}
	response.addHeader("Content-Type", "text/plain");
	response.getOutputStream().write("200 OK".getBytes());
    }
}
