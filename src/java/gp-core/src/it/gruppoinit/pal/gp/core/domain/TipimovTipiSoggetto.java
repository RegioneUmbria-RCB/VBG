package it.gruppoinit.pal.gp.core.domain;

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
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "TIPIMOV_TIPISOGGETTO")
public class TipimovTipiSoggetto implements Serializable {

    private static final long serialVersionUID = -3530140923269645432L;
    private PkId id;
    private Tipimovimento tipimovimento;
    private Tipisoggetto tipisoggetto;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDTIPOMOVIMENTO", referencedColumnName = "TIPOMOVIMENTO", nullable = false, insertable = false, updatable = false) })
    public Tipimovimento getTipimovimento() {

	return this.tipimovimento;
    }

    public void setTipimovimento(Tipimovimento tipimovimento) {

	this.tipimovimento = tipimovimento;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDTIPOSOGGETTO", referencedColumnName = "CODICETIPOSOGGETTO", nullable = false, insertable = false, updatable = false) })
    public Tipisoggetto getTipisoggetto() {

	return this.tipisoggetto;
    }

    public void setTipisoggetto(Tipisoggetto tipisoggetto) {

	this.tipisoggetto = tipisoggetto;
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "TIPIMOV_TIPISOGGETTO.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private String tipimovimentoId;

    @Column(name = "FK_IDTIPOMOVIMENTO")
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

    private Integer tipisoggettoId;

    @Column(name = "FK_IDTIPOSOGGETTO")
    @SuppressWarnings("unused")
    private Integer getTipisoggettoId() {

	if (null != this.getTipisoggetto()) {
	    if (null != this.getTipisoggetto().getId()) {
		this.tipisoggettoId = getTipisoggetto().getId().getCodice();
		return this.tipisoggettoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setTipisoggettoId(Integer tipisoggettoId) {

	if (null != this.getTipisoggetto()) {
	    if (null != this.getTipisoggetto().getId()) {
		this.tipisoggettoId = getTipisoggetto().getId().getCodice();
	    }
	}
    }
    //END FIX///////////////////////////////////////////////////// 
}
