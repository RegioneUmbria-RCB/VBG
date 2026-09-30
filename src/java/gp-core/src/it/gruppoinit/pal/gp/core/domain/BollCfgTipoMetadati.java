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
@Table(name = "BOLL_CFG_TIPO_METADATI")
public class BollCfgTipoMetadati implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4109739107320234902L;
    private PkId id;
    private BollCfgTipo bollCfgTipo;
    private Comuni comune;
    private String chiave;
    private String valore;

    public BollCfgTipoMetadati() {

	this.id = new PkId();
	this.bollCfgTipo = new BollCfgTipo();
	this.comune = new Comuni();
    }

    public BollCfgTipoMetadati(Integer id) {

	this.id = new PkId(id);
	this.bollCfgTipo = new BollCfgTipo();
	this.comune = new Comuni();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BOLL_CFG_TIPO_METADATI.ID") })
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "CODICECOMUNE", referencedColumnName = "CODICECOMUNE", updatable = false) })
    public Comuni getComune() {

	return comune;
    }

    public void setComune(Comuni comune) {

	this.comune = comune;
    }

    @Column(name = "CHIAVE", nullable = false, length = 60)
    public String getChiave() {

	return chiave;
    }

    public void setChiave(String chiave) {

	this.chiave = chiave;
    }

    @Column(name = "VALORE", nullable = false, length = 500)
    public String getValore() {

	return valore;
    }

    public void setValore(String valore) {

	this.valore = valore;
    }
}
