package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "NATURE_PROCEDURE")
public class NatureProcedure implements Serializable {

    private static final long serialVersionUID = -4918391708242818034L;
    private PkId id;
    private Software software;
    private Tipiprocedure tipiprocedure;
    private String codicenaturabase;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "NATURE_PROCEDURE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 4, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOFTWARE", nullable = false)
    public Software getSoftware() {

	return software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEPROCEDURA", referencedColumnName = "CODICEPROCEDURA", nullable = false, insertable = false, updatable = false) })
    public Tipiprocedure getTipiprocedure() {

	return this.tipiprocedure;
    }

    public void setTipiprocedure(Tipiprocedure tipiprocedure) {

	this.tipiprocedure = tipiprocedure;
    }

    private Integer tipiprocedureId;

    @Column(name = "CODICEPROCEDURA")
    @SuppressWarnings("unused")
    private Integer getTipiprocedureId() {

	if (null != this.getTipiprocedure()) {
	    if (null != this.getTipiprocedure().getId()) {
		this.tipiprocedureId = getTipiprocedure().getId().getCodice();
		return this.tipiprocedureId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setTipiprocedureId(Integer tipiprocedureId) {

	if (null != this.getTipiprocedure()) {
	    if (null != this.getTipiprocedure().getId()) {
		this.tipiprocedureId = getTipiprocedure().getId().getCodice();
	    }
	}
    }

    @NotEmpty
    @Column(name = "CODICENATURABASE", nullable = false, length = 20)
    public String getCodicenaturabase() {

	return codicenaturabase;
    }

    public void setCodicenaturabase(String codicenaturabase) {

	this.codicenaturabase = codicenaturabase;
    }
}
