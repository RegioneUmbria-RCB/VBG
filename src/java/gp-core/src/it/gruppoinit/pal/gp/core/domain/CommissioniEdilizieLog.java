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

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "COMMISSIONI_EDILIZIE_LOG")
public class CommissioniEdilizieLog implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private PkId id;
    private String categoria;
    private Date data;
    private CommissioniedilizieT commissioniedilizieT;
    private String messaggio;

    public CommissioniEdilizieLog() {

	this.id = new PkId();
	this.commissioniedilizieT = new CommissioniedilizieT();
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "COMMISSIONI_EDILIZIE_LOG.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Column(name = "CATEGORIA", length = 50)
    public String getCategoria() {

	return categoria;
    }

    public void setCategoria(String categoria) {

	this.categoria = categoria;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA", nullable = false)
    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_COMMISSIONE", referencedColumnName = "CODICECOMMISSIONE", nullable = false, insertable = false, updatable = false) })
    public CommissioniedilizieT getCommissioniedilizieT() {

	return commissioniedilizieT;
    }

    public void setCommissioniedilizieT(CommissioniedilizieT commissioniedilizieT) {

	this.commissioniedilizieT = commissioniedilizieT;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer commissioniedilizieTId;

    @Column(name = "FK_COMMISSIONE")
    @SuppressWarnings("unused")
    private Integer getCommissioniedilizieTId() {

	if (null != this.getCommissioniedilizieT()) {
	    if (null != this.getCommissioniedilizieT().getId()) {
		this.commissioniedilizieTId = getCommissioniedilizieT().getId().getCodice();
		return commissioniedilizieTId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setCommissioniedilizieTId(Integer commissioniedilizieTId) {

	if (null != this.getCommissioniedilizieT()) {
	    if (null != this.getCommissioniedilizieT().getId()) {
		this.commissioniedilizieTId = getCommissioniedilizieT().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    @Column(name = "MESSAGGIO", length = 4000)
    public String getMessaggio() {

	return messaggio;
    }

    public void setMessaggio(String messaggio) {

	this.messaggio = messaggio;
    }
}
