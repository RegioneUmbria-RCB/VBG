package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.eventi.EventoAllegatiCaricati;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti.verticalizzazione.VerticalizzazioneWSAttiService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreEventoAllegatiCaricatiServiceImpl implements IEventSubscriber<EventoAllegatiCaricati> {

    private VerticalizzazioneWSAttiService verticalizzazioneWSAttiService;
    private WSAttiService attiService;
    private AutorizzazioniService autorizzazioniService;

    @Autowired
    public void setVerticalizzazioneWSAttiService(VerticalizzazioneWSAttiService verticalizzazioneWSAttiService) {

	this.verticalizzazioneWSAttiService = verticalizzazioneWSAttiService;
    }

    @Autowired
    public void setAttiService(WSAttiService attiService) {

	this.attiService = attiService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Override
    public void onEvent(EventoAllegatiCaricati e) {

	if (!this.verticalizzazioneWSAttiService.isFascicolaAtto()) {
	    return;
	}
	Autorizzazioni aut = this.autorizzazioniService.findById(new PkId(e.getIdAutorizzazione()));
	Integer idDocumento = Integer.parseInt(aut.getFkidprotocollo());
	Calendar calendar = new GregorianCalendar();
	calendar.setTime(aut.getAutorizdata());
	String numeroAtto = aut.getAutoriznumero().contains("/") ? aut.getAutoriznumero().substring(0, aut.getAutoriznumero().indexOf("/"))
		: aut.getAutoriznumero();
	Integer numero = Integer.parseInt(numeroAtto);
	Integer anno = calendar.get(Calendar.YEAR);
	//1. Verifico se già fascicolata
	boolean fascicolata = this.attiService.isFascicolata(aut.getAutorizcomune().getCodicecomune(), idDocumento);
	if (fascicolata) {
	    return;
	}
	//2. Eventualmente fascicolo
	Fascicolo datiFascicolo = new Fascicolo();
	datiFascicolo.setClassifica(this.verticalizzazioneWSAttiService.getClassifica());
	datiFascicolo.setIdDocumento(idDocumento);
	datiFascicolo.setOggetto(aut.getTipologiaregistro().getTrDescrizione() + " - " + aut.getTransientEstremiAut());
	this.attiService.fascicola(aut.getAutorizcomune().getCodicecomune(), datiFascicolo);
    }
}
