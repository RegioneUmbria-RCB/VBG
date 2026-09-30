package it.alveo.ricalcoloaree.entities;

import it.alveo.ricalcoloaree.entities.composefields.ResponsabiliComuniPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "RESPONSABILICOMUNI")
@IdClass(ResponsabiliComuniPK.class)
public class Responsabilicomuni {

    @Id
    private String codiceresponsabile; // Campo 1 della chiave primaria composta
    @Id
    private String codicecomune; // Campo 2 della chiave primaria composta
    @Id
    private String idcomune;

    public String getCodiceresponsabile() {

	return codiceresponsabile;
    }

    public void setCodiceresponsabile(String codiceresponsabile) {

	this.codiceresponsabile = codiceresponsabile;
    }

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }
}
