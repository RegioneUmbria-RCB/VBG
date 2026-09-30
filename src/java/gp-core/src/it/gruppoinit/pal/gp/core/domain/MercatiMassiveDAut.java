package it.gruppoinit.pal.gp.core.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MERCATI_MASSIVE_D_AUT")
public class MercatiMassiveDAut implements java.io.Serializable{
   
    /**
     * 
     */
    private static final long serialVersionUID = -1197559901730669322L;
    private MercatiMassiveDAutId id;
    private MercatiMassiveD mercatiMassiveD;
    private Integer fkcodicemercato;
    
    public MercatiMassiveDAut() {
	this.id = new MercatiMassiveDAutId();
    }
    
    
    @EmbeddedId
    public MercatiMassiveDAutId getId() {
    
        return id;
    }
    
    public void setId(MercatiMassiveDAutId id) {
    
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_MERCATI_MASSIVE_D", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MercatiMassiveD getMercatiMassiveD() {
    
        return mercatiMassiveD;
    }
    
    public void setMercatiMassiveD(MercatiMassiveD mercatiMassiveD) {
    
        this.mercatiMassiveD = mercatiMassiveD;
    }
  
    @Column(name = "FK_CODICEMERCATO")
    public Integer getFkcodicemercato() {
    
        return fkcodicemercato;
    }

    public void setFkcodicemercato(Integer fkcodicemercato) {
    
        this.fkcodicemercato = fkcodicemercato;
    }
    
    
    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer mercatiMassiveDId;
    
    @Column(name = "FK_MERCATI_MASSIVE_D")
    @SuppressWarnings("unused")
    public Integer getMercatiMassiveDId() {
    
	if (null != this.getMercatiMassiveD()) {
	    if (null != this.getMercatiMassiveD().getId()) {
		this.mercatiMassiveDId = getMercatiMassiveD().getId().getCodice();
		return this.mercatiMassiveDId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setMercatiMassiveDId(Integer mercatiMassiveDId) {
    
	if (null != this.getMercatiMassiveD()) {
	    if (null != this.getMercatiMassiveD().getId()) {
		this.mercatiMassiveDId = getMercatiMassiveD().getId().getCodice();
	    }
	}
    }

  //END FIX/////////////////////////////////////////////////////
}
