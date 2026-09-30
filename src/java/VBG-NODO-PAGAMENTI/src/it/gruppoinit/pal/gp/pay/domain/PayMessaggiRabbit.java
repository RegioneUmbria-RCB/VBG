package it.gruppoinit.pal.gp.pay.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "PAY_MESSAGGI_RABBIT")
public class PayMessaggiRabbit implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5997015721074776149L;
    private PayMessaggiRabbitId id;
    private String topic;
    private String messaggio;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "guid", column = @Column(name = "GUID", nullable = false, length = 40)) })
    public PayMessaggiRabbitId getId() {

	return id;
    }

    public void setId(PayMessaggiRabbitId id) {

	this.id = id;
    }

    @NotNull
    @Length(max = 50)
    @Column(name = "TOPIC")
    public String getTopic() {

	return topic;
    }

    public void setTopic(String topic) {

	this.topic = topic;
    }

    @NotNull
    @Length(max = 10000)
    @Column(name = "MESSAGGIO")
    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_TRASMISSIONE")
    public Date getDataTrasmissione() {

	return dataTrasmissione;
    }

    public void setDataTrasmissione(Date dataTrasmissione) {

	this.dataTrasmissione = dataTrasmissione;
    }

    private Date dataTrasmissione;
}
