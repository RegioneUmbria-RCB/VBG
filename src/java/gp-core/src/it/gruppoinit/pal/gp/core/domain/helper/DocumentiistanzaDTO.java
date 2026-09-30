package it.gruppoinit.pal.gp.core.domain.helper;

import java.sql.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import it.gruppoinit.pal.gp.core.domain.PkId;

public class DocumentiistanzaDTO {

    private PkId id;
    private Integer codiceIstanza;
    private Date data;
    private String documento;
    private Integer codiceOggetto;
    private String nomeFile;
    private String note;
    private Boolean necessario;
    private Boolean presente;
    private String stcIddocumento;
    private String stcIdallegato;
    private Boolean flgDaModelloDinamico;
    private Integer controllook;
    private boolean transientSegnaPerInvio;
    private String idBase;
    private String idDocer;
    private Integer dimensioneFile;
    private String alberoprocDocumentiCat;
    private String codicecomune;
    private String tipoDocumento;
    private Boolean isAttivoAllaChiusura;
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

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public String getDocumento() {

	return documento;
    }

    public void setDocumento(String documento) {

	this.documento = documento;
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

    public Boolean getNecessario() {

	return necessario;
    }

    public void setNecessario(Boolean necessario) {

	this.necessario = necessario;
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

    public Boolean getFlgDaModelloDinamico() {

	return flgDaModelloDinamico;
    }

    public void setFlgDaModelloDinamico(Boolean flgDaModelloDinamico) {

	this.flgDaModelloDinamico = flgDaModelloDinamico;
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

    public boolean getTransientSegnaPerInvio() {

	return transientSegnaPerInvio;
    }

    public void setTransientSegnaPerInvio(boolean transientSegnaPerInvio) {

	this.transientSegnaPerInvio = transientSegnaPerInvio;
    }

    public Integer getDimensioneFile() {

	return dimensioneFile;
    }

    public void setDimensioneFile(Integer dimensioneFile) {

	this.dimensioneFile = dimensioneFile;
    }

    public String getIdDocer() {

	return idDocer;
    }

    public void setIdDocer(String idDocer) {

	this.idDocer = idDocer;
    }

    public String getAlberoprocDocumentiCat() {

	return alberoprocDocumentiCat;
    }

    public void setAlberoprocDocumentiCat(String alberoprocDocumentiCat) {

	this.alberoprocDocumentiCat = alberoprocDocumentiCat;
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

    public Boolean getIsAttivoAllaChiusura() {

	return isAttivoAllaChiusura;
    }

    public void setIsAttivoAllaChiusura(Boolean isAttivoAllaChiusura) {

	this.isAttivoAllaChiusura = isAttivoAllaChiusura;
    }

    @Override
    public String toString() {

	ToStringBuilder toStringBuilder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
	toStringBuilder.append("id", getId());
	toStringBuilder.append("documento", getDocumento());
	if (StringUtils.isNotBlank(getStcIdallegato())) {
	    toStringBuilder.append("stcidallegato", getStcIdallegato());
	}
	if (StringUtils.isNotBlank(getStcIddocumento())) {
	    toStringBuilder.append("stciddocumento", getStcIddocumento());
	}
	return toStringBuilder.toString();
    }

    public boolean isTransientSegnaPerInvioPec() {

	return transientSegnaPerInvioPec;
    }

    public void setTransientSegnaPerInvioPec(boolean transientSegnaPerInvioPec) {

	this.transientSegnaPerInvioPec = transientSegnaPerInvioPec;
    }
}
