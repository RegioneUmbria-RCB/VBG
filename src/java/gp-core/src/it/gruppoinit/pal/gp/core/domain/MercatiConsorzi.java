package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "MERCATI_CONSORZI")
public class MercatiConsorzi implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3840503389926618657L;
    private PkId id;
    private Mercati mercato;
    private Anagrafe consorzio;
    private Date dataInizioEsercizio;
    private Date dataFineEsercizio;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MERCATI_CONSORZI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 4, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumns({ @JoinColumn(name = "FK_CODICE_MERCATO", referencedColumnName = "CODICEMERCATO", insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false) })
    public Mercati getMercato() {

	return mercato;
    }

    public void setMercato(Mercati mercato) {

	this.mercato = mercato;
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatoId;

    @Column(name = "FK_CODICE_MERCATO")
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

    //END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumns({ @JoinColumn(name = "FK_ANAG_CONSORZIO", referencedColumnName = "CODICEANAGRAFE", insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false) })
    public Anagrafe getConsorzio() {

	return consorzio;
    }

    public void setConsorzio(Anagrafe consorzio) {

	this.consorzio = consorzio;
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer consorzioId;

    @Column(name = "FK_ANAG_CONSORZIO")
    private Integer getConsorzioId() {

	if (null != this.getConsorzio()) {
	    if (null != this.getConsorzio().getId()) {
		this.consorzioId = getConsorzio().getId().getCodice();
		return this.consorzioId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setConsorzioId(Integer consorzioId) {

	if (null != this.getConsorzio()) {
	    if (null != this.getConsorzio().getId()) {
		this.consorzioId = getConsorzio().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_INIZIO_ESERCIZIO", length = 7)
    public Date getDataInizioEsercizio() {

	return dataInizioEsercizio;
    }

    public void setDataInizioEsercizio(Date dataInizioEsercizio) {

	this.dataInizioEsercizio = dataInizioEsercizio;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_FINE_ESERCIZIO", length = 7)
    public Date getDataFineEsercizio() {

	return dataFineEsercizio;
    }

    public void setDataFineEsercizio(Date dataFineEsercizio) {

	this.dataFineEsercizio = dataFineEsercizio;
    }
}
