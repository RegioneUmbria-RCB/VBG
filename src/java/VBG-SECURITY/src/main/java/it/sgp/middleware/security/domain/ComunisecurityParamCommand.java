package it.sgp.middleware.security.domain;

import java.util.LinkedHashSet;
import java.util.Set;

public class ComunisecurityParamCommand extends BaseCommand<ComunisecurityParam> {

    Set<ComunisecurityParam> listaParametri = new LinkedHashSet<ComunisecurityParam>();

    public ComunisecurityParamCommand() {

	this.entity = new ComunisecurityParam();
    }

    private ComunisecurityParam entity;

    @Override
    public ComunisecurityParam getEntity() {

	return this.entity;
    }

    @Override
    public void setEntity(ComunisecurityParam entity) {

	this.entity = entity;
    }

    public void setListaParametri(Set<ComunisecurityParam> listaParametri) {

	this.listaParametri = listaParametri;
    }

    public Set<ComunisecurityParam> getListaParametri() {

	return listaParametri;
    }
}
