package it.alveo.ricalcoloaree.entities;

import java.util.Date;

import it.alveo.ricalcoloaree.entities.composefields.PkId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "istanze")
public class Istanze {

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "idcomune")),
	    @AttributeOverride(name = "codice", column = @Column(name = "codiceistanza")) })
    private PkId pkId;
    private String software;
    private String codicecomune;
    private Date data;

    public PkId getPkId() {

	return pkId;
    }

    public void setPkId(PkId pkId) {

	this.pkId = pkId;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }
}
