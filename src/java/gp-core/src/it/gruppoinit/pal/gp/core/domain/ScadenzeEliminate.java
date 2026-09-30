package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SCADENZE_ELIMINATE")
public class ScadenzeEliminate implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5484057986778590050L;
    private ScadenzeEliminateId id;
    private String uuidIstanza;
    private String software;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "uuid", column = @Column(name = "UUID", nullable = false, precision = 60, scale = 0)) })
    public ScadenzeEliminateId getId() {

	return id;
    }

    public void setId(ScadenzeEliminateId id) {

	this.id = id;
    }

    @Column(name = "UUID_ISTANZA", length = 60)
    public String getUuidIstanza() {

	return uuidIstanza;
    }

    public void setUuidIstanza(String uuidIstanza) {

	this.uuidIstanza = uuidIstanza;
    }

    @Column(name = "SOFTWARE", length = 2)
    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }
}
