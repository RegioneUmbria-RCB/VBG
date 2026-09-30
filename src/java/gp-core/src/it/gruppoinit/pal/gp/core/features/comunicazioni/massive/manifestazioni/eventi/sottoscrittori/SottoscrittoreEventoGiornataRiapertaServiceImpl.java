package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.eventi.sottoscrittori;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.ConfigurazioneComunicazioniManifestazioni.TIPO_COMUNICAZIONE;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.IComunicazioniManifestazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoGiornataRiaperta;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

@Service
public class SottoscrittoreEventoGiornataRiapertaServiceImpl implements IEventSubscriber<EventoGiornataRiaperta> {

    private UserSecurityService userSecurityService;
    private IComunicazioniManifestazioniService comunicazioniManifestazioniService;

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setComunicazioniManifestazioniService(IComunicazioniManifestazioniService comunicazioniManifestazioniService) {

	this.comunicazioniManifestazioniService = comunicazioniManifestazioniService;
    }

    @Override
    public void onEvent(EventoGiornataRiaperta e) {

	//1. Verifico la presenza di comunicazioni non elaborate che potrebbero essere cancellate
	if (!this.comunicazioniManifestazioniService.comunicazioneCancellabilePerTipologia(e.getIdGiornata(), TIPO_COMUNICAZIONE.CHIUSURA_GIORNATA)) {
	    return;
	}
	//2. Cancello la comunicazione
	Responsabili operatore = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	this.comunicazioniManifestazioniService.eliminaComunicazioniDellaGiornataPerTipologia(e.getIdGiornata(), operatore,
		TIPO_COMUNICAZIONE.CHIUSURA_GIORNATA);
    }
}
