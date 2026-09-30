package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DYN2_MASSIVESCHEDE")
public class Dyn2Massiveschede implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6625508195049936198L;
    private Dyn2MassiveschedeId id;
    private int ordine;
    private Dyn2Modellit dyn2Modellit;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkidelaborazione", column = @Column(name = "FKIDELABORAZIONE", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "fkD2mtId", column = @Column(name = "FK_D2MT_ID", nullable = false, precision = 10, scale = 0)) })
    public Dyn2MassiveschedeId getId() {

	return id;
    }

    public void setId(Dyn2MassiveschedeId id) {

	this.id = id;
    }

    @Column(name = "ORDINE", nullable = false, precision = 2, scale = 0)
    public int getOrdine() {

	return ordine;
    }

    public void setOrdine(int ordine) {

	this.ordine = ordine;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_D2MT_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public Dyn2Modellit getDyn2Modellit() {

	return this.dyn2Modellit;
    }

    public void setDyn2Modellit(Dyn2Modellit dyn2Modellit) {

	this.dyn2Modellit = dyn2Modellit;
    }
}
