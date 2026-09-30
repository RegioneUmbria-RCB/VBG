package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "LOG_SISTEMA")
public class LogSistema {

    private PkId id;
    private String attore;
    private String codiceEvento;
    private String evento;
    private String descrizioneEvento;
    private Date dataEvento;
    private String utenteConnesso;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "LOG_SISTEMA.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 15, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Column(name = "ATTORE", length = 10)
    public String getAttore() {

	return attore;
    }

    public void setAttore(String attore) {

	this.attore = attore;
    }

    @Column(name = "CODICE_EVENTO", length = 20)
    public String getCodiceEvento() {

	return codiceEvento;
    }

    public void setCodiceEvento(String codiceEvento) {

	this.codiceEvento = codiceEvento;
    }

    @Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
    @Column(name = "EVENTO")
    public String getEvento() {

	return evento;
    }

    public void setEvento(String evento) {

	this.evento = evento;
    }

    @Column(name = "DESCRIZIONE_EVENTO", length = 4000)
    public String getDescrizioneEvento() {

	return descrizioneEvento;
    }

    public void setDescrizioneEvento(String descrizioneEvento) {

	this.descrizioneEvento = descrizioneEvento;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_EVENTO", length = 7)
    public Date getDataEvento() {

	return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {

	this.dataEvento = dataEvento;
    }

    @Column(name = "UTENTE_CONNESSO", length = 50)
    public String getUtenteConnesso() {

	return utenteConnesso;
    }

    public void setUtenteConnesso(String utenteConnesso) {

	this.utenteConnesso = utenteConnesso;
    }
}
