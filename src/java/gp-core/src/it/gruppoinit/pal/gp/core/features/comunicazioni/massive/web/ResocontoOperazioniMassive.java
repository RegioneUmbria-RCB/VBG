package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "operazioni")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResocontoOperazioniMassive {

    @XmlElement(name = "titolo")
    private String titoloResoconto;
    @XmlElement(name = "totale_operazioni_richieste")
    private int totaleOperazioniRichieste;
    @XmlElement(name = "totale_operazioni_eseguite")
    private int totaleOperazioniEseguite;
    @XmlElement(name = "rapporto")
    private double rapporto;

    public ResocontoOperazioniMassive() {

    }

    public String getTitoloResoconto() {

	return titoloResoconto;
    }

    public void setTitoloResoconto(String titoloResoconto) {

	this.titoloResoconto = titoloResoconto;
    }

    public int getTotaleOperazioniRichieste() {

	return totaleOperazioniRichieste;
    }

    public void setTotaleOperazioniRichieste(int totaleOperazioniRichieste) {

	this.totaleOperazioniRichieste = totaleOperazioniRichieste;
    }

    public int getTotaleOperazioniEseguite() {

	return totaleOperazioniEseguite;
    }

    public void setTotaleOperazioniEseguite(int totaleOperazioniEseguite) {

	this.totaleOperazioniEseguite = totaleOperazioniEseguite;
    }

    public double getRapporto() {

	return ((double) this.totaleOperazioniEseguite / (double) this.totaleOperazioniRichieste) * 100;
    }

    public void setRapporto(double rapporto) {

	this.rapporto = rapporto;
    }

    @Override
    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }
}
