package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "DYN2_MASSIVEFILTRI")
public class Dyn2MassiveFiltri implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8872413455351851231L;
    private Dyn2MassiveFiltriId id;
    private String valore;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkidelaborazione", column = @Column(name = "FKIDELABORAZIONE", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "filtro", column = @Column(name = "FILTRO", nullable = false, length = 50)) })
    public Dyn2MassiveFiltriId getId() {

	return id;
    }

    public void setId(Dyn2MassiveFiltriId id) {

	this.id = id;
    }

    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
