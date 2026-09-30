package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.MercatiLivelloServizio;

public class MercatiLivelloServizioCommand {

    private MercatiLivelloServizio entity;
    private String mercatiusi;
    private MercatiLivelloServizio entityupdate;

    public MercatiLivelloServizio getEntity() {

	return entity;
    }

    public void setEntity(MercatiLivelloServizio entity) {

	this.entity = entity;
    }

    public String getMercatiusi() {

	return mercatiusi;
    }

    public void setMercatiusi(String mercatiusi) {

	this.mercatiusi = mercatiusi;
    }

    public MercatiLivelloServizio getEntityupdate() {

	return entityupdate;
    }

    public void setEntityupdate(MercatiLivelloServizio entityupdate) {

	this.entityupdate = entityupdate;
    }
}
