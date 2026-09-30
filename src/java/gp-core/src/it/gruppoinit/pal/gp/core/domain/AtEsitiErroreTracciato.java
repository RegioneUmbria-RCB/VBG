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
@Table(name = "AT_ESITI_ERRORE_TRACCIATO")
public class AtEsitiErroreTracciato {

    private PkId id;
    private Integer fkidAtesitoErrori;
    private String tracciatoRecord;
    private Integer posNelTracciato;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "AT_ESITI_ERRORE_TRACCIATO.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Column(name = "FKID_ATESITO_ERRORI")
    public Integer getFkidAtesitoErrori() {

	return fkidAtesitoErrori;
    }

    public void setFkidAtesitoErrori(Integer fkidAtesitoErrori) {

	this.fkidAtesitoErrori = fkidAtesitoErrori;
    }

    @Column(name = "TRACCIATO_RECORD")
    public String getTracciatoRecord() {

	return tracciatoRecord;
    }

    public void setTracciatoRecord(String tracciatoRecord) {

	this.tracciatoRecord = tracciatoRecord;
    }

    @Column(name = "POS_NEL_TRACCIATO")
    public Integer getPosNelTracciato() {

	return posNelTracciato;
    }

    public void setPosNelTracciato(Integer posNelTracciato) {

	this.posNelTracciato = posNelTracciato;
    }
}
