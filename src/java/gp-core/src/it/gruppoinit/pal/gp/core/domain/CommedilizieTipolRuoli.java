package it.gruppoinit.pal.gp.core.domain;

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
@Table(name = "COMMEDILIZIE_TIPOL_RUOLI")
public class CommedilizieTipolRuoli implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4012281128899713101L;
    private CommedilizieTipolRuoliId id;
    private CommedilizieTipologie commedilizieTipologie;
    private Ruoli ruolo;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkCommeditipoId", column = @Column(name = "FK_COMMEDITIPO_ID", nullable = false, precision = 4, scale = 0)),
	    @AttributeOverride(name = "fkRuoliId", column = @Column(name = "FK_RUOLI_ID", nullable = false, precision = 4, scale = 0)) })
    public CommedilizieTipolRuoliId getId() {

	return id;
    }

    public void setId(CommedilizieTipolRuoliId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_COMMEDITIPO_ID", referencedColumnName = "CODCOMMTIPOLOGIA", nullable = false, insertable = false, updatable = false) })
    public CommedilizieTipologie getCommedilizieTipologie() {

	return this.commedilizieTipologie;
    }

    public void setCommedilizieTipologie(CommedilizieTipologie commedilizieTipologie) {

	this.commedilizieTipologie = commedilizieTipologie;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_RUOLI_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public Ruoli getRuolo() {

	return ruolo;
    }

    public void setRuolo(Ruoli ruolo) {

	this.ruolo = ruolo;
    }
}
