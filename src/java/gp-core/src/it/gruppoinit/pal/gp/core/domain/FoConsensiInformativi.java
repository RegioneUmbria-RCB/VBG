package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

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
import org.hibernate.annotations.Type;

@Entity
@Table(name = "FO_CONSENSI_INFORMATIVI")
public class FoConsensiInformativi {

    private PkId id;
    private String identificativoUtente;
    private Boolean flagConsenso;
    private Date dataAvvenutoConsenso;
    private String contesto;
    private String testoConsenso;
    private ConsensiInformativi consensiInformativi;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "FO_CONSENSI_INFORMATIVI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 24)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Column(name = "IDENTIFICATIVO_UTENTE", length = 50)
    public String getIdentificativoUtente() {

	return identificativoUtente;
    }

    public void setIdentificativoUtente(String identificativoUtente) {

	this.identificativoUtente = identificativoUtente;
    }

    @Column(name = "FLAG_CONSENSO")
    public Boolean getFlagConsenso() {

	return flagConsenso;
    }

    public void setFlagConsenso(Boolean flagConsenso) {

	this.flagConsenso = flagConsenso;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_AVVENUTO_CONSENSO")
    public Date getDataAvvenutoConsenso() {

	return dataAvvenutoConsenso;
    }

    public void setDataAvvenutoConsenso(Date dataAvvenutoConsenso) {

	this.dataAvvenutoConsenso = dataAvvenutoConsenso;
    }

    @Column(name = "CONTESTO", length = 50)
    public String getContesto() {

	return contesto;
    }

    public void setContesto(String contesto) {

	this.contesto = contesto;
    }

    @Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
    @Column(name = "TESTO_CONSENSO")
    public String getTestoConsenso() {

	return testoConsenso;
    }

    public void setTestoConsenso(String testoConsenso) {

	this.testoConsenso = testoConsenso;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_CONS_INFORM_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public ConsensiInformativi getConsensiInformativi() {

	return consensiInformativi;
    }

    public void setConsensiInformativi(ConsensiInformativi consensiInformativi) {

	this.consensiInformativi = consensiInformativi;
    }

    // FIX WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer consensiInformativiId;

    @Column(name = "FK_CONS_INFORM_ID")
    @SuppressWarnings("unused")
    private Integer getConsensiInformativiId() {

	if (null != this.getConsensiInformativi()) {
	    if (null != this.getConsensiInformativi().getId()) {
		this.consensiInformativiId = getConsensiInformativi().getId().getCodice();
		return this.consensiInformativiId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setConsensiInformativiId(Integer consensiInformativiId) {

	if (null != this.getConsensiInformativi()) {
	    if (null != this.getConsensiInformativi().getId()) {
		this.consensiInformativiId = getConsensiInformativi().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////
}
