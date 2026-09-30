package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import java.math.BigDecimal;
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
import org.hibernate.validator.Digits;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "MERCATI_CFG_CONTI")
public class MercatiCfgConti {

    private PkId id;
    private Conti conti;
    private Attivita attivita;
    private Concessioniuso concessioniuso;
    private Date datainizioval;
    private Date datafineval;
    private Boolean flagMoltiplicaMq;
    private BigDecimal importo;
    private Software software;
    //    private Mercati mercati;
    private MercatiCategorie mercatiCategorie;
    private PosteggiSettori posteggiSettori;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MERCATI_CFG_CONTI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 9, scale = 0)),
	    @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "FK_CONTO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Conti getConti() {

	return conti;
    }

    public void setConti(Conti conti) {

	this.conti = conti;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer contiId;

    @Column(name = "FK_CONTO")
    @SuppressWarnings("unused")
    private Integer getContiId() {

	if (null != this.getConti()) {
	    if (null != this.getConti().getId()) {
		this.contiId = getConti().getId().getCodice();
		return this.contiId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setContiId(Integer contiId) {

	if (null != this.getConti()) {
	    if (null != this.getConti().getId()) {
		this.contiId = getConti().getId().getCodice();
	    }
	}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "FK_CODICEISTAT", referencedColumnName = "CODICEISTAT", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Attivita getAttivita() {

	return attivita;
    }

    public void setAttivita(Attivita attivita) {

	this.attivita = attivita;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private String attivitaId;

    @Column(name = "FK_CODICEISTAT")
    @SuppressWarnings("unused")
    private String getAttivitaId() {

	if (null != this.getAttivita()) {
	    if (null != this.getAttivita().getId()) {
		this.attivitaId = getAttivita().getId().getCodiceistat();
		return this.attivitaId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAttivitaId(String attivitaId) {

	if (null != this.getAttivita()) {
	    if (null != this.getAttivita().getId()) {
		this.attivitaId = getAttivita().getId().getCodiceistat();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "FK_CODICE_CONC_USO", referencedColumnName = "CODICE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Concessioniuso getConcessioniuso() {

	return concessioniuso;
    }

    public void setConcessioniuso(Concessioniuso concessioniuso) {

	this.concessioniuso = concessioniuso;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer concessioniusoId;

    @Column(name = "FK_CODICE_CONC_USO")
    @SuppressWarnings("unused")
    private Integer getConcessioniusoId() {

	if (null != this.getConcessioniuso()) {
	    if (null != this.getConcessioniuso().getId()) {
		this.concessioniusoId = getConcessioniuso().getId().getCodice();
		return this.concessioniusoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setConcessioniusoId(Integer concessioniusoId) {

	if (null != this.getConcessioniuso()) {
	    if (null != this.getConcessioniuso().getId()) {
		this.concessioniusoId = getConcessioniuso().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @Temporal(TemporalType.DATE)
    @Column(name = "DATAINZIOVAL", length = 7)
    public Date getDatainizioval() {

	return datainizioval;
    }

    public void setDatainizioval(Date datainizioval) {

	this.datainizioval = datainizioval;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATAFINEVAL", length = 7)
    public Date getDatafineval() {

	return datafineval;
    }

    public void setDatafineval(Date datafineval) {

	this.datafineval = datafineval;
    }

    @Column(name = "FLAG_MOLTIPLICA_MQ")
    public Boolean getFlagMoltiplicaMq() {

	return flagMoltiplicaMq;
    }

    public void setFlagMoltiplicaMq(Boolean flagMoltiplicaMq) {

	this.flagMoltiplicaMq = flagMoltiplicaMq;
    }

    @NotNull
    @Digits(integerDigits = 3, fractionalDigits = 5)
    @Column(name = "IMPORTO", nullable = false, precision = 5)
    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOFTWARE", nullable = false)
    public Software getSoftware() {

	return this.software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
    //	    @JoinColumn(name = "FK_CODICEMERCATO", referencedColumnName = "CODICEMERCATO", nullable = false, insertable = false, updatable = false) })
    //    public Mercati getMercati() {
    //
    //	return this.mercati;
    //    }
    //
    //    public void setMercati(Mercati mercati) {
    //
    //	this.mercati = mercati;
    //    }
    //
    //    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    //    private Integer mercatiId;
    //
    //    @Column(name = "FK_CODICEMERCATO")
    //    @SuppressWarnings("unused")
    //    private Integer getMercatiId() {
    //
    //	if (null != this.getMercati()) {
    //	    if (null != this.getMercati().getId()) {
    //		this.mercatiId = getMercati().getId().getCodice();
    //		return this.mercatiId;
    //	    }
    //	}
    //	return null;
    //    }
    //
    //    @SuppressWarnings("unused")
    //    private void setMercatiId(Integer mercatiId) {
    //
    //	if (null != this.getMercati()) {
    //	    if (null != this.getMercati().getId()) {
    //		this.mercatiId = getMercati().getId().getCodice();
    //	    }
    //	}
    //    }
    //
    //    // END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_CATEGORIA_MERCATO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MercatiCategorie getMercatiCategorie() {

	return this.mercatiCategorie;
    }

    public void setMercatiCategorie(MercatiCategorie mercatiCategorie) {

	this.mercatiCategorie = mercatiCategorie;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatiCategorieId;

    @Column(name = "FK_CATEGORIA_MERCATO")
    @SuppressWarnings("unused")
    private Integer getMercatiCategorieId() {

	if (null != this.getMercatiCategorie()) {
	    if (null != this.getMercatiCategorie().getId()) {
		this.mercatiCategorieId = getMercatiCategorie().getId().getCodice();
		return this.mercatiCategorieId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMercatiCategorieId(Integer mercatiCategorieId) {

	if (null != this.getMercatiCategorie()) {
	    if (null != this.getMercatiCategorie().getId()) {
		this.mercatiCategorieId = getMercatiCategorie().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_POSTEGGISETTORI_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public PosteggiSettori getPosteggiSettori() {

	return this.posteggiSettori;
    }

    public void setPosteggiSettori(PosteggiSettori posteggiSettori) {

	this.posteggiSettori = posteggiSettori;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer posteggiSettoriId;

    @Column(name = "FK_POSTEGGISETTORI_ID")
    @SuppressWarnings("unused")
    private Integer getPosteggiSettoriId() {

	if (null != this.getPosteggiSettori()) {
	    if (null != this.getPosteggiSettori().getId()) {
		this.posteggiSettoriId = getPosteggiSettori().getId().getCodice();
		return this.posteggiSettoriId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setPosteggiSettoriId(Integer posteggiSettoriId) {

	if (null != this.getPosteggiSettori()) {
	    if (null != this.getPosteggiSettori().getId()) {
		this.posteggiSettoriId = getPosteggiSettori().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////
}
