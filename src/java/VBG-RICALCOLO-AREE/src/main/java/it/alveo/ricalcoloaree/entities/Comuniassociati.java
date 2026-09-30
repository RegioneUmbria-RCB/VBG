package it.alveo.ricalcoloaree.entities;

import it.alveo.ricalcoloaree.entities.composefields.ComuniassociatiPK;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "comuniassociati")
@IdClass(ComuniassociatiPK.class)
public class Comuniassociati {

    @Id
    private String idcomune;
    @Id
    private String codicecomune;

    public String getIdcomune() {

	return idcomune;
    }

    public void setIdcomune(String idcomune) {

	this.idcomune = idcomune;
    }

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }
}
