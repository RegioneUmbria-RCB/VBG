package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.Responsabili;

import java.io.Serializable;

public class ResponsabiliAssegnazioniHelperBean implements Serializable, Comparable<ResponsabiliAssegnazioniHelperBean> {

    /**
     * 
     */
    private static final long serialVersionUID = -4414489674134315234L;
    private Responsabili responsabile;
    private Integer codiceResponsabile;
    private int numIstanze = 0;
    private int peso = 0;
    private double pesoCarrello = 0d;
    private int numMaxIstanze = 0;

    public ResponsabiliAssegnazioniHelperBean(Responsabili responsabile, int numIstanze, int numMaxIstanze) {

	super();
	this.responsabile = responsabile;
	this.numIstanze = numIstanze;
	if (numIstanze == 0) {
	    numIstanze = 1;
	}
	this.numMaxIstanze = numMaxIstanze;
	this.codiceResponsabile = responsabile.getId().getCodice();
	this.peso = responsabile.getPesoCaricoLavoro() == null ? 10 : responsabile.getPesoCaricoLavoro();
    }

    public String toString() {

	String ret = "";
	ret += "Responsabile: " + responsabile.getResponsabile() + ", ";
	ret += "peso: " + this.peso + ", ";
	ret += "numIstanze: " + this.numIstanze + ", ";
	ret += "numMaxIstanze: " + this.numMaxIstanze + ", ";
	ret += "calcolo: " + this.calcola() + ", ";
	return ret;
    }

    public Integer getCodiceResponsabile() {

	return codiceResponsabile;
    }

    public int getNumIstanze() {

	return numIstanze;
    }

    public int getPeso() {

	return peso;
    }

    public double getPesoCarrello() {

	return pesoCarrello;
    }

    public Responsabili getResponsabile() {

	return responsabile;
    }

    public int getNumMaxIstanze() {

	return numMaxIstanze;
    }

    public double calcola() {

	double perc_peso /*D2*/= peso * 10;
	if (perc_peso < 0) {
	    perc_peso = 1;
	}
	double carrello_peso /*E2*/= ((perc_peso) / 100) * numMaxIstanze;
	double perc_peso_carrello /*F2*/= 0;
	if (carrello_peso > 0) {
	    perc_peso_carrello = (numIstanze * 100) / carrello_peso;
	}
	return perc_peso_carrello;
    }

    @Override
    public int compareTo(ResponsabiliAssegnazioniHelperBean o) {

	if (o == null) {
	    if (getPeso() > 0) {
		return 1;
	    }
	}
	double questo = this.calcola();
	double altro = o.calcola();
	if ((questo - altro) == 0) {
	    return 0;
	}
	if ((questo - altro) > 0) {
	    return 1;
	}
	return -1;
    }
}
