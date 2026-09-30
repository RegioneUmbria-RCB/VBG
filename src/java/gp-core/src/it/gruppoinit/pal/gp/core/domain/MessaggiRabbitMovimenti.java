package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MESSAGGI_RABBIT_MOVIMENTI")
public class MessaggiRabbitMovimenti {

    private MessaggiRabbitMovimentiId id;

    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "fkMsgrabbitGuid", column = @Column(name = "FK_MSGRABBIT_GUID", nullable = false, length = 40)),
	    @AttributeOverride(name = "uuIdMovimento", column = @Column(name = "UUID_MOVIMENTO", nullable = false)) })
    public MessaggiRabbitMovimentiId getId() {

	return id;
    }

    public void setId(MessaggiRabbitMovimentiId id) {

	this.id = id;
    }
}
