package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Tipiapertura;
import it.gruppoinit.pal.gp.core.domain.Tipiorario;

import java.util.List;

public class TipiorarioCommand extends BaseCommand {

    private Tipiorario entity;
    private List<Integer> codicetipiaperturaList;
    private List<Tipiapertura> tipiaperturaList;

    public Tipiorario getEntity() {

	return entity;
    }

    public void setEntity(Tipiorario entity) {

	this.entity = entity;
    }

    public List<Integer> getCodicetipiaperturaList() {

	return codicetipiaperturaList;
    }

    public void setCodicetipiaperturaList(List<Integer> codicetipiaperturaList) {

	this.codicetipiaperturaList = codicetipiaperturaList;
    }

    public List<Tipiapertura> getTipiaperturaList() {

	return tipiaperturaList;
    }

    public void setTipiaperturaList(List<Tipiapertura> tipiaperturaList) {

	this.tipiaperturaList = tipiaperturaList;
    }
}
