package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DYN2_MASSIVEISTANZE")
public class Dyn2Massiveistanze implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7475105676907528880L;
    private Dyn2MassiveistanzeId id;
    private String statoesecuzione;
    private String log;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkidelaborazione", column = @Column(name = "FKIDELABORAZIONE", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "codiceistanza", column = @Column(name = "CODICEISTANZA", nullable = false, precision = 6, scale = 0)) })
    public Dyn2MassiveistanzeId getId() {

	return id;
    }

    public void setId(Dyn2MassiveistanzeId id) {

	this.id = id;
    }

    @Column(name = "STATOESECUZIONE", length = 50)
    public String getStatoesecuzione() {

	return statoesecuzione;
    }

    public void setStatoesecuzione(String statoesecuzione) {

	this.statoesecuzione = statoesecuzione;
    }

    @Column(name = "LOG", length = 4000)
    public String getLog() {

	return log;
    }

    public void setLog(String log) {

	this.log = log;
    }
}
