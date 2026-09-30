package it.alveo.firmaremota.aruba.configurazione;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.alveo.firmaremota.aruba.CryptoUtils;

public class ConfigurazioneBean {

    @JsonProperty("url")
    private String url;
    @JsonProperty("richiediotp")
    private Boolean richiediOTP;
    @JsonProperty("typehsm")
    private String typeHSM;
    @JsonProperty("profilofirma")
    private String profiloFirma;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("relaxssl")
    private Boolean relaxSSL;
    @JsonProperty("typesendotp")
    private String typeSendOTP;
    @JsonProperty("detached")
    private Boolean detached;
    @JsonProperty("otp")
    private String otp;
    @JsonProperty("certid")
    private String certId;
    @JsonProperty("returnder")
    private Boolean returnDer;
    @JsonProperty("posrettfirmleftpades")
    private String posRettFirmLeftPades;
    @JsonProperty("posrettfirmrightpades")
    private String posRettFirmRightPades;
    @JsonProperty("testofirmapades")
    private String testoFirmaPades;
    @JsonProperty("profilofirmapades")
    private String profiloFirmaPades;
    @JsonProperty("numpaginafirmapades")
    private Integer numPaginaFirmaPades;
    @JsonProperty("emailnotifica")
    private String emailNotifica;
    @JsonProperty("firmacongiuntacades")
    private Boolean firmaCongiuntaCades;
    @JsonProperty("marcatemporalerichiesta")
    private Boolean marcaTemporaleRichiesta;
    @JsonProperty("motivofirmapades")
    private String motivoFirmaPades;
    @JsonProperty("tipofirma")
    private String tipoFirma;
    @JsonIgnore
    private String sessionId;

    public String getUrl() {

	return url;
    }

    public void setUrl(String url) {

	this.url = url;
    }

    public Boolean getRichiediOTP() {

	return richiediOTP;
    }

    public void setRichiediOTP(Boolean richiediOTP) {

	this.richiediOTP = richiediOTP;
    }

    public String getTypeHSM() {

	return typeHSM;
    }

    public void setTypeHSM(String typeHSM) {

	this.typeHSM = typeHSM;
    }

    public String getProfiloFirma() {

	return profiloFirma;
    }

    public void setProfiloFirma(String profiloFirma) {

	this.profiloFirma = profiloFirma;
    }

    public String getUsername() {

	return username;
    }

    public void setUsername(String username) {

	this.username = username;
    }

    public String getPassword() {

	return password;
    }

    public void setPassword(String password) {

	this.password = password;
    }

    public Boolean getRelaxSSL() {

	return relaxSSL;
    }

    public void setRelaxSSL(Boolean relaxSSL) {

	this.relaxSSL = relaxSSL;
    }

    public String getTypeSendOTP() {

	return typeSendOTP;
    }

    public void setTypeSendOTP(String typeSendOTP) {

	this.typeSendOTP = typeSendOTP;
    }

    public Boolean getDetached() {

	return detached;
    }

    public void setDetached(Boolean detached) {

	this.detached = detached;
    }

    public String getOtp() {

	return otp;
    }

    public void setOtp(String otp) {

	this.otp = otp;
    }

    public String getCertId() {

	return certId;
    }

    public void setCertId(String certId) {

	this.certId = certId;
    }

    public Boolean getReturnDer() {

	return returnDer;
    }

    public void setReturnDer(Boolean returnDer) {

	this.returnDer = returnDer;
    }

    public String getPosRettFirmLeftPades() {

	return posRettFirmLeftPades;
    }

    public void setPosRettFirmLeftPades(String posRettFirmLeftPades) {

	this.posRettFirmLeftPades = posRettFirmLeftPades;
    }

    public String getPosRettFirmRightPades() {

	return posRettFirmRightPades;
    }

    public void setPosRettFirmRightPades(String posRettFirmRightPades) {

	this.posRettFirmRightPades = posRettFirmRightPades;
    }

    public String getTestoFirmaPades() {

	return testoFirmaPades;
    }

    public void setTestoFirmaPades(String testoFirmaPades) {

	this.testoFirmaPades = testoFirmaPades;
    }

    public String getProfiloFirmaPades() {

	return profiloFirmaPades;
    }

    public void setProfiloFirmaPades(String profiloFirmaPades) {

	this.profiloFirmaPades = profiloFirmaPades;
    }

    public Integer getNumPaginaFirmaPades() {

	return numPaginaFirmaPades;
    }

    public void setNumPaginaFirmaPades(Integer numPaginaFirmaPades) {

	this.numPaginaFirmaPades = numPaginaFirmaPades;
    }

    public String getEmailNotifica() {

	return emailNotifica;
    }

    public void setEmailNotifica(String emailNotifica) {

	this.emailNotifica = emailNotifica;
    }

    public Boolean getFirmaCongiuntaCades() {

	return firmaCongiuntaCades;
    }

    public void setFirmaCongiuntaCades(Boolean firmaCongiuntaCades) {

	this.firmaCongiuntaCades = firmaCongiuntaCades;
    }

    public Boolean getMarcaTemporaleRichiesta() {

	return marcaTemporaleRichiesta;
    }

    public void setMarcaTemporaleRichiesta(Boolean marcaTemporaleRichiesta) {

	this.marcaTemporaleRichiesta = marcaTemporaleRichiesta;
    }

    public String getMotivoFirmaPades() {

	return motivoFirmaPades;
    }

    public void setMotivoFirmaPades(String motivoFirmaPades) {

	this.motivoFirmaPades = motivoFirmaPades;
    }

    public String getTipoFirma() {

	return tipoFirma;
    }

    public void setTipoFirma(String tipoFirma) {

	this.tipoFirma = tipoFirma;
    }

    public void setSessionId(String sessionId) {

	this.sessionId = sessionId;
    }

    @JsonIgnore
    public String getDecryptedPassword() {

	if (StringUtils.isBlank(this.password) || StringUtils.isBlank(this.sessionId)) {
	    return null;
	}
	return CryptoUtils.decrypt(this.sessionId, this.password);
    }
}
