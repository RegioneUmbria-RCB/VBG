package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "AT_ESITO_ERRORI")
public class AtEsitoErrori implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7436998146897569010L;
    private PkId id;
    private Integer fkidAtEsitoGruppo;
    private String tipoErrore;
    private String descrizioneErrore;
    private String intestazione;
    private boolean flagVerificato;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "AT_ESITO_ERRORI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Column(name = "FKID_AT_ESITO_GRUPPO")
    public Integer getFkidAtEsitoGruppo() {

	return fkidAtEsitoGruppo;
    }

    public void setFkidAtEsitoGruppo(Integer fkidAtEsitoGruppo) {

	this.fkidAtEsitoGruppo = fkidAtEsitoGruppo;
    }

    @Column(name = "TIPO_ERRORE")
    public String getTipoErrore() {

	return tipoErrore;
    }

    public void setTipoErrore(String tipoErrore) {

	this.tipoErrore = tipoErrore;
    }

    @Column(name = "DESCRIZIONE_ERRORE")
    public String getDescrizioneErrore() {

	return descrizioneErrore;
    }

    public void setDescrizioneErrore(String descrizioneErrore) {

	this.descrizioneErrore = descrizioneErrore;
    }

    @Column(name = "INTESTAZIONE")
    public String getIntestazione() {

	return intestazione;
    }

    public void setIntestazione(String intestazione) {

	this.intestazione = intestazione;
    }

    @Column(name = "FLAG_VERIFICATO")
    public boolean getFlagVerificato() {

	return flagVerificato;
    }

    public void setFlagVerificato(boolean flagVerificato) {

	this.flagVerificato = flagVerificato;
    }
}
