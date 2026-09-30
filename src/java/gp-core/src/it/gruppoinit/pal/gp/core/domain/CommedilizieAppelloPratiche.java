package it.gruppoinit.pal.gp.core.domain;

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
@Table(name = "COMMEDILIZIE_APPELLO_PRATICHE")
public class CommedilizieAppelloPratiche implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private PkId id;
    private CommedilizieAppello commedilizieAppello;
    private CommissioniedilizieR commissioniedilizieR;

    public CommedilizieAppelloPratiche() {

	this.id = new PkId();
	this.commedilizieAppello = new CommedilizieAppello();
	this.commissioniedilizieR = new CommissioniedilizieR();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "COMMEDILIZIE_APPELLO_PRATICHE.ID") })
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
	    @JoinColumn(name = "FK_APPELLO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public CommedilizieAppello getCommedilizieAppello() {

	return commedilizieAppello;
    }

    public void setCommedilizieAppello(CommedilizieAppello commedilizieAppello) {

	this.commedilizieAppello = commedilizieAppello;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer commedilizieAppelloId;

    @Column(name = "FK_APPELLO")
    @SuppressWarnings("unused")
    private Integer getCommedilizieAppelloId() {

	if (null != this.getCommedilizieAppello()) {
	    if (null != this.getCommedilizieAppello().getId()) {
		this.commedilizieAppelloId = getCommedilizieAppello().getId().getCodice();
		return this.commedilizieAppelloId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setCommedilizieAppelloId(Integer commedilizieAppelloId) {

	if (null != this.getCommedilizieAppello()) {
	    if (null != this.getCommedilizieAppello().getId()) {
		this.commedilizieAppelloId = getCommedilizieAppello().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_COMMEDILIZIER", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public CommissioniedilizieR getCommissioniedilizieR() {

	return commissioniedilizieR;
    }

    public void setCommissioniedilizieR(CommissioniedilizieR commissioniedilizieR) {

	this.commissioniedilizieR = commissioniedilizieR;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer commissioniedilizieRId;

    @Column(name = "FK_COMMEDILIZIER")
    @SuppressWarnings("unused")
    private Integer getCommissioniedilizieRId() {

	if (null != this.getCommissioniedilizieR()) {
	    if (null != this.getCommissioniedilizieR().getId()) {
		this.commissioniedilizieRId = getCommissioniedilizieR().getId().getCodice();
		return this.commissioniedilizieRId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setCommissioniedilizieRId(Integer commissioniedilizieRId) {

	if (null != this.getCommissioniedilizieR()) {
	    if (null != this.getCommissioniedilizieR().getId()) {
		this.commissioniedilizieRId = getCommissioniedilizieR().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////
}
