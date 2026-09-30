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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.NotNull;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "BORSELLINO_ACCETTAZIONI")
public class BorsellinoAccettazioni implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8527062190460624623L;
    private PkId id;
    private BorsellinoInformative informativa;
    private Borsellino borsellino;
    private Date dataAccettazione;

    public BorsellinoAccettazioni() {

	this.id = new PkId();
	this.informativa = new BorsellinoInformative();
	this.borsellino = new Borsellino();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BORSELLINO_ACCETTAZIONI.ID") })
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
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FK_IDINFORMATIVA", referencedColumnName = "ID", insertable = false, updatable = false) })
    public BorsellinoInformative getInformativa() {

	return informativa;
    }

    public void setInformativa(BorsellinoInformative informativa) {

	this.informativa = informativa;
    }

    private Integer informativaId;

    @Column(name = "FK_IDINFORMATIVA")
    @SuppressWarnings("unused")
    public Integer getInformativaId() {

	if (null != this.getInformativa() && null != this.getInformativa().getId()) {
	    this.informativaId = this.getInformativa().getId().getCodice();
	    return this.informativaId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setInformativaId(Integer informativaId) {

	if (null != this.getInformativa() && null != this.getInformativa().getId()) {
	    this.informativaId = this.getInformativa().getId().getCodice();
	}
    }

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_BORSELLINO", referencedColumnName = "ID", insertable = false, updatable = false) })
    public Borsellino getBorsellino() {

	return borsellino;
    }

    public void setBorsellino(Borsellino borsellino) {

	this.borsellino = borsellino;
    }

    private Integer borsellinoId;

    @Column(name = "FKID_BORSELLINO")
    @SuppressWarnings("unused")
    public Integer getBorsellinoId() {

	if (null != this.getBorsellino() && null != this.getBorsellino().getId()) {
	    this.borsellinoId = this.getBorsellino().getId().getCodice();
	    return this.borsellinoId;
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setBorsellinoId(Integer borsellinoId) {

	if (null != this.getBorsellino() && null != this.getBorsellino().getId()) {
	    this.borsellinoId = this.getBorsellino().getId().getCodice();
	}
    }

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_ACCETTAZIONE")
    public Date getDataAccettazione() {

	return dataAccettazione;
    }

    public void setDataAccettazione(Date dataAccettazione) {

	this.dataAccettazione = dataAccettazione;
    }
}
