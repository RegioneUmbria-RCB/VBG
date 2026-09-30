package it.gruppoinit.pal.gp.core.domain;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "MAIL_CONFIG_COMUNI")
public class MailConfigComuni implements Serializable {

    private static final long serialVersionUID = 3458334728179385621L;
    private PkId id;
    private Comuni comune;
    private MailConfig mailConfig;

    public MailConfigComuni() {

	this.id = new PkId();
	this.comune = new Comuni();
	this.mailConfig = new MailConfig();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MAIL_CONFIG_COMUNI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)),
	    @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "FK_CODICECOMUNE", referencedColumnName = "CODICECOMUNE", nullable = true, insertable = true, updatable = false) })
    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "FK_MAILCONFIG_ID", referencedColumnName = "ID", nullable = true, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = true, insertable = false, updatable = false) })
    public MailConfig getMailConfig() {

	return mailConfig;
    }

    public void setMailConfig(MailConfig mailConfig) {

	this.mailConfig = mailConfig;
    }

    public static long getSerialversionuid() {

	return serialVersionUID;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mailConfigId;

    @Column(name = "FK_MAILCONFIG_ID")
    @SuppressWarnings("unused")
    private Integer getMailConfigId() {

	if (null != this.getMailConfig()) {
	    if (null != this.getMailConfig().getId()) {
		this.mailConfigId = getMailConfig().getId().getCodice();
		return this.mailConfigId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMailConfigId(Integer mailConfigId) {

	if (null != this.getMailConfig()) {
	    if (null != this.getMailConfig().getId()) {
		this.mailConfigId = getMailConfig().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////
}
