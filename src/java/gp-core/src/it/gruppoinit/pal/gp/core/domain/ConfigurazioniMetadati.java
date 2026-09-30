package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

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

@Entity
@Table(name = "CONFIGURAZIONI_METADATI")
public class ConfigurazioniMetadati implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1658145289452443127L;
    private ConfigurazioniMetadatiId id;
    private String valore;
    private String software;
    private Integer ordine;
    private String categoria;
    private Comuniassociatisoftware comuniassociatisoftware;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "idComuniAssociatiSoftware", column = @Column(name = "FKIDCOMUNIASSOCIATISOFTWARE", nullable = false, precision = 8, scale = 0)),
	    @AttributeOverride(name = "chiave", column = @Column(name = "chiave", nullable = false, length = 254)) })
    public ConfigurazioniMetadatiId getId() {

	return id;
    }

    public void setId(ConfigurazioniMetadatiId id) {

	this.id = id;
    }

    @Column(name = "VALORE", length = 4000)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }

    @Column(name = "SOFTWARE", length = 2)
    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKIDCOMUNIASSOCIATISOFTWARE", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public Comuniassociatisoftware getComuniassociatisoftware() {

	return comuniassociatisoftware;
    }

    public void setComuniassociatisoftware(Comuniassociatisoftware comuniassociatisoftware) {

	this.comuniassociatisoftware = comuniassociatisoftware;
    }

    @Column(name = "ORDINE", precision = 1, scale = 0)
    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    @Column(name = "CATEGORIA", length = 254)
    public String getCategoria() {

	return categoria;
    }

    public void setCategoria(String categoria) {

	this.categoria = categoria;
    }
}
