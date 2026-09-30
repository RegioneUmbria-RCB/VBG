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
@Table(name = "COMMEDILIZIE_MASSIVE_T")
public class CommedilizieMassiveT implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3322335234212196207L;
    private PkId id;
    private MassiveTestata massiveTestata;
    private CommissioniedilizieT commissioniedilizieT;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "COMMEDILIZIE_MASSIVE_T.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_TESTATA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MassiveTestata getMassiveTestata() {

	return massiveTestata;
    }

    public void setMassiveTestata(MassiveTestata massiveTestata) {

	this.massiveTestata = massiveTestata;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer massiveTestataId;

    @Column(name = "FKID_TESTATA")
    @SuppressWarnings("unused")
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_COMMEDT_ID", referencedColumnName = "CODICECOMMISSIONE", nullable = false, insertable = false, updatable = false) })
    public CommissioniedilizieT getCommissioniedilizieT() {

	return commissioniedilizieT;
    }

    public void setCommissioniedilizieT(CommissioniedilizieT commissioniedilizieT) {

	this.commissioniedilizieT = commissioniedilizieT;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer commissioniedilizieTId;

    @Column(name = "FKID_COMMEDT_ID")
    @SuppressWarnings("unused")
    private Integer getCommissioniedilizieTId() {

	if (null != this.getCommissioniedilizieT()) {
	    if (null != this.getCommissioniedilizieT().getId()) {
		this.commissioniedilizieTId = getCommissioniedilizieT().getId().getCodice();
		return this.commissioniedilizieTId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setCommissioniedilizieTId(Integer commissioniedilizieTId) {

	if (null != this.getCommissioniedilizieT()) {
	    if (null != this.getCommissioniedilizieT().getId()) {
		this.commissioniedilizieTId = getCommissioniedilizieT().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////
}
