package it.alveo.ricalcoloaree.entities;

import it.alveo.ricalcoloaree.entities.composefields.AreePK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "aree")
@IdClass(AreePK.class)
public class Aree {

    @Id
    private String idcomune;
    @Id
    private Integer codicearea;
    private String software;
    private String denominazione;
    @ManyToOne
    @JoinColumn(name = "CODICECOMUNE", referencedColumnName = "CODICECOMUNE")
    private Comuni comune;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public Integer getCodicearea() {

	return codicearea;
    }

    public void setCodicearea(Integer codicearea) {

	this.codicearea = codicearea;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    public String getDenominazione() {

	return denominazione;
    }

    public void setDenominazione(String denominazione) {

	this.denominazione = denominazione;
    }
}
