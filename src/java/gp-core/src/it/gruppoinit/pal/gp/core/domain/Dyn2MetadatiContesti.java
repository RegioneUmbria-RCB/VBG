package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "DYN2_METADATI_CONTESTI", uniqueConstraints = @UniqueConstraint(columnNames = { "IDCOMUNE", "CONTESTO" }))
public class Dyn2MetadatiContesti implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2276335450484270419L;
    private PkId id;
    private String contesto;
    private String descrizione;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "DYN2_METADATI_CONTESTI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)),
	    @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotNull
    @Column(name = "CONTESTO", length = 255)
    public String getContesto() {

	return contesto;
    }

    public void setContesto(String contesto) {

	this.contesto = contesto;
    }

    @Column(name = "DESCRIZIONE", length = 4000)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }
}
