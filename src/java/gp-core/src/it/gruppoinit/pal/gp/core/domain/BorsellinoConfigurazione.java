package it.gruppoinit.pal.gp.core.domain;

import java.math.BigDecimal;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotEmpty;

import it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator;

@Entity
@Table(name = "BORSELLINO_CONFIGURAZIONE")
public class BorsellinoConfigurazione implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5100264643016314341L;
    private PkId id;
    private String tipoInstallazione;
    private String msgNodoPagNonDisp;
    private Boolean gestioneFO;
    private String attivaPagamenti;
    private BigDecimal importomassimo;

    public BorsellinoConfigurazione() {

	this.id = new PkId();
	this.gestioneFO = Boolean.valueOf(true);
    }

    @EmbeddedId
    @GenericGenerator(name = "pkGenerator", strategy = "it.gruppoinit.pal.gp.core.dao.helper.PkIdGenerator", parameters = {
	    @Parameter(name = PkIdGenerator.CONFIG_PREFER_SEGMENT_PER_ENTITY, value = "true"),
	    @Parameter(name = PkIdGenerator.TABLE_PARAM, value = "SEQUENCETABLE"),
	    @Parameter(name = PkIdGenerator.VALUE_COLUMN_PARAM, value = "CURRVAL"),
	    @Parameter(name = PkIdGenerator.SEGMENT_COLUMN_PARAM, value = "SEQUENCENAME"),
	    @Parameter(name = PkIdGenerator.SEGMENT_VALUE_PARAM, value = "BORSELLINO_CONFIGURAZIONE.ID") })
    @GeneratedValue(generator = "pkGenerator")
    @AttributeOverrides({ @AttributeOverride(name = "idcomune", column = @Column(name = "IDCOMUNE", nullable = false, length = 6)),
	    @AttributeOverride(name = "codice", column = @Column(name = "ID", nullable = false, precision = 10, scale = 0)) })
    public PkId getId() {

	return this.id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    @NotEmpty
    @Length(max = 50)
    @Column(name = "TIPO_INSTALLAZIONE", length = 50)
    public String getTipoInstallazione() {

	return tipoInstallazione;
    }

    public void setTipoInstallazione(String tipoInstallazione) {

	this.tipoInstallazione = tipoInstallazione;
    }

    @Length(max = 500)
    @Column(name = "MSG_NODOPAG_NONDISP", length = 500)
    public String getMsgNodoPagNonDisp() {

	return msgNodoPagNonDisp;
    }

    public void setMsgNodoPagNonDisp(String msgNodoPagNonDisp) {

	this.msgNodoPagNonDisp = msgNodoPagNonDisp;
    }

    @Column(name = "GESTIONE_FO", precision = 1, scale = 0)
    public Boolean getGestioneFO() {

	return gestioneFO;
    }

    public void setGestioneFO(Boolean gestioneFO) {

	this.gestioneFO = gestioneFO;
    }

    @Column(name = "ATTIVA_PAGAMENTI")
    public String getAttivaPagamenti() {
    
        return attivaPagamenti;
    }

    
    public void setAttivaPagamenti(String attivaPagamenti) {
    
        this.attivaPagamenti = attivaPagamenti;
    }

    @Column(name = "IMPORTO_MASSIMO")
    public BigDecimal getImportomassimo() {
    
        return importomassimo;
    }
    
    public void setImportomassimo(BigDecimal importomassimo) {
    
        this.importomassimo = importomassimo;
    }        
    
}
