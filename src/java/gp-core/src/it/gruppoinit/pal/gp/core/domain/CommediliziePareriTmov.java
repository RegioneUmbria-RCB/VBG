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

import org.hibernate.validator.NotNull;

@Entity
@Table(name = "COMMEDILIZIE_PARERI_TMOV")
public class CommediliziePareriTmov implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3850933130597123109L;
    private CommediliziePareriTmovId id;
    private Tipimovimento tipimovimento;
    private CommedilizieTipopareri tipopareri;
    private Software software;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "software", column = @Column(name = "SOFTWARE", nullable = false, length = 2)),
	    @AttributeOverride(name = "fkCommedpareriId", column = @Column(name = "FK_COMMEDPARERI_ID", nullable = false, precision = 4, scale = 0)) })
    public CommediliziePareriTmovId getId() {

	return id;
    }

    public void setId(CommediliziePareriTmovId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "TIPOMOVIMENTO", referencedColumnName = "TIPOMOVIMENTO", nullable = false, insertable = false, updatable = false) })
    public Tipimovimento getTipimovimento() {

	return this.tipimovimento;
    }

    public void setTipimovimento(Tipimovimento tipimovimento) {

	this.tipimovimento = tipimovimento;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private String tipimovimentoId;

    @Column(name = "TIPOMOVIMENTO")
    @SuppressWarnings("unused")
    private String getTipimovimentoId() {

	if (null != this.getTipimovimento()) {
	    if (null != this.getTipimovimento().getId()) {
		this.tipimovimentoId = getTipimovimento().getId().getTipomovimento();
		return this.tipimovimentoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setTipimovimentoId(String tipimovimentoId) {

	if (null != this.getTipimovimento()) {
	    if (null != this.getTipimovimento().getId()) {
		this.tipimovimentoId = getTipimovimento().getId().getTipomovimento();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_COMMEDPARERI_ID", referencedColumnName = "CODICE", nullable = false, insertable = false, updatable = false) })
    public CommedilizieTipopareri getTipopareri() {

	return tipopareri;
    }

    public void setTipopareri(CommedilizieTipopareri tipopareri) {

	this.tipopareri = tipopareri;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOFTWARE", nullable = false, insertable = false, updatable = false)
    public Software getSoftware() {

	return software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }
}
