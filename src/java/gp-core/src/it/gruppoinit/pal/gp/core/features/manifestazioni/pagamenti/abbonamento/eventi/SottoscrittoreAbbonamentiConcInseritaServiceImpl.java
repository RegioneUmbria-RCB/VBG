package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniDAO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoConcessioneInserita;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniDAO;

@Service
public class SottoscrittoreAbbonamentiConcInseritaServiceImpl extends BaseAutConcInserita implements IEventSubscriber<EventoConcessioneInserita> {

    @Autowired
    private AutorizzazioniConcessioniDAO autConcDAO;
    @Autowired
    private AutorizzazioniDAO autorizzazioniDAO;

    @Override
    public void onEvent(EventoConcessioneInserita e) throws EventAbortedException {

	if (e.getIdConcessione() == null) {
	    throw new EventAbortedException("L'autorizzazione non specificata", this.getClass().getSimpleName(), e);
	}
	AutorizzazioniConcessioni autConc = autConcDAO.findById(new PkId(e.getIdConcessione()));
	if (autConc == null) {
	    throw new EventAbortedException("La concessione con id " + e.getIdConcessione() + " non esiste", this.getClass().getSimpleName(), e);
	}
	Autorizzazioni a = autorizzazioniDAO.findById(new PkId(autConc.getAutorizzazioniByFkAutconcAutatt().getId().getCodice()));
	if (a == null) {
	    throw new EventAbortedException(
		    "L'autorizzazione con id " + autConc.getAutorizzazioniByFkAutconcAutatt().getId().getCodice() + " non esiste",
		    this.getClass().getSimpleName(), e);
	}
	if (checkCollegaAutorizzazioniAutomatiche()) {
	    // Devo trovare se una delle anagrafiche dell'autorizzazione ha un borsellino attivo
	    // se si la collego 
	    Anagrafe occupante = a.getOccupante();
	    if (occupante == null) {
		throw new EventAbortedException("L'autorizzazione " + a + " non ha occupante", this.getClass().getSimpleName(), e);
	    }
	    collegaAlBorsellino(occupante.getId().getCodice(), autConc.getAutorizzazioniByFkAutconcAutatt().getId().getCodice(), e);
	}
    }
}
