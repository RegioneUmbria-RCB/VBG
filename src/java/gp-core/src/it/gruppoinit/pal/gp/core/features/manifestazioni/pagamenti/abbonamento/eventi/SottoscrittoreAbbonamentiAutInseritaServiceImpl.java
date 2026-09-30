package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniDAO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoAutorizzazioneInserita;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;

@Service
public class SottoscrittoreAbbonamentiAutInseritaServiceImpl extends BaseAutConcInserita implements IEventSubscriber<EventoAutorizzazioneInserita> {

    @Autowired
    private AutorizzazioniDAO autorizzazioniDAO;

    @Override
    public void onEvent(EventoAutorizzazioneInserita e) throws EventAbortedException {

	if (e.getIdAutorizzazione() == null) {
	    throw new EventAbortedException("L'autorizzazione non specificata", this.getClass().getSimpleName(), e);
	}
	Autorizzazioni a = autorizzazioniDAO.findById(new PkId(e.getIdAutorizzazione()));
	if (a == null) {
	    throw new EventAbortedException("L'autorizzazione con id " + e.getIdAutorizzazione() + " non esiste", this.getClass().getSimpleName(), e);
	}
	if (checkCollegaAutorizzazioniAutomatiche()) {
	    Anagrafe occupante = a.getOccupante();
	    if (occupante == null) {
		throw new EventAbortedException("L'autorizzazione " + a + " non ha occupante", this.getClass().getSimpleName(), e);
	    }
	    collegaAlBorsellino(occupante.getId().getCodice(), e.getIdAutorizzazione(), e);
	}
    }
}
