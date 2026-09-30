package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "autorizzazione")
@XmlAccessorType(XmlAccessType.FIELD)
public class AutorizzazioniModel {

    @XmlElement(name = "id_borsellino")
    private Integer idBorsellino;
    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "numero_autorizzazione")
    private String numeroAutorizzazione;

    public AutorizzazioniModel() {

    }

    public Integer getIdBorsellino() {

	return idBorsellino;
    }

    public void setIdBorsellino(Integer idBorsellino) {

	this.idBorsellino = idBorsellino;
    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNumeroAutorizzazione() {

	return numeroAutorizzazione;
    }

    public void setNumeroAutorizzazione(String numeroAutorizzazione) {

	this.numeroAutorizzazione = numeroAutorizzazione;
    }

    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }
}
