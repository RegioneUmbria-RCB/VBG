package it.gruppoinit.pal.gp.core.domain.helper;

import java.sql.Date;
import java.sql.Timestamp;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class MovimentiallegatiDTO {

    private PkId id;
    private Integer codiceIstanza;
    private Integer codiceMovimento;
    private String descrizioneMovimento;
    private Timestamp dataMovimento;
    private String responsabileMovimento;
    private Date dataregistrazione;
    private String descrizione;
    private Integer codiceOggetto;
    private String nomeFile;
    private String note;
    private Boolean flagPubblica;
    private String amministrazione;
    private String tipomovimento;
    private String desctipomovimento;
    private String stcIddocumento;
    private String stcIdallegato;
    private Integer controllook;
    private boolean transientSegnaPerInvio;
    private String descrizioneEstesa;
    private String idBase;
    private Integer dimensioneFile;
    private String numeroprotocollo;
    private Date dataprotocollo;
    private String protocolloAndData;
    private String codicecomune;
    private String tipoDocumento;
    private String messageId;
    private boolean transientSegnaPerInvioPec;

    public PkId getId() {

	return id;
    }

    public void setId(PkId id) {

	this.id = id;
    }

    public Integer getCodiceIstanza() {

	return codiceIstanza;
    }

    public void setCodiceIstanza(Integer codiceIstanza) {

	this.codiceIstanza = codiceIstanza;
    }

    public Integer getCodiceMovimento() {

	return codiceMovimento;
    }

    public void setCodiceMovimento(Integer codiceMovimento) {

	this.codiceMovimento = codiceMovimento;
    }

    public String getDescrizioneMovimento() {

	return descrizioneMovimento;
    }

    public void setDescrizioneMovimento(String descrizioneMovimento) {

	this.descrizioneMovimento = descrizioneMovimento;
    }

    public Timestamp getDataMovimento() {

	return dataMovimento;
    }

    public void setDataMovimento(Timestamp dataMovimento) {

	this.dataMovimento = dataMovimento;
    }

    public String getResponsabileMovimento() {

	return responsabileMovimento;
    }

    public void setResponsabileMovimento(String responsabileMovimento) {

	this.responsabileMovimento = responsabileMovimento;
    }

    public Date getDataregistrazione() {

	return dataregistrazione;
    }

    public void setDataregistrazione(Date dataregistrazione) {

	this.dataregistrazione = dataregistrazione;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Integer getCodiceOggetto() {

	return codiceOggetto;
    }

    public void setCodiceOggetto(Integer codiceOggetto) {

	this.codiceOggetto = codiceOggetto;
    }

    public String getNomeFile() {

	return nomeFile;
    }

    public void setNomeFile(String nomeFile) {

	this.nomeFile = nomeFile;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Boolean getFlagPubblica() {

	return flagPubblica;
    }

    public void setFlagPubblica(Boolean flagPubblica) {

	this.flagPubblica = flagPubblica;
    }

    public boolean isTransientSegnaPerInvio() {

	return transientSegnaPerInvio;
    }

    public void setTransientSegnaPerInvio(boolean transientSegnaPerInvio) {

	this.transientSegnaPerInvio = transientSegnaPerInvio;
    }

    public String getAmministrazione() {

	return amministrazione;
    }

    public void setAmministrazione(String amministrazione) {

	this.amministrazione = amministrazione;
    }

    public String getTipomovimento() {

	return tipomovimento;
    }

    public void setTipomovimento(String tipomovimento) {

	this.tipomovimento = tipomovimento;
    }

    public String getDesctipomovimento() {

	return desctipomovimento;
    }

    public void setDesctipomovimento(String desctipomovimento) {

	this.desctipomovimento = desctipomovimento;
    }

    public String getStcIddocumento() {

	return stcIddocumento;
    }

    public void setStcIddocumento(String stcIddocumento) {

	this.stcIddocumento = stcIddocumento;
    }

    public String getStcIdallegato() {

	return stcIdallegato;
    }

    public void setStcIdallegato(String stcIdallegato) {

	this.stcIdallegato = stcIdallegato;
    }

    public String getIdBase() {

	return idBase;
    }

    public void setIdBase(String idBase) {

	this.idBase = idBase;
    }

    public Integer getControllook() {

	return controllook;
    }

    public void setControllook(Integer controllook) {

	this.controllook = controllook;
    }

    public String getDescrizioneEstesa() {

	this.descrizioneEstesa = "";
	if (StringUtils.isNotBlank(getDescrizioneMovimento())) {
	    this.descrizioneEstesa = getDesctipomovimento() + " (" + getTipomovimento() + ")";
	}
	return this.descrizioneEstesa;
    }

    public void setDescrizioneEstesa(String descrizioneEstesa) {

	this.descrizioneEstesa = descrizioneEstesa;
    }

    public Integer getDimensioneFile() {

	return dimensioneFile;
    }

    public void setDimensioneFile(Integer dimensioneFile) {

	this.dimensioneFile = dimensioneFile;
    }

    public String getNumeroprotocollo() {

	return numeroprotocollo;
    }

    public void setNumeroprotocollo(String numeroprotocollo) {

	this.numeroprotocollo = numeroprotocollo;
    }

    public Date getDataprotocollo() {

	return dataprotocollo;
    }

    public void setDataprotocollo(Date dataprotocollo) {

	this.dataprotocollo = dataprotocollo;
    }

    public String getProtocolloAndData() {

	this.protocolloAndData = "";
	if (StringUtils.isNotBlank(this.getNumeroprotocollo())) {
	    this.protocolloAndData += this.getNumeroprotocollo() + " - ";
	}
	if (this.getDataprotocollo() != null) {
	    this.protocolloAndData += Utilities.formatDate(this.getDataprotocollo(), false);
	}
	return protocolloAndData;
    }

    public void setProtocolloAndData(String protocolloAndData) {

	this.protocolloAndData = protocolloAndData;
    }

    public String getCodicecomune() {

	return codicecomune;
    }

    public void setCodicecomune(String codicecomune) {

	this.codicecomune = codicecomune;
    }

    public String getTipoDocumento() {

	return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {

	this.tipoDocumento = tipoDocumento;
    }

    public String getMessageId() {

	return messageId;
    }

    public void setMessageId(String messageId) {

	this.messageId = messageId;
    }

    public boolean isTransientSegnaPerInvioPec() {

	return transientSegnaPerInvioPec;
    }

    public void setTransientSegnaPerInvioPec(boolean transientSegnaPerInvioPec) {

	this.transientSegnaPerInvioPec = transientSegnaPerInvioPec;
    }
}
