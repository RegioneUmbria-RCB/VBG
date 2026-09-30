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
@Table(name = "ISTANZE_MASSIVE_D")
public class IstanzeMassiveD implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -3315635074830921045L;
    
    private PkId id;
    private MassiveDettaglio massiveDettaglio;
    private Boolean flagMovimento;
    private String fktipimovimento;
    private Integer fkcodiceamministrazione;
    
    public IstanzeMassiveD() {
	this.id = new PkId();
	this.flagMovimento = false;
    }
    
    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "ISTANZE_MASSIVE_D.ID") })
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
	    @JoinColumn(name = "FKID_MASSIVE_D", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public MassiveDettaglio getMassiveDettaglio() {
    
        return massiveDettaglio;
    }
    
    public void setMassiveDettaglio(MassiveDettaglio massiveDettaglio) {
    
        this.massiveDettaglio = massiveDettaglio;
    }
    
    @Column(name = "FLAG_MOVIMENTO", precision = 1, scale = 0)
    public Boolean getFlagMovimento() {
    
        return flagMovimento;
    }

    public void setFlagMovimento(Boolean flagMovimento) {
    
        this.flagMovimento = flagMovimento;
    }

    @Column(name = "FK_TIPIMOVIMENTO")
    public String getFktipimovimento() {
    
        return fktipimovimento;
    }

    
    public void setFktipimovimento(String fktipimovimento) {
    
        this.fktipimovimento = fktipimovimento;
    }

    @Column(name = "FK_AMMINISTRAZIONE")
    public Integer getFkcodiceamministrazione() {
    
        return fkcodiceamministrazione;
    }

    
    public void setFkcodiceamministrazione(Integer fkcodiceamministrazione) {
    
        this.fkcodiceamministrazione = fkcodiceamministrazione;
    }





    //FIXME WORKAROUND PER AGGIORNAMENTO CHIAVI ESTERNE COMPOSITE///
    private Integer massiveDettaglioId;

    @Column(name = "FKID_MASSIVE_D")
    @SuppressWarnings("unused")
    private Integer getMassiveDettaglioId() {

	if (null != this.getMassiveDettaglio()) {
	    if (null != this.getMassiveDettaglio().getId()) {
		this.massiveDettaglioId = getMassiveDettaglio().getId().getCodice();
		return this.massiveDettaglioId;
	    }
	}
	return null;
    }

    @SuppressWarnings("unused")
    private void setMassiveDettaglioId(Integer massiveDettaglioId) {

	if (null != this.getMassiveDettaglio()) {
	    if (null != this.getMassiveDettaglio().getId()) {
		this.massiveDettaglioId = getMassiveDettaglio().getId().getCodice();
	    }
	}
    }
    
  //END FIX/////////////////////////////////////////////////////
}
