/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * @author francol
 * 
 */
public class PecStatusHelper {

    private Integer idOperatore;
    private String operatore;
    private String idProtocollo;
    private String numeroProtocollo;
    private Date dataProtocollo;
    private Integer idIstanza;
    private String codiceIstanza;
    private Integer idMovimento;
    private String codiceMovimento;
    private String software;
    private String errore;
    private Boolean cancellata = Boolean.FALSE;
    private Boolean allegatoProtocolloPresente = Boolean.FALSE;
    private Boolean allegatiPresenti = Boolean.FALSE;

    public Integer getIdOperatore() {

	return idOperatore;
    }

    public void setIdOperatore(Integer idOperatore) {

	this.idOperatore = idOperatore;
    }

    public String getOperatore() {

	return operatore;
    }

    public void setOperatore(String idOperatore) {

	this.operatore = idOperatore;
    }

    public String getIdProtocollo() {

	return idProtocollo;
    }

    public void setIdProtocollo(String idProtocollo) {

	this.idProtocollo = idProtocollo;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public Date getDataProtocollo() {

	return dataProtocollo;
    }

    public void setDataProtocollo(Date dataProtocollo) {

	this.dataProtocollo = dataProtocollo;
    }

    public Integer getIdIstanza() {

	return idIstanza;
    }

    public void setIdIstanza(Integer idIstanza) {

	this.idIstanza = idIstanza;
    }

    public Integer getIdMovimento() {

	return idMovimento;
    }

    public void setIdMovimento(Integer idMovimento) {

	this.idMovimento = idMovimento;
    }

    public boolean hasIstanza() {

	return this.idIstanza != null;
    }

    public boolean hasMovimento() {

	return this.idMovimento != null;
    }

    public boolean hasProtocollo() {

	boolean protocolloPec = StringUtils.isNotBlank(this.numeroProtocollo) && this.dataProtocollo != null;
	return protocolloPec;
    }

    public boolean isAssegnata() {

	return this.idOperatore != null;
    }

    public String getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(String codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public String getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(String codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public String getErrore() {

	return errore;
    }

    public void setErrore(String errore) {

	this.errore = errore;
    }

    public Boolean getCancellata() {

	return cancellata;
    }

    public void setCancellata(Boolean cancellata) {

	this.cancellata = cancellata;
    }

    public Boolean getAllegatoProtocolloPresente() {

	return allegatoProtocolloPresente;
    }

    public void setAllegatoProtocolloPresente(Boolean allegatoProtocolloPresente) {

	this.allegatoProtocolloPresente = allegatoProtocolloPresente;
    }

    public Boolean getAllegatiPresenti() {

	return allegatiPresenti;
    }

    public void setAllegatiPresenti(Boolean allegatiPresenti) {

	this.allegatiPresenti = allegatiPresenti;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public String getSoftware() {

	return software;
    }
}
