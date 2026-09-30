package it.alveo.ricalcoloaree.bean;

import it.alveo.ricalcoloaree.entities.AreeDettagli;
import it.alveo.ricalcoloaree.entities.Istanze;

public class AreeDettagliBean {

    private AreeDettagli areeDettagli;
    private Integer codiceIstanza;
    private String idcomune;
    private Istanze istanza;

    public AreeDettagli getAreeDettagli() {

	return areeDettagli;
    }

    public void setAreeDettagli(AreeDettagli areeDettagli) {

	this.areeDettagli = areeDettagli;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }
}
