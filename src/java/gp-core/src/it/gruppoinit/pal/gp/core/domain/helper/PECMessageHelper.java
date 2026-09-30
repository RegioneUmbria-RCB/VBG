package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class PECMessageHelper {

    public static final String EMAIL_ADDRESS_SEPARATOR = ";";
    private String identificativo;
    private Date dataRicezione;
    private List<String> mittenti = new ArrayList<String>();
    private String oggetto;
    private String numeroProtocollo;
    private PecStatusHelper status;
    private Boolean letto = Boolean.FALSE;
    private Boolean processato = Boolean.FALSE;
    private Integer fkRespInEvidenza;
    private String respInEvidenza;
    private String descrizioneAccountMailCfg;
    private Integer idAccountMailcfg;
    private List<String> destinatariCC = new ArrayList<String>();
    private List<String> destinatari = new ArrayList<String>();

    public PECMessageHelper() {

	this.status = new PecStatusHelper();
    }

    public String getIdentificativo() {

	return identificativo;
    }

    public void setIdentificativo(String identificativo) {

	this.identificativo = identificativo;
    }

    public Date getDataRicezione() {

	return dataRicezione;
    }

    public void setDataRicezione(Date dataRicezione) {

	this.dataRicezione = dataRicezione;
    }

    public List<String> getMittenti() {

	return mittenti;
    }

    public void setMittenti(List<String> mittenti) {

	this.mittenti = mittenti;
    }

    public String getMittentiString() {

	String retVal = this.mittenti != null ? StringUtils.join(this.mittenti.iterator(), EMAIL_ADDRESS_SEPARATOR) : "";
	return retVal;
    }

    public void setMittentiString(String fromString) {

	if (StringUtils.isNotEmpty(fromString)) {
	    String[] splitted = StringUtils.split(fromString, EMAIL_ADDRESS_SEPARATOR);
	    this.mittenti = Arrays.asList(splitted);
	} else {
	    this.mittenti = new ArrayList<String>();
	}
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }

    public PecStatusHelper getStatus() {

	return this.status;
    }

    public void setStatus(PecStatusHelper pecStatus) {

	this.status = pecStatus;
    }

    public Boolean getLetto() {

	return letto;
    }

    public void setLetto(Boolean letto) {

	this.letto = letto;
    }

    public Boolean getProcessato() {

	return processato;
    }

    public void setProcessato(Boolean processato) {

	this.processato = processato;
    }

    public String getNumeroProtocollo() {

	return numeroProtocollo;
    }

    public void setNumeroProtocollo(String numeroProtocollo) {

	this.numeroProtocollo = numeroProtocollo;
    }

    public Integer getFkRespInEvidenza() {

	return fkRespInEvidenza;
    }

    public void setFkRespInEvidenza(Integer fkRespInEvidenza) {

	this.fkRespInEvidenza = fkRespInEvidenza;
    }

    public String getRespInEvidenza() {

	return respInEvidenza;
    }

    public void setRespInEvidenza(String respInEvidenza) {

	this.respInEvidenza = respInEvidenza;
    }

    public List<String> getDestinatariCC() {

	return destinatariCC;
    }

    public void setDestinatariCC(List<String> destinatariCC) {

	this.destinatariCC = destinatariCC;
    }

    public String getDestinatariCCString() {

	String retVal = this.destinatariCC != null ? StringUtils.join(this.destinatariCC.iterator(), EMAIL_ADDRESS_SEPARATOR) : "";
	return retVal;
    }

    public void setDestinatariCCString(String destinatariCCString) {

	if (StringUtils.isNotEmpty(destinatariCCString)) {
	    String[] splitted = StringUtils.split(destinatariCCString, EMAIL_ADDRESS_SEPARATOR);
	    this.destinatariCC = Arrays.asList(splitted);
	} else {
	    this.destinatariCC = new ArrayList<String>();
	}
    }

    public List<String> getDestinatari() {

	return destinatari;
    }

    public void setDestinatari(List<String> destinatari) {

	this.destinatari = destinatari;
    }

    public String getDestinatariString() {

	String retVal = this.destinatari != null ? StringUtils.join(this.destinatari.iterator(), EMAIL_ADDRESS_SEPARATOR) : "";
	return retVal;
    }

    public void setDestinatariString(String destinatariString) {

	if (StringUtils.isNotEmpty(destinatariString)) {
	    String[] splitted = StringUtils.split(destinatariString, EMAIL_ADDRESS_SEPARATOR);
	    this.destinatari = Arrays.asList(splitted);
	} else {
	    this.destinatari = new ArrayList<String>();
	}
    }

    public String getDescrizioneAccountMailCfg() {

	return descrizioneAccountMailCfg;
    }

    public void setDescrizioneAccountMailCfg(String descrizioneAccountMailCfg) {

	this.descrizioneAccountMailCfg = descrizioneAccountMailCfg;
    }

    public Integer getIdAccountMailcfg() {

	return idAccountMailcfg;
    }

    public void setIdAccountMailcfg(Integer idAccountMailcfg) {

	this.idAccountMailcfg = idAccountMailcfg;
    }
}
