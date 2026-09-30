package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement
public class GetDebtPositionPaymentDataResponse {

    @XmlElement
    private BigDecimal importoPagatoTotale;
    @XmlElement
    private String enteBeneficiario;
    @XmlElement
    private String codiceFiscaleEnteBeneficiario;
    @XmlElement
    private String tipologiaVersamento;
    @XmlElement
    private BigDecimal importoPagato;
    @XmlElement
    private String nomeECognomeRagioneSociale;
    @XmlElement
    private String codiceFiscalePIva;
    @XmlElement
    private String codiceAvviso;
    @XmlElement
    private String identificativoUnicoVersamento;
    @XmlElement
    private String numeroTransazione;
    @XmlElement
    private String prestatoreDiServiziDiPagamento;
    @XmlElement(name = "dataEOraOperazione")
    private Long dataEOraOperazione;
    @XmlElement(name = "dataEsitoPagamento")
    private Long dataEsitoPagamento;
    @XmlElement
    private GetDebtDatiMultiBeneficiario datiMultibeneficiario;
    @XmlElement
    private String identificativoUnicoRiscossione;
    @XmlElement
    private String infoAggiuntive;
    @XmlElement
    private RestAPIResult result;

    public BigDecimal getImportoPagatoTotale() {

	return importoPagatoTotale;
    }

    public void setImportoPagatoTotale(BigDecimal importoPagatoTotale) {

	this.importoPagatoTotale = importoPagatoTotale;
    }

    public String getEnteBeneficiario() {

	return enteBeneficiario;
    }

    public void setEnteBeneficiario(String enteBeneficiario) {

	this.enteBeneficiario = enteBeneficiario;
    }

    public String getCodiceFiscaleEnteBeneficiario() {

	return codiceFiscaleEnteBeneficiario;
    }

    public void setCodiceFiscaleEnteBeneficiario(String codiceFiscaleEnteBeneficiario) {

	this.codiceFiscaleEnteBeneficiario = codiceFiscaleEnteBeneficiario;
    }

    public String getTipologiaVersamento() {

	return tipologiaVersamento;
    }

    public void setTipologiaVersamento(String tipologiaVersamento) {

	this.tipologiaVersamento = tipologiaVersamento;
    }

    public BigDecimal getImportoPagato() {

	return importoPagato;
    }

    public void setImportoPagato(BigDecimal importoPagato) {

	this.importoPagato = importoPagato;
    }

    public String getNomeECognomeRagioneSociale() {

	return nomeECognomeRagioneSociale;
    }

    public void setNomeECognomeRagioneSociale(String nomeECognomeRagioneSociale) {

	this.nomeECognomeRagioneSociale = nomeECognomeRagioneSociale;
    }

    public String getCodiceFiscalePIva() {

	return codiceFiscalePIva;
    }

    public void setCodiceFiscalePIva(String codiceFiscalePIva) {

	this.codiceFiscalePIva = codiceFiscalePIva;
    }

    public String getCodiceAvviso() {

	return codiceAvviso;
    }

    public void setCodiceAvviso(String codiceAvviso) {

	this.codiceAvviso = codiceAvviso;
    }

    public String getIdentificativoUnicoVersamento() {

	return identificativoUnicoVersamento;
    }

    public void setIdentificativoUnicoVersamento(String identificativoUnicoVersamento) {

	this.identificativoUnicoVersamento = identificativoUnicoVersamento;
    }

    public String getNumeroTransazione() {

	return numeroTransazione;
    }

    public void setNumeroTransazione(String numeroTransazione) {

	this.numeroTransazione = numeroTransazione;
    }

    public String getPrestatoreDiServiziDiPagamento() {

	return prestatoreDiServiziDiPagamento;
    }

    public void setPrestatoreDiServiziDiPagamento(String prestatoreDiServiziDiPagamento) {

	this.prestatoreDiServiziDiPagamento = prestatoreDiServiziDiPagamento;
    }

    public Long getDataEOraOperazione() {

	return dataEOraOperazione;
    }

    public void setDataEOraOperazione(Long dataEOraOperazione) {

	this.dataEOraOperazione = dataEOraOperazione;
    }

    public Long getDataEsitoPagamento() {

	return dataEsitoPagamento;
    }

    public void setDataEsitoPagamento(Long dataEsitoPagamento) {

	this.dataEsitoPagamento = dataEsitoPagamento;
    }

    public GetDebtDatiMultiBeneficiario getDatiMultibeneficiario() {

	return datiMultibeneficiario;
    }

    public void setDatiMultibeneficiario(GetDebtDatiMultiBeneficiario datiMultibeneficiario) {

	this.datiMultibeneficiario = datiMultibeneficiario;
    }

    public String getIdentificativoUnicoRiscossione() {

	return identificativoUnicoRiscossione;
    }

    public void setIdentificativoUnicoRiscossione(String identificativoUnicoRiscossione) {

	this.identificativoUnicoRiscossione = identificativoUnicoRiscossione;
    }

    public String getInfoAggiuntive() {

	return infoAggiuntive;
    }

    public void setInfoAggiuntive(String infoAggiuntive) {

	this.infoAggiuntive = infoAggiuntive;
    }

    public RestAPIResult getResult() {

	return result;
    }

    public void setResult(RestAPIResult result) {

	this.result = result;
    }

    @XmlTransient
    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
