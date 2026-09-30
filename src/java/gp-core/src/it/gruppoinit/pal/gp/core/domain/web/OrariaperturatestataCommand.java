package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Orariaperturatestata;
import it.gruppoinit.pal.gp.core.domain.Tipiapertura;

import java.util.List;

public class OrariaperturatestataCommand extends BaseCommand {

    private Orariaperturatestata entity;
    private List<Tipiapertura> tipiaperturaList;
    private List<Integer> codicetipiaperturaList;

    public OrariaperturatestataCommand() {

	super();
	this.entity = new Orariaperturatestata();
    }

    public Orariaperturatestata getEntity() {

	return entity;
    }

    public void setEntity(Orariaperturatestata entity) {

	this.entity = entity;
    }

    public List<Tipiapertura> getTipiaperturaList() {

	return tipiaperturaList;
    }

    public void setTipiaperturaList(List<Tipiapertura> tipiaperturaList) {

	this.tipiaperturaList = tipiaperturaList;
    }

    public List<Integer> getCodicetipiaperturaList() {

	return codicetipiaperturaList;
    }

    public void setCodicetipiaperturaList(List<Integer> codicetipiaperturaList) {

	this.codicetipiaperturaList = codicetipiaperturaList;
    }
}
