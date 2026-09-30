package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoRichiestaBloccoAutorizzazione;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.rest.NumeraDeterminaResponse;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione.VerticalizzazioneWSAttiService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoRichiestaBloccoAutorizzazioneServiceImpl implements IEventSubscriber<EventoRichiestaBloccoAutorizzazione> {

    private AutorizzazioniService autorizzazioniService;
    private VerticalizzazioneWSAttiService verticalizzazioneWSAttiService;
    private WSAttiService wsAttiService;

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setVerticalizzazioneWSAttiService(VerticalizzazioneWSAttiService verticalizzazioneWSAttiService) {

	this.verticalizzazioneWSAttiService = verticalizzazioneWSAttiService;
    }

    @Autowired
    public void setWsAttiService(WSAttiService wsAttiService) {

	this.wsAttiService = wsAttiService;
    }

    @Override
    public void onEvent(EventoRichiestaBloccoAutorizzazione e) {

	if (!this.verticalizzazioneWSAttiService.isAttiva()) {
	    return;
	}
	Autorizzazioni aut = this.autorizzazioniService.findById(new PkId(e.getIdAutorizzazione()));
	if (!"numerazioneDaWSAttiServiceImpl".equalsIgnoreCase(aut.getTipologiaregistro().getNumerazioneCustom())) {
	    return;
	}
	//2. Numero definitivamente l'atto
	NumeraDeterminaResponse response = this.wsAttiService.numera(Integer.parseInt(aut.getFkidprotocollo()), aut.getAutorizdata(),
		aut.getIstanza().getComune().getCodicecomune());
	this.autorizzazioniService.aggiornaEstremiAutorizzazioni(e.getIdAutorizzazione(), response.getNumero().toString(), response.getData());
	aut.setAutoriznumero(response.getNumero().toString());
	aut.setAutorizdata(response.getData());
	//3. Generazione allegato principale
	this.wsAttiService.generaAllegatoPrincipale(aut);
    }
}
