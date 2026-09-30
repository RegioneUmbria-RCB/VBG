package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

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

@Entity
@Table(name = "ALBEROPROC_GRUPPI_SMIST")
public class AlberoprocGruppiSmist implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2813780199589670144L;
    private PkId id;
    private Software software;
    private Alberoproc alberoproc;
    private GruppiEndoprocedimentiT gruppo1;
    private GruppiEndoprocedimentiT gruppo2;
    private GruppiEndoprocedimentiT gruppo3;
    private Tipiprocedure tipiprocedureSCIA;
    private Tipiprocedure tipiprocedureOrdinario;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "ALBEROPROC_GRUPPI_SMIST.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOFTWARE", nullable = false)
    public Software getSoftware() {

	return software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_SCID", referencedColumnName = "SC_ID", nullable = false, insertable = false, updatable = false) })
    public Alberoproc getAlberoproc() {

	return this.alberoproc;
    }

    public void setAlberoproc(Alberoproc alberoproc) {

	this.alberoproc = alberoproc;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer alberoprocId;

    @Column(name = "FK_SCID")
    @SuppressWarnings("unused")
    private Integer getAlberoprocId() {

	if (null != this.getAlberoproc()) {
	    if (null != this.getAlberoproc().getId()) {
		this.alberoprocId = getAlberoproc().getId().getCodice();
		return this.alberoprocId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAlberoprocId(Integer alberoprocId) {

	if (null != this.getAlberoproc()) {
	    if (null != this.getAlberoproc().getId()) {
		this.alberoprocId = getAlberoproc().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_GET_ID_PRIMO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public GruppiEndoprocedimentiT getGruppo1() {

	return this.gruppo1;
    }

    public void setGruppo1(GruppiEndoprocedimentiT gruppo1) {

	this.gruppo1 = gruppo1;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer gruppo1Id;

    @Column(name = "FK_GET_ID_PRIMO")
    @SuppressWarnings("unused")
    private Integer getGruppo1Id() {

	if (null != this.getGruppo1()) {
	    if (null != this.getGruppo1().getId()) {
		this.gruppo1Id = getGruppo1().getId().getCodice();
		return this.gruppo1Id;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setGruppo1Id(Integer gruppo1Id) {

	if (null != this.getGruppo1()) {
	    if (null != this.getGruppo1().getId()) {
		this.gruppo1Id = getGruppo1().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_GET_ID_SECONDO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public GruppiEndoprocedimentiT getGruppo2() {

	return this.gruppo2;
    }

    public void setGruppo2(GruppiEndoprocedimentiT gruppo2) {

	this.gruppo2 = gruppo2;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer gruppo2Id;

    @Column(name = "FK_GET_ID_SECONDO")
    @SuppressWarnings("unused")
    private Integer getGruppo2Id() {

	if (null != this.getGruppo2()) {
	    if (null != this.getGruppo2().getId()) {
		this.gruppo2Id = getGruppo2().getId().getCodice();
		return this.gruppo2Id;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setGruppo2Id(Integer gruppo2Id) {

	if (null != this.getGruppo2()) {
	    if (null != this.getGruppo2().getId()) {
		this.gruppo2Id = getGruppo2().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_GET_ID_TERZO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public GruppiEndoprocedimentiT getGruppo3() {

	return this.gruppo3;
    }

    public void setGruppo3(GruppiEndoprocedimentiT gruppo3) {

	this.gruppo3 = gruppo3;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer gruppo3Id;

    @Column(name = "FK_GET_ID_TERZO")
    @SuppressWarnings("unused")
    private Integer getGruppo3Id() {

	if (null != this.getGruppo3()) {
	    if (null != this.getGruppo3().getId()) {
		this.gruppo3Id = getGruppo3().getId().getCodice();
		return this.gruppo3Id;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setGruppo3Id(Integer gruppo3Id) {

	if (null != this.getGruppo3()) {
	    if (null != this.getGruppo3().getId()) {
		this.gruppo3Id = getGruppo3().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_TIPIPROCEDURE_SCIA", referencedColumnName = "CODICEPROCEDURA", nullable = false, insertable = false, updatable = false) })
    public Tipiprocedure getTipiprocedureSCIA() {

	return this.tipiprocedureSCIA;
    }

    public void setTipiprocedureSCIA(Tipiprocedure tipiprocedureSCIA) {

	this.tipiprocedureSCIA = tipiprocedureSCIA;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer tipiprocedureSCIAId;

    @Column(name = "FK_TIPIPROCEDURE_SCIA")
    @SuppressWarnings("unused")
    private Integer getTipiprocedureSCIAId() {

	if (null != this.getTipiprocedureSCIA()) {
	    if (null != this.getTipiprocedureSCIA().getId()) {
		this.tipiprocedureSCIAId = getTipiprocedureSCIA().getId().getCodice();
		return this.tipiprocedureSCIAId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setTipiprocedureSCIAId(Integer tipiprocedureSCIAId) {

	if (null != this.getTipiprocedureSCIA()) {
	    if (null != this.getTipiprocedureSCIA().getId()) {
		this.tipiprocedureSCIAId = getTipiprocedureSCIA().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    //END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_TIPIPROCEDURE_ORDINARIO", referencedColumnName = "CODICEPROCEDURA", nullable = false, insertable = false, updatable = false) })
    public Tipiprocedure getTipiprocedureOrdinario() {

	return this.tipiprocedureOrdinario;
    }

    public void setTipiprocedureOrdinario(Tipiprocedure tipiprocedureOrdinario) {

	this.tipiprocedureOrdinario = tipiprocedureOrdinario;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer tipiprocedureOrdinarioId;

    @Column(name = "FK_TIPIPROCEDURE_ORDINARIO")
    @SuppressWarnings("unused")
    private Integer getTipiprocedureOrdinarioId() {

	if (null != this.getTipiprocedureOrdinario()) {
	    if (null != this.getTipiprocedureOrdinario().getId()) {
		this.tipiprocedureOrdinarioId = getTipiprocedureOrdinario().getId().getCodice();
		return this.tipiprocedureOrdinarioId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setTipiprocedureOrdinarioId(Integer tipiprocedureOrdinarioId) {

	if (null != this.getTipiprocedureOrdinario()) {
	    if (null != this.getTipiprocedureOrdinario().getId()) {
		this.tipiprocedureOrdinarioId = getTipiprocedureOrdinario().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////
}
