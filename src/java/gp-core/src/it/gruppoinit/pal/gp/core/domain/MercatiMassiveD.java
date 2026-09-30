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
@Table(name = "MERCATI_MASSIVE_D")
public class MercatiMassiveD implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2088674626266932389L;
    
    private PkId id;
    private MassiveDettaglio massiveDettaglio;
    private Autorizzazioni autorizzazione;

    public MercatiMassiveD() {

	this.id = new PkId();
    }

    
    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "MERCATI_MASSIVE_D.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
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
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "IDCOMUNE", referencedColumnName = "IDCOMUNE", nullable = false, insertable = false, updatable = false),
	    @JoinColumn(name = "FK_AUTORIZZAZIONE", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false) })
    public Autorizzazioni getAutorizzazione() {
    
        return autorizzazione;
    }


    
    public void setAutorizzazione(Autorizzazioni autorizzazione) {
    
        this.autorizzazione = autorizzazione;
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
    
    
    private Integer autorizzazioneId;

    @Column(name = "FK_AUTORIZZAZIONE")
    @SuppressWarnings("unused")
    public Integer getAutorizzazioneId() {
	if (null != this.getAutorizzazione()) {
	    if (null != this.getAutorizzazione().getId()) {
		this.autorizzazioneId = this.getAutorizzazione().getId().getCodice();
		return autorizzazioneId;
	    }
	}
        return null;
    }


    
    public void setAutorizzazioneId(Integer autorizzazioneId) {
    
	if (null != this.getAutorizzazione()) {
	    if (null != this.getAutorizzazione().getId()) {
		this.autorizzazioneId = this.getAutorizzazione().getId().getCodice();
	    }
	}
    }
    
    //END FIX/////////////////////////////////////////////////////
    
}
