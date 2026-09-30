package it.alveo.ricalcoloaree.entities;

import it.alveo.ricalcoloaree.entities.composefields.ResponsabiliPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "responsabili")
@IdClass(ResponsabiliPK.class)
public class Responsabili {

    @Id
    private String idcomune;
    @Id
    private String codiceresponsabile;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getCodiceresponsabile() {

	return codiceresponsabile;
    }

    public void setCodiceresponsabile(String codiceresponsabile) {

	this.codiceresponsabile = codiceresponsabile;
    }
}
