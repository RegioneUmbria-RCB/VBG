package it.gruppoinit.pal.gp.core.domain;

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

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "BOLL_CFG_TIPO_RATE")
public class BollCfgTipoRate implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9032523060292845052L;
    private PkId id;
    private BollCfgTipo bollCfgTipo;
    private RangeRateizzazioni rangeRateizzazioni;

    public BollCfgTipoRate() {

	this.id = new PkId();
	this.bollCfgTipo = new BollCfgTipo();
	this.rangeRateizzazioni = new RangeRateizzazioni();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BOLL_CFG_TIPO_RATE.ID") })
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_BOLLCFGTIPO_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public BollCfgTipo getBollCfgTipo() {

	return this.bollCfgTipo;
    }

    public void setBollCfgTipo(BollCfgTipo bollCfgTipo) {

	this.bollCfgTipo = bollCfgTipo;
    }

    private Integer bollCfgTipoId;

    @Column(name = "FK_BOLLCFGTIPO_ID")
    public Integer getBollCfgTipoId() {

	if (this.getBollCfgTipo() != null && this.getBollCfgTipo().getId() != null) {
	    this.bollCfgTipoId = getBollCfgTipo().getId().getCodice();
	    return this.bollCfgTipoId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setBollCfgTipoId(Integer bollCfgTipoId) {

	if (this.getBollCfgTipo() != null && this.getBollCfgTipo().getId() != null) {
	    this.bollCfgTipoId = getBollCfgTipo().getId().getCodice();
	}
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_RANGERATE", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public RangeRateizzazioni getRangeRateizzazioni() {

	return rangeRateizzazioni;
    }

    public void setRangeRateizzazioni(RangeRateizzazioni rangeRateizzazioni) {

	this.rangeRateizzazioni = rangeRateizzazioni;
    }

    private Integer rangeRateizzazioniId;

    @Column(name = "FK_RANGERATE")
    public Integer getRangeRateizzazioniId() {

	if (this.getRangeRateizzazioni() != null && this.getRangeRateizzazioni().getId() != null) {
	    this.rangeRateizzazioniId = getRangeRateizzazioni().getId().getCodice();
	    return this.rangeRateizzazioniId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setRangeRateizzazioniId(Integer rangeRateizzazioniId) {

	if (this.getRangeRateizzazioni() != null && this.getRangeRateizzazioni().getId() != null) {
	    this.rangeRateizzazioniId = getRangeRateizzazioni().getId().getCodice();
	}
    }
}
