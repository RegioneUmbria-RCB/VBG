package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class IstanzeprocureDTO {

    private PkId id;
    private Integer codiceIstanza;
    private AnagrafeDTO anagrafeRappresentato;
    private AnagrafeDTO anagrafeProcuratore;
    private String stcIddocumento;
    private String stcIdallegato;
    private Integer codiceOggetto;
    private String nomeFile;
    private Date dataDocumento;
    private Integer dimensioneFile;
    // Campo transiet utilizzato per segnare le procure da inviare nella
    // funzionalità : invia email
    private boolean transientSegnaPerInvio;
    private String tipoDocumento;
    private Integer controllook;
    private String note;
    private Boolean necessario;
    private Boolean presente;
    private Integer codiceOggettoDocId;
    private String nomeFileDocId;
    private Integer dimensioneFiledocId;
    private String stcIddocumentoDocId;
    private String stcIdallegatoDocId;
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

    public AnagrafeDTO getAnagrafeRappresentato() {

	return anagrafeRappresentato;
    }

    public void setAnagrafeRappresentato(AnagrafeDTO anagrafeRappresentato) {

	this.anagrafeRappresentato = anagrafeRappresentato;
    }

    public AnagrafeDTO getAnagrafeProcuratore() {

	return anagrafeProcuratore;
    }

    public void setAnagrafeProcuratore(AnagrafeDTO anagrafeProcuratore) {

	this.anagrafeProcuratore = anagrafeProcuratore;
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

    public Date getDataDocumento() {

	return dataDocumento;
    }

    public void setDataDocumento(Date dataDocumento) {

	this.dataDocumento = dataDocumento;
    }

    public Integer getDimensioneFile() {

	return dimensioneFile;
    }

    public void setDimensioneFile(Integer dimensioneFile) {

	this.dimensioneFile = dimensioneFile;
    }

    public boolean getTransientSegnaPerInvio() {

	return transientSegnaPerInvio;
    }

    public void setTransientSegnaPerInvio(boolean transientSegnaPerInvio) {

	this.transientSegnaPerInvio = transientSegnaPerInvio;
    }

    public String getTipoDocumento() {

	return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {

	this.tipoDocumento = tipoDocumento;
    }

    public Integer getControllook() {

	return this.controllook;
    }

    public void setControllook(Integer controllook) {

	this.controllook = controllook;
    }

    public String getNote() {

	return this.note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Boolean getNecessario() {

	return this.necessario;
    }

    public void setNecessario(Boolean necessario) {

	this.necessario = necessario;
    }

    public Boolean getPresente() {

	return this.presente;
    }

    public void setPresente(Boolean presente) {

	this.presente = presente;
    }

    public Integer getCodiceOggettoDocId() {

	return codiceOggettoDocId;
    }

    public void setCodiceOggettoDocId(Integer codiceOggettoDocId) {

	this.codiceOggettoDocId = codiceOggettoDocId;
    }

    public String getNomeFileDocId() {

	return nomeFileDocId;
    }

    public void setNomeFileDocId(String nomeFileDocId) {

	this.nomeFileDocId = nomeFileDocId;
    }

    public Integer getDimensioneFiledocId() {

	return dimensioneFiledocId;
    }

    public void setDimensioneFiledocId(Integer dimensioneFiledocId) {

	this.dimensioneFiledocId = dimensioneFiledocId;
    }

    public String getStcIddocumentoDocId() {

	return stcIddocumentoDocId;
    }

    public void setStcIddocumentoDocId(String stcIddocumentoDocId) {

	this.stcIddocumentoDocId = stcIddocumentoDocId;
    }

    public String getStcIdallegatoDocId() {

	return stcIdallegatoDocId;
    }

    public void setStcIdallegatoDocId(String stcIdallegatoDocId) {

	this.stcIdallegatoDocId = stcIdallegatoDocId;
    }

    public boolean isTransientSegnaPerInvioPec() {

	return transientSegnaPerInvioPec;
    }

    public void setTransientSegnaPerInvioPec(boolean transientSegnaPerInvioPec) {

	this.transientSegnaPerInvioPec = transientSegnaPerInvioPec;
    }
}
