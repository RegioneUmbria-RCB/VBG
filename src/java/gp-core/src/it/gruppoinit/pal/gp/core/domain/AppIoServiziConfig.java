package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;

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
@Table(name = "APP_IO_SERVIZI_CONFIG")
public class AppIoServiziConfig implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5297014824061533409L;
    private AppIoServiziConfigId id;
    private String ambito;
    private BigDecimal maxMessaggiGiorno;
    private String templateOggetto;
    private String templateMessaggio;
    private boolean attivo;
    private AppIoServizi appIoServizi;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codiceComune", column = @Column(name = "CODICECOMUNE", nullable = false, length = 5)),
	    @AttributeOverride(name = "software", column = @Column(name = "SOFTWARE", nullable = false, length = 2)),
	    @AttributeOverride(name = "identificativoServizio", column = @Column(name = "IDENTIFICATIVO_SERVIZIO", nullable = false, length = 50)) })
    public AppIoServiziConfigId getId() {

	return id;
    }

    public void setId(AppIoServiziConfigId id) {

	this.id = id;
    }

    @Length(max = 50)
    @Column(name = "AMBITO")
    public String getAmbito() {

	return ambito;
    }

    public void setAmbito(String ambito) {

	this.ambito = ambito;
    }

    @Column(name = "MAX_MESSAGGI_GIORNO", precision = 10, scale = 0)
    public BigDecimal getMaxMessaggiGiorno() {

	return maxMessaggiGiorno;
    }

    public void setMaxMessaggiGiorno(BigDecimal maxMessaggiGiorno) {

	this.maxMessaggiGiorno = maxMessaggiGiorno;
    }

    @Length(min = 10, max = 120)
    @Column(name = "TEMPLATE_OGGETTO")
    public String getTemplateOggetto() {

	return templateOggetto;
    }

    public void setTemplateOggetto(String templateOggetto) {

	this.templateOggetto = templateOggetto;
    }

    @Length(min = 80, max = 10000)
    @Column(name = "TEMPLATE_MESSAGGIO")
    public String getTemplateMessaggio() {

	return templateMessaggio;
    }

    public void setTemplateMessaggio(String templateMessaggio) {

	this.templateMessaggio = templateMessaggio;
    }

    @Column(name = "ATTIVO", precision = 1, scale = 0)
    public boolean isAttivo() {

	return attivo;
    }

    public void setAttivo(boolean attivo) {

	this.attivo = attivo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "IDENTIFICATIVO_SERVIZIO", referencedColumnName = "IDENTIFICATIVO_SERVIZIO", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public AppIoServizi getAppIoServizi() {

	return appIoServizi;
    }

    public void setAppIoServizi(AppIoServizi appIoServizi) {

	if (null != this.getAppIoServizi()) {
	    if (null != this.getAppIoServizi().getId()) {
		this.appIoServizi = getAppIoServizi();
	    }
	}
    }
    // FIX WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    /*private String appIoServiziId;
    
    @Column(name = "IDENTIFICATIVO_SERVIZIO")
    private String getAppIoServiziId() {
    
    if (null != this.getAppIoServizi()) {
        if (null != this.getAppIoServizi().getId()) {
    	this.appIoServiziId = getAppIoServizi().getId().getIdentificativoServizio();
    	return this.appIoServiziId;
        }
    }
    return null;
    }
    
    @SuppressWarnings("unused")
    private void setAppIoServiziId() {
    
    }*/
    // END FIX/////////////////////////////////////////////////////
}
