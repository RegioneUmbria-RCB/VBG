package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Valid;

@Entity
@Table(name = "FIRMEREMOTE_PARAMETRI")
public class FirmeRemoteParametri implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3989419706261402435L;
    private FirmeRemoteParametriId id;
    private FirmeRemote firmaRemota;
    private String descrizione;
    private Boolean obbligatorio;
    private Boolean visibile;
    private Boolean readonly;
    private String tipoCampo;
    private String valoreDefault;
    private Integer ordine;

    public FirmeRemoteParametri() {

	this.id = new FirmeRemoteParametriId();
	this.firmaRemota = new FirmeRemote();
    }

    @Valid
    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkIdFirmaRemota", column = @Column(name = "FK_IDFIRMAREMOTA", nullable = false, precision = 10, scale = 0)),
	    @AttributeOverride(name = "chiave", column = @Column(name = "CHIAVE", nullable = false, length = 40)) })
    public FirmeRemoteParametriId getId() {

	return this.id;
    }

    public void setId(FirmeRemoteParametriId id) {

	this.id = id;
    }

    @NotEmpty
    @Length(max = 500)
    @Column(name = "DESCRIZIONE", length = 500)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @NotNull
    @Column(name = "OBBLIGATORIO", precision = 1, scale = 0)
    public Boolean getObbligatorio() {

	return obbligatorio;
    }

    public void setObbligatorio(Boolean obbligatorio) {

	this.obbligatorio = obbligatorio;
    }

    @NotNull
    @Column(name = "VISIBILE", precision = 1, scale = 0)
    public Boolean getVisibile() {

	return visibile;
    }

    public void setVisibile(Boolean visibile) {

	this.visibile = visibile;
    }

    @NotNull
    @Column(name = "READONLY", precision = 1, scale = 0)
    public Boolean getReadonly() {

	return readonly;
    }

    public void setReadonly(Boolean readonly) {

	this.readonly = readonly;
    }

    @NotEmpty
    @Length(max = 20)
    @Column(name = "TIPOCAMPO", length = 20)
    public String getTipoCampo() {

	return tipoCampo;
    }

    public void setTipoCampo(String tipoCampo) {

	this.tipoCampo = tipoCampo;
    }

    @Length(max = 200)
    @Column(name = "VALOREDEFAULT", length = 200)
    public String getValoreDefault() {

	return valoreDefault;
    }

    public void setValoreDefault(String valoreDefault) {

	this.valoreDefault = valoreDefault;
    }

    @NotNull
    @Column(name = "ORDINE", precision = 3, scale = 0)
    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDFIRMAREMOTA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public FirmeRemote getFirmaRemota() {

	return firmaRemota;
    }

    public void setFirmaRemota(FirmeRemote firmaRemota) {

	this.firmaRemota = firmaRemota;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer firmaRemotaId;

    @Column(name = "FK_IDFIRMAREMOTA", nullable = false, insertable = false, updatable = false)
    @SuppressWarnings("unused")
    public Integer getFirmaRemotaId() {

	if (null != this.getFirmaRemota()) {
	    if (null != this.getFirmaRemota().getId()) {
		this.firmaRemotaId = this.getFirmaRemota().getId().getCodice();
		return this.firmaRemotaId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setFirmaRemotaId(Integer firmaRemotaId) {

	if (null != this.getFirmaRemota()) {
	    if (null != this.getFirmaRemota().getId()) {
		this.firmaRemotaId = this.getFirmaRemota().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////
}
