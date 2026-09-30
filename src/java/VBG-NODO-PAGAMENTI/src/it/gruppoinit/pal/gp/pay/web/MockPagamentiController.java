package it.gruppoinit.pal.gp.pay.web;

import java.util.List;

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
import it.gruppoinit.pal.gp.pay.connector.mock.MockPayConnector;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.domain.PaySessioniPagamento;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.service.ConfigurazionePagamentiService;
import it.gruppoinit.pal.gp.pay.service.PayConnectorService;
import it.gruppoinit.pal.gp.pay.service.PaySessioniPagamentoService;

@Controller
public class MockPagamentiController {

    @Autowired
    private PaySessioniPagamentoService paySessioniPagamentoService;
    private static final Logger log = LoggerFactory.getLogger(MockPagamentiController.class);
    @Autowired
    private ConfigurazionePagamentiService configurazionePagamentiService;
    @Autowired
    private PayConnectorService payConnectorService;

    @RequestMapping(path = "/mockPagamenti/mockPagamento")
    public String mockPagamento(@ModelAttribute(name = "profilo") String profilo, @ModelAttribute(name = "idSessione") String idSessione, Model model,
	    HttpServletRequest request, HttpServletResponse response) throws PayConfigurationException {

	if (log.isInfoEnabled()) {
	    log.info("mockPagamento - Richiesta di esito pagamento {}", request.getParameterMap());
	}
	PayProfiliEntiCreditori ctorCfg = this.configurazionePagamentiService.configuraRequestPerEnteCreditore(profilo);
	IPayConnector ctor = this.payConnectorService.getPayConnectorInstance(ctorCfg.getPayConnector());
	if (!(ctor instanceof MockPayConnector)) {
	    throw new PayConfigurationException("Funzionalità valida solamente per il connettore MOCK");
	}
	List<PaySessioniPagamento> sessioni = this.paySessioniPagamentoService.findBySessionId(idSessione);
	model.addAttribute("idSessione", idSessione);
	model.addAttribute("sessioni", sessioni);
	model.addAttribute("profilo", profilo);
	return "mock/gestisciPagamento";
    }
}
