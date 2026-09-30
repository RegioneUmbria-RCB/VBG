package it.gruppoinit.pal.gp.core.domain;

// Generated 26-set-2008 16.05.21 by Hibernate Tools 3.2.2.GA
import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

/**
 * 
 * @author gianpaolot
 * 
 */
@Entity
@Table(name = "TIPIFAMIGLIEENDO")
public class Tipifamiglieendo implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1489870666508884640L;
    private PkId id;
    private String tipo;
    private Integer ordine;
    private Software software;
    private String note;
    private Boolean flagPubblica;
    private Set<Tipiendo> tipiendos = new HashSet<Tipiendo>(0);
    private Set<StpTipologieEndo1> stpTipologieEndo1s = new HashSet<StpTipologieEndo1>(0);
    private Set<AlberoprocArendo> alberoprocArendos = new HashSet<AlberoprocArendo>(0);
    // Campi transiet
    private String descrizioneEstesa;

    public Tipifamiglieendo() {

	this.id = new PkId();
	this.software = new Software();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "TIPIFAMIGLIEENDO.CODICE") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "CODICE", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotEmpty
    @Length(max = 4000)
    @Column(name = "TIPO", length = 4000)
    public String getTipo() {

	return this.tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    @Column(name = "ORDINE", precision = 6, scale = 0)
    public Integer getOrdine() {

	return this.ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOFTWARE", nullable = false)
    public Software getSoftware() {

	return this.software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    @Length(max = 4000)
    @Column(name = "NOTE", length = 4000)
    public String getNote() {

	return this.note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    @Column(name = "FLAG_PUBBLICA", length = 1)
    public Boolean getFlagPubblica() {

	return flagPubblica;
    }

    public void setFlagPubblica(Boolean flagPubblica) {

	this.flagPubblica = flagPubblica;
    }

    //@OrderBy("ordine asc, tipo asc")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipifamiglieendo")
    public Set<Tipiendo> getTipiendos() {

	return this.tipiendos;
    }

    public void setTipiendos(Set<Tipiendo> tipiendos) {

	this.tipiendos = tipiendos;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tipifamiglieendo")
    public Set<StpTipologieEndo1> getStpTipologieEndo1s() {

	return stpTipologieEndo1s;
    }

    public void setStpTipologieEndo1s(Set<StpTipologieEndo1> stpTipologieEndo1s) {

	this.stpTipologieEndo1s = stpTipologieEndo1s;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipifamiglieendo")
    public Set<AlberoprocArendo> getAlberoprocArendos() {

	return this.alberoprocArendos;
    }

    public void setAlberoprocArendos(Set<AlberoprocArendo> alberoprocArendos) {

	this.alberoprocArendos = alberoprocArendos;
    }

    @Transient
    public String getDescrizioneEstesa() {

	this.descrizioneEstesa = "";
	if (StringUtils.isNotBlank(getTipo())) {
	    descrizioneEstesa = getTipo() + " (" + getId().getCodice() + ")";
	}
	return this.descrizioneEstesa;
    }
}
