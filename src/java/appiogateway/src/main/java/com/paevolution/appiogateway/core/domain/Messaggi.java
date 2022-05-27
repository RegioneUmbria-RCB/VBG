package com.paevolution.appiogateway.core.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@Entity
@AllArgsConstructor
@Builder
@Table(name = "MESSAGGI")
public class Messaggi implements Serializable {

    private static final long serialVersionUID = -1077158020892422410L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "IDCOMUNE", nullable = false, length = 6)
    private String idcomune;
    @Column(name = "CODICECOMUNE", nullable = false, length = 6)
    private String codicecomune;
    @Column(name = "SOFTWARE", nullable = false, length = 2)
    private String software;
    @Column(name = "TIME_TO_LIVE", precision = 6, scale = 0)
    private Integer timeToLive;
    @Column(name = "SUBJECT", length = 120)
    private String subject;
    @Lob
    @Column(name = "MARKDOWN")
    private String markdown;
    @Column(name = "AMOUNT", precision = 10, scale = 0)
    private Integer amount;
    @Column(name = "NOTICE_NUMBER", length = 17)
    private String noticeNumber;
    @Column(name = "INVALID_AFTER_DUE_DATE", precision = 1, scale = 0)
    private Boolean invalidAfterDueDate;
    @Column(name = "NRE", length = 15)
    private String nre;
    @Column(name = "IUP", length = 16)
    private String iup;
    @Column(name = "PRESCRIBER_FISCAL_CODE", length = 16)
    private String prescriberFiscalCode;
    // @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DUE_DATE")
    private Timestamp dueDate;
    @Column(name = "EMAIL", length = 320)
    private String email;
    @Column(name = "FISCAL_CODE", length = 16)
    private String fiscalCode;
    @Column(name = "ID_TRANSAZIONE", length = 100, unique = true)
    private String idTransazione;
    @Column(name = "SENDER_ALLOWED", precision = 1, scale = 0)
    private Boolean senderAllowed;
    @Column(name = "PREFERRED_LANGUAGES", length = 200)
    private String preferredLanguages;
    // @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "FK_SERVIZI")
    private Servizi servizi;
    @Column(name = "MESSAGE_ID", length = 36, unique = true)
    private String messageId;
    @Column(name = "EMAIL_NOTIFICATION", length = 50)
    private String emailNotification;
    @Column(name = "WEBHOOK_NOTIFICATION", length = 50)
    private String webhookNotification;
    @Column(name = "STATUS", length = 50)
    private String status;
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "FK_STATO_MESSAGGIO")
    private StatoMessaggio statoMessaggio;
    @JsonBackReference
    @OneToOne(mappedBy = "messaggi", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Problem problem;

    public Messaggi() {

	this.servizi = new Servizi();
	this.statoMessaggio = new StatoMessaggio();
    }

    @Override
    public boolean equals(Object o) {

	if (o == this)
	    return true;
	if (!(o instanceof Messaggi))
	    return false;
	Messaggi other = (Messaggi) o;
	if (!other.canEqual((Object) this))
	    return false;
	if (this.getId() == null ? other.getId() != null : !this.getId().equals(other.getId()))
	    return false;
	if (this.getMessageId() == null ? other.getMessageId() != null : !this.getMessageId().equals(other.getMessageId()))
	    return false;
	if (this.getIdTransazione() == null ? other.getIdTransazione() != null : !this.getIdTransazione().equals(other.getIdTransazione()))
	    return false;
	return true;
    }

    @Override
    public int hashCode() {

	final int PRIME = 59;
	int result = 1;
	result = (result * PRIME) + (this.getId() == null ? 43 : this.getId().hashCode());
	result = (result * PRIME) + (this.getMessageId() == null ? 0 : this.getMessageId().hashCode());
	result = (result * PRIME) + (this.getIdTransazione() == null ? 0 : this.getIdTransazione().hashCode());
	return result;
    }

    protected boolean canEqual(Object other) {

	return other instanceof Messaggi;
    }
}
