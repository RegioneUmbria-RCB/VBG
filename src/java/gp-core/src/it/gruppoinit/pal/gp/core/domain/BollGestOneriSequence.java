package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

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

import org.hibernate.validator.NotEmpty;

@Entity
@Table(name = "BOLL_GEST_ONERI_SEQUENCE")
public class BollGestOneriSequence implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6894788170461023846L;
    private BollGestOneriSequenceId id;
    private BollGestTestata bollGestTestata;
    private String guid;

    public BollGestOneriSequence() {

	this.id = new BollGestOneriSequenceId();
	this.bollGestTestata = bollGestTestata;
    }

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "idbollegestdett", column = @Column(name = "ID_BOLLEGEST_DETT", nullable = false, length = 10)),
	    @AttributeOverride(name = "idbollegestoneri", column = @Column(name = "ID_BOLLEGEST_ONERI", nullable = false, length = 10)) })
    public BollGestOneriSequenceId getId() {

	return this.id;
    }

    public void setId(BollGestOneriSequenceId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_BOLLGEST_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public BollGestTestata getBollGestTestata() {

	return this.bollGestTestata;
    }

    public void setBollGestTestata(BollGestTestata bollGestTestata) {

	this.bollGestTestata = bollGestTestata;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer bollGestTestataId;

    @Column(name = "FK_BOLLGEST_ID")
    @SuppressWarnings("unused")
    private Integer getBollGestTestataId() {

	if (null != this.getBollGestTestata()) {
	    if (null != this.getBollGestTestata().getId()) {
		this.bollGestTestataId = getBollGestTestata().getId().getCodice();
		return this.bollGestTestataId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setBollGestTestataId(Integer bollGestTestataId) {

	if (null != this.getBollGestTestata()) {
	    if (null != this.getBollGestTestata().getId()) {
		this.bollGestTestataId = getBollGestTestata().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @NotEmpty
    @Column(name = "GUIID", length = 60)
    public String getGuid() {

	return guid;
    }

    public void setGuid(String guid) {

	this.guid = guid;
    }
}
