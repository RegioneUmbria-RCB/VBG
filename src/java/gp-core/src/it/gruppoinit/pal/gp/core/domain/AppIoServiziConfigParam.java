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

import org.hibernate.validator.Length;

@Entity
@Table(name = "APP_IO_SERVIZI_CONFIG_PARAM")
public class AppIoServiziConfigParam implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7049562618148047324L;
    private AppIoServiziConfigParamId id;
    private String descrizione;
    private AppIoServizi appIoServizi;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idComune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "parametro", column = @Column(name = "PARAMETRO", nullable = false, length = 50)),
	    @AttributeOverride(name = "codiceComune", column = @Column(name = "CODICECOMUNE", nullable = false, length = 5)),
	    @AttributeOverride(name = "software", column = @Column(name = "SOFTWARE", nullable = false, length = 2)),
	    @AttributeOverride(name = "identificativoServizio", column = @Column(name = "IDENTIFICATIVO_SERVIZIO", nullable = false, length = 50)) })
    public AppIoServiziConfigParamId getId() {

	return id;
    }

    public void setId(AppIoServiziConfigParamId id) {

	this.id = id;
    }

    @Length(max = 1000)
    @Column(name = "DESCRIZIONE")
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
	    @JoinColumn(name = "IDENTIFICATIVO_SERVIZIO", referencedColumnName = "IDENTIFICATIVO_SERVIZIO", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public AppIoServizi getAppIoServizi() {

	return appIoServizi;
    }

    public void setAppIoServizi(AppIoServizi appIoServizi) {

	this.appIoServizi = appIoServizi;
    }
}
