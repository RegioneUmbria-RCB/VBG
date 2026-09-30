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
@Table(name = "COMMEDILIZIE_MASSIVE_D")
public class CommedilizieMassiveD implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 141005237216364870L;
    private PkId id;
    private MassiveDettaglio massiveDettaglio;
    private CommedilizieAppello commedilizieAppello;

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
	    @JoinColumn(name = "FKID_MASSIVE_D", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MassiveDettaglio getMassiveDettaglio() {

	return massiveDettaglio;
    }

    public void setMassiveDettaglio(MassiveDettaglio massiveDettaglio) {

	this.massiveDettaglio = massiveDettaglio;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer massiveDettaglioId;

    @Column(name = "FKID_MASSIVE_D")
    @SuppressWarnings("unused")
    private Integer getMassiveDettaglioId() {

	if (null != this.getMassiveDettaglio()) {
	    if (null != this.getMassiveDettaglio().getId()) {
		this.massiveDettaglioId = getMassiveDettaglio().getId().getCodice();
		return this.massiveDettaglioId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMassiveDettaglioId(Integer massiveDettaglioId) {

	if (null != this.getMassiveDettaglio()) {
	    if (null != this.getMassiveDettaglio().getId()) {
		this.massiveDettaglioId = getMassiveDettaglio().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_APPELLO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public CommedilizieAppello getCommedilizieAppello() {

	return commedilizieAppello;
    }

    public void setCommedilizieAppello(CommedilizieAppello commedilizieAppello) {

	this.commedilizieAppello = commedilizieAppello;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer commedilizieAppelloId;

    @Column(name = "FKID_APPELLO")
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
}
