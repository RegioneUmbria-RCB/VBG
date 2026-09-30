package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import java.io.Serializable;

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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "CATEGORIE_EVENTI_MAIL")
public class CategorieEventiMail implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1466691187686893955L;
    private PkId id;
    private String descrizione;
    private Categorieeventibase categorieeventibase;
    private String triggerType;
    private String triggerText;
    private Mailtipo mailtipo;
    private String destinatarimail;
    private String destinatariAggiuntivi;
    private Boolean flagAttiva;
    private MailConfig mailConfig;
    private Software software;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "CATEGORIE_EVENTI_MAIL.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 4, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @Column(name = "DESCRIZIONE", length = 200)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FKIDCATEGORIAEVENTO")
    public Categorieeventibase getCategorieeventibase() {

	return this.categorieeventibase;
    }

    public void setCategorieeventibase(Categorieeventibase categorieeventibase) {

	this.categorieeventibase = categorieeventibase;
    }

    @NotNull
    @Column(name = "TRIGGER_TYPE", length = 20)
    public String getTriggerType() {

	return triggerType;
    }

    public void setTriggerType(String triggerType) {

	this.triggerType = triggerType;
    }

    @NotNull
    @Column(name = "TRIGGER_TEXT", length = 50)
    public String getTriggerText() {

	return triggerText;
    }

    public void setTriggerText(String triggerText) {

	this.triggerText = triggerText;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEMAILTIPO", referencedColumnName = "CODICEMAIL", nullable = false, insertable = false, updatable = false) })
    public Mailtipo getMailtipo() {

	return this.mailtipo;
    }

    public void setMailtipo(Mailtipo mailtipo) {

	this.mailtipo = mailtipo;
    }

    private Integer mailtipoId;

    @Column(name = "CODICEMAILTIPO")
    private Integer getMailtipoId() {

	if (null != this.getMailtipo()) {
	    if (null != this.getMailtipo().getId()) {
		this.mailtipoId = getMailtipo().getId().getCodice();
		return this.mailtipoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMailtipoId(Integer mailtipoId) {

	if (null != this.getMailtipo()) {
	    if (null != this.getMailtipo().getId()) {
		this.mailtipoId = getMailtipo().getId().getCodice();
	    }
	}
    }

    @NotNull
    @Column(name = "DESTINATARIMAIL", length = 500)
    public String getDestinatarimail() {

	return destinatarimail;
    }

    public void setDestinatarimail(String destinatarimail) {

	this.destinatarimail = destinatarimail;
    }

    @Column(name = "DESTINATARIAGGIUNTIVI", length = 50)
    public String getDestinatariAggiuntivi() {

	return destinatariAggiuntivi;
    }

    public void setDestinatariAggiuntivi(String destinatariAggiuntivi) {

	this.destinatariAggiuntivi = destinatariAggiuntivi;
    }

    @Column(name = "FLAG_ATTIVA")
    public Boolean getFlagAttiva() {

	return flagAttiva;
    }

    public void setFlagAttiva(Boolean flagAttiva) {

	this.flagAttiva = flagAttiva;
    }

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEMAILCONFIG", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MailConfig getMailConfig() {
    
        return mailConfig;
    }

    
    public void setMailConfig(MailConfig mailConfig) {
    
        this.mailConfig = mailConfig;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOFTWARE")
    public Software getSoftware() {

	return this.software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }
    
    private Integer mailconfigId;

    @Column(name = "CODICEMAILCONFIG")
    private Integer getMailconfigId() {

	if (null != this.getMailConfig()) {
	    if (null != this.getMailConfig().getId()) {
		this.mailconfigId = getMailConfig().getId().getCodice();
		return this.mailconfigId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMailconfigId(Integer mailtipoId) {

	if (null != this.getMailConfig()) {
	    if (null != this.getMailConfig().getId()) {
		this.mailconfigId = getMailConfig().getId().getCodice();
	    }
	}
    }
    
    
}
