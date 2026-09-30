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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "BLACKLIST_AUTORIZZAZIONI")
public class BlacklistAutorizzazioni implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5988061524894317704L;
    private PkId id;
    private BlacklistMotivi blacklistMotivi;
    private Anagrafe anagrafe;
    private Autorizzazioni autorizzazioni;
    private Boolean flagPrincipale;
    private MercatiUso mercatiUso;

    public BlacklistAutorizzazioni(BlacklistMotivi motivo, Autorizzazioni autorizzazione, Anagrafe anagrafe, MercatiUso mercatiUso,
	    boolean principale) {

	this.setBlacklistMotivi(motivo);
	this.setAutorizzazioni(autorizzazione);
	this.setAnagrafe(anagrafe);
	this.setFlagPrincipale(principale);
	this.setMercatiUso(mercatiUso);
    }

    public BlacklistAutorizzazioni() {

    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BLACKLIST_AUTORIZZAZIONI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_ID_AUTORIZZAZIONE", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public Autorizzazioni getAutorizzazioni() {

	return this.autorizzazioni;
    }

    public void setAutorizzazioni(Autorizzazioni autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer autorizzazioniId;

    @Column(name = "FK_ID_AUTORIZZAZIONE")
    private Integer getAutorizzazioniId() {

	if (this.getAutorizzazioni() == null || this.getAutorizzazioni().getId() == null) {
	    return null;
	}
	this.autorizzazioniId = getAutorizzazioni().getId().getCodice();
	return this.autorizzazioniId;
    }

    @SuppressWarnings("unused")
    private void setAutorizzazioniId(Integer autorizzazioniId) {

	if (this.getAutorizzazioni() == null || this.getAutorizzazioni().getId() == null) {
	    return;
	}
	this.autorizzazioniId = getAutorizzazioni().getId().getCodice();
    }
    // END FIX/////////////////////////////////////////////////////

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = true, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDMERCATIUSO", referencedColumnName = "ID", nullable = true, insertable = false, updatable = false) })
    public MercatiUso getMercatiUso() {

	return this.mercatiUso;
    }

    public void setMercatiUso(MercatiUso mercatiUso) {

	this.mercatiUso = mercatiUso;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatiUsoId;

    @Column(name = "FK_IDMERCATIUSO")
    private Integer getMercatiUsoId() {

	if (this.getMercatiUso() == null || this.getMercatiUso().getId() == null) {
	    return null;
	}
	this.mercatiUsoId = getMercatiUso().getId().getCodice();
	return this.mercatiUsoId;
    }

    @SuppressWarnings("unused")
    private void setMercatiUsoId(Integer mercatiUsoId) {

	if (this.getMercatiUso() == null || this.getMercatiUso().getId() == null) {
	    return;
	}
	this.mercatiUsoId = getMercatiUso().getId().getCodice();
    }
    // END FIX/////////////////////////////////////////////////////

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_ID_BLACKLIST_MOT", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public BlacklistMotivi getBlacklistMotivi() {

	return blacklistMotivi;
    }

    public void setBlacklistMotivi(BlacklistMotivi blacklistMotivi) {

	this.blacklistMotivi = blacklistMotivi;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer blacklistMotiviId;

    @Column(name = "FK_ID_BLACKLIST_MOT")
    private Integer getBlacklistMotiviId() {

	if (this.getBlacklistMotivi() == null || this.getBlacklistMotivi().getId() == null) {
	    return null;
	}
	this.blacklistMotiviId = getBlacklistMotivi().getId().getCodice();
	return this.blacklistMotiviId;
    }

    @SuppressWarnings("unused")
    private void setBlacklistMotiviId(Integer blacklistMotiviId) {

	if (this.getBlacklistMotivi() == null || this.getBlacklistMotivi().getId() == null) {
	    return;
	}
	this.blacklistMotiviId = getBlacklistMotivi().getId().getCodice();
    }

    // END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_CODICEANAGRAFE", referencedColumnName = "CODICEANAGRAFE", insertable = false, updatable = false) })
    public Anagrafe getAnagrafe() {

	return this.anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer anagrafeId;

    @Column(name = "FK_CODICEANAGRAFE")
    private Integer getAnagrafeId() {

	if (this.getAnagrafe() == null || this.getAnagrafe().getId() == null) {
	    return null;
	}
	this.anagrafeId = getAnagrafe().getId().getCodice();
	return this.anagrafeId;
    }

    @SuppressWarnings("unused")
    private void setAnagrafeId(Integer anagrafeId) {

	if (this.getAnagrafe() == null || this.getAnagrafe().getId() == null) {
	    return;
	}
	this.anagrafeId = getAnagrafe().getId().getCodice();
    }

    // END FIX/////////////////////////////////////////////////////
    @Column(name = "FLAG_PRINCIPALE")
    public Boolean getFlagPrincipale() {

	return flagPrincipale;
    }

    public void setFlagPrincipale(Boolean flagPrincipale) {

	this.flagPrincipale = flagPrincipale;
    }
}
