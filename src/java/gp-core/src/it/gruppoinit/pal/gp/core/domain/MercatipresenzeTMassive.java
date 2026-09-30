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
@Table(name = "MERCATIPRESENZE_T_MASSIVE")
public class MercatipresenzeTMassive implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3122950444285321771L;
    private PkId id;
    private MercatipresenzeT mercatipresenzeT;
    private MassiveTestata massiveTestata;

    public MercatipresenzeTMassive() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MERCATIPRESENZE_T_MASSIVE.ID") })
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
	    @JoinColumn(name = "FKID_MERCATIPRESENZE_T", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MercatipresenzeT getMercatipresenzeT() {

	return this.mercatipresenzeT;
    }

    public void setMercatipresenzeT(MercatipresenzeT mercatipresenzeT) {

	this.mercatipresenzeT = mercatipresenzeT;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatipresenzeTId;

    @Column(name = "FKID_MERCATIPRESENZE_T")
    private Integer getmercatipresenzeTId() {

	if (null != this.getMercatipresenzeT()) {
	    if (null != this.getMercatipresenzeT().getId()) {
		this.mercatipresenzeTId = this.getMercatipresenzeT().getId().getCodice();
		return this.mercatipresenzeTId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setmercatipresenzeTId(Integer mercatipresenzeTId) {

	if (null != this.getMercatipresenzeT()) {
	    if (null != this.getMercatipresenzeT().getId()) {
		this.mercatipresenzeTId = this.getMercatipresenzeT().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_MASSIVE_TESTATA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MassiveTestata getMassiveTestata() {

	return this.massiveTestata;
    }

    public void setMassiveTestata(MassiveTestata massiveTestata) {

	this.massiveTestata = massiveTestata;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer massiveTestataId;

    @Column(name = "FKID_MASSIVE_TESTATA")
    private Integer getMassiveTestataId() {

	if (null != this.getMassiveTestata()) {
	    if (null != this.getMassiveTestata().getId()) {
		this.massiveTestataId = getMassiveTestata().getId().getCodice();
		return this.massiveTestataId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMassiveTestataId(Integer massiveTestataId) {

	if (null != this.getMassiveTestata()) {
	    if (null != this.getMassiveTestata().getId()) {
		this.massiveTestataId = getMassiveTestata().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////
}
