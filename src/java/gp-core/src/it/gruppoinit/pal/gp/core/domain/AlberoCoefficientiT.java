package it.gruppoinit.pal.gp.core.domain;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "ALBERO_COEFFICIENTI_T")
public class AlberoCoefficientiT implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3115281077845348149L;
    private PkId id;
    private Alberoproc alberoProc;
    private String descrizione;
    private String note;
    private Boolean attivo;
    private int codiceCopiaTestata;

    public AlberoCoefficientiT() {

	this.id = new PkId();
	this.alberoProc = new Alberoproc();
	this.attivo = Boolean.FALSE;
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "ALBERO_COEFFICIENTI_T.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)),
	    @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "FK_SCID", referencedColumnName = "SC_ID", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Alberoproc getAlberoProc() {

	return alberoProc;
    }

    public void setAlberoProc(Alberoproc alberoProc) {

	this.alberoProc = alberoProc;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer alberoProcId;

    @Column(name = "FK_SCID")
    @SuppressWarnings("unused")
    private Integer getAlberoProcId() {

	if (null != this.getAlberoProc()) {
	    if (null != this.getAlberoProc().getId()) {
		this.alberoProcId = getAlberoProc().getId().getCodice();
		return this.alberoProcId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAlberoProcId(Integer alberoProcId) {

	if (null != this.getAlberoProc()) {
	    if (null != this.getAlberoProc().getId()) {
		this.alberoProcId = getAlberoProc().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @NotNull
    @Column(name = "DESCRIZIONE", nullable = false, length = 500)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Column(name = "NOTE", length = 4000)
    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    @NotNull
    @Column(name = "ATTIVO", precision = 1, scale = 0)
    public Boolean getAttivo() {

	return attivo;
    }

    public void setAttivo(Boolean attivo) {

	this.attivo = attivo;
    }

    @Transient
    public int getCodiceCopiaTestata() {

	return codiceCopiaTestata;
    }

    public void setCodiceCopiaTestata(int codiceCopiaTestata) {

	this.codiceCopiaTestata = codiceCopiaTestata;
    }
}
