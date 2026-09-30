package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "BLACKLIST_MOTIVI")
public class BlacklistMotivi implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7760067453029337351L;
    private PkId id;
    private String motivo;
    private Date dataInizioBl;
    private Date dataFineBl;
    private String contesto;
    private Date dataAccertamento;
    private Set<BlacklistAutorizzazioni> blacklistAutorizzazionis = null;
    private Set<BlacklistSrcPDebSp> blacklistSrcPDebSps = null;

    public BlacklistMotivi(String motivo, Date dataInizioBl) {

	this.id = new PkId();
	this.setMotivo(motivo);
	this.setDataInizioBl(dataInizioBl);
    }

    public BlacklistMotivi() {

	this(null, null);
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BLACKLIST_MOTIVI.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @Type(type = "org.springframework.orm.hibernate3.support.ClobStringType")
    @Column(name = "MOTIVO")
    public String getMotivo() {

	return motivo;
    }

    public void setMotivo(String motivo) {

	this.motivo = motivo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_INIZIO_BL")
    public Date getDataInizioBl() {

	return dataInizioBl;
    }

    public void setDataInizioBl(Date dataInizioBl) {

	this.dataInizioBl = dataInizioBl;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATA_FINE_BL")
    public Date getDataFineBl() {

	return dataFineBl;
    }

    public void setDataFineBl(Date dataFineBl) {

	this.dataFineBl = dataFineBl;
    }
    
    @Column(name = "CONTESTO")
    public String getContesto() {
    
        return contesto;
    }

    
    public void setContesto(String contesto) {
    
        this.contesto = contesto;
    }
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DATA_ACCERTAMENTO")
    public Date getDataAccertamento() {
    
        return dataAccertamento;
    }

    
    public void setDataAccertamento(Date dataAccertamento) {
    
        this.dataAccertamento = dataAccertamento;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blacklistMotivi")
    public Set<BlacklistAutorizzazioni> getBlacklistAutorizzazionis() {

	return blacklistAutorizzazionis;
    }

    public void setBlacklistAutorizzazionis(Set<BlacklistAutorizzazioni> blacklistAutorizzazionis) {

	this.blacklistAutorizzazionis = blacklistAutorizzazionis;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "blacklistMotivi")
    public Set<BlacklistSrcPDebSp> getBlacklistSrcPDebSps() {

	return blacklistSrcPDebSps;
    }

    public void setBlacklistSrcPDebSps(Set<BlacklistSrcPDebSp> blacklistSrcPDebSps) {

	this.blacklistSrcPDebSps = blacklistSrcPDebSps;
    }
}
