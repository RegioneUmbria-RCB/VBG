package it.gruppoinit.pal.gp.core.features.oneri;

import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService.ENUM_COPIA_ONERI;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.eventi.EventoSubentroEffettuato;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventSubscriber;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SottoscrittoreSubentriServiceImpl implements IEventSubscriber<EventoSubentroEffettuato> {

    private AutorizzazioniSubentriService autorizzazioniSubentriService;
    private IstanzeoneriService istanzeoneriService;

    @Autowired
    public SottoscrittoreSubentriServiceImpl(AutorizzazioniSubentriService autorizzazioniSubentriService, IstanzeoneriService istanzeoneriService) {

	super();
	this.autorizzazioniSubentriService = autorizzazioniSubentriService;
	this.istanzeoneriService = istanzeoneriService;
    }

    @Override
    public void onEvent(EventoSubentroEffettuato e) {

	if (ENUM_COPIA_ONERI.COPIARE_ONERI_NON_PAGATI.equals(e.getCopiaONERI())) {
	    AutorizzazioniSubentri sub = autorizzazioniSubentriService.findById(new PkId(e.getIdSubentroEffettuato()));
	    Integer codiceIstanzaSubentrata = sub.getIstanze().getId().getCodice();
	    Integer codiceIstanzaCheSubentra = sub.getAutorizzazioni().getIstanza().getId().getCodice();
	    istanzeoneriService.copiaOneriNonPagatiDaIstanzaSorgenteADestinazione(codiceIstanzaSubentrata, codiceIstanzaCheSubentra);
	}
    }
}
