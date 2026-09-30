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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "MESSAGGI_MAIL_ALLEGATI")
public class MessaggiMailAllegati implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -232934490980755161L;
    private PkId id;
    private MessaggiMail messaggiMail;
    private Integer codiceOggetto;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MESSAGGI_MAIL_ALLEGATI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public void setmessaggiMail(MessaggiMail messaggiMail) {

	this.messaggiMail = messaggiMail;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_MESSAGGIMAIL", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MessaggiMail getmessaggiMail() {

	return this.messaggiMail;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer messaggiMailId;

    @Column(name = "FKID_MESSAGGIMAIL")
    @SuppressWarnings("unused")
    private Integer getmessaggiMailId() {

	if (null != this.getmessaggiMail()) {
	    if (null != this.getmessaggiMail().getId()) {
		this.messaggiMailId = getmessaggiMail().getId().getCodice();
		return this.messaggiMailId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setmessaggiMailId(Integer messaggiMailId) {

	if (null != this.getmessaggiMail()) {
	    if (null != this.getmessaggiMail().getId()) {
		this.messaggiMailId = getmessaggiMail().getId().getCodice();
	    }
	}
    }

    @Column(name = "CODICEOGGETTO", precision = 10, scale = 0, nullable = false)
    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(Integer codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }
}
