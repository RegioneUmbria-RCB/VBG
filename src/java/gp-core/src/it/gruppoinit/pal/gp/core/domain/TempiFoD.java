package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;
import it.gruppoinit.pal.gp.core.features.alberoproc.tempi.TipoTempiFOEnum;

@Entity
@Table(name = "TEMPI_FO_D")
public class TempiFoD implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6414837241742361767L;
    private PkId id;
    private TempiFoT testata;
    private String titolo;
    private String descrizione;
    private String tipo;
    private TipoTempiFOEnum tipoTempi;
    private Integer giorni;
    private Date scadenza;
    private Integer ordine;

    public TempiFoD() {

	this.id = new PkId();
	this.testata = new TempiFoT();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "TEMPI_FO_D.ID") })
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
    @JoinColumns({ @JoinColumn(name = "FKID_TEMPI", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false) })
    public TempiFoT getTestata() {

	return testata;
    }

    public void setTestata(TempiFoT testata) {

	this.testata = testata;
    }

    // FIXWORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer tempiFoTId;

    @Column(name = "FKID_TEMPI")
    @SuppressWarnings("unused")
    private Integer getTempiFoTId() {

	if (null != this.getTestata()) {
	    if (null != this.getTestata().getId()) {
		this.tempiFoTId = this.getTestata().getId().getCodice();
		return this.tempiFoTId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setTempiFoTId(Integer tempiFoTId) {

	if (null != this.getTestata()) {
	    if (null != this.getTestata().getId()) {
		this.tempiFoTId = this.getTestata().getId().getCodice();
	    }
	}
    }
    // END FIX/////////////////////////////////////////////////////

    @NotEmpty
    @Length(max = 100)
    @Column(name = "TITOLO", length = 100)
    public String getTitolo() {

	return titolo;
    }

    public void setTitolo(String titolo) {

	this.titolo = titolo;
    }

    @Length(max = 200)
    @Column(name = "DESCRIZIONE", length = 200)
    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    @NotEmpty
    @Length(max = 1)
    @Pattern(regex = "^A$|^R$", message = "validator.tipotempofo")
    @Column(name = "TIPO", length = 1)
    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    @Column(name = "GIORNI", precision = 3, scale = 0)
    public Integer getGiorni() {

	return giorni;
    }

    public void setGiorni(Integer giorni) {

	this.giorni = giorni;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SCADENZA")
    public Date getScadenza() {

	return scadenza;
    }

    public void setScadenza(Date scadenza) {

	this.scadenza = scadenza;
    }

    @NotNull
    @Column(name = "ORDINE", precision = 2, scale = 0)
    public Integer getOrdine() {

	return ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    @Transient
    public TipoTempiFOEnum getTipoTempi() {

	return TipoTempiFOEnum.fromValue(this.tipo);
    }
}
