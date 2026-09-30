package it.gruppoinit.pal.gp.core.features.rateizzazioni;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;

public class TemplateRata {

    private Tipicausalioneri tipicausalioneri;
    private Istanze istanze;
    private Date dataOnere;
    private boolean flagEntrataUscita;
    private String note;
    private String nrDocumento;
    private boolean flagOnereRateizzato = true;
    private Amministrazioni amministrazioni;
    private Inventarioprocedimenti inventarioprocedimenti;
    private String guidRateizzazione;

    public TemplateRata(Istanzeoneri io, String guidRaggruppamento) {

	this();
	this.tipicausalioneri = io.getTipicausalioneri();
	this.istanze = io.getIstanza();
	this.dataOnere = io.getData();
	this.flagEntrataUscita = io.getFlentratauscita() == null ? false : io.getFlentratauscita().booleanValue();
	this.note = io.getNote();
	this.nrDocumento = io.getNrDocumento();
	this.amministrazioni = io.getAmministrazioni();
	this.inventarioprocedimenti = io.getInventarioprocedimenti();
	this.guidRateizzazione = (StringUtils.isEmpty(guidRaggruppamento)) ? this.guidRateizzazione = UUID.randomUUID().toString()
		: guidRaggruppamento;
    }

    public String getGuidRateizzazione() {

	return guidRateizzazione;
    }

    public Amministrazioni getAmministrazioni() {

	return amministrazioni;
    }

    public Inventarioprocedimenti getInventarioprocedimenti() {

	return inventarioprocedimenti;
    }

    private TemplateRata() {

	super();
    }

    public Tipicausalioneri getTipicausalioneri() {

	return tipicausalioneri;
    }

    public Istanze getIstanze() {

	return istanze;
    }

    public Date getDataOnere() {

	return dataOnere;
    }

    public boolean isFlagEntrataUscita() {

	return flagEntrataUscita;
    }

    public String getNote() {

	return note;
    }

    public String getNrDocumento() {

	return nrDocumento;
    }

    public boolean isFlagOnereRateizzato() {

	return flagOnereRateizzato;
    }
}
