package it.gruppoinit.pal.gp.core.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "BORSELLINO_AUT_STORICO")
public class BorsellinoAutStorico implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1198326582332663691L;
    private PkId id;
    private Integer fkidBorsellino;
    private Integer fkidAutorizzazioni;
    private String tipoOperazione;
    private Date dataOperazione;
    
    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BORSELLINO_AUT_STORICO.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides( { @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 4, scale = 0)) })
    public PkId getId() {
    
        return id;
    }
    
    public void setId(PkId id) {
    
        this.id = id;
    }
    
    @Column(name = "FKID_BORSELLINO")
    public Integer getFkidBorsellino() {
    
        return fkidBorsellino;
    }
    
    public void setFkidBorsellino(Integer fkidBorsellino) {
    
        this.fkidBorsellino = fkidBorsellino;
    }
    
    @Column(name = "FKID_AUTORIZZAZIONI")
    public Integer getFkidAutorizzazioni() {
    
        return fkidAutorizzazioni;
    }
    
    public void setFkidAutorizzazioni(Integer fkidAutorizzazioni) {
    
        this.fkidAutorizzazioni = fkidAutorizzazioni;
    }

    @Column(name = "TIPO_OPERAZIONE")
    public String getTipoOperazione() {
    
        return tipoOperazione;
    }

    
    public void setTipoOperazione(String tipoOperazione) {
    
        this.tipoOperazione = tipoOperazione;
    }

    @Column(name = "DATA_OPERAZIONE")
    public Date getDataOperazione() {
    
        return dataOperazione;
    }

    
    public void setDataOperazione(Date dataOperazione) {
    
        this.dataOperazione = dataOperazione;
    }
    
    
    
}
