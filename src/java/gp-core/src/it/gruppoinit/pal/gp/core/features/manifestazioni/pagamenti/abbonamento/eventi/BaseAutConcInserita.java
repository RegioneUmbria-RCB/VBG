package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.eventi;

import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.core.domain.BorsellinoConfigurazione;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.ComportamentoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IAbbonamentoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.configurazione.IBorsellinoConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.exceptions.BorsellinoException;

public class BaseAutConcInserita {

    @Autowired
    private IBorsellinoConfigurazioneDAO borsellinoConfigurazioneDAO;
    @Autowired
    private IAbbonamentoService abbonamentoService;

    protected boolean checkCollegaAutorizzazioniAutomatiche() {

	BorsellinoConfigurazione cfg = borsellinoConfigurazioneDAO.findConfigurazione();
	if (cfg != null) {
	    return cfg.getTipoInstallazione().equalsIgnoreCase(ComportamentoEnum.OPERATORE.name());
	}
	return false;
    }

    protected void collegaAlBorsellino(Integer codiceOccupante, Integer idAutorizzazione, IEvent e) throws EventAbortedException {

	try {
	    abbonamentoService.collegaAlBorsellino(codiceOccupante, idAutorizzazione);
	} catch (BorsellinoException e1) {
	    throw new EventAbortedException(e1, this.getClass().getSimpleName(), e);
	}
    }
}
