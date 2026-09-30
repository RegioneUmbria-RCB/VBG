package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "TEMP_LINKALLEGATI")
public class TempLinkallegati {

    private PkId id;
    private String uuid;
    private String link;
    private Integer codiceoggetto;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "TEMP_LINKALLEGATI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotEmpty
    @Length(max = 50)
    @Column(name = "UUID", length = 50)
    public String getUuid() {

	return uuid;
    }

    public void setUuid(String uuid) {

	this.uuid = uuid;
    }

    @NotEmpty
    @Length(max = 500)
    @Column(name = "LINK", length = 500)
    public String getLink() {

	return link;
    }

    public void setLink(String link) {

	this.link = link;
    }

    @NotNull
    @Column(name = "CODICEOGGETTO", precision = 10, scale = 0)
    public Integer getCodiceoggetto() {

	return codiceoggetto;
    }

    public void setCodiceoggetto(Integer codiceoggetto) {

	this.codiceoggetto = codiceoggetto;
    }
}
