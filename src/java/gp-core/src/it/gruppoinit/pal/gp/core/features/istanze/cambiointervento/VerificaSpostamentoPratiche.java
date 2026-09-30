package it.gruppoinit.pal.gp.core.features.istanze.cambiointervento;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "errore", "listaRuoli", "listaSchedeDinamiche", "numeroPraticheDaSpostare" })
public class VerificaSpostamentoPratiche {

    @XmlElement(name = "errore")
    private String errore;
    @XmlElement(name = "listaRuoli")
    private List<IdentificativoDescrizioneBean> listaRuoli;
    @XmlElement(name = "listaSchedeDinamiche")
    private List<IdentificativoDescrizioneBean> listaSchedeDinamiche;
    @XmlElement(name = "numeroPraticheDaSpostare")
    private int numeroPraticheDaSpostare;

    public VerificaSpostamentoPratiche(String errore) {

	this.errore = errore;
    }

    public VerificaSpostamentoPratiche() {

	super();
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    @Override
    public String toString() {

	return "VerificaSpostamentoPratiche [errore=" +
		errore +
		", listaRuoli=" +
		listaRuoli +
		", listaSchedeDinamiche=" +
		listaSchedeDinamiche +
		"]";
    }

    public List<IdentificativoDescrizioneBean> getListaRuoli() {

	if (this.listaRuoli == null) {
	    this.listaRuoli = new ArrayList<IdentificativoDescrizioneBean>();
	}
	return listaRuoli;
    }

    public void setListaRuoli(List<IdentificativoDescrizioneBean> listaRuoli) {

	this.listaRuoli = listaRuoli;
    }

    public List<IdentificativoDescrizioneBean> getListaSchedeDinamiche() {

	if (this.listaSchedeDinamiche == null) {
	    this.listaSchedeDinamiche = new ArrayList<IdentificativoDescrizioneBean>();
	}
	return listaSchedeDinamiche;
    }

    public void setListaSchedeDinamiche(List<IdentificativoDescrizioneBean> listaSchedeDinamiche) {

	this.listaSchedeDinamiche = listaSchedeDinamiche;
    }

    public int getNumeroPraticheDaSpostare() {

	return numeroPraticheDaSpostare;
    }

    public void setNumeroPraticheDaSpostare(int numeroPraticheDaSpostare) {

	this.numeroPraticheDaSpostare = numeroPraticheDaSpostare;
    }
}
