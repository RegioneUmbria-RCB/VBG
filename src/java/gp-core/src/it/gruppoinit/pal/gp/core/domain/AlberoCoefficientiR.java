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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "ALBERO_COEFFICIENTI_R")
public class AlberoCoefficientiR implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3724005827124341741L;
    private PkId id;
    private AlberoCoefficientiT alberoCoefficientiT;
    private String codiceCoefficente;
    private String descrizione;
    private String note;
    private String valore;
    private String tipo;
    private Boolean visibile;

    public AlberoCoefficientiR() {

	this.id = new PkId();
	this.alberoCoefficientiT = new AlberoCoefficientiT();
	this.visibile = Boolean.TRUE;
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "ALBERO_COEFFICIENTI_R.ID") })
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
    @JoinColumns({ @JoinColumn(name = "FK_IDTESTATA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public AlberoCoefficientiT getAlberoCoefficientiT() {

	return alberoCoefficientiT;
    }

    public void setAlberoCoefficientiT(AlberoCoefficientiT alberoCoefficientiT) {

	this.alberoCoefficientiT = alberoCoefficientiT;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer alberoCoefficientiTId;

    @Column(name = "FK_IDTESTATA")
    @SuppressWarnings("unused")
    private Integer getAlberoCoefficientiTId() {

	if (null != this.getAlberoCoefficientiT()) {
	    if (null != this.getAlberoCoefficientiT().getId()) {
		this.alberoCoefficientiTId = getAlberoCoefficientiT().getId().getCodice();
		return this.alberoCoefficientiTId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setAlberoCoefficientiTId(Integer alberoCoefficientiTId) {

	if (null != this.getAlberoCoefficientiT()) {
	    if (null != this.getAlberoCoefficientiT().getId()) {
		this.alberoCoefficientiTId = getAlberoCoefficientiT().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    @NotNull
    @Column(name = "CODICE_COEFFICENTE", nullable = false, length = 6)
    public String getCodiceCoefficente() {

	return codiceCoefficente;
    }

    public void setCodiceCoefficente(String codiceCoefficente) {

	this.codiceCoefficente = codiceCoefficente;
    }

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
    @Column(name = "VALORE", nullable = false, length = 50)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    @Column(name = "TIPO", length = 50)
    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    @NotNull
    @Column(name = "VISIBILE", precision = 1, scale = 0)
    public Boolean getVisibile() {

	return visibile;
    }

    public void setVisibile(Boolean visibile) {

	this.visibile = visibile;
    }
}
