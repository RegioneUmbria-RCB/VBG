package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "BORSELLINO")
public class Borsellino implements java.io.Serializable {

    private static final long serialVersionUID = 1914510106569009051L;
    private PkId id;
    private Date dataCreazione;
    private String descrizione;
    private Anagrafe anagrafe;
    private String stato;
    private String uuid;
    private BigDecimal saldoTotale;
    private Set<BorsellinoAutorizzazioni> borsellinoAutorizzazioni = new HashSet<BorsellinoAutorizzazioni>(0);
    private Set<BorsellinoMovimenti> borsellinoMovimenti = new HashSet<BorsellinoMovimenti>(0);

    public Borsellino() {

	this.id = new PkId();
	this.anagrafe = new Anagrafe();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BORSELLINO.ID") })
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
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATACREAZIONE")
    public Date getDataCreazione() {

	return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {

	this.dataCreazione = dataCreazione;
    }

    @NotEmpty
    @Length(max = 200)
    @Column(name = "DESCRIZIONE", length = 200)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_ANAGRAFE", referencedColumnName = "CODICEANAGRAFE", insertable = false, updatable = false) })
    public Anagrafe getAnagrafe() {

	return this.anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    private Integer anagrafeID;

    @Column(name = "FKID_ANAGRAFE")
    @SuppressWarnings("unused")
    public Integer getAnagrafeID() {

	if (null != this.getAnagrafe()) {
	    if (null != this.getAnagrafe().getId()) {
		this.anagrafeID = this.getAnagrafe().getId().getCodice();
		return this.anagrafeID;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setAnagrafeID(Integer anagrafeID) {

	if (null != this.getAnagrafe()) {
	    if (null != this.getAnagrafe().getId()) {
		this.anagrafeID = this.getAnagrafe().getId().getCodice();
	    }
	}
    }

    @NotEmpty
    @Length(max = 50)
    @Column(name = "STATO", length = 200)
    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    @Column(name = "UUID", length = 60)
    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }
    
    @Column(name = "SALDO_TOTALE")    
    public BigDecimal getSaldoTotale() {
    
        return saldoTotale;
    }

    
    public void setSaldoTotale(BigDecimal saldoTotale) {
    
        this.saldoTotale = saldoTotale;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "borsellino")
    public Set<BorsellinoAutorizzazioni> getBorsellinoAutorizzazioni() {

	return borsellinoAutorizzazioni;
    }

    public void setBorsellinoAutorizzazioni(Set<BorsellinoAutorizzazioni> borsellinoAutorizzazioni) {

	this.borsellinoAutorizzazioni = borsellinoAutorizzazioni;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "borsellino")
    public Set<BorsellinoMovimenti> getBorsellinoMovimenti() {

	return borsellinoMovimenti;
    }

    public void setBorsellinoMovimenti(Set<BorsellinoMovimenti> borsellinoMovimenti) {

	this.borsellinoMovimenti = borsellinoMovimenti;
    }
}
