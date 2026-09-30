package it.gruppoinit.pal.gp.pay.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PAY_MESSAGGI_RABBIT_PAGAMENTI")
public class PayMessaggiRabbitPagamenti implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4866329394109422574L;
    private PayMessaggiRabbitPagamentiId id;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkMsgrabbitGuid", column = @Column(name = "FK_MSGRABBIT_GUID", nullable = false, length = 40)),
	    @AttributeOverride(name = "uuidPosizioneDeb", column = @Column(name = "UUID_POSIZIONE_DEB", nullable = false)) })
    public PayMessaggiRabbitPagamentiId getId() {

	return id;
    }

    public void setId(PayMessaggiRabbitPagamentiId id) {

	this.id = id;
    }
}
