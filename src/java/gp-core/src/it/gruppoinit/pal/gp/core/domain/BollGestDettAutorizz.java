package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;
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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "BOLL_GEST_DETT_AUTORIZZ")
public class BollGestDettAutorizz implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5922777802717686082L;
    private PkId id;
    private BollGestDettaglio bollGestDettaglio;
    private BollGestTestata bollGestTestata;
    private Autorizzazioni autorizzazione;
    private Set<BollGestMercatiDett> bollGestMercatiDetts = new HashSet<BollGestMercatiDett>(0);
    private AutorizzazioniSubentri subentro;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BOLL_GEST_DETT_AUTORIZZ.ID") })
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
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_AUTORIZZAZIONE_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public Autorizzazioni getAutorizzazione() {

	return this.autorizzazione;
    }

    public void setAutorizzazione(Autorizzazioni autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer autorizzazioneId;

    @Column(name = "FK_AUTORIZZAZIONE_ID")
    @SuppressWarnings("unused")
    private Integer getAutorizzazioniId() {

	if (null != this.getAutorizzazione()) {
	    if (null != this.getAutorizzazione().getId()) {
		this.autorizzazioneId = getAutorizzazione().getId().getCodice();
		return this.autorizzazioneId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAutorizzazioniId(Integer autorizzazioniId) {

	if (null != this.getAutorizzazione()) {
	    if (null != this.getAutorizzazione().getId()) {
		this.autorizzazioneId = getAutorizzazione().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_AUTSUBENTRI_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public AutorizzazioniSubentri getSubentro() {

	return this.subentro;
    }

    public void setSubentro(AutorizzazioniSubentri subentro) {

	this.subentro = subentro;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer subentroId;

    @Column(name = "FK_AUTSUBENTRI_ID")
    @SuppressWarnings("unused")
    private Integer getSubentroId() {

	if (null != this.getSubentro()) {
	    if (null != this.getSubentro().getId()) {
		this.subentroId = getSubentro().getId().getCodice();
		return this.subentroId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setSubentroId(Integer subentroId) {

	if (null != this.getSubentro()) {
	    if (null != this.getSubentro().getId()) {
		this.subentroId = getSubentro().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_BOLLGESTDET_ID", referencedColumnName = "ID", insertable = false, updatable = false) })
    public BollGestDettaglio getBollGestDettaglio() {

	return this.bollGestDettaglio;
    }

    public void setBollGestDettaglio(BollGestDettaglio bollGestDettaglio) {

	this.bollGestDettaglio = bollGestDettaglio;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer bollGestDettaglioId;

    @Column(name = "FK_BOLLGESTDET_ID")
    @SuppressWarnings("unused")
    private Integer getBollGestDettaglioId() {

	if (null != this.getBollGestDettaglio()) {
	    if (null != this.getBollGestDettaglio().getId()) {
		this.bollGestDettaglioId = getBollGestDettaglio().getId().getCodice();
		return this.bollGestDettaglioId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setBollGestDettaglioId(Integer bollGestDettaglioId) {

	if (null != this.getBollGestDettaglio()) {
	    if (null != this.getBollGestDettaglio().getId()) {
		this.bollGestDettaglioId = getBollGestDettaglio().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bollGestDettAutorizz")
    public Set<BollGestMercatiDett> getBollGestMercatiDetts() {

	return bollGestMercatiDetts;
    }

    public void setBollGestMercatiDetts(Set<BollGestMercatiDett> bollGestMercatiDetts) {

	this.bollGestMercatiDetts = bollGestMercatiDetts;
    }
}
