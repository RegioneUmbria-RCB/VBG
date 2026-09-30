package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;

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
@Table(name = "BORSELLINO_MOVIMENTI_IMPORTI")
public class BorsellinoMovimentiImporti implements java.io.Serializable {

    private static final long serialVersionUID = 8124535325902416147L;
    private PkId id;
    private BorsellinoMovimenti borsellinoMovimenti;
    private Conti conto;
    private BigDecimal importo;

    public BorsellinoMovimentiImporti() {

	this.id = new PkId();
	this.borsellinoMovimenti = new BorsellinoMovimenti();
	this.conto = new Conti();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BORSELLINO_MOVIMENTI_IMPORTI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_BORSELLINOMOVIMENTI", referencedColumnName = "ID", insertable = false, updatable = false) })
    public BorsellinoMovimenti getBorsellinoMovimenti() {

	return borsellinoMovimenti;
    }

    public void setBorsellinoMovimenti(BorsellinoMovimenti borsellinoMovimenti) {

	this.borsellinoMovimenti = borsellinoMovimenti;
    }

    private Integer borsellinoMovimentiID;

    @Column(name = "FKID_BORSELLINOMOVIMENTI")
    @SuppressWarnings("unused")
    public Integer getBorsellinoMovimentiID() {

	if (null != this.getBorsellinoMovimenti()) {
	    if (null != this.getBorsellinoMovimenti().getId()) {
		this.borsellinoMovimentiID = this.getBorsellinoMovimenti().getId().getCodice();
		return this.borsellinoMovimentiID;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setBorsellinoMovimentiID(Integer borsellinoMovimentiID) {

	if (null != this.getBorsellinoMovimenti()) {
	    if (null != this.getBorsellinoMovimenti().getId()) {
		this.borsellinoMovimentiID = this.getBorsellinoMovimenti().getId().getCodice();
	    }
	}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_CONTI", referencedColumnName = "ID", insertable = false, updatable = false) })
    public Conti getConto() {

	return conto;
    }

    public void setConto(Conti conto) {

	this.conto = conto;
    }

    private Integer contoId;

    @Column(name = "FKID_CONTI")
    @SuppressWarnings("unused")
    public Integer getContoId() {

	if (null != this.getConto()) {
	    if (null != this.getConto().getId()) {
		this.contoId = this.getConto().getId().getCodice();
		return this.contoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setContoId(Integer contoId) {

	if (null != this.getConto()) {
	    if (null != this.getConto().getId()) {
		this.contoId = this.getConto().getId().getCodice();
	    }
	}
    }

    @NotNull
    @Column(name = "IMPORTO", precision = 10, scale = 2)
    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }
}
