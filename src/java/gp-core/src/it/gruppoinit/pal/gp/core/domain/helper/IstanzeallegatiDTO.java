package it.gruppoinit.pal.gp.core.domain.helper;

import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class IstanzeallegatiDTO {

    private PkId id;
    private Integer codiceIstanza;
    private String allegatoextra;
    private String note;
    private Boolean verificato;
    private Integer controllook;
    private Boolean presente;
    private String stcIddocumento;
    private String stcIdallegato;
    private Integer codiceOggetto;
    private String nomeFile;
    private String procedimento;
    private Boolean necessario;
    private Date dataEndo;
    private boolean transientSegnaPerInvio;
    private Integer dimensioneFile;
    private String codicecomune;
    private String tipoDocumento;
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

    public String getAllegatoextra() {

	return allegatoextra;
    }

    public void setAllegatoextra(String allegatoextra) {

	this.allegatoextra = allegatoextra;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }

    public Boolean getVerificato() {

	return verificato;
    }

    public void setVerificato(Boolean verificato) {

	this.verificato = verificato;
    }

    public Integer getControllook() {

	return controllook;
    }

    public void setControllook(Integer controllook) {

	this.controllook = controllook;
    }

    public Boolean getPresente() {

	return presente;
    }

    public void setPresente(Boolean presente) {

	this.presente = presente;
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

    public String getProcedimento() {

	return procedimento;
    }

    public void setProcedimento(String procedimento) {

	this.procedimento = procedimento;
    }

    public Boolean getNecessario() {

	return necessario;
    }

    public void setNecessario(Boolean necessario) {

	this.necessario = necessario;
    }

    public boolean isTransientSegnaPerInvio() {

	return transientSegnaPerInvio;
    }

    public void setTransientSegnaPerInvio(boolean transientSegnaPerInvio) {

	this.transientSegnaPerInvio = transientSegnaPerInvio;
    }

    public Date getDataEndo() {

	return dataEndo;
    }

    public void setDataEndo(Date dataEndo) {

	this.dataEndo = dataEndo;
    }

    public Integer getDimensioneFile() {

	return dimensioneFile;
    }

    public void setDimensioneFile(Integer dimensioneFile) {

	this.dimensioneFile = dimensioneFile;
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

    public boolean isTransientSegnaPerInvioPec() {

	return transientSegnaPerInvioPec;
    }

    public void setTransientSegnaPerInvioPec(boolean transientSegnaPerInvioPec) {

	this.transientSegnaPerInvioPec = transientSegnaPerInvioPec;
    }
}
