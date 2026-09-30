package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazionibase;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.VerticalizzazioniparametriHelper;

import java.util.ArrayList;
import java.util.List;

public class VerticalizzazionibaseCommand extends BaseCommand {

    private Verticalizzazionibase entity;
    private Verticalizzazioni verticalizzazioni;
    // utilizzata per la visualizzazione dei parametri base configurati per una verticalizzazione base
    // in modo da poter creare una sezione per ogno software
    private List<VerticalizzazioniparametriHelper> verticalizzazioniparametriHelpers = new ArrayList<VerticalizzazioniparametriHelper>();
    private Verticalizzazioniparametri verticalizzazioniparametri;

    public VerticalizzazionibaseCommand() {

	super();
	this.entity = new Verticalizzazionibase();
	this.verticalizzazioni = new Verticalizzazioni();
	this.verticalizzazioniparametri = new Verticalizzazioniparametri();
    }

    public Verticalizzazionibase getEntity() {

	return entity;
    }

    public void setEntity(Verticalizzazionibase entity) {

	this.entity = entity;
    }

    public Verticalizzazioni getVerticalizzazioni() {

	return verticalizzazioni;
    }

    public void setVerticalizzazioni(Verticalizzazioni verticalizzazioni) {

	this.verticalizzazioni = verticalizzazioni;
    }

    public List<VerticalizzazioniparametriHelper> getVerticalizzazioniparametriHelpers() {

	return verticalizzazioniparametriHelpers;
    }

    public void setVerticalizzazioniparametriHelpers(List<VerticalizzazioniparametriHelper> verticalizzazioniparametriHelpers) {

	this.verticalizzazioniparametriHelpers = verticalizzazioniparametriHelpers;
    }

    public Verticalizzazioniparametri getVerticalizzazioniparametri() {

	return verticalizzazioniparametri;
    }

    public void setVerticalizzazioniparametri(Verticalizzazioniparametri verticalizzazioniparametri) {

	this.verticalizzazioniparametri = verticalizzazioniparametri;
    }
}
