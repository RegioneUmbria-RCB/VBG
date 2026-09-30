package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneDAO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneBloccata;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.eventi.EventoAllegatiCaricati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione.VerticalizzazioneWSAttiService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;

@Service
public class SottoscrittoreEventoAutorizzazioneBloccataServiceImpl implements IEventSubscriber<EventoAutorizzazioneBloccata> {

    private static Logger log = LoggerFactory.getLogger(SottoscrittoreEventoAutorizzazioneBloccataServiceImpl.class);
    private AutorizzazioniService autorizzazioniService;
    private VerticalizzazioneWSAttiService verticalizzazioneWSAttiService;
    private DocumentiAutorizzazioneService documentiAutorizzazioneService;
    private DocumentiAutorizzazioneDAO documentiAutorizzazioneDAO;
    private WSAttiService attiService;
    private OggettiService oggettiService;
    private IEventPublisher eventPublisher;

    @Autowired
    public void setDocumentiAutorizzazioneDAO(DocumentiAutorizzazioneDAO documentiAutorizzazioneDAO) {

	this.documentiAutorizzazioneDAO = documentiAutorizzazioneDAO;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setVerticalizzazioneWSAttiService(VerticalizzazioneWSAttiService verticalizzazioneWSAttiService) {

	this.verticalizzazioneWSAttiService = verticalizzazioneWSAttiService;
    }

    @Autowired
    public void setDocumentiAutorizzazioneService(DocumentiAutorizzazioneService documentiAutorizzazioneService) {

	this.documentiAutorizzazioneService = documentiAutorizzazioneService;
    }

    @Autowired
    public void setAttiService(WSAttiService attiService) {

	this.attiService = attiService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setEventPublisher(IEventPublisher eventPublisher) {

	this.eventPublisher = eventPublisher;
    }

    @Override
    public void onEvent(EventoAutorizzazioneBloccata e) {

	log.debug("Entro nel metodo");
	if (!this.verticalizzazioneWSAttiService.isAttiva()) {
	    log.debug("Verticalizzazione WS_ATTI non attiva");
	    return;
	}
	Autorizzazioni aut = this.autorizzazioniService.findById(new PkId(e.getIdAutorizzazione()));
	if (!"numerazioneDaWSAttiServiceImpl".equalsIgnoreCase(aut.getTipologiaregistro().getNumerazioneCustom())) {
	    log.debug("Verticalizzazione WS_ATTI non attiva");
	    return;
	}
	//1. Recupero gli allegati dell'autorizzazione
	List<DocumentiAutorizzazione> documenti = this.documentiAutorizzazioneService.findByAutorizzazioni(e.getIdAutorizzazione(), null, null);
	for (DocumentiAutorizzazione documento : documenti) {
	    try {
		log.debug("Autorizzazione {}, Elaboro il documento {}", e.getIdAutorizzazione(), documento.getId().getCodiceoggetto());
		documento = this.documentiAutorizzazioneService.findById(documento.getId());
		this.documentiAutorizzazioneDAO.refreshEntity(documento);
		log.debug("Autorizzazione {}, Elaboro il documento {} - entity riletta", e.getIdAutorizzazione(),
			documento.getId().getCodiceoggetto());
		//2. Trasmetto gli allegati dell'autorizzazione
		Allegato request = Allegato.fromDocumentiAutorizzazione(documento);
		//3. Recupero i byte dell'oggetto
		log.debug("Autorizzazione {}, Elaboro il documento {} - recupero il blob", e.getIdAutorizzazione(),
			documento.getId().getCodiceoggetto());
		InputStream stream = this.oggettiService.getOggettoAsInputStream(documento.getOggetti().getId().getCodice());
		request.setAllegatoBase64(Base64.encodeBase64String(IOUtils.toByteArray(stream)));
		String idAllegatoWSAtti = this.attiService.aggiungiAllegato(aut.getAutorizcomune().getCodicecomune(), request);
		log.debug("Autorizzazione {}, Elaboro il documento {} - allegato aggiunto", e.getIdAutorizzazione(),
			documento.getId().getCodiceoggetto());
		//4. Aggiorno il riferimento sul documento dell'autorizzazione
		documento.setRiferimentoEsterno(idAllegatoWSAtti);
		this.documentiAutorizzazioneService.update(documento);
		log.debug("Autorizzazione {}, Elaboro il documento {} - update(documento)", e.getIdAutorizzazione(),
			documento.getId().getCodiceoggetto());
		//5. Inserisco il movimento del completamento della determina
		this.attiService.registraCompletamentoAtto(aut);
		log.debug("Autorizzazione {}, Elaboro il documento {} - registraCompletamentoAtto", e.getIdAutorizzazione(),
			documento.getId().getCodiceoggetto());
	    } catch (Exception ex) {
		log.error("Errore nel blocco dell'autorizzazione", e.getIdAutorizzazione(), ex);
		throw new RuntimeException(ex);
	    }
	}
	//5. Inserisco il movimento del completamento della determina
	this.attiService.registraCompletamentoAtto(aut);
	this.eventPublisher.publish(new EventoAllegatiCaricati(e.getIdAutorizzazione()));
    }
}
