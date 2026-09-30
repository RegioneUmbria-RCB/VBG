package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import java.io.Serializable;
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

@Entity
@Table(name = "MERCATIPRESENZE_T_PRENOT")
public class MercatipresenzeTPrenot implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4009361120862313523L;
    private PkId id;
    private Date dataInserimento;
    private MercatipresenzeT mercatipresenzeT;
    private MercatiD mercatiD;
    private Anagrafe anagrafe;
    private String note;

    public MercatipresenzeTPrenot() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MERCATIPRESENZE_T_PRENOT.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_INSERIMENTO")
    public Date getDataInserimento() {

	return dataInserimento;
    }

    public void setDataInserimento(Date dataInserimento) {

	this.dataInserimento = dataInserimento;
    }

    @Column(name = "NOTE", length = 4000)
    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKIDMERCATIPRESENZET", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MercatipresenzeT getMercatipresenzeT() {

	return this.mercatipresenzeT;
    }

    public void setMercatipresenzeT(MercatipresenzeT mercatipresenzeT) {

	this.mercatipresenzeT = mercatipresenzeT;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatipresenzeTId;

    @Column(name = "FKIDMERCATIPRESENZET")
    @SuppressWarnings("unused")
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
	    @JoinColumn(name = "FKIDPOSTEGGIO", referencedColumnName = "IDPOSTEGGIO", nullable = false, insertable = false, updatable = false) })
    public MercatiD getMercatiD() {

	return this.mercatiD;
    }

    public void setMercatiD(MercatiD mercatiD) {

	this.mercatiD = mercatiD;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatiDId;

    @Column(name = "FKIDPOSTEGGIO")
    @SuppressWarnings("unused")
    private Integer getMercatiDId() {

	if (null != this.getMercatiD()) {
	    if (null != this.getMercatiD().getId()) {
		this.mercatiDId = getMercatiD().getId().getCodice();
		return this.mercatiDId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMercatiDId(Integer mercatiDId) {

	if (null != this.getMercatiD()) {
	    if (null != this.getMercatiD().getId()) {
		this.mercatiDId = getMercatiD().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "CODICEANAGRAFE", referencedColumnName = "CODICEANAGRAFE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Anagrafe getAnagrafe() {

	return this.anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer anagrafeId;

    @Column(name = "CODICEANAGRAFE")
    @SuppressWarnings("unused")
    private Integer getAnagrafeId() {

	if (null != this.getAnagrafe()) {
	    if (null != this.getAnagrafe().getId()) {
		this.anagrafeId = getAnagrafe().getId().getCodice();
		return this.anagrafeId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAnagrafeId(Integer anagrafeId) {

	if (null != this.getAnagrafe()) {
	    if (null != this.getAnagrafe().getId()) {
		this.anagrafeId = getAnagrafe().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////
}
