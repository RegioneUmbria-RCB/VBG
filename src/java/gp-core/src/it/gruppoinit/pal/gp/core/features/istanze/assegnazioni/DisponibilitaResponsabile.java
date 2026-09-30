package it.gruppoinit.pal.gp.core.features.istanze.assegnazioni;

import it.gruppoinit.pal.gp.core.domain.Istanze;

public class DisponibilitaResponsabile {

    private Integer codiceResponsabile;
    private String nominativo;
    private Integer capienza;
    private Integer percentualeAssegnata;
    private Istanze istanza;
    private Integer idTestata;

    public DisponibilitaResponsabile(Integer codiceResponsabile, String nominativo, Integer capienza, Integer percentualeAssegnata, Istanze istanza,
	    Integer idTestata) {

	this.codiceResponsabile = codiceResponsabile;
	this.nominativo = nominativo;
	this.capienza = capienza;
	this.percentualeAssegnata = percentualeAssegnata;
	this.istanza = istanza;
	this.idTestata = idTestata;
    }

    public Integer getCodiceResponsabile() {

	return codiceResponsabile;
    }

    public void setCodiceResponsabile(Integer codiceResponsabile) {

	this.codiceResponsabile = codiceResponsabile;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public Integer getCapienza() {

	return capienza;
    }

    public void setCapienza(Integer capienza) {

	this.capienza = capienza;
    }

    public Integer getPercentualeAssegnata() {

	return percentualeAssegnata;
    }

    public void setPercentualeAssegnata(Integer percentualeAssegnata) {

	this.percentualeAssegnata = percentualeAssegnata;
    }

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public Integer getIdTestata() {

	return idTestata;
    }

    public void setIdTestata(Integer idTestata) {

	this.idTestata = idTestata;
    }
}
