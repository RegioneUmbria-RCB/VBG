package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "MESSAGGI_RABBIT")
public class MessaggiRabbit implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6209088758311268508L;
    private MessaggiRabbitId id;
    private String topic;
    private String messaggio;
    private Date dataTrasmissione;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "guid", column = @Column(name = "GUID", nullable = false, length = 40)) })
    public MessaggiRabbitId getId() {

	return id;
    }

    public void setId(MessaggiRabbitId id) {

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
}
