package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "MESSAGGI_MAIL")
public class MessaggiMail implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9119614283243081694L;
    private PkId id;
    private String mittente;
    private String destinatario;
    private String destinatariocc;
    private String destinatarioabcc;
    private String oggetto;
    private Date dataInvio;
    private String messageId;
    private MessaggiMail messaggiMailPadre;
    private String corpo;
    private MailConfig accountId;
    private Set<MessaggiMail> mailCollegate = new HashSet<MessaggiMail>(0);

    public MessaggiMail() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MESSAGGI_MAIL.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @Length(max = 200)
    @Column(name = "MITTENTE")
    public String getmittente() {

	return this.mittente;
    }

    @Length(max = 2000)
    @Column(name = "DESTINATARIO")
    public String getdestinatario() {

	return this.destinatario;
    }

    @Length(max = 2000)
    @Column(name = "DESTINATARIOCC")
    public String getdestinatariocc() {

	return this.destinatariocc;
    }

    @Length(max = 2000)
    @Column(name = "DESTINATARIOBCC")
    public String getdestinatarioabcc() {

	return this.destinatarioabcc;
    }

    @Length(max = 1500)
    @Column(name = "OGGETTO")
    public String getoggetto() {

	return this.oggetto;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATAINVIO")
    public Date getdataInvio() {

	return this.dataInvio;
    }

    @Length(max = 400)
    @Column(name = "MESSAGE_ID")
    public String getmessageId() {

	return this.messageId;
    }

    @Column(name = "CORPO")
    @Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
    public String getcorpo() {

	return this.corpo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MailConfig getaccountId() {

	return this.accountId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "ID_PADRE", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MessaggiMail getMessaggiMailPadre() {

	return messaggiMailPadre;
    }

    public void setMessaggiMailPadre(MessaggiMail messaggiMailPadre) {

	this.messaggiMailPadre = messaggiMailPadre;
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer messaggiMailPadreId;

    @Column(name = "ID_PADRE")
    @SuppressWarnings("unused")
    private Integer getMessaggiMailPadreId() {

	if (null != this.getMessaggiMailPadre() && null != this.getMessaggiMailPadre().getId()) {
	    this.messaggiMailPadreId = getMessaggiMailPadre().getId().getCodice();
	    return this.messaggiMailPadreId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMessaggiMailPadreId(Integer idMessaggiMailPadre) {

	if (null != this.getMessaggiMailPadre() && null != this.getMessaggiMailPadre().getId()) {
	    this.messaggiMailPadreId = getMessaggiMailPadre().getId().getCodice();
	}
    }

    // WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer idAccount;

    @Column(name = "ACCOUNT_ID")
    @SuppressWarnings("unused")
    private Integer getIdAccount() {

	if (null != this.getaccountId() && null != this.getaccountId().getId()) {
	    this.idAccount = getaccountId().getId().getCodice();
	    return this.idAccount;
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setIdAccount(Integer idAccount) {

	if (null != this.getaccountId() && null != this.getaccountId().getId()) {
	    this.idAccount = getaccountId().getId().getCodice();
	}
    }

    public void setMittente(String mittente) {

	this.mittente = mittente;
    }

    public void setDestinatario(String destinatario) {

	this.destinatario = destinatario;
    }

    public void setDestinatariocc(String destinatariocc) {

	this.destinatariocc = destinatariocc;
    }

    public void setDestinatarioabcc(String destinatarioabcc) {

	this.destinatarioabcc = destinatarioabcc;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public void setDataInvio(Date dataInvio) {

	this.dataInvio = dataInvio;
    }

    public void setMessageId(String messageId) {

	this.messageId = messageId;
    }

    public void setCorpo(String corpo) {

	this.corpo = corpo;
    }

    public void setAccountId(MailConfig accountId) {

	this.accountId = accountId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "messaggiMailPadre")
    public Set<MessaggiMail> getMailCollegate() {

	return mailCollegate;
    }

    public void setMailCollegate(Set<MessaggiMail> mailCollegate) {

	this.mailCollegate = mailCollegate;
    }
}
