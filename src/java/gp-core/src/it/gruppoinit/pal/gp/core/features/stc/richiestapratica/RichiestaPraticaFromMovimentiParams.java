package it.gruppoinit.pal.gp.core.features.stc.richiestapratica;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.StcService;
import it.init.sigepro.rte.types.SportelloType;

public class RichiestaPraticaFromMovimentiParams {

    private StcService stcService;
    private Integer codiceMovimento;
    private boolean mittente;
    private SportelloType sportello;

    public RichiestaPraticaFromMovimentiParams(StcService stcService, VerticalizzazioniService verticalizzazioniService, Integer codiceMovimento,
	    boolean mittente) {

	this.stcService = stcService;
	this.codiceMovimento = codiceMovimento;
	this.mittente = mittente;
	Verticalizzazioniparametri vparam = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC, "NLA_IDNODO");
	if (vparam == null) {
	    throw new RuntimeException("Attenzione! non è stato configurato il parametro NLA_IDNODO della verticalizzazione STC");
	}
	this.sportello = new SportelloType();
	this.sportello.setIdEnte(ORMHelper.getIdcomuneAlias());
	this.sportello.setIdSportello(ORMHelper.getSoftware());
	this.sportello.setIdNodo(vparam.getValore());
    }

    public StcService getStcService() {

	return this.stcService;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public boolean isMittente() {

	return mittente;
    }

    public SportelloType getSportello() {

	return sportello;
    }
}
