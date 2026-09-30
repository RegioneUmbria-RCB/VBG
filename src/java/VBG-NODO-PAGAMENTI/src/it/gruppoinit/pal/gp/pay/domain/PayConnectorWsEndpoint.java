package it.gruppoinit.pal.gp.pay.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.BaseDomainObject;
import it.gruppoinit.pal.gp.core.domain.IdAwareDomainObject;

@Entity
@Table(name = "PAY_CONNECTOR_WS_ENDPOINT")
public class PayConnectorWsEndpoint extends BaseDomainObject implements Serializable, IdAwareDomainObject<PayConnectorWsEndpointId> {

    private static final long serialVersionUID = 4193685162341325558L;
    private PayConnectorWsEndpointId id;
    private String endpointUrl;
    private String utente;
    private String password;
    private String descrizione;
    private Integer timeout;
    private String quartzSchedule;
    private Boolean flagSoloSchedulato;
    private Integer maxChiamate;
    private Boolean flagSpegniScheduler;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "codiceConnettore", column = @Column(name = "CODICE_CONNETTORE", nullable = false, length = 20)),
	    @AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false, scale = 9, precision = 0)) })
    public PayConnectorWsEndpointId getId() {

	return id;
    }

    public void setId(PayConnectorWsEndpointId id) {

	this.id = id;
    }

    @Column(name = "ENDPOINT_URL", nullable = true, length = 256)
    public String getEndpointUrl() {

	return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {

	this.endpointUrl = endpointUrl;
    }

    @Column(name = "UTENTE", nullable = true, length = 50)
    public String getUtente() {

	return utente;
    }

    public void setUtente(String utente) {

	this.utente = utente;
    }

    @Column(name = "PASSWORD", nullable = true, length = 50)
    public String getPassword() {

	return password;
    }

    public void setPassword(String password) {

	this.password = password;
    }

    @Column(name = "DESCRIZIONE", nullable = true, length = 100)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Column(name = "TIMEOUT", nullable = false, precision = 8, scale = 0)
    public Integer getTimeout() {

	return timeout;
    }

    public void setTimeout(Integer timeout) {

	this.timeout = timeout;
    }

    
    @Column(name = "QUARTZ_SCHEDULE", nullable = true, length = 100)
    public String getQuartzSchedule() {
    
        return quartzSchedule;
    }

    
    public void setQuartzSchedule(String quartzSchedule) {
    
        this.quartzSchedule = quartzSchedule;
    }

    
    @Column(name = "FLAG_SOLO_SCHEDULATO", nullable = true, precision = 1, scale = 0)
    public Boolean getFlagSoloSchedulato() {
    
        return flagSoloSchedulato == null ? Boolean.FALSE : flagSoloSchedulato;
    }

    
    public void setFlagSoloSchedulato(Boolean flagSoloSchedulato) {
    
        this.flagSoloSchedulato = flagSoloSchedulato;
    }
    
    @Transient
    public boolean isSoloSchedulato() {
	return StringUtils.isNotBlank(this.getQuartzSchedule()) && getFlagSoloSchedulato();
    }

    
    @Column(name = "MAX_CHIAMATE", nullable = false, precision = 9, scale = 0)
    public Integer getMaxChiamate() {
    
        return maxChiamate;
    }

    
    public void setMaxChiamate(Integer maxChiamate) {
    
        this.maxChiamate = maxChiamate;
    }

    @Column(name = "FLAG_SPEGNI_SCHEDULER", nullable = true, precision = 1, scale = 0)
    public Boolean getFlagSpegniScheduler() {
    
        return flagSpegniScheduler == null ? Boolean.FALSE : flagSpegniScheduler;
    }

    
    public void setFlagSpegniScheduler(Boolean flagSpegniScheduler) {
    
        this.flagSpegniScheduler = flagSpegniScheduler;
    }

}
