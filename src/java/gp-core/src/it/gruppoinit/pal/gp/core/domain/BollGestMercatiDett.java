package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;
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
@Table(name = "BOLL_GEST_MERCATI_DETT")
public class BollGestMercatiDett implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5472087437427802793L;
    private PkId id;
    private MercatiD posteggio;
    private MercatiUso mercatoUso;
    private BollGestDettAutorizz bollGestDettAutorizz;
    private BollGestTestata bollGestTestata;
    private MercatipresenzeT mercatiPresenzeT;
    private BigDecimal importoTotale;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BOLL_GEST_MERCATI_DETT.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDPOSTEGGIO", referencedColumnName = "IDPOSTEGGIO", nullable = false, insertable = false, updatable = false) })
    public MercatiD getPosteggio() {

	return this.posteggio;
    }

    public void setPosteggio(MercatiD posteggio) {

	this.posteggio = posteggio;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer posteggioId;

    @Column(name = "FK_IDPOSTEGGIO")
    @SuppressWarnings("unused")
    private Integer getPosteggioId() {

	if (null != this.getPosteggio()) {
	    if (null != this.getPosteggio().getId()) {
		this.posteggioId = this.getPosteggio().getId().getCodice();
		return this.posteggioId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setPosteggioId(Integer posteggioId) {

	if (null != this.getPosteggio()) {
	    if (null != this.getPosteggio().getId()) {
		this.posteggioId = this.getPosteggio().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_MERCATO_USO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MercatiUso getMercatoUso() {

	return this.mercatoUso;
    }

    public void setMercatoUso(MercatiUso mercatoUso) {

	this.mercatoUso = mercatoUso;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatoUsoId;

    @Column(name = "FK_MERCATO_USO")
    @SuppressWarnings("unused")
    private Integer getMercatoUsoId() {

	if (null != this.getMercatoUso()) {
	    if (null != this.getMercatoUso().getId()) {
		this.mercatoUsoId = this.getMercatoUso().getId().getCodice();
		return this.mercatoUsoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMercatoUsoId(Integer mercatoUsoId) {

	if (null != this.getMercatoUso()) {
	    if (null != this.getMercatoUso().getId()) {
		this.mercatoUsoId = this.getMercatoUso().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_ID_GEST_AUTORIZZAZIONI", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public BollGestDettAutorizz getBollGestDettAutorizz() {

	return this.bollGestDettAutorizz;
    }

    public void setBollGestDettAutorizz(BollGestDettAutorizz bollGestDettAutorizz) {

	this.bollGestDettAutorizz = bollGestDettAutorizz;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer bollGestDettAutorizzId;

    @Column(name = "FK_ID_GEST_AUTORIZZAZIONI")
    @SuppressWarnings("unused")
    private Integer getBollGestDettAutorizzId() {

	if (null != this.getBollGestDettAutorizz()) {
	    if (null != this.getBollGestDettAutorizz().getId()) {
		this.bollGestDettAutorizzId = this.getBollGestDettAutorizz().getId().getCodice();
		return this.bollGestDettAutorizzId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setBollGestDettAutorizzId(Integer bollGestDettAutorizzId) {

	if (null != this.getBollGestDettAutorizz()) {
	    if (null != this.getBollGestDettAutorizz().getId()) {
		this.bollGestDettAutorizzId = this.getBollGestDettAutorizz().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_MERCATIPRESENZET_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MercatipresenzeT getMercatiPresenzeT() {

	return mercatiPresenzeT;
    }

    public void setMercatiPresenzeT(MercatipresenzeT mercatiPresenzeT) {

	this.mercatiPresenzeT = mercatiPresenzeT;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatiPresenzeTId;

    @Column(name = "FK_MERCATIPRESENZET_ID")
    @SuppressWarnings("unused")
    private Integer getMercatiPresenzeTId() {

	if (null != this.getMercatiPresenzeT()) {
	    if (null != this.getMercatiPresenzeT().getId()) {
		this.mercatiPresenzeTId = this.getMercatiPresenzeT().getId().getCodice();
		return this.mercatiPresenzeTId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMercatiPresenzeTId(Integer mercatiPresenzeTId) {

	if (null != this.getMercatiPresenzeT()) {
	    if (null != this.getMercatiPresenzeT().getId()) {
		this.mercatiPresenzeTId = this.getMercatiPresenzeT().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @NotNull
    @Column(name = "IMPORTO_TOTALE", precision = 11, scale = 2, nullable = false)
    public BigDecimal getImportoTotale() {

	return this.importoTotale;
    }

    public void setImportoTotale(BigDecimal importoTotale) {

	this.importoTotale = importoTotale;
    }
}
