package it.gruppoinit.pal.gp.core.domain;

import java.io.Serializable;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

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
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

@Entity
@Table(name = "RUOLI_PROTOCOLLO")
public class RuoliProtocollo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3874895540237941754L;
    private PkId id;
    private String ruoloExt;
    private Ruoli ruolo;
    private Comuni comune;
    private Software software;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "RUOLI_PROTOCOLLO.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)),
	    @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotEmpty
    @Length(max = 50)
    @Column(name = "RUOLO_EXT", nullable = false, length = 50)
    public String getRuoloExt() {

	return ruoloExt;
    }

    public void setRuoloExt(String ruoloExt) {

	this.ruoloExt = ruoloExt;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDRUOLO", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public Ruoli getRuolo() {

	return this.ruolo;
    }

    public void setRuolo(Ruoli ruolo) {

	this.ruolo = ruolo;
    }

    private Integer ruoliId;

    @Column(name = "IDRUOLO")
    @SuppressWarnings("unused")
    private Integer getRuoliId() {

	if (null != this.getRuolo()) {
	    if (null != this.getRuolo().getId()) {
		this.ruoliId = getRuolo().getId().getCodice();
		return this.ruoliId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setRuoliId(Integer ruoliId) {

	if (null != this.getRuolo()) {
	    if (null != this.getRuolo().getId()) {
		this.ruoliId = getRuolo().getId().getCodice();
	    }
	}
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOFTWARE")
    public Software getSoftware() {

	return this.software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODICECOMUNE", referencedColumnName = "CODICECOMUNE")
    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }
}
