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
import org.hibernate.validator.Max;
import org.hibernate.validator.Min;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "INVENTARIOPROC_ENDO")
public class InventarioprocEndo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1415491322311917520L;
	private PkId id;
	private Inventarioprocedimenti inventarioprocEndoT;
	private Inventarioprocedimenti inventarioprocEndoD;
	private Boolean flagPubblica;
	private Boolean flagNecessario;
	private Comuni comune;
	private Integer ordine;

	public InventarioprocEndo() {

		this.id = new PkId();
		this.inventarioprocEndoD = new Inventarioprocedimenti();
		this.inventarioprocEndoT = new Inventarioprocedimenti();
		this.flagNecessario = Boolean.FALSE;
		this.flagPubblica = Boolean.FALSE;
		this.comune = new Comuni();
	}

	@EmbeddedId
	@GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
			@Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
			@Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
			@Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
			@Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
			@Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "INVENTARIOPROC_ENDO.ID") })
	@GeneratedValue(generator = "pkGenerator")
	@AttributeOverrides({
			@AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
			@AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
	public PkId getId() {

		return this.id;
	}

	public void setId(PkId id) {

		this.id = id;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CODICEINVENTARIO_T", referencedColumnName = "CODICEINVENTARIO", nullable = false, insertable = false, updatable = false) })
	public Inventarioprocedimenti getInventarioprocEndoT() {

		return inventarioprocEndoT;
	}

	public void setInventarioprocEndoT(Inventarioprocedimenti inventarioprocEndoT) {

		this.inventarioprocEndoT = inventarioprocEndoT;
	}

	// FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
	private Integer inventarioprocEndoTId;

	@Column(name = "CODICEINVENTARIO_T")
	@SuppressWarnings("unused")
	private Integer getInventarioprocEndoTId() {

		if (null != this.getInventarioprocEndoT()) {
			if (null != this.getInventarioprocEndoT().getId()) {
				this.inventarioprocEndoTId = getInventarioprocEndoT().getId().getCodice();
				return this.inventarioprocEndoTId;
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	private void setInventarioprocEndoTId(Integer inventarioprocEndoTId) {

		if (null != this.getInventarioprocEndoT()) {
			if (null != this.getInventarioprocEndoT().getId()) {
				this.inventarioprocEndoTId = getInventarioprocEndoT().getId().getCodice();
			}
		}
	}

	// END FIX/////////////////////////////////////////////////////
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "CODICEINVENTARIO_D", referencedColumnName = "CODICEINVENTARIO", nullable = false, insertable = false, updatable = false) })
	public Inventarioprocedimenti getInventarioprocEndoD() {

		return inventarioprocEndoD;
	}

	public void setInventarioprocEndoD(Inventarioprocedimenti inventarioprocEndoD) {

		this.inventarioprocEndoD = inventarioprocEndoD;
	}

	// FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
	private Integer inventarioprocEndoDId;

	@Column(name = "CODICEINVENTARIO_D")
	@SuppressWarnings("unused")
	private Integer getinventarioprocEndoDId() {

		if (null != this.getInventarioprocEndoD()) {
			if (null != this.getInventarioprocEndoD().getId()) {
				this.inventarioprocEndoDId = getInventarioprocEndoD().getId().getCodice();
				return this.inventarioprocEndoDId;
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	private void setinventarioprocEndoDId(Integer inventarioprocEndoDId) {

		if (null != this.getInventarioprocEndoD()) {
			if (null != this.getInventarioprocEndoD().getId()) {
				this.inventarioprocEndoDId = getInventarioprocEndoD().getId().getCodice();
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

	@Column(name = "FLAg_NECESSARIO", precision = 1, scale = 0)
	public Boolean getFlagNecessario() {

		return flagNecessario;
	}

	public void setFlagNecessario(Boolean flagNecessario) {

		this.flagNecessario = flagNecessario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CODICECOMUNE")
	public Comuni getComune() {

		return comune;
	}

	public void setComune(Comuni comune) {

		this.comune = comune;
	}

	@Min(value = 0)
	@Max(value = 999999)
	@Column(name = "ORDINE", precision = 6, scale = 0)
	public Integer getOrdine() {
		return ordine;
	}

	public void setOrdine(Integer ordine) {
		this.ordine = ordine;
	}
}
