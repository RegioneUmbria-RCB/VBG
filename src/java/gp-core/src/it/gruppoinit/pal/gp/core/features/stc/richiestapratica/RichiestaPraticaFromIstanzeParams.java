package it.gruppoinit.pal.gp.core.features.stc.richiestapratica;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.StcService;
import it.init.sigepro.rte.types.SportelloType;

public class RichiestaPraticaFromIstanzeParams {

    private StcService stcService;
    private Integer codiceIstanza;
    private SportelloType mittente;
    private SportelloType destinatario;

    public RichiestaPraticaFromIstanzeParams(StcService stcService, VerticalizzazioniService verticalizzazioniService, Integer codiceIstanza,
	    SportelloType destinatario) {

	this.stcService = stcService;
	this.codiceIstanza = codiceIstanza;
	this.setMittente(verticalizzazioniService);
	this.destinatario = destinatario;
    }

    public StcService getStcService() {

	return this.stcService;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public SportelloType getMittente() {

	return mittente;
    }

    public SportelloType getDestinatario() {

	return destinatario;
    }

    private void setMittente(VerticalizzazioniService verticalizzazioniService) {

	Verticalizzazioniparametri vparam = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC, "NLA_IDNODO");
	if (vparam == null) {
	    throw new RuntimeException("Attenzione! non è stato configurato il parametro NLA_IDNODO della verticalizzazione STC");
	}
	this.mittente = new SportelloType();
	this.mittente.setIdEnte(ORMHelper.getIdcomuneAlias());
	this.mittente.setIdSportello(ORMHelper.getSoftware());
	this.mittente.setIdNodo(vparam.getValore());
    }
}
