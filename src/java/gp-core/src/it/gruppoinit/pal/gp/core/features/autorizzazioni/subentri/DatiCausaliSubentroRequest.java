package it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniSubentriCommand;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService.ENUM_COPIA_ONERI;

public class DatiCausaliSubentroRequest {

    private Integer causaleAcquisizione;
    private Integer causaleCessazione;
    private Date dataCessazione;
    private Date dataFineAffitto;
    private ENUM_COPIA_ONERI copiaONERI;

    public DatiCausaliSubentroRequest(AutorizzazioniSubentriCommand command) {

	this.causaleAcquisizione = command.getCausaleAcquisizione().getId().getCodice();
	this.causaleCessazione = command.getCausaleCessazione().getId().getCodice();
	this.dataCessazione = command.getDataCessazione();
	this.dataFineAffitto = command.getFilter().getDataFineAffitto();
	this.copiaONERI = ENUM_COPIA_ONERI.valueOf(command.getSubentriComportamentoOneri());
    }

    public Integer getCausaleAcquisizione() {

	return causaleAcquisizione;
    }

    public Integer getCausaleCessazione() {

	return causaleCessazione;
    }

    public Date getDataCessazione() {

	return dataCessazione;
    }

    public Date getDataFineAffitto() {

	return dataFineAffitto;
    }

    public ENUM_COPIA_ONERI getCopiaONERI() {

	return copiaONERI;
    }
}
