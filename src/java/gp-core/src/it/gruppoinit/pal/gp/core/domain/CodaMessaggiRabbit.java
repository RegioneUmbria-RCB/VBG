package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "CODA_MESSAGGI_RABBIT")
public class CodaMessaggiRabbit implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4745167858585572229L;
    private CodaMessaggiRabbitId id;
    private String uuidIstanza;
    private String uuidMovimento;
    private String uuidPosDeb;
    private String topic;
    private String messaggio;
    private Date dataCreazione;
    private Long progressivo;
    private Date dataTrasmissione;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "uuid", column = @Column(name = "UUID", nullable = false, length = 40)) })
    public CodaMessaggiRabbitId getId() {

	return id;
    }

    public void setId(CodaMessaggiRabbitId id) {

	this.id = id;
    }

    @Column(name = "UUID_ISTANZA")
    public String getUuidIstanza() {

	return uuidIstanza;
    }

    public void setUuidIstanza(String uuidIstanza) {

	this.uuidIstanza = uuidIstanza;
    }

    @Column(name = "UUID_MOVIMENTO")
    public String getUuidMovimento() {

	return uuidMovimento;
    }

    public void setUuidMovimento(String uuidMovimento) {

	this.uuidMovimento = uuidMovimento;
    }

    @Column(name = "UUID_POS_DEB")
    public String getUuidPosDeb() {

	return uuidPosDeb;
    }

    public void setUuidPosDeb(String uuidPosDeb) {

	this.uuidPosDeb = uuidPosDeb;
    }

    @Column(name = "TOPIC")
    public String getTopic() {

	return topic;
    }

    public void setTopic(String topic) {

	this.topic = topic;
    }

    @Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
    @Column(name = "MESSAGGIO")
    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_CREAZIONE")
    public Date getDataCreazione() {

	return dataCreazione;
    }

    public void setDataCreazione(Date dataCreazione) {

	this.dataCreazione = dataCreazione;
    }

    @Column(name = "PROGRESSIVO")
    public Long getProgressivo() {

	return progressivo;
    }

    public void setProgressivo(Long progressivo) {

	this.progressivo = progressivo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_TRASMISSIONE")
    public Date getDataTrasmissione() {

	return dataTrasmissione;
    }

    public void setDataTrasmissione(Date dataTrasmissione) {

	this.dataTrasmissione = dataTrasmissione;
    }

    @Transient
    public static CodaMessaggiRabbit defaultVal(String idComune) {

	CodaMessaggiRabbit ret = new CodaMessaggiRabbit();
	ret.setId(new CodaMessaggiRabbitId(idComune, UUID.randomUUID().toString()));
	ret.setDataCreazione(Calendar.getInstance().getTime());
	ret.setProgressivo(Long.parseLong(StringUtils.right(String.valueOf(System.nanoTime()), 15)));
	return ret;
    }
}
