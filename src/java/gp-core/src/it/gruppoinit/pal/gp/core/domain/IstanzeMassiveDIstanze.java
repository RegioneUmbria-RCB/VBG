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
@Table(name = "ISTANZE_MASSIVE_D_ISTANZE")
public class IstanzeMassiveDIstanze implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -540364941717933090L;
    
    private PkId id;
    private IstanzeMassiveD istanzeMassiveD;
    private Integer fkcodiceistanza;
    private Integer fkmovimento;
    
    public IstanzeMassiveDIstanze() {
	this.id = new PkId();
    }
    
    
    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "ISTANZE_MASSIVE_D_ISTANZE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 24)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 6, scale = 0)) })
    public PkId getId() {
    
        return id;
    }
    
    public void setId(PkId id) {
    
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FKID_ISTANZEMD", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public IstanzeMassiveD getIstanzeMassiveD() {
    
        return istanzeMassiveD;
    }
    
    public void setIstanzeMassiveD(IstanzeMassiveD istanzeMassiveD) {
    
        this.istanzeMassiveD = istanzeMassiveD;
    }
    
    @Column(name = "FKCODICEISTANZA")
    public Integer getFkcodiceistanza() {
    
        return fkcodiceistanza;
    }
    
    public void setFkcodiceistanza(Integer fkcodiceistanza) {
    
        this.fkcodiceistanza = fkcodiceistanza;
    }
    
    @Column(name = "FKMOVIMENTO")
    public Integer getFkmovimento() {
    
        return fkmovimento;
    }


    
    public void setFkmovimento(Integer fkmovimento) {
    
        this.fkmovimento = fkmovimento;
    }



    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer istanzeMassiveDId;
    
    @Column(name = "FKID_ISTANZEMD")
    @SuppressWarnings("unused")
    public Integer getIstanzeMassiveTId() {
    
	if (null != this.getIstanzeMassiveD()) {
	    if (null != this.getIstanzeMassiveD().getId()) {
		this.istanzeMassiveDId = getIstanzeMassiveD().getId().getCodice();
		return this.istanzeMassiveDId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    public void setIstanzeMassiveTId(Integer istanzeMassiveTId) {
    
	if (null != this.getIstanzeMassiveD()) {
	    if (null != this.getIstanzeMassiveD().getId()) {
		this.istanzeMassiveDId = getIstanzeMassiveD().getId().getCodice();
	    }
	}
    }

  //END FIX/////////////////////////////////////////////////////
}
