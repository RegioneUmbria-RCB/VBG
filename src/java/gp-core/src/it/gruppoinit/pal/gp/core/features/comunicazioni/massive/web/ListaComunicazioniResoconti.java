package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "comunicazione")
@XmlAccessorType(XmlAccessType.FIELD)
public class ListaComunicazioniResoconti<TStatoConfEnum extends Enum<?>> {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "data")
    private Date data;
    @XmlElement(name = "data_string")
    private String dataString;
    @XmlElement(name = "operazioni")
    private List<ResocontoOperazioniMassive> resocontoOperazioniMassive;
    @XmlElement(name = "stato_conclusivo")
    private TStatoConfEnum statoConclusivo;

    public ListaComunicazioniResoconti() {

	this.resocontoOperazioniMassive = new ArrayList<ResocontoOperazioniMassive>();
    }

    public ListaComunicazioniResoconti(TStatoConfEnum statoConclusivo) {

	this.resocontoOperazioniMassive = new ArrayList<ResocontoOperazioniMassive>();
	this.statoConclusivo = statoConclusivo;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
	this.dataString = Utilities.formatDate(this.data, false);
    }

    public List<ResocontoOperazioniMassive> getResocontoOperazioniMassive() {

	return resocontoOperazioniMassive;
    }

    public void setResocontoOperazioniMassive(List<ResocontoOperazioniMassive> resocontoOperazioniMassive) {

	this.resocontoOperazioniMassive = resocontoOperazioniMassive;
    }

    @XmlElement(name = "totale_operazioni_richieste")
    public int getTotaleOperazioniRichieste() {

	int totale = 0;
	for (ResocontoOperazioniMassive operazione : resocontoOperazioniMassive) {
	    totale += operazione.getTotaleOperazioniEseguite();
	}
	return totale;
    }

    @XmlElement(name = "totale_operazioni_completate")
    public int getTotaleOperazioniCompletate() {

	for (ResocontoOperazioniMassive operazione : resocontoOperazioniMassive) {
	    if (operazione.getTitoloResoconto().equals(this.statoConclusivo.name())) {
		return operazione.getTotaleOperazioniEseguite();
	    }
	}
	return 0;
    }

    @XmlElement(name = "completa")
    public boolean isCompleta() {

	return this.getTotaleOperazioniRichieste() == this.getTotaleOperazioniCompletate();
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
