package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.RegistrazioniFilter;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniImporti;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniInOut;

import java.util.List;

public class RegistrazioniInOutCommand extends BaseCommand {

    public RegistrazioniInOutCommand() {

	this.filter = new RegistrazioniFilter();
    }

    private RegistrazioniFilter filter;
    private RegistrazioniInOut entity;
    private List<RegistrazioniImporti> scadenzeList;

    public void setFilter(RegistrazioniFilter filter) {

	this.filter = filter;
    }

    public RegistrazioniFilter getFilter() {

	return filter;
    }

    public void setEntity(RegistrazioniInOut entity) {

	this.entity = entity;
    }

    public RegistrazioniInOut getEntity() {

	return entity;
    }

    public void setScadenzeList(List<RegistrazioniImporti> scadenzeList) {

	this.scadenzeList = scadenzeList;
    }

    public List<RegistrazioniImporti> getScadenzeList() {

	return scadenzeList;
    }
}
