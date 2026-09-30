package it.gruppoinit.pal.gp.core.domain;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

import java.io.Serializable;

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
import org.hibernate.validator.Max;
import org.hibernate.validator.NotEmpty;

@Entity
@Table(name = "INVENTARIOPROC_QRT")
public class InventarioprocQrt implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5441394201759245698L;
    private PkId id;
    private String titolo;
    private Oggetti oggetti;
    private Boolean flagPubblica;
    private Integer ordine;
    private String codice;
    private String help;
    private Inventarioprocedimenti inventarioprocedimento;

    public InventarioprocQrt() {

	this.oggetti = new Oggetti();
	this.inventarioprocedimento = new Inventarioprocedimenti();
	this.id = new PkId();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "INVENTARIOPROC_QRT.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotEmpty
    @Length(max = 200)
    @Column(name = "TITOLO", length = 200)
    public String getTitolo() {

	return titolo;
    }

    public void setTitolo(String titolo) {

	this.titolo = titolo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEOGGETTO", referencedColumnName = "CODICEOGGETTO", nullable = false, insertable = false, updatable = false) })
    public Oggetti getOggetti() {

	return this.oggetti;
    }

    public void setOggetti(Oggetti oggetti) {

	this.oggetti = oggetti;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer oggettiId;

    @Column(name = "CODICEOGGETTO")
    private Integer getOggettiId() {

	if (null != this.getOggetti()) {
	    if (null != this.getOggetti().getId()) {
		this.oggettiId = getOggetti().getId().getCodice();
		return this.oggettiId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setOggettiId(Integer oggettiId) {

	if (null != this.getOggetti()) {
	    if (null != this.getOggetti().getId()) {
		this.oggettiId = getOggetti().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @Column(name = "FLAG_PUBBLICA", precision = 1, scale = 0)
    public Boolean getFlagPubblica() {

	return flagPubblica;
    }

    public void setFlagPubblica(Boolean flagPubblica) {

	this.flagPubblica = flagPubblica;
    }

    @Max(value = 9999)
    @Column(name = "ORDINE", precision = 4, scale = 0)
    public Integer getOrdine() {

	return this.ordine;
    }

    public void setOrdine(Integer ordine) {

	this.ordine = ordine;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
	    @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "CODICEINVENTARIO", referencedColumnName = "CODICEINVENTARIO", nullable = false, insertable = false, updatable = false) })
    public Inventarioprocedimenti getInventarioprocedimento() {

	return this.inventarioprocedimento;
    }

    public void setInventarioprocedimento(Inventarioprocedimenti inventarioprocedimento) {

	this.inventarioprocedimento = inventarioprocedimento;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer inventarioprocedimentoId;

    @Column(name = "CODICEINVENTARIO")
    private Integer getInventarioprocedimentoId() {

	if (null != this.getInventarioprocedimento()) {
	    if (null != this.getInventarioprocedimento().getId()) {
		this.inventarioprocedimentoId = getInventarioprocedimento().getId().getCodice();
		return this.inventarioprocedimentoId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setInventarioprocedimentoId(Integer inventarioprocedimentoId) {

	if (null != this.getInventarioprocedimento()) {
	    if (null != this.getInventarioprocedimento().getId()) {
		this.inventarioprocedimentoId = getInventarioprocedimento().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
    @Length(max = 60)
    @Column(name = "CODICE", length = 60)
    public String getCodice() {

	return codice;
    }

    public void setCodice(String codice) {

	this.codice = codice;
    }

    @Length(max = 60)
    @Column(name = "HELP", length = 60)
    public String getHelp() {

	return help;
    }

    public void setHelp(String help) {

	this.help = help;
    }
}
