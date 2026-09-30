package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeattivita;
import it.gruppoinit.pal.gp.core.domain.Settori;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class IstanzeattivitaCommand extends BaseCommand {

    private Istanzeattivita entity;
    private Settori settori;
    private Map<String, Boolean> attivitaPresentiMap;
    private Map<String, BigDecimal> attivitaPresentiUMMap;
    private List<Istanzeattivita> istAttSettoreList;
    private Istanze istanza;
    private Boolean isStep2;
    private Boolean isInsMultiplo;
    private Boolean isUnitaMisura;
    private Map<Settori, List<Istanzeattivita>> settoreAttivitaMap;
    private Map<Settori, BigDecimal> settoreTotMQ;

    public IstanzeattivitaCommand() {

	super();
	this.entity = new Istanzeattivita();
	this.settori = new Settori();
	this.istanza = new Istanze();
    }

    public Istanzeattivita getEntity() {

	return entity;
    }

    public void setEntity(Istanzeattivita entity) {

	this.entity = entity;
    }

    public Settori getSettori() {

	return settori;
    }

    public void setSettori(Settori settori) {

	this.settori = settori;
    }

    public Map<String, Boolean> getAttivitaPresentiMap() {

	return attivitaPresentiMap;
    }

    public void setAttivitaPresentiMap(Map<String, Boolean> attivitaPresentiMap) {

	this.attivitaPresentiMap = attivitaPresentiMap;
    }

    public Map<String, BigDecimal> getAttivitaPresentiUMMap() {

	return attivitaPresentiUMMap;
    }

    public void setAttivitaPresentiUMMap(Map<String, BigDecimal> attivitaPresentiUMMap) {

	this.attivitaPresentiUMMap = attivitaPresentiUMMap;
    }

    public List<Istanzeattivita> getIstAttSettoreList() {

	return istAttSettoreList;
    }

    public void setIstAttSettoreList(List<Istanzeattivita> istAttSettoreList) {

	this.istAttSettoreList = istAttSettoreList;
    }

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public Boolean getIsStep2() {

	return isStep2;
    }

    public void setIsStep2(Boolean isStep2) {

	this.isStep2 = isStep2;
    }

    public Boolean getIsInsMultiplo() {

	return isInsMultiplo;
    }

    public void setIsInsMultiplo(Boolean isInsMultiplo) {

	this.isInsMultiplo = isInsMultiplo;
    }

    public Boolean getIsUnitaMisura() {

	return isUnitaMisura;
    }

    public void setIsUnitaMisura(Boolean isUnitaMisura) {

	this.isUnitaMisura = isUnitaMisura;
    }

    public Map<Settori, List<Istanzeattivita>> getSettoreAttivitaMap() {

	return settoreAttivitaMap;
    }

    public void setSettoreAttivitaMap(Map<Settori, List<Istanzeattivita>> settoreAttivitaMap) {

	this.settoreAttivitaMap = settoreAttivitaMap;
    }

    public Map<Settori, BigDecimal> getSettoreTotMQ() {

	return settoreTotMQ;
    }

    public void setSettoreTotMQ(Map<Settori, BigDecimal> settoreTotMQ) {

	this.settoreTotMQ = settoreTotMQ;
    }
}
