package com.paevolution.appioproducer.core.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "MOVIMENTI_IO_COMUNICAZIONI")
public class MovimentiIoComunicazioni implements Serializable {

    private static final long serialVersionUID = 6526492224931288359L;
    private MovimentiIoComunicazioniId id;
    @Column(name = "MESSAGE_ID", length = 36, unique = true)
    private String messageId;
    @Column(name = "FISCAL_CODE", length = 16)
    private String fiscalCode;
    @Column(name = "DATAINVIO", length = 16)
    private Date datainvio;
    @Column(name = "SENDER_ALLOWED", precision = 1, scale = 0)
    private Boolean senderAllowed;
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    @Column(name = "NOTIFICATION_STATUS", length = 50)
    private String notificationStatus;
    @Column(name = "NOTIFICATION_WEBHOOK", length = 50)
    private String notificationWebhook;
    @Column(name = "NOTIFICATION_EMAIL", length = 50)
    private String notificationEmail;
    @Lob
    @Column(name = "ERRORE")
    private String errore;
    @Column(name = "FLAG_COMPLETATO", precision = 1, scale = 0)
    private Boolean flagCompletato;
    
    @EmbeddedId
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
		@AttributeOverride(name = "codicecomune", column = @Column(name = "CODICECOMUNE", nullable = false, length = 5)),
		@AttributeOverride(name = "software", column = @Column(name = "SOFTWARE", nullable = false, length = 2)),
	    @AttributeOverride(name = "codicemovimento", column = @Column(name = "CODICEMOVIMENTO", nullable = false, precision = 8, scale = 0)),
	    @AttributeOverride(name = "codiceanagrafe", column = @Column(name = "CODICEANAGRAFE", nullable = false, precision = 6, scale = 0)) })
    public MovimentiIoComunicazioniId getId() {

	return this.id;
    }

    public void setId(MovimentiIoComunicazioniId id) {

	this.id = id;
    }
}
