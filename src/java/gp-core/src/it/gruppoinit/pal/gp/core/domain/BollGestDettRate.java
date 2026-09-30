package it.gruppoinit.pal.gp.core.domain;

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
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "BOLL_GEST_DETT_RATE")
public class BollGestDettRate implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5416504521257445024L;
    private PkId id;
    private BollGestDettaglio bollGestDettaglio;
    private BollGestTestata bollGestTestata;
    private Integer numeroRata;
    private Date scadenza;
    private BigDecimal importoTotale;
    private DettPosizioneDebitoria dettPosizioneDebitoria;

    public BollGestDettRate() {

	this.id = new PkId();
	this.bollGestDettaglio = new BollGestDettaglio();
	this.bollGestTestata = new BollGestTestata();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BOLL_GEST_DETT_RATE.ID") })
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
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_BOLLGESTDET_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public BollGestDettaglio getBollGestDettaglio() {

	return bollGestDettaglio;
    }

    public void setBollGestDettaglio(BollGestDettaglio bollGestDettaglio) {

	this.bollGestDettaglio = bollGestDettaglio;
    }

    private Integer bollGestDettaglioId;

    @Column(name = "FK_BOLLGESTDET_ID")
    public Integer getBollGestDettaglioId() {

	if (this.getBollGestDettaglio() != null && this.getBollGestDettaglio().getId() != null) {
	    this.bollGestDettaglioId = getBollGestDettaglio().getId().getCodice();
	    return this.bollGestDettaglioId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setBollGestDettaglioId(Integer bollGestDettaglioId) {

	if (this.getBollGestDettaglio() != null && this.getBollGestDettaglio().getId() != null) {
	    this.bollGestDettaglioId = getBollGestDettaglio().getId().getCodice();
	}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_BOLLGEST_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public BollGestTestata getBollGestTestata() {

	return this.bollGestTestata;
    }

    public void setBollGestTestata(BollGestTestata bollGestTestata) {

	this.bollGestTestata = bollGestTestata;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer bollGestTestataId;

    @Column(name = "FK_BOLLGEST_ID")
    @SuppressWarnings("unused")
    private Integer getBollGestTestataId() {

	if (null != this.getBollGestTestata()) {
	    if (null != this.getBollGestTestata().getId()) {
		this.bollGestTestataId = getBollGestTestata().getId().getCodice();
		return this.bollGestTestataId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setBollGestTestataId(Integer bollGestTestataId) {

	if (null != this.getBollGestTestata()) {
	    if (null != this.getBollGestTestata().getId()) {
		this.bollGestTestataId = getBollGestTestata().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @NotNull
    @Column(name = "NUMERORATA", nullable = false, precision = 2, scale = 0)
    public Integer getNumeroRata() {

	return numeroRata;
    }

    public void setNumeroRata(Integer numeroRata) {

	this.numeroRata = numeroRata;
    }

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "SCADENZA", length = 7)
    public Date getScadenza() {

	return scadenza;
    }

    public void setScadenza(Date scadenza) {

	this.scadenza = scadenza;
    }

    @NotNull
    @Column(name = "IMPORTO_TOTALE", precision = 11, scale = 2, nullable = false)
    public BigDecimal getImportoTotale() {

	return this.importoTotale;
    }

    public void setImportoTotale(BigDecimal importoTotale) {

	this.importoTotale = importoTotale;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_POSDEBDETTAGLIO_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public DettPosizioneDebitoria getDettPosizioneDebitoria() {

	return this.dettPosizioneDebitoria;
    }

    public void setDettPosizioneDebitoria(DettPosizioneDebitoria dettPosizioneDebitoria) {

	this.dettPosizioneDebitoria = dettPosizioneDebitoria;
    }

    private Integer dettPosizioneDebitoriaId;

    @Column(name = "FK_POSDEBDETTAGLIO_ID")
    private Integer getDettPosizioneDebitoriaId() {

	if (this.getDettPosizioneDebitoria() != null && this.getDettPosizioneDebitoria().getId() != null) {
	    this.dettPosizioneDebitoriaId = getDettPosizioneDebitoria().getId().getCodice();
	    return this.dettPosizioneDebitoriaId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setDettPosizioneDebitoriaId(Integer dettPosizioneDebitoriaId) {

	if (this.getDettPosizioneDebitoria() != null && this.getDettPosizioneDebitoria().getId() != null) {
	    this.dettPosizioneDebitoriaId = getDettPosizioneDebitoria().getId().getCodice();
	}
    }

    @Override
    public int hashCode() {

	final int prime = 31;
	int result = 1;
	result = prime * result + ((bollGestDettaglioId == null) ? 0 : bollGestDettaglioId.hashCode());
	result = prime * result + ((dettPosizioneDebitoriaId == null) ? 0 : dettPosizioneDebitoriaId.hashCode());
	result = prime * result + ((id == null && id.getCodice() == null) ? 0 : id.getCodice().hashCode());
	result = prime * result + ((importoTotale == null) ? 0 : importoTotale.hashCode());
	result = prime * result + ((numeroRata == null) ? 0 : numeroRata.hashCode());
	result = prime * result + ((scadenza == null) ? 0 : scadenza.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {

	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	BollGestDettRate other = (BollGestDettRate) obj;
	if (bollGestDettaglioId == null) {
	    if (other.bollGestDettaglioId != null) {
		return false;
	    }
	} else if (!bollGestDettaglioId.equals(other.bollGestDettaglioId)) {
	    return false;
	}
	if (dettPosizioneDebitoriaId == null) {
	    if (other.dettPosizioneDebitoriaId != null) {
		return false;
	    }
	} else if (!dettPosizioneDebitoriaId.equals(other.dettPosizioneDebitoriaId)) {
	    return false;
	}
	if (id == null || id.getCodice() == null) {
	    if (other.id != null) {
		return false;
	    }
	} else if (!id.getCodice().equals(other.id.getCodice())) {
	    return false;
	}
	if (importoTotale == null) {
	    if (other.importoTotale != null) {
		return false;
	    }
	} else if (!importoTotale.equals(other.importoTotale)) {
	    return false;
	}
	if (numeroRata == null) {
	    if (other.numeroRata != null) {
		return false;
	    }
	} else if (!numeroRata.equals(other.numeroRata)) {
	    return false;
	}
	if (scadenza == null) {
	    if (other.scadenza != null) {
		return false;
	    }
	} else if (!scadenza.equals(other.scadenza)) {
	    return false;
	}
	return true;
    }
}
