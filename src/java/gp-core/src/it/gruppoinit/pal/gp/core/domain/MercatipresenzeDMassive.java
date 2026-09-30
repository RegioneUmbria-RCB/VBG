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

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "MERCATIPRESENZE_D_MASSIVE")
public class MercatipresenzeDMassive implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8065482706476411185L;
    private PkId id;
    private MercatipresenzeD mercatipresenzeD;
    private MassiveDettaglio massiveDettaglio;

    public MercatipresenzeDMassive() {

	this.id = new PkId();
    }

    public MercatipresenzeDMassive(Integer id) {

	this.id = new PkId(id);
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MERCATIPRESENZE_D_MASSIVE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_MERCATIPRESENZE_D", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MercatipresenzeD getMercatipresenzeD() {

	return mercatipresenzeD;
    }

    public void setMercatipresenzeD(MercatipresenzeD mercatipresenzeD) {

	this.mercatipresenzeD = mercatipresenzeD;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatipresenzeDId;

    @Column(name = "FKID_MERCATIPRESENZE_D")
    private Integer getmercatipresenzeDId() {

	if (null != this.getMercatipresenzeD()) {
	    if (null != this.getMercatipresenzeD().getId()) {
		this.mercatipresenzeDId = this.getMercatipresenzeD().getId().getCodice();
		return this.mercatipresenzeDId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setmercatipresenzeDId(Integer mercatipresenzeDId) {

	if (null != this.getMercatipresenzeD()) {
	    if (null != this.getMercatipresenzeD().getId()) {
		this.mercatipresenzeDId = this.getMercatipresenzeD().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_MASSIVE_DETTAGLIO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MassiveDettaglio getMassiveDettaglio() {

	return massiveDettaglio;
    }

    public void setMassiveDettaglio(MassiveDettaglio massiveDettaglio) {

	this.massiveDettaglio = massiveDettaglio;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer massiveDettaglioId;

    @Column(name = "FKID_MASSIVE_DETTAGLIO")
    private Integer getMassiveDettaglioId() {

	if (null != this.getMassiveDettaglio()) {
	    if (null != this.getMassiveDettaglio().getId()) {
		this.massiveDettaglioId = this.getMassiveDettaglio().getId().getCodice();
		return this.massiveDettaglioId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMassiveDettaglioId(Integer massiveDettaglioId) {

	if (null != this.getMassiveDettaglio()) {
	    if (null != this.getMassiveDettaglio().getId()) {
		this.massiveDettaglioId = this.getMassiveDettaglio().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////
}
