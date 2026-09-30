package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;
import java.util.Calendar;
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
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.BorsellinoMovimentiToStringFactory;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.TipoEnum;

@Entity
@Table(name = "BORSELLINO_MOVIMENTI")
public class BorsellinoMovimenti implements java.io.Serializable {

    private static final long serialVersionUID = 2952583313705763770L;
    private PkId id;
    private Borsellino borsellino;
    private String tipo;
    private Date data;
    private BigDecimal importo;
    private DettPosizioneDebitoria dettPosizioneDebitoria;
    private MercatiD mercatiD;
    private MercatipresenzeT mercatipresenzeT;
    private Autorizzazioni autorizzazione;
    private BorsellinoMovimenti movimentoStorno;
    private BigDecimal creditoIniziale;
    private BigDecimal creditoFinale;
    private Set<BorsellinoMovimentiImporti> importi = new HashSet<BorsellinoMovimentiImporti>(0);

    public BorsellinoMovimenti() {

	this.id = new PkId();
	this.borsellino = new Borsellino();
	this.mercatiD = new MercatiD();
	this.mercatipresenzeT = new MercatipresenzeT();
	this.autorizzazione = new Autorizzazioni();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BORSELLINO_MOVIMENTI.ID") })
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
	    @JoinColumn(name = "FKID_BORSELLINO", referencedColumnName = "ID", insertable = false, updatable = false) })
    public Borsellino getBorsellino() {

	return borsellino;
    }

    public void setBorsellino(Borsellino borsellino) {

	this.borsellino = borsellino;
    }

    private Integer borsellinoID;

    @Column(name = "FKID_BORSELLINO")
    @SuppressWarnings("unused")
    public Integer getBorsellinoID() {

	if (null != this.getBorsellino()) {
	    if (null != this.getBorsellino().getId()) {
		this.borsellinoID = this.getBorsellino().getId().getCodice();
		return this.borsellinoID;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setBorsellinoID(Integer borsellinoID) {

	if (null != this.getBorsellino()) {
	    if (null != this.getBorsellino().getId()) {
		this.borsellinoID = this.getBorsellino().getId().getCodice();
	    }
	}
    }

    @NotEmpty
    @Length(max = 50)
    @Column(name = "TIPO", length = 50)
    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA")
    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    @NotNull
    @Column(name = "IMPORTO", precision = 10, scale = 2)
    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_DETTPOSIZIONEDEBITORIA", referencedColumnName = "ID", insertable = false, updatable = false) })
    public DettPosizioneDebitoria getDettPosizioneDebitoria() {

	return dettPosizioneDebitoria;
    }

    public void setDettPosizioneDebitoria(DettPosizioneDebitoria dettPosizioneDebitoria) {

	this.dettPosizioneDebitoria = dettPosizioneDebitoria;
    }

    private Integer dettPosizioneDebitoriaId;

    @Column(name = "FKID_DETTPOSIZIONEDEBITORIA")
    @SuppressWarnings("unused")
    public Integer getDettPosizioneDebitoriaId() {

	if (null != this.getDettPosizioneDebitoria()) {
	    if (null != this.getDettPosizioneDebitoria().getId()) {
		this.dettPosizioneDebitoriaId = this.getDettPosizioneDebitoria().getId().getCodice();
		return this.dettPosizioneDebitoriaId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setDettPosizioneDebitoriaId(Integer dettPosizioneDebitoriaId) {

	if (null != this.getDettPosizioneDebitoria()) {
	    if (null != this.getDettPosizioneDebitoria().getId()) {
		this.dettPosizioneDebitoriaId = this.getDettPosizioneDebitoria().getId().getCodice();
	    }
	}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_MERCATID", referencedColumnName = "IDPOSTEGGIO", insertable = false, updatable = false) })
    public MercatiD getMercatiD() {

	return mercatiD;
    }

    public void setMercatiD(MercatiD mercatiD) {

	this.mercatiD = mercatiD;
    }

    private Integer mercatiDId;

    @Column(name = "FKID_MERCATID")
    @SuppressWarnings("unused")
    public Integer getMercatiDId() {

	if (null != this.getMercatiD()) {
	    if (null != this.getMercatiD().getId()) {
		this.mercatiDId = this.getMercatiD().getId().getCodice();
		return this.mercatiDId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setMercatiDId(Integer mercatiDId) {

	if (null != this.getMercatiD()) {
	    if (null != this.getMercatiD().getId()) {
		this.mercatiDId = this.getMercatiD().getId().getCodice();
	    }
	}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_MERCATIPRESENZET", referencedColumnName = "ID", insertable = false, updatable = false) })
    public MercatipresenzeT getMercatipresenzeT() {

	return mercatipresenzeT;
    }

    public void setMercatipresenzeT(MercatipresenzeT mercatipresenzeT) {

	this.mercatipresenzeT = mercatipresenzeT;
    }

    private Integer mercatiPresenzeTId;

    @Column(name = "FKID_MERCATIPRESENZET")
    @SuppressWarnings("unused")
    public Integer getMercatiPresenzeTId() {

	if (null != this.getMercatipresenzeT()) {
	    if (null != this.getMercatipresenzeT().getId()) {
		this.mercatiPresenzeTId = this.getMercatipresenzeT().getId().getCodice();
		return this.mercatiPresenzeTId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setMercatiPresenzeTId(Integer mercatiPresenzeTId) {

	if (null != this.getMercatipresenzeT()) {
	    if (null != this.getMercatipresenzeT().getId()) {
		this.mercatiPresenzeTId = this.getMercatipresenzeT().getId().getCodice();
	    }
	}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_AUTORIZZAZIONI", referencedColumnName = "ID", insertable = false, updatable = false) })
    public Autorizzazioni getAutorizzazione() {

	return autorizzazione;
    }

    public void setAutorizzazione(Autorizzazioni autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    private Integer autorizzazioneId;

    @Column(name = "FKID_AUTORIZZAZIONI")
    @SuppressWarnings("unused")
    public Integer getAutorizzazioneId() {

	if (null != this.getAutorizzazione()) {
	    if (null != this.getAutorizzazione().getId()) {
		this.autorizzazioneId = this.getAutorizzazione().getId().getCodice();
		return this.autorizzazioneId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setAutorizzazioneId(Integer autorizzazioneId) {

	if (null != this.getAutorizzazione()) {
	    if (null != this.getAutorizzazione().getId()) {
		this.autorizzazioneId = this.getAutorizzazione().getId().getCodice();
	    }
	}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_MOVIMENTOSTORNO", referencedColumnName = "ID", insertable = false, updatable = false) })
    public BorsellinoMovimenti getMovimentoStorno() {

	return movimentoStorno;
    }

    public void setMovimentoStorno(BorsellinoMovimenti movimentoStorno) {

	this.movimentoStorno = movimentoStorno;
    }

    private Integer movimentoStornoId;

    @Column(name = "FKID_MOVIMENTOSTORNO")
    @SuppressWarnings("unused")
    public Integer getMovimentoStornoId() {

	if (null != this.getMovimentoStorno()) {
	    if (null != this.getMovimentoStorno().getId()) {
		this.movimentoStornoId = this.getMovimentoStorno().getId().getCodice();
		return this.movimentoStornoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setMovimentoStornoId(Integer movimentoStornoId) {

	if (null != this.getMovimentoStorno()) {
	    if (null != this.getMovimentoStorno().getId()) {
		this.movimentoStornoId = this.getMovimentoStorno().getId().getCodice();
	    }
	}
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "borsellinoMovimenti")
    public Set<BorsellinoMovimentiImporti> getImporti() {

	return importi;
    }

    public void setImporti(Set<BorsellinoMovimentiImporti> importi) {

	this.importi = importi;
    }
    
    @Column(name = "CREDITO_INIZIALE")   
    public BigDecimal getCreditoIniziale() {
    
        return creditoIniziale;
    }

    
    public void setCreditoIniziale(BigDecimal creditoIniziale) {
    
        this.creditoIniziale = creditoIniziale;
    }

    @Column(name = "CREDITO_FINALE")
    public BigDecimal getCreditoFinale() {
    
        return creditoFinale;
    }

    
    public void setCreditoFinale(BigDecimal creditoFinale) {
    
        this.creditoFinale = creditoFinale;
    }

    public static BorsellinoMovimenti movimentoUscita(Borsellino borsellino, MercatipresenzeT giornata, Autorizzazioni autorizzazione,
	    MercatiD posteggio, BigDecimal importoDaScalare) {

	if (importoDaScalare == null) {
	    throw new IllegalArgumentException("Non è stato indicato nessun importo in uscita");
	}
	BorsellinoMovimenti retVal = new BorsellinoMovimenti();
	retVal.setAutorizzazione(autorizzazione);
	retVal.setBorsellino(borsellino);
	retVal.setData(Calendar.getInstance().getTime());
	retVal.setDettPosizioneDebitoria(null);
	retVal.setImporto(importoDaScalare.multiply(new BigDecimal(-1)));
	retVal.setMercatiD(posteggio);
	retVal.setMercatipresenzeT(giornata);
	retVal.setMovimentoStorno(null);
	retVal.setTipo(TipoEnum.USCITA.toString());
	return retVal;
    }

    public static BorsellinoMovimenti fromMovimentoDaStornare(BorsellinoMovimenti mov) {

	BorsellinoMovimenti retVal = new BorsellinoMovimenti();
	retVal.setAutorizzazione(mov.getAutorizzazione());
	retVal.setBorsellino(mov.getBorsellino());
	retVal.setData(Calendar.getInstance().getTime());
	retVal.setDettPosizioneDebitoria(null);
	retVal.setImporto(mov.getImporto().multiply(new BigDecimal(-1)));
	retVal.setMercatiD(mov.getMercatiD());
	retVal.setMercatipresenzeT(mov.getMercatipresenzeT());
	retVal.setMovimentoStorno(null);
	retVal.setTipo(TipoEnum.STORNO.toString());
	return retVal;
    }

    @Override
    public String toString() {

	return BorsellinoMovimentiToStringFactory.fromBorsellinoMovimenti(this).toString();
    }
}
