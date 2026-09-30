package it.gruppoinit.pal.gp.pay.connector.easybridge.ws.messages.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;

public class DatiPagamentoInAttesa {

    @XmlElement(name = "dataScadenzaPagamento")
    private String dataScadenzaPagamento;
    @XmlElement(name = "VisualisationExpirationDate")
    private String visualisationExpirationDate;
    @XmlElement(name = "identificativoUnivocoVersamento", required = false)
    private String identificativoUnivocoVersamento;
    @XmlElement(name = "UniqueClientID", required = false)
    private String uniqueClientID;
    @XmlElement(name = "CallbackType")
    private String CallbackType;
    @XmlElement(name = "soggettoPagatore")
    private SoggettoPagatore soggettoPagatore;
    @XmlElement(name = "importoTotaleDaVersare")
    private BigDecimal importoTotaleDaVersare;
    @XmlElement(name = "importoSecondoBeneficiario")
    private String importoSecondoBeneficiario;
    @XmlElement(name = "causaleVersamentoModel3")
    private String causaleVersamentoModel3;
    @XmlElement(name = "descrizioneTestualeCausaleVersamento")
    private String descrizioneTestualeCausaleVersamento;
    @XmlElement(name = "identificativoServizio")
    private String identificativoServizio;
    @XmlElement(name = "parametroAggiuntivo", required = false)
    private String parametroAggiuntivo;
    @XmlElement(name = "parametroAggiuntivo2", required = false)
    private String parametroAggiuntivo2;
    @XmlElement(name = "parametroAggiuntivo3", required = false)
    private String parametroAggiuntivo3;
    @XmlElement(name = "TaxType")
    private String TaxType;
    @XmlElement(name = "InstalmentRata")
    private String InstalmentRata;
    @XmlElement(name = "Mobile")
    private String Mobile;
    @XmlElement(name = "datiMarcaBolloDigitale")
    private DatiMarcaBolloDigitale datiMarcaBolloDigitale;

    public String getDataScadenzaPagamento() {

	return dataScadenzaPagamento;
    }

    public void setDataScadenzaPagamento(String dataScadenzaPagamento) {

	this.dataScadenzaPagamento = dataScadenzaPagamento;
    }

    public String getVisualisationExpirationDate() {

	return visualisationExpirationDate;
    }

    public void setVisualisationExpirationDate(String visualisationExpirationDate) {

	this.visualisationExpirationDate = visualisationExpirationDate;
    }

    public String getIdentificativoUnivocoVersamento() {

	return identificativoUnivocoVersamento;
    }

    public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {

	this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }

    public String getUniqueClientID() {

	return uniqueClientID;
    }

    public void setUniqueClientID(String uniqueClientID) {

	this.uniqueClientID = uniqueClientID;
    }

    public String getCallbackType() {

	return CallbackType;
    }

    public void setCallbackType(String callbackType) {

	CallbackType = callbackType;
    }

    public SoggettoPagatore getSoggettoPagatore() {

	return soggettoPagatore;
    }

    public void setSoggettoPagatore(SoggettoPagatore soggettoPagatore) {

	this.soggettoPagatore = soggettoPagatore;
    }

    public BigDecimal getImportoTotaleDaVersare() {

	return importoTotaleDaVersare;
    }

    public void setImportoTotaleDaVersare(BigDecimal importoTotaleDaVersare) {

	this.importoTotaleDaVersare = importoTotaleDaVersare;
    }

    public String getImportoSecondoBeneficiario() {

	return importoSecondoBeneficiario;
    }

    public void setImportoSecondoBeneficiario(String importoSecondoBeneficiario) {

	this.importoSecondoBeneficiario = importoSecondoBeneficiario;
    }

    public String getCausaleVersamentoModel3() {

	return causaleVersamentoModel3;
    }

    public void setCausaleVersamentoModel3(String causaleVersamentoModel3) {

	this.causaleVersamentoModel3 = causaleVersamentoModel3;
    }

    public String getDescrizioneTestualeCausaleVersamento() {

	return descrizioneTestualeCausaleVersamento;
    }

    public void setDescrizioneTestualeCausaleVersamento(String descrizioneTestualeCausaleVersamento) {

	this.descrizioneTestualeCausaleVersamento = descrizioneTestualeCausaleVersamento;
    }

    public String getIdentificativoServizio() {

	return identificativoServizio;
    }

    public void setIdentificativoServizio(String identificativoServizio) {

	this.identificativoServizio = identificativoServizio;
    }

    public String getParametroAggiuntivo() {

	return parametroAggiuntivo;
    }

    public void setParametroAggiuntivo(String parametroAggiuntivo) {

	this.parametroAggiuntivo = parametroAggiuntivo;
    }

    public String getParametroAggiuntivo2() {

	return parametroAggiuntivo2;
    }

    public void setParametroAggiuntivo2(String parametroAggiuntivo2) {

	this.parametroAggiuntivo2 = parametroAggiuntivo2;
    }

    public String getParametroAggiuntivo3() {

	return parametroAggiuntivo3;
    }

    public void setParametroAggiuntivo3(String parametroAggiuntivo3) {

	this.parametroAggiuntivo3 = parametroAggiuntivo3;
    }

    public String getTaxType() {

	return TaxType;
    }

    public void setTaxType(String taxType) {

	TaxType = taxType;
    }

    public String getInstalmentRata() {

	return InstalmentRata;
    }

    public void setInstalmentRata(String instalmentRata) {

	InstalmentRata = instalmentRata;
    }

    public String getMobile() {

	return Mobile;
    }

    public void setMobile(String mobile) {

	Mobile = mobile;
    }

    public DatiMarcaBolloDigitale getDatiMarcaBolloDigitale() {

	return datiMarcaBolloDigitale;
    }

    public void setDatiMarcaBolloDigitale(DatiMarcaBolloDigitale datiMarcaBolloDigitale) {

	this.datiMarcaBolloDigitale = datiMarcaBolloDigitale;
    }
}
