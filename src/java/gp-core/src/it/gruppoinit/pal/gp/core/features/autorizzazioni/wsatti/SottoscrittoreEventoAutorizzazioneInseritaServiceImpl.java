package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneInserita;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.metadati.AutorizzazioniMetadatiService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati.IAutorizzazioneMetadatoFactory;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.metadati.MetadatiInserimentoFactory;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.LeggiDeterminaResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione.VerticalizzazioneWSAttiService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoAutorizzazioneInseritaServiceImpl implements IEventSubscriber<EventoAutorizzazioneInserita> {

    private VerticalizzazioneWSAttiService verticalizzazioneWSAttiService;
    private AutorizzazioniService autorizzazioniService;
    private WSAttiService wsAttiService;
    private AutorizzazioniMetadatiService metadatiService;

    @Autowired
    public void setVerticalizzazioneWSAttiService(VerticalizzazioneWSAttiService verticalizzazioneWSAttiService) {

	this.verticalizzazioneWSAttiService = verticalizzazioneWSAttiService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setWsAttiService(WSAttiService wsAttiService) {

	this.wsAttiService = wsAttiService;
    }

    @Autowired
    public void setMetadatiService(AutorizzazioniMetadatiService metadatiService) {

	this.metadatiService = metadatiService;
    }

    @Override
    public void onEvent(EventoAutorizzazioneInserita e) {

	if (!this.verticalizzazioneWSAttiService.isAttiva()) {
	    return;
	}
	Autorizzazioni aut = this.autorizzazioniService.findById(new PkId(e.getIdAutorizzazione()));
	if (!"numerazioneDaWSAttiServiceImpl".equalsIgnoreCase(aut.getTipologiaregistro().getNumerazioneCustom())) {
	    return;
	}
	if (aut.getFkidprotocollo() == null) {
	    return;
	}
	//1. Riferimento esterno dell'atto
	Integer idAttoExt = Integer.parseInt(aut.getFkidprotocollo());
	//2. Rilettura dell'atto per recupero dati da inserire tra i metadati
	LeggiDeterminaResponse responseLettura = this.wsAttiService.leggi(idAttoExt, e.getCodiceComuneRiferimento());
	IAutorizzazioneMetadatoFactory metadatiFactory = MetadatiInserimentoFactory.fromIdELeggiDeterminaResponse(e.getIdAutorizzazione(),
		responseLettura);
	this.metadatiService.insertMetadati(metadatiFactory.get());
	//3. Inserimento dei documenti da precompilare tra i documenti autorizzazioni
	this.wsAttiService.precompilaAllegatiSecondari(aut);
    }
}
