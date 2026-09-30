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

@Entity
@Table(name = "APP_IO_PARAM")
public class AppIoParam implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3957478330863068787L;
    private AppIoParamId id;
    private String descrizione;
    private AppIoServizi servizio;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idComune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "parametro", column = @Column(name = "PARAMETRO", nullable = false, length = 5)),
	    @AttributeOverride(name = "identificativoServizio", column = @Column(name = "IDENTIFICATIVO_SERVIZIO", nullable = false, length = 50)) })
    public AppIoParamId getId() {

	return id;
    }

    public void setId(AppIoParamId id) {

	this.id = id;
    }

    @Column(name = "DESCRIZIONE", length = 1000)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "IDENTIFICATIVO_SERVIZIO", referencedColumnName = "IDENTIFICATIVO_SERVIZIO", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public AppIoServizi getServizio() {

	return servizio;
    }

    public void setServizio(AppIoServizi appIoServizi) {

	this.servizio = appIoServizi;
    }
}
