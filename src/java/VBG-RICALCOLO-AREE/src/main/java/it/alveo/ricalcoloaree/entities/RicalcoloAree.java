package it.alveo.ricalcoloaree.entities;

import java.util.Date;

import it.alveo.ricalcoloaree.entities.composefields.RicalcoloAreePK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ricalcolo_aree")
public class RicalcoloAree {

    @EmbeddedId
    private RicalcoloAreePK pk1;
    private String descrizione;
    private String stato;
    private Date datafine;
    private Integer dafare;
    private Integer fatti;
    private Integer totali;

    public RicalcoloAreePK getPk1() {

	return pk1;
    }

    public void setPk1(RicalcoloAreePK pk1) {

	this.pk1 = pk1;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public Date getDatafine() {

	return datafine;
    }

    public void setDatafine(Date datafine) {

	this.datafine = datafine;
    }

    public Integer getDafare() {

	return dafare;
    }

    public void setDafare(Integer dafare) {

	this.dafare = dafare;
    }

    public Integer getFatti() {

	return fatti;
    }

    public void setFatti(Integer fatti) {

	this.fatti = fatti;
    }

    public Integer getTotali() {

	return totali;
    }

    public void setTotali(Integer totali) {

	this.totali = totali;
    }
}
