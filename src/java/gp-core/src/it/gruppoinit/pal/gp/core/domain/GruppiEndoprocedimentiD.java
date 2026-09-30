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
@Table(name = "GRUPPI_ENDOPROCEDIMENTI_D")
public class GruppiEndoprocedimentiD implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6470195484330963528L;
    private PkId id;
    private GruppiEndoprocedimentiT gruppiEndoprocedimentiT;
    private Inventarioprocedimenti inventarioprocedimento;

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "GRUPPI_ENDOPROCEDIMENTI_D.ID") })
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
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_GET_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public GruppiEndoprocedimentiT getGruppiEndoprocedimentiT() {

	return gruppiEndoprocedimentiT;
    }

    public void setGruppiEndoprocedimentiT(GruppiEndoprocedimentiT gruppiEndoprocedimentiT) {

	this.gruppiEndoprocedimentiT = gruppiEndoprocedimentiT;
    }

    // FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer gruppiEndoprocedimentiTId;

    @Column(name = "FK_GET_ID")
    @SuppressWarnings("unused")
    private Integer getGruppiEndoprocedimentiTId() {

	if (null != this.getGruppiEndoprocedimentiT()) {
	    if (null != this.getGruppiEndoprocedimentiT().getId()) {
		this.gruppiEndoprocedimentiTId = getGruppiEndoprocedimentiT().getId().getCodice();
		return this.gruppiEndoprocedimentiTId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setGruppiEndoprocedimentiTId(Integer gruppiEndoprocedimentiTId) {

	if (null != this.getGruppiEndoprocedimentiT()) {
	    if (null != this.getGruppiEndoprocedimentiT().getId()) {
		this.gruppiEndoprocedimentiTId = getGruppiEndoprocedimentiT().getId().getCodice();
	    }
	}
    }

    // END FIX/////////////////////////////////////////////////////
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
    @SuppressWarnings("unused")
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
}
