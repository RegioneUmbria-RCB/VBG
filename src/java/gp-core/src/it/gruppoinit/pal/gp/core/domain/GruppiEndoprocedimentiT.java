package it.gruppoinit.pal.gp.core.domain;

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

@Entity
@Table(name = "GRUPPI_ENDOPROCEDIMENTI_T")
public class GruppiEndoprocedimentiT implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8273693867573194939L;
    private PkId id;
    private Software software;
    private String descrizione;
    private Integer numEndoWarning;
    private Tipimovimento tipimovimento;

    public GruppiEndoprocedimentiT() {

	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "GRUPPI_ENDOPROCEDIMENTI_T.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 4, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOFTWARE", nullable = false)
    public Software getSoftware() {

	return this.software;
    }

    public void setSoftware(Software software) {

	this.software = software;
    }

    @Column(name = "DESCRIZIONE", nullable = false, length = 100)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @Column(name = "NUM_ENDO_WARNING", precision = 2, scale = 0)
    public Integer getNumEndoWarning() {

	return numEndoWarning;
    }

    public void setNumEndoWarning(Integer numEndoWarning) {

	this.numEndoWarning = numEndoWarning;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "TIPOMOVIMENTO", referencedColumnName = "TIPOMOVIMENTO", nullable = false, insertable = false, updatable = false) })
    public Tipimovimento getTipimovimento() {

	return this.tipimovimento;
    }

    public void setTipimovimento(Tipimovimento tipimovimento) {

	this.tipimovimento = tipimovimento;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private String tipimovimentoId;

    @Column(name = "TIPOMOVIMENTO")
    @SuppressWarnings("unused")
    private String getTipimovimentoId() {

	if (null != this.getTipimovimento()) {
	    if (null != this.getTipimovimento().getId()) {
		this.tipimovimentoId = getTipimovimento().getId().getTipomovimento();
		return this.tipimovimentoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setTipimovimentoId(String tipimovimentoId) {

	if (null != this.getTipimovimento()) {
	    if (null != this.getTipimovimento().getId()) {
		this.tipimovimentoId = getTipimovimento().getId().getTipomovimento();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////
}
