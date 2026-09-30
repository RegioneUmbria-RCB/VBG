package it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.modellazione;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServizioResponse", propOrder = { "id", "serviceCode", "descriptionCode", "lastUpdateDate", "deactivationDate", "serviceType",
	"descriptionServiceType", "serviceCategory", "siaCode", "anagrafica", "address", "cap", "locality", "province", "defaultTypeId",
	"flagStandardFlow", "flagViewDisposition", "outsideListAllowed", "typeAccounting", "descrptionTypeAccounting", "accountingCodeNode",
	"ibanCode", "flagTipoAggancio", "bankAccount", "tipologiaProvvisori", "codiceIbanAccredito", "codiceDivisa", "belfioreCode",
	"istatCodeProvince", "istatCodeComune", "flagAbilitazioneSpc", "flagAbilitazioneTsp", "applicationCode", "postalAccount", "stato",
	"codSottoServizioBT", "numProgressivo", "dataValiditaDa", "dataValiditaA", "auxDigit", "flagPortaleDebitore", "tipoProfilo", "flagAlert",
	"flagAlertSms", "flagAlertMail", "tipoGiorni", "numGiorni", "importoPred", "importoPredMod", "generaIuvSpontanei", "flagUploadDaPortale",
	"flagGenerationIuv", "routineIuv" })
public class ServizioResponse {

    @XmlElement(name = "id")
    protected Integer id;
    @XmlElement(name = "serviceCode")
    protected String serviceCode;
    @XmlElement(name = "descriptionCode")
    protected String descriptionCode;
    @XmlElement(name = "lastUpdateDate")
    protected String lastUpdateDate;
    @XmlElement(name = "deactivationDate")
    protected Date deactivationDate;
    @XmlElement(name = "serviceType")
    protected String serviceType;
    @XmlElement(name = "descriptionServiceType")
    protected String descriptionServiceType;
    @XmlElement(name = "serviceCategory")
    protected String serviceCategory;
    @XmlElement(name = "siaCode")
    protected String siaCode;
    @XmlElement(name = "anagrafica")
    protected String anagrafica;
    @XmlElement(name = "address")
    protected String address;
    @XmlElement(name = "cap")
    protected String cap;
    @XmlElement(name = "locality")
    protected String locality;
    @XmlElement(name = "province")
    protected String province;
    @XmlElement(name = "defaultTypeId")
    protected String defaultTypeId;
    @XmlElement(name = "flagStandardFlow")
    protected Boolean flagStandardFlow;
    @XmlElement(name = "flagViewDisposition")
    protected Boolean flagViewDisposition;
    @XmlElement(name = "outsideListAllowed")
    protected Boolean outsideListAllowed;
    @XmlElement(name = "typeAccounting")
    protected String typeAccounting;
    @XmlElement(name = "descrptionTypeAccounting")
    protected String descrptionTypeAccounting;
    @XmlElement(name = "accountingCodeNode")
    protected String accountingCodeNode;
    @XmlElement(name = "ibanCode")
    protected String ibanCode;
    @XmlElement(name = "flagTipoAggancio")
    protected String flagTipoAggancio;
    @XmlElement(name = "bankAccount")
    protected String bankAccount;
    @XmlElement(name = "tipologiaProvvisori")
    protected String tipologiaProvvisori;
    @XmlElement(name = "codiceIbanAccredito")
    protected String codiceIbanAccredito;
    @XmlElement(name = "codiceDivisa")
    protected String codiceDivisa;
    @XmlElement(name = "belfioreCode")
    protected String belfioreCode;
    @XmlElement(name = "istatCodeProvince")
    protected String istatCodeProvince;
    @XmlElement(name = "istatCodeComune")
    protected String istatCodeComune;
    @XmlElement(name = "flagAbilitazioneSpc")
    protected boolean flagAbilitazioneSpc;
    @XmlElement(name = "flagAbilitazioneTsp")
    protected boolean flagAbilitazioneTsp;
    @XmlElement(name = "applicationCode")
    protected String applicationCode;
    @XmlElement(name = "postalAccount")
    protected String postalAccount;
    @XmlElement(name = "stato")
    protected String stato;
    @XmlElement(name = "codSottoServizioBT")
    protected String codSottoServizioBT;
    @XmlElement(name = "numProgressivo")
    protected Integer numProgressivo;
    @XmlElement(name = "dataValiditaDa")
    protected long dataValiditaDa;
    @XmlElement(name = "dataValiditaA")
    protected long dataValiditaA;
    @XmlElement(name = "auxDigit")
    protected String auxDigit;
    @XmlElement(name = "flagPortaleDebitore")
    protected boolean flagPortaleDebitore;
    @XmlElement(name = "tipoProfilo")
    protected String tipoProfilo;
    @XmlElement(name = "flagAlert")
    protected boolean flagAlert;
    @XmlElement(name = "flagAlertSms")
    protected boolean flagAlertSms;
    @XmlElement(name = "flagAlertMail")
    protected boolean flagAlertMail;
    @XmlElement(name = "tipoGiorni")
    protected String tipoGiorni;
    @XmlElement(name = "numGiorni")
    protected Integer numGiorni;
    @XmlElement(name = "importoPred")
    protected BigDecimal importoPred;
    @XmlElement(name = "importoPredMod")
    protected boolean importoPredMod;
    @XmlElement(name = "generaIuvSpontanei")
    protected boolean generaIuvSpontanei;
    @XmlElement(name = "flagUploadDaPortale")
    protected boolean flagUploadDaPortale;
    @XmlElement(name = "flagGenerationIuv")
    protected String flagGenerationIuv;
    @XmlElement(name = "routineIuv")
    protected String routineIuv;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getServiceCode() {

	return serviceCode;
    }

    public void setServiceCode(String serviceCode) {

	this.serviceCode = serviceCode;
    }

    public String getDescriptionCode() {

	return descriptionCode;
    }

    public void setDescriptionCode(String descriptionCode) {

	this.descriptionCode = descriptionCode;
    }

    public String getLastUpdateDate() {

	return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {

	this.lastUpdateDate = lastUpdateDate;
    }

    public Date getDeactivationDate() {

	return deactivationDate;
    }

    public void setDeactivationDate(Date deactivationDate) {

	this.deactivationDate = deactivationDate;
    }

    public String getServiceType() {

	return serviceType;
    }

    public void setServiceType(String serviceType) {

	this.serviceType = serviceType;
    }

    public String getDescriptionServiceType() {

	return descriptionServiceType;
    }

    public void setDescriptionServiceType(String descriptionServiceType) {

	this.descriptionServiceType = descriptionServiceType;
    }

    public String getServiceCategory() {

	return serviceCategory;
    }

    public void setServiceCategory(String serviceCategory) {

	this.serviceCategory = serviceCategory;
    }

    public String getSiaCode() {

	return siaCode;
    }

    public void setSiaCode(String siaCode) {

	this.siaCode = siaCode;
    }

    public String getAnagrafica() {

	return anagrafica;
    }

    public void setAnagrafica(String anagrafica) {

	this.anagrafica = anagrafica;
    }

    public String getAddress() {

	return address;
    }

    public void setAddress(String address) {

	this.address = address;
    }

    public String getCap() {

	return cap;
    }

    public void setCap(String cap) {

	this.cap = cap;
    }

    public String getLocality() {

	return locality;
    }

    public void setLocality(String locality) {

	this.locality = locality;
    }

    public String getProvince() {

	return province;
    }

    public void setProvince(String province) {

	this.province = province;
    }

    public String getDefaultTypeId() {

	return defaultTypeId;
    }

    public void setDefaultTypeId(String defaultTypeId) {

	this.defaultTypeId = defaultTypeId;
    }

    public Boolean getFlagStandardFlow() {

	return flagStandardFlow;
    }

    public void setFlagStandardFlow(Boolean flagStandardFlow) {

	this.flagStandardFlow = flagStandardFlow;
    }

    public Boolean getFlagViewDisposition() {

	return flagViewDisposition;
    }

    public void setFlagViewDisposition(Boolean flagViewDisposition) {

	this.flagViewDisposition = flagViewDisposition;
    }

    public Boolean getOutsideListAllowed() {

	return outsideListAllowed;
    }

    public void setOutsideListAllowed(Boolean outsideListAllowed) {

	this.outsideListAllowed = outsideListAllowed;
    }

    public String getTypeAccounting() {

	return typeAccounting;
    }

    public void setTypeAccounting(String typeAccounting) {

	this.typeAccounting = typeAccounting;
    }

    public String getDescrptionTypeAccounting() {

	return descrptionTypeAccounting;
    }

    public void setDescrptionTypeAccounting(String descrptionTypeAccounting) {

	this.descrptionTypeAccounting = descrptionTypeAccounting;
    }

    public String getAccountingCodeNode() {

	return accountingCodeNode;
    }

    public void setAccountingCodeNode(String accountingCodeNode) {

	this.accountingCodeNode = accountingCodeNode;
    }

    public String getIbanCode() {

	return ibanCode;
    }

    public void setIbanCode(String ibanCode) {

	this.ibanCode = ibanCode;
    }

    public String getFlagTipoAggancio() {

	return flagTipoAggancio;
    }

    public void setFlagTipoAggancio(String flagTipoAggancio) {

	this.flagTipoAggancio = flagTipoAggancio;
    }

    public String getBankAccount() {

	return bankAccount;
    }

    public void setBankAccount(String bankAccount) {

	this.bankAccount = bankAccount;
    }

    public String getTipologiaProvvisori() {

	return tipologiaProvvisori;
    }

    public void setTipologiaProvvisori(String tipologiaProvvisori) {

	this.tipologiaProvvisori = tipologiaProvvisori;
    }

    public String getCodiceIbanAccredito() {

	return codiceIbanAccredito;
    }

    public void setCodiceIbanAccredito(String codiceIbanAccredito) {

	this.codiceIbanAccredito = codiceIbanAccredito;
    }

    public String getCodiceDivisa() {

	return codiceDivisa;
    }

    public void setCodiceDivisa(String codiceDivisa) {

	this.codiceDivisa = codiceDivisa;
    }

    public String getBelfioreCode() {

	return belfioreCode;
    }

    public void setBelfioreCode(String belfioreCode) {

	this.belfioreCode = belfioreCode;
    }

    public String getIstatCodeProvince() {

	return istatCodeProvince;
    }

    public void setIstatCodeProvince(String istatCodeProvince) {

	this.istatCodeProvince = istatCodeProvince;
    }

    public String getIstatCodeComune() {

	return istatCodeComune;
    }

    public void setIstatCodeComune(String istatCodeComune) {

	this.istatCodeComune = istatCodeComune;
    }

    public boolean isFlagAbilitazioneSpc() {

	return flagAbilitazioneSpc;
    }

    public void setFlagAbilitazioneSpc(boolean flagAbilitazioneSpc) {

	this.flagAbilitazioneSpc = flagAbilitazioneSpc;
    }

    public boolean isFlagAbilitazioneTsp() {

	return flagAbilitazioneTsp;
    }

    public void setFlagAbilitazioneTsp(boolean flagAbilitazioneTsp) {

	this.flagAbilitazioneTsp = flagAbilitazioneTsp;
    }

    public String getApplicationCode() {

	return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {

	this.applicationCode = applicationCode;
    }

    public String getPostalAccount() {

	return postalAccount;
    }

    public void setPostalAccount(String postalAccount) {

	this.postalAccount = postalAccount;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getCodSottoServizioBT() {

	return codSottoServizioBT;
    }

    public void setCodSottoServizioBT(String codSottoServizioBT) {

	this.codSottoServizioBT = codSottoServizioBT;
    }

    public Integer getNumProgressivo() {

	return numProgressivo;
    }

    public void setNumProgressivo(Integer numProgressivo) {

	this.numProgressivo = numProgressivo;
    }

    public long getDataValiditaDa() {

	return dataValiditaDa;
    }

    public void setDataValiditaDa(long dataValiditaDa) {

	this.dataValiditaDa = dataValiditaDa;
    }

    public long getDataValiditaA() {

	return dataValiditaA;
    }

    public void setDataValiditaA(long dataValiditaA) {

	this.dataValiditaA = dataValiditaA;
    }

    public String getAuxDigit() {

	return auxDigit;
    }

    public void setAuxDigit(String auxDigit) {

	this.auxDigit = auxDigit;
    }

    public boolean isFlagPortaleDebitore() {

	return flagPortaleDebitore;
    }

    public void setFlagPortaleDebitore(boolean flagPortaleDebitore) {

	this.flagPortaleDebitore = flagPortaleDebitore;
    }

    public String getTipoProfilo() {

	return tipoProfilo;
    }

    public void setTipoProfilo(String tipoProfilo) {

	this.tipoProfilo = tipoProfilo;
    }

    public boolean getFlagAlert() {

	return flagAlert;
    }

    public void setFlagAlert(boolean flagAlert) {

	this.flagAlert = flagAlert;
    }

    public boolean isFlagAlertSms() {

	return flagAlertSms;
    }

    public void setFlagAlertSms(boolean flagAlertSms) {

	this.flagAlertSms = flagAlertSms;
    }

    public boolean isFlagAlertMail() {

	return flagAlertMail;
    }

    public void setFlagAlertMail(boolean flagAlertMail) {

	this.flagAlertMail = flagAlertMail;
    }

    public String getTipoGiorni() {

	return tipoGiorni;
    }

    public void setTipoGiorni(String tipoGiorni) {

	this.tipoGiorni = tipoGiorni;
    }

    public Integer getNumGiorni() {

	return numGiorni;
    }

    public void setNumGiorni(Integer numGiorni) {

	this.numGiorni = numGiorni;
    }

    public BigDecimal getImportoPred() {

	return importoPred;
    }

    public void setImportoPred(BigDecimal importoPred) {

	this.importoPred = importoPred;
    }

    public boolean isImportoPredMod() {

	return importoPredMod;
    }

    public void setImportoPredMod(boolean importoPredMod) {

	this.importoPredMod = importoPredMod;
    }

    public boolean isGeneraIuvSpontanei() {

	return generaIuvSpontanei;
    }

    public void setGeneraIuvSpontanei(boolean generaIuvSpontanei) {

	this.generaIuvSpontanei = generaIuvSpontanei;
    }

    public boolean isFlagUploadDaPortale() {

	return flagUploadDaPortale;
    }

    public void setFlagUploadDaPortale(boolean flagUploadDaPortale) {

	this.flagUploadDaPortale = flagUploadDaPortale;
    }

    public String getFlagGenerationIuv() {

	return flagGenerationIuv;
    }

    public void setFlagGenerationIuv(String flagGenerationIuv) {

	this.flagGenerationIuv = flagGenerationIuv;
    }

    public String getRoutineIuv() {

	return routineIuv;
    }

    public void setRoutineIuv(String routineIuv) {

	this.routineIuv = routineIuv;
    }

    public Date getDataValiditaA2() {

	return new Date(this.dataValiditaA);
    }

    public Date getDataValiditaDa2() {

	return new Date(this.dataValiditaDa);
    }

    @XmlTransient
    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
