package it.gruppoinit.pal.gp.core.features.suapxml.eventi;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.rabbitmq.eventi.EventoInserimentoIstanza;
import it.gruppoinit.pal.gp.core.features.suapxml.ISuapXmlService;
import it.gruppoinit.pal.gp.core.features.suapxml.IVerticalizzazioneSuapXmlService;
import it.gruppoinit.pal.gp.core.features.suapxml.SuapXmlServiceImpl;
import it.gruppoinit.pal.gp.core.features.suapxml.VerticalizzazioneSuapXmlServiceImpl;
import it.gruppoinit.pal.gp.core.features.suapxml.exceptions.GenerazioneSuapXmlException;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class SottoscrittoreSuapXMLInserimentoIstanzaServiceImpl implements IEventSubscriber<EventoInserimentoIstanza> {

    private static final Logger logger = LoggerFactory.getLogger(SottoscrittoreSuapXMLInserimentoIstanzaServiceImpl.class);
    private VerticalizzazioniService verticalizzazioniService;
    private IstanzeService istanzeService;
    private ISuapXmlService iSuapXmlService;
    private OggettiService oggettiService;
    private DocumentiistanzaService documentiistanzaService;
    private IstanzeeventiService istanzeeventiService;

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setiSuapXmlService(ISuapXmlService iSuapXmlService) {

	this.iSuapXmlService = iSuapXmlService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Override
    public void onEvent(EventoInserimentoIstanza e) throws EventAbortedException {

	Istanze istanza = this.istanzeService.findById(new PkId(e.getCodiceIstanza()));
	String codiceComune = istanza.getComune().getCodicecomune();
	logger.debug("Inizio verifica e generazione SUAP XML per l'istanza {} con codicecomune {} [{}]",
		new Object[] { istanza.getNumeroistanza(), codiceComune, istanza.getId() });
	IVerticalizzazioneSuapXmlService vertSuapXmlService = new VerticalizzazioneSuapXmlServiceImpl(verticalizzazioniService, codiceComune);
	if (!vertSuapXmlService.isAttiva() || !vertSuapXmlService.generaSuInserimentoIstanza()) {
	    return;
	}
	byte[] bytes = new byte[0];
	try {
	    bytes = this.iSuapXmlService.generaSuapXML(codiceComune, e.getCodiceIstanza());
	} catch (GenerazioneSuapXmlException es) {
	    inserisciEventoSuapXMLFallito(istanza, es);
	    return;
	}
	if (bytes.length == 0) {
	    return;
	}
	String nomeFile = SuapXmlServiceImpl.PREFIX_NAME_PRATICA_XML_SUAP + Utilities.formatDate(new Date(), "yyyyMMdd");
	logger.debug("Inserisco il file {} nella tabella oggetti", nomeFile);
	Oggetti oggetto = new Oggetti();
	oggetto.setDimensioneFile(bytes.length);
	oggetto.setNonUsareContenutoBLOB(bytes);
	oggetto.setNomefile(nomeFile + ".xml");
	oggettiService.insert(oggetto);
	logger.debug("Referenzio il file tra i documenti dell'istanza");
	Documentiistanza docIstanza = new Documentiistanza();
	docIstanza.setData(istanza.getData());
	docIstanza.setDocumento(nomeFile);
	docIstanza.setFlgDaModelloDinamico(false);
	docIstanza.setIstanza(istanza);
	docIstanza.setNecessario(true);
	docIstanza.setNote(SuapXmlServiceImpl.ANNOTAZIONI_DOCUMENTO);
	docIstanza.setOggetto(oggetto);
	docIstanza.setPresente(true);
	this.documentiistanzaService.insert(docIstanza);
	logger.debug("Fine generazione SUAP XML");
    }

    private void inserisciEventoSuapXMLFallito(Istanze istanza, GenerazioneSuapXmlException es) {

	logger.error("Generazione SUAP_XML fallito per la pratica {}: {}", istanza.getId(), es.getMessage());
	istanzeeventiService.insert("Errore nella generazione del file SUAP.xml: " + es.getMessage(), IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI,
		null, istanza);
    }
}
