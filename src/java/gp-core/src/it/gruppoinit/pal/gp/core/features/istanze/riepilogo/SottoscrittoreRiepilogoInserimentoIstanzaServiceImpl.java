package it.gruppoinit.pal.gp.core.features.istanze.riepilogo;

import java.net.URL;

import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.istanze.riepilogo.ws.BinaryFile;
import it.gruppoinit.pal.gp.core.features.istanze.riepilogo.ws.RiepilogoPratica;
import it.gruppoinit.pal.gp.core.features.istanze.riepilogo.ws.RiepilogoPraticaSoap;
import it.gruppoinit.pal.gp.core.features.rabbitmq.eventi.EventoInserimentoIstanza;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;

@Service
public class SottoscrittoreRiepilogoInserimentoIstanzaServiceImpl implements IEventSubscriber<EventoInserimentoIstanza> {

    private static final Logger logger = LoggerFactory.getLogger(SottoscrittoreRiepilogoInserimentoIstanzaServiceImpl.class);
    private VerticalizzazioniService verticalizzazioniService;
    private IstanzeService istanzeService;
    private DocumentiistanzaService documentiistanzaService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Override
    public void onEvent(EventoInserimentoIstanza e) throws EventAbortedException {

	//1. Verifica parametro COMPORTAMENTI_ISTANZE.UR_WS_RIEPILOGO_PRATICA configurato
	String url = this.getUrlWsRiepilogo();
	logger.debug("codiceistanza {}, url {}", e.getCodiceIstanza(), url);
	if (StringUtils.isBlank(url)) {
	    return;
	}
	//2. Verifica istanza con intervento per domanda on line
	Istanze istanza = this.istanzeService.findById(new PkId(e.getCodiceIstanza()));
	logger.debug("codiceistanza {}, istanza.getAlberoproc().isPubblicaSoloDomandaOnLine() {}", e.getCodiceIstanza(),
		istanza.getAlberoproc().isPubblicaSoloDomandaOnLine());
	if (!istanza.getAlberoproc().isPubblicaSoloDomandaOnLine()) {
	    return;
	}
	//3. Verifica e recupero ISTANZE.UUID
	String uuid = istanza.getUuid();
	logger.debug("codiceistanza {}, uuid {}", e.getCodiceIstanza(), uuid);
	if (StringUtils.isBlank(uuid)) {
	    return;
	}
	//4. Invocazione metodo area riservata per generazione riepilogo
	try {
	    QName SERVICE_NAME = new QName("http://tempuri.org/", "riepilogo_pratica");
	    URL wsdlURL = new URL(url);
	    RiepilogoPratica ss = new RiepilogoPratica(wsdlURL, SERVICE_NAME);
	    RiepilogoPraticaSoap port = ss.getRiepilogoPraticaSoap();
	    BinaryFile riepilogo = port.generaRiepilogo(ORMHelper.getToken(), uuid);
	    if (riepilogo == null) {
		logger.error(
			"E' stato invocato il servizio di generazione del riepilogo url={} che non è andato in errore ma non ha restituito il riepilogo, per l'istanza {}",
			url, uuid);
		throw new RuntimeException(
			"E' stato invocato il servizio di generazione del riepilogo che non è andato in errore ma non ha restituito il riepilogo");
	    }
	    //5. Salvataggio tra i documenti istanza
	    this.documentiistanzaService.updateAggiornaRiepilogo(istanza, riepilogo.getFileContent(), riepilogo.getFileName());
	} catch (Exception er) {
	    logger.warn("onEvent: Non è possibile generare il riepilogo per l'istanza con codice {} a causa di {}",
		    new Object[] { e.getCodiceIstanza(), er.getMessage() });
	}
    }

    private String getUrlWsRiepilogo() {

	logger.debug("getUrlWsRiepilogo {}-{}", ORMHelper.getIdcomune(), ORMHelper.getSoftware());
	if (!this.verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE)) {
	    logger.debug("VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE non attiva {}-{}", ORMHelper.getIdcomune(), ORMHelper.getSoftware());
	    return null;
	}
	Verticalizzazioniparametri vp = this.verticalizzazioniService.getVerticalizzazioniparametri(
		WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE, WebConstants.VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_URL_WS_RIEPILOGO_PRATICA);
	if (vp == null || StringUtils.isBlank(vp.getValore())) {
	    logger.debug("VERTICALIZZAZIONE_COMPORTAMENTI_ISTANZE_URL_WS_RIEPILOGO_PRATICA non valorizzato");
	    return null;
	}
	return vp.getValore();
    }
}
