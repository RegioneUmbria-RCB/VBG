package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model;

import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.core.domain.Dyn2MassiveFiltri;

public class FiltriRicercaTestataModel {

    private String filtro;
    private String valore;

    public FiltriRicercaTestataModel() {

    }

    @XmlElement(name = "filtro")
    public String getFiltro() {

	return filtro;
    }

    public void setFiltro(String filtro) {

	this.filtro = filtro;
    }

    @XmlElement(name = "valore")
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((filtro == null) ? 0 : filtro.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FiltriRicercaTestataModel other = (FiltriRicercaTestataModel) obj;
	if (filtro == null) {
	    if (other.filtro != null) {
		return false;
	    }
	} else if (!filtro.equals(other.filtro))
	    return false;
	return true;
    }

    public FiltriRicercaTestataModel(String filtro, String valore) {

	this.filtro = filtro;
	this.valore = valore;
    }

    public static FiltriRicercaTestataModel fromMassiveFiltri(Dyn2MassiveFiltri filtro) {

	return new FiltriRicercaTestataModel(filtro.getId().getFiltro(), filtro.getValore());
    }
}
