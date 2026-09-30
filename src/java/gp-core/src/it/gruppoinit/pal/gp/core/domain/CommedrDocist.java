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
@Table(name = "COMMEDR_DOCIST")
public class CommedrDocist {

    private PkId id;
    private CommissioniedilizieR commissioniedilizieR;
    private Istanze istanze;
    private Documentiistanza documentiistanza;

    public static CommedrDocist fromDocumentiIstanza(Documentiistanza documentiistanza, CommissioniedilizieR commissioniEdilizeR) {

	CommedrDocist e = new CommedrDocist();
	e.setDocumentiistanza(documentiistanza);
	e.setIstanze(documentiistanza.getIstanza());
	e.setCommissioniedilizieR(commissioniEdilizeR);
	return e;
    }

    public static CommedrDocist createNewForInsert(int fkCommedilizier, int fkCodiceistanza, int fkDocumentiistanza) {

	CommedrDocist e = new CommedrDocist();
	Documentiistanza docIst = new Documentiistanza();
	docIst.setId(new PkId(fkDocumentiistanza));
	CommissioniedilizieR comR = new CommissioniedilizieR();
	comR.setId(new PkId(fkCommedilizier));
	e.setCommissioniedilizieR(comR);
	e.setIstanze(new Istanze(fkCodiceistanza));
	e.setDocumentiistanza(docIst);
	return e;
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "COMMEDR_DOCIST.ID") })
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
	    @JoinColumn(name = "FK_DOCUMENTIISTANZA", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public Documentiistanza getDocumentiistanza() {

	return documentiistanza;
    }

    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer documentiistanzaId;

    @Column(name = "FK_DOCUMENTIISTANZA")
    @SuppressWarnings("unused")
    private Integer getDocumentiistanzaId() {

	if (null != this.getDocumentiistanza()) {
	    if (null != this.getDocumentiistanza().getId()) {
		this.documentiistanzaId = getDocumentiistanza().getId().getCodice();
		return this.documentiistanzaId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setDocumentiistanzaId(Integer documentiistanzaId) {

	if (null != this.getDocumentiistanza()) {
	    if (null != this.getDocumentiistanza().getId()) {
		this.documentiistanzaId = getDocumentiistanza().getId().getCodice();
	    }
	}
    }

    //END FIX/////////////////////////////////////////////////////
    public void setDocumentiistanza(Documentiistanza documentiistanza) {

	this.documentiistanza = documentiistanza;
    }
}
