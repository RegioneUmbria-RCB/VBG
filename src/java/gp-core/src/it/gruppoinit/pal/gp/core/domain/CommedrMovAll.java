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

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "COMMEDR_MOVALL")
public class CommedrMovAll {

    private PkId id;
    private CommissioniedilizieR commissioniedilizieR;
    private Istanze istanze;
    private Movimentiallegati movimentiallegati;

    public static CommedrMovAll fromIstanzeallegati(Movimentiallegati movimentiallegati, CommissioniedilizieR commissioniEdilizieR) {

	CommedrMovAll el = new CommedrMovAll();
	el.setCommissioniedilizieR(commissioniEdilizieR);
	el.setIstanze(movimentiallegati.getMovimento().getIstanza());
	el.setMovimentiallegati(movimentiallegati);
	return el;
    }

    public static CommedrMovAll createNewForInsert(int idCommissioneR, int codiceIstanza, Integer idMovimentiAllegati) {

	CommedrMovAll e = new CommedrMovAll();
	Movimentiallegati movAll = new Movimentiallegati();
	movAll.setId(new PkId(idMovimentiAllegati));
	e.setCommissioniedilizieR(new CommissioniedilizieR(idCommissioneR));
	e.setIstanze(new Istanze(codiceIstanza));
	e.setMovimentiallegati(movAll);
	return e;
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "COMMEDR_MOVALL.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 4, scale = 0)),
	    @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_COMMEDILIZIER", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public CommissioniedilizieR getCommissioniedilizieR() {

	return commissioniedilizieR;
    }

    public void setCommissioniedilizieR(CommissioniedilizieR commissioniedilizieR) {

	this.commissioniedilizieR = commissioniedilizieR;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_CODICEISTANZA", referencedColumnName = "CODICEISTANZA", nullable = false, insertable = false, updatable = false) })
    public Istanze getIstanze() {

	return istanze;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer istanzeId;

    @Column(name = "FK_CODICEISTANZA")
    @SuppressWarnings("unused")
    private Integer getIstanzeId() {

	if (null != this.getIstanze()) {
	    if (null != this.getIstanze().getId()) {
		this.istanzeId = getIstanze().getId().getCodice();
		return this.istanzeId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setIstanzeId(Integer istanzeId) {

	if (null != this.getIstanze()) {
	    if (null != this.getIstanze().getId()) {
		this.istanzeId = getIstanze().getId().getCodice();
	    }
	}
    }

    private Integer commEdilizieRid;

    @Column(name = "FK_COMMEDILIZIER")
    @SuppressWarnings("unused")
    private Integer getCommEdilizieRid() {

	if (null != this.getCommissioniedilizieR() && null != this.getIstanze().getId()) {
	    this.commEdilizieRid = this.getCommissioniedilizieR().getId().getCodice();
	    return this.commEdilizieRid;
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setCommEdilizieRid(Integer commEdilizieRid) {

	if (null != this.getCommissioniedilizieR() && null != this.getCommissioniedilizieR().getId()) {
	    this.commEdilizieRid = getCommissioniedilizieR().getId().getCodice();
	}
    }
    //END FIX/////////////////////////////////////////////////////

    public void setIstanze(Istanze istanze) {

	this.istanze = istanze;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_MOVIMENTIALLEGATI", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public Movimentiallegati getMovimentiallegati() {

	return movimentiallegati;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer movimentiallegatiId;

    @Column(name = "FK_MOVIMENTIALLEGATI")
    @SuppressWarnings("unused")
    private Integer getMovimentiallegatiId() {

	if (null != this.getMovimentiallegati()) {
	    if (null != this.getMovimentiallegati().getId()) {
		this.movimentiallegatiId = getMovimentiallegati().getId().getCodice();
		return this.movimentiallegatiId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMovimentiallegatiId(Integer movimentiallegatiId) {

	if (null != this.getMovimentiallegati()) {
	    if (null != this.getMovimentiallegati().getId()) {
		this.movimentiallegatiId = getMovimentiallegati().getId().getCodice();
	    }
	}
    }
    //END FIX/////////////////////////////////////////////////////

    public void setMovimentiallegati(Movimentiallegati movimentiallegati) {

	this.movimentiallegati = movimentiallegati;
    }
}
