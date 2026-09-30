package it.gruppoinit.pal.gp.core.features.istanze.datidinamici.elaborazionemassiva.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TestataModel {

    private int id;
    private String descrizione;
    private Date dataInizio;
    private Date dataFine;
    private Boolean flgEliminata;

    @XmlElement(name = "id")
    public int getId() {

	return id;
    }

    public void setId(int id) {

	this.id = id;
    }

    @XmlElement(name = "descrizione")
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @XmlElement(name = "data_inizio")
    public Date getDataInizio() {

	return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {

	this.dataInizio = dataInizio;
    }

    @XmlElement(name = "data_fine")
    public Date getDataFine() {

	return dataFine;
    }

    public void setDataFine(Date dataFine) {

	this.dataFine = dataFine;
    }

    @XmlElement(name = "flg_eliminata")
    public Boolean getFlgEliminata() {

	return flgEliminata;
    }

    public void setFlgEliminata(Boolean flgEliminata) {

	this.flgEliminata = flgEliminata;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
