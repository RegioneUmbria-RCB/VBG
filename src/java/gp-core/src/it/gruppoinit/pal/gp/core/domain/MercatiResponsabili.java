package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

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

@Entity
@Table(name = "MERCATI_RESPONSABILI")
public class MercatiResponsabili {

    private PkId id;
    private Mercati mercato;
    private Responsabili responsabili;

    public MercatiResponsabili() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MERCATI_RESPONSABILI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 4, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_CODICEMERCATO", referencedColumnName = "CODICEMERCATO", nullable = false, insertable = false, updatable = false) })
    public Mercati getMercato() {

	return mercato;
    }

    public void setMercato(Mercati mercato) {

	this.mercato = mercato;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatoId;

    @Column(name = "FK_CODICEMERCATO")
    @SuppressWarnings("unused")
    private Integer getMercatoId() {

	if (null != this.getMercato()) {
	    if (null != this.getMercato().getId()) {
		this.mercatoId = getMercato().getId().getCodice();
		return this.mercatoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMercatoId(Integer mercatoId) {

	if (null != this.getMercato()) {
	    if (null != this.getMercato().getId()) {
		this.mercatoId = getMercato().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_CODICERESPONSABILE", referencedColumnName = "CODICERESPONSABILE", nullable = false, insertable = false, updatable = false) })
    public Responsabili getResponsabili() {

	return this.responsabili;
    }

    public void setResponsabili(Responsabili responsabili) {

	this.responsabili = responsabili;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer responsabiliId;

    @Column(name = "FK_CODICERESPONSABILE")
    @SuppressWarnings("unused")
    private Integer getResponsabiliId() {

	if (null != this.getResponsabili()) {
	    if (null != this.getResponsabili().getId()) {
		this.responsabiliId = getResponsabili().getId().getCodice();
		return this.responsabiliId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setResponsabiliId(Integer responsabiliId) {

	if (null != this.getResponsabili()) {
	    if (null != this.getResponsabili().getId()) {
		this.responsabiliId = getResponsabili().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////
}
