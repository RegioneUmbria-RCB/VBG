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
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

@Entity
@Table(name = "MERCATI_SPUNTE")
public class MercatiSpunte implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2889451033606463946L;
    private PkId id;
    private Mercati mercato;
//    private Boolean flagSegnaPres;
    private String descrizione;
    private Integer ordine;
    private Boolean flagFiltroCatmerc;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MERCATI_SPUNTE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 4, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEMERCATO", referencedColumnName = "CODICEMERCATO", nullable = false, insertable = false, updatable = false) })
    public Mercati getMercato() {

	return this.mercato;
    }

    public void setMercato(Mercati mercato) {

	this.mercato = mercato;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatoId;

    @Column(name = "CODICEMERCATO")
    @SuppressWarnings("unused")
    private Integer getMercatoId() {

	if (null != this.getMercato()) {
	    if (null != this.getMercato().getId()) {
		this.mercatoId = this.getMercato().getId().getCodice();
		return this.mercatoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMercatoId(Integer mercatoId) {

	if (null != this.getMercato()) {
	    if (null != this.getMercato().getId()) {
		this.mercatoId = this.getMercato().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    //    @Column(name = "FLAG_SEGNA_PRES", nullable = false, precision = 1, scale = 0)
    //    public Boolean getFlagSegnaPres() {
    //
    //	return flagSegnaPres;
    //    }
    //
    //    public void setFlagSegnaPres(Boolean flagSegnaPres) {
    //
    //	this.flagSegnaPres = flagSegnaPres;
    //    }

    @Column(name = "FLAG_FILTRO_CATMERC", nullable = false, precision = 1, scale = 0)
    public Boolean getFlagFiltroCatmerc() {

	return flagFiltroCatmerc;
    }

    public void setFlagFiltroCatmerc(Boolean flagFiltroCatmerc) {

	this.flagFiltroCatmerc = flagFiltroCatmerc;
    }

    @NotEmpty
    @Length(max = 50)
    @Column(name = "DESCRIZIONE", nullable = false, length = 50)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Column(name = "ORDINE", precision = 2, scale = 0)
    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }
}
