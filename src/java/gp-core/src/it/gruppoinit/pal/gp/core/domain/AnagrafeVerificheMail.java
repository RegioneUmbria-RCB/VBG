package it.gruppoinit.pal.gp.core.domain;

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

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "ANAGRAFE_VERIFICHE_MAIL")
public class AnagrafeVerificheMail {

    private PkId id;
    private Anagrafe anagrafe;
    private String codiceVerifica;
    private String nuovaEmail;
    private Date dataScadenza;
    private Date dataverifica;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "ANAGRAFE_VERIFICHE_MAIL.ID") })
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
	    @JoinColumn(name = "FK_CODICEANAGRAFE", referencedColumnName = "CODICEANAGRAFE", nullable = false, insertable = false, updatable = false) })
    public Anagrafe getAnagrafe() {

	return this.anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer anagrafeId;

    @Column(name = "FK_CODICEANAGRAFE")
    @SuppressWarnings("unused")
    public Integer getAnagrafeId() {

	if (null != this.getAnagrafe()) {
	    if (null != this.getAnagrafe().getId()) {
		this.anagrafeId = getAnagrafe().getId().getCodice();
		return this.anagrafeId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setAnagrafeId(Integer anagrafeId) {

	if (null != this.getAnagrafe()) {
	    if (null != this.getAnagrafe().getId()) {
		this.anagrafeId = getAnagrafe().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @Column(name = "CODICE_VERIFICA", length = 320)
    public String getCodiceVerifica() {

	return codiceVerifica;
    }

    public void setCodiceVerifica(String codiceVerifica) {

	this.codiceVerifica = codiceVerifica;
    }

    @Column(name = "NUOVA_EMAIL", length = 320)
    public String getNuovaEmail() {

	return nuovaEmail;
    }

    public void setNuovaEmail(String nuovaEmail) {

	this.nuovaEmail = nuovaEmail;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_SCADENZA")
    public Date getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_VERIFICA")
    public Date getDataverifica() {

	return dataverifica;
    }

    public void setDataverifica(Date dataverifica) {

	this.dataverifica = dataverifica;
    }
}
