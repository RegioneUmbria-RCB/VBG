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
@Table(name = "MASSIVE_DETT_MESSAGGIMAIL")
public class MassiveDettMessaggimail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3275898631302601807L;
    private PkId id;
    private MassiveDettaglio massiveDettaglio;
    private MessaggiMail messaggiMail;

    public MassiveDettMessaggimail() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MASSIVE_DETT_MESSAGGIMAIL.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 24)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
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

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer massiveDettaglioId;

    @Column(name = "FKID_MASSIVE_D")
    @SuppressWarnings("unused")
    private Integer getMassiveDettaglioId() {

	if (null != this.getMassiveDettaglio() && null != this.getMassiveDettaglio().getId()) {
	    this.massiveDettaglioId = getMassiveDettaglio().getId().getCodice();
	    return this.massiveDettaglioId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMassiveDettaglioId(Integer massiveDettaglioId) {

	if (null != this.getMassiveDettaglio() && null != this.getMassiveDettaglio().getId()) {
	    this.massiveDettaglioId = getMassiveDettaglio().getId().getCodice();
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_MESSAGGIMAIL", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MessaggiMail getMessaggiMail() {

	return messaggiMail;
    }

    public void setMessaggiMail(MessaggiMail messaggiMail) {

	this.messaggiMail = messaggiMail;
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer messaggiMailId;

    @Column(name = "FKID_MESSAGGIMAIL")
    @SuppressWarnings("unused")
    private Integer getMessaggiMailId() {

	if (null != this.getMessaggiMail() && null != this.getMessaggiMail().getId()) {
	    this.messaggiMailId = getMessaggiMail().getId().getCodice();
	    return this.messaggiMailId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMessaggiMailId(Integer messaggiMailId) {

	if (null != this.getMessaggiMail() && null != this.getMessaggiMail().getId()) {
	    this.messaggiMailId = getMessaggiMail().getId().getCodice();
	}
    }
    //END FIX/////////////////////////////////////////////////////
}
