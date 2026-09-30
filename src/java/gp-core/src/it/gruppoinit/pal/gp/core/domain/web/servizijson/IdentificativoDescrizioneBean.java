package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "descrizione", "descrizioneEstesa" })
public class IdentificativoDescrizioneBean {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "descrizione")
    private String descrizione;
    @XmlElement(name = "descrizioneEstesa")
    private String descrizioneEstesa;

    public IdentificativoDescrizioneBean() {

	super();
    }

    public IdentificativoDescrizioneBean(Integer id, String descrizione) {

	this();
	this.id = id;
	this.descrizione = descrizione;
    }

    public IdentificativoDescrizioneBean(Integer id, String descrizione, String descrizioneEstesa) {

	this();
	this.id = id;
	this.descrizione = descrizione;
	this.descrizioneEstesa = descrizioneEstesa;
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

    public String getDescrizioneEstesa() {

	return descrizioneEstesa;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {

	this.descrizioneEstesa = descrizioneEstesa;
    }
}
