package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement(name = "borsellini")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorsellinoListModel {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "nominativo")
    private String nominativo;
    @XmlElement(name = "idAnagrafica")
    private Integer idAnagrafica;
    @XmlElement(name = "stato")
    private String stato;
    @XmlElement(name = "data_creazione")
    private Date dataCreazione;
    @XmlElement(name = "credito_residuo")
    private BigDecimal creditoResiduo;
    @XmlElement(name = "idAutorizzazioni")
    private Integer idAutorizzazioni;
    @XmlElement(name = "nAutorizzazioni")
    private String nAutorizzazioni;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getNominativo() {

	return nominativo;
    }

    public void setNominativo(String nominativo) {

	this.nominativo = nominativo;
    }

    public Integer getIdAnagrafica() {

	return idAnagrafica;
    }

    public void setIdAnagrafica(Integer idAnagrafica) {

	this.idAnagrafica = idAnagrafica;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public Date getDataCreazione() {

	return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {

	this.dataCreazione = dataCreazione;
    }

    public BigDecimal getCreditoResiduo() {

	return creditoResiduo;
    }

    public void setCreditoResiduo(BigDecimal creditoResiduo) {

	this.creditoResiduo = creditoResiduo;
    }

    public Integer getIdAutorizzazioni() {

	return idAutorizzazioni;
    }

    public void setIdAutorizzazioni(Integer idAutorizzazioni) {

	this.idAutorizzazioni = idAutorizzazioni;
    }

    public String getnAutorizzazioni() {

	return nAutorizzazioni;
    }

    public void setnAutorizzazioni(String nAutorizzazioni) {

	this.nAutorizzazioni = nAutorizzazioni;
    }

    public String toString() {

	try {
	    return Utilities.marshalJsonObject(this, this.getClass(), true, Utilities.JAXB_ENCODING_UTF_8);
	} catch (JAXBException e) {
	    throw new RuntimeException(e);
	}
    }
}
