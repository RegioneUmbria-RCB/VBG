package it.gruppoinit.pal.gp.core.domain.web;


import it.gruppoinit.pal.gp.core.domain.helper.RegolamentoComunaleHelper;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.ParteLocaleSchedaEndoTipo1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParteLocaleSchedaEndoTipo1Command extends BaseCommand {

    private ParteLocaleSchedaEndoTipo1 entity;
    // Utilizzate per acquisire le date nel formate correte
    private Date dataInizioValidita;
    private Date dataFineValidita;
    // campi necessari per inserire un elemento dell'elenco NormativeLocaliEndoTipo1
    private RegolamentoComunaleHelper regolamentoComunaleTipo1;
    // usato per visualizzare la lista (la jsp non riesce a vedere i campi estesi)
    private List<RegolamentoComunaleHelper> regolamentoComunaleHelpers = new ArrayList<RegolamentoComunaleHelper>();

    public ParteLocaleSchedaEndoTipo1Command() {

	super();
	this.regolamentoComunaleTipo1 = new RegolamentoComunaleHelper();
    }

    public ParteLocaleSchedaEndoTipo1 getEntity() {

	return entity;
    }

    public void setEntity(ParteLocaleSchedaEndoTipo1 entity) {

	this.entity = entity;
    }

    public Date getDataInizioValidita() {

	return dataInizioValidita;
    }

    public void setDataInizioValidita(Date dataInizioValidita) {

	this.dataInizioValidita = dataInizioValidita;
    }

    public Date getDataFineValidita() {

	return dataFineValidita;
    }

    public void setDataFineValidita(Date dataFineValidita) {

	this.dataFineValidita = dataFineValidita;
    }

    public RegolamentoComunaleHelper getRegolamentoComunaleTipo1() {

	return regolamentoComunaleTipo1;
    }

    public void setRegolamentoComunaleTipo1(RegolamentoComunaleHelper regolamentoComunaleTipo1) {

	this.regolamentoComunaleTipo1 = regolamentoComunaleTipo1;
    }

    public List<RegolamentoComunaleHelper> getRegolamentoComunaleHelpers() {

	return regolamentoComunaleHelpers;
    }

    public void setRegolamentoComunaleHelpers(List<RegolamentoComunaleHelper> regolamentoComunaleHelpers) {

	this.regolamentoComunaleHelpers = regolamentoComunaleHelpers;
    }
}
