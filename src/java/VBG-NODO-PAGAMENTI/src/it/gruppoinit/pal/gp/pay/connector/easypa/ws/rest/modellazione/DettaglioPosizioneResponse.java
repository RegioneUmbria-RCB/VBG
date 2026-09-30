package it.gruppoinit.pal.gp.pay.connector.easypa.ws.rest.modellazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DettaglioPosizioneResponse", propOrder = { "id", "codiceInterfaccia", "idTrasmissione", "tipoServizio", "numeroProgessivoRecord",
	"idUnivocoUdrIda", "tipoOperazione", "numeroLista", "numeroCliente", "tipoIdPagatore", "identificativoPagatore", "anagraficaPagatore",
	"indirizzoPagatore", "civicoPagatore", "capPagatore", "localitaPagatore", "codiceNazionePagatore", "provinciaPagatore", "emailPagatore",
	"tipoIdBeneficiario", "identificativoBeneficiario", "anagraficaBeneficiario", "codiceUnitaOperativaBeneficiario",
	"denominazioneUnitaOperativaBeneficiario", "indirizzoBeneficiario", "civicoBeneficiario", "capBeneficiario", "localitaBeneficiario",
	"provinciaBeneficiario", "codiceNazioneBeneficiario", "dataScadenzaPagamento", "importoPagamento", "tipoVersamento",
	"identificativoUnivocoVersamento", "tipoRiferimentoCreditore", "codiceRiferimentoCreditore", "tipoPresentazione",
	"codiceIdentificativoPresentazione", "codiceContestoPagamento", "ibanAddebito", "bicAddebito", "tipoFirmaRicevuta", "importoVersamento",
	"importoCommissioniPa", "ibanAccreditoBt", "bicAccreditoBt", "ibanAccreditoAppoggioPsp", "bicAccreditoAppoggioPsp", "credenzialiPagatore",
	"causaleVersamento", "tipoDatiSpecificiRiscossione", "datiSpecificiRiscossione", "importoTotaleRiscosso", "provenienza", "stato", "idDominio",
	"servizio", "scaduto", "causaleFormattataXMLRPT" })
@XmlRootElement(name = "Payload")
public class DettaglioPosizioneResponse {

    @XmlElement(name = "id")
    @XmlSchemaType(name = "integer")
    protected Integer id;
    @XmlElement(name = "codiceInterfaccia")
    protected String codiceInterfaccia;
    @XmlElement(name = "idTrasmissione")
    protected String idTrasmissione;
    @XmlElement(name = "tipoServizio")
    protected String tipoServizio;
    @XmlElement(name = "numeroProgessivoRecord")
    protected String numeroProgessivoRecord;
    @XmlElement(name = "idUnivocoUdrIda")
    @XmlSchemaType(name = "integer")
    protected Integer idUnivocoUdrIda;
    @XmlElement(name = "tipoOperazione")
    protected String tipoOperazione;
    @XmlElement(name = "numeroLista")
    protected String numeroLista;
    @XmlElement(name = "numeroCliente")
    protected String numeroCliente;
    @XmlElement(name = "tipoIdPagatore")
    protected String tipoIdPagatore;
    @XmlElement(name = "identificativoPagatore")
    protected String identificativoPagatore;
    @XmlElement(name = "anagraficaPagatore")
    protected String anagraficaPagatore;
    @XmlElement(name = "indirizzoPagatore")
    protected String indirizzoPagatore;
    @XmlElement(name = "civicoPagatore")
    protected String civicoPagatore;
    @XmlElement(name = "capPagatore")
    protected String capPagatore;
    @XmlElement(name = "localitaPagatore")
    protected String localitaPagatore;
    @XmlElement(name = "codiceNazionePagatore")
    protected String codiceNazionePagatore;
    @XmlElement(name = "provinciaPagatore")
    protected String provinciaPagatore;
    @XmlElement(name = "emailPagatore")
    protected String emailPagatore;
    @XmlElement(name = "tipoIdBeneficiario")
    protected String tipoIdBeneficiario;
    @XmlElement(name = "identificativoBeneficiario")
    protected String identificativoBeneficiario;
    @XmlElement(name = "anagraficaBeneficiario")
    protected String anagraficaBeneficiario;
    @XmlElement(name = "codiceUnitaOperativaBeneficiario")
    protected String codiceUnitaOperativaBeneficiario;
    @XmlElement(name = "denominazioneUnitaOperativaBeneficiario")
    protected String denominazioneUnitaOperativaBeneficiario;
    @XmlElement(name = "indirizzoBeneficiario")
    protected String indirizzoBeneficiario;
    @XmlElement(name = "civicoBeneficiario")
    protected String civicoBeneficiario;
    @XmlElement(name = "capBeneficiario")
    protected String capBeneficiario;
    @XmlElement(name = "localitaBeneficiario")
    protected String localitaBeneficiario;
    @XmlElement(name = "provinciaBeneficiario")
    protected String provinciaBeneficiario;
    @XmlElement(name = "codiceNazioneBeneficiario")
    protected String codiceNazioneBeneficiario;
    @XmlElement(name = "dataScadenzaPagamento")
    protected String dataScadenzaPagamento;
    @XmlElement(name = "importoPagamento")
    protected String importoPagamento;
    @XmlElement(name = "tipoVersamento")
    protected String tipoVersamento;
    @XmlElement(name = "identificativoUnivocoVersamento")
    protected String identificativoUnivocoVersamento;
    @XmlElement(name = "tipoRiferimentoCreditore")
    protected String tipoRiferimentoCreditore;
    @XmlElement(name = "codiceRiferimentoCreditore")
    protected String codiceRiferimentoCreditore;
    @XmlElement(name = "tipoPresentazione")
    protected String tipoPresentazione;
    @XmlElement(name = "codiceIdentificativoPresentazione")
    protected String codiceIdentificativoPresentazione;
    @XmlElement(name = "codiceContestoPagamento")
    protected String codiceContestoPagamento;
    @XmlElement(name = "ibanAddebito")
    protected String ibanAddebito;
    @XmlElement(name = "bicAddebito")
    protected String bicAddebito;
    @XmlElement(name = "tipoFirmaRicevuta")
    protected String tipoFirmaRicevuta;
    @XmlElement(name = "importoVersamento")
    protected String importoVersamento;
    @XmlElement(name = "importoCommissioniPa")
    protected String importoCommissioniPa;
    @XmlElement(name = "ibanAccreditoBt")
    protected String ibanAccreditoBt;
    @XmlElement(name = "bicAccreditoBt")
    protected String bicAccreditoBt;
    @XmlElement(name = "ibanAccreditoAppoggioPsp")
    protected String ibanAccreditoAppoggioPsp;
    @XmlElement(name = "bicAccreditoAppoggioPsp")
    protected String bicAccreditoAppoggioPsp;
    @XmlElement(name = "credenzialiPagatore")
    protected String credenzialiPagatore;
    @XmlElement(name = "causaleVersamento")
    protected String causaleVersamento;
    @XmlElement(name = "tipoDatiSpecificiRiscossione")
    protected String tipoDatiSpecificiRiscossione;
    @XmlElement(name = "datiSpecificiRiscossione")
    protected String datiSpecificiRiscossione;
    @XmlElement(name = "importoTotaleRiscosso")
    protected String importoTotaleRiscosso;
    @XmlElement(name = "provenienza")
    protected String provenienza;
    @XmlElement(name = "stato")
    protected String stato;
    @XmlElement(name = "idDominio")
    protected String idDominio;
    @XmlElement(name = "servizio")
    protected ServizioResponse servizio;
    @XmlElement(name = "scaduto")
    protected boolean scaduto;
    @XmlElement(name = "causaleFormattataXMLRPT")
    protected String causaleFormattataXMLRPT;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getCodiceInterfaccia() {

	return codiceInterfaccia;
    }

    public void setCodiceInterfaccia(String codiceInterfaccia) {

	this.codiceInterfaccia = codiceInterfaccia;
    }

    public String getIdTrasmissione() {

	return idTrasmissione;
    }

    public void setIdTrasmissione(String idTrasmissione) {

	this.idTrasmissione = idTrasmissione;
    }

    public String getTipoServizio() {

	return tipoServizio;
    }

    public void setTipoServizio(String tipoServizio) {

	this.tipoServizio = tipoServizio;
    }

    public String getNumeroProgessivoRecord() {

	return numeroProgessivoRecord;
    }

    public void setNumeroProgessivoRecord(String numeroProgessivoRecord) {

	this.numeroProgessivoRecord = numeroProgessivoRecord;
    }

    public Integer getIdUnivocoUdrIda() {

	return idUnivocoUdrIda;
    }

    public void setIdUnivocoUdrIda(Integer idUnivocoUdrIda) {

	this.idUnivocoUdrIda = idUnivocoUdrIda;
    }

    public String getTipoOperazione() {

	return tipoOperazione;
    }

    public void setTipoOperazione(String tipoOperazione) {

	this.tipoOperazione = tipoOperazione;
    }

    public String getNumeroLista() {

	return numeroLista;
    }

    public void setNumeroLista(String numeroLista) {

	this.numeroLista = numeroLista;
    }

    public String getNumeroCliente() {

	return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {

	this.numeroCliente = numeroCliente;
    }

    public String getTipoIdPagatore() {

	return tipoIdPagatore;
    }

    public void setTipoIdPagatore(String tipoIdPagatore) {

	this.tipoIdPagatore = tipoIdPagatore;
    }

    public String getIdentificativoPagatore() {

	return identificativoPagatore;
    }

    public void setIdentificativoPagatore(String identificativoPagatore) {

	this.identificativoPagatore = identificativoPagatore;
    }

    public String getAnagraficaPagatore() {

	return anagraficaPagatore;
    }

    public void setAnagraficaPagatore(String anagraficaPagatore) {

	this.anagraficaPagatore = anagraficaPagatore;
    }

    public String getIndirizzoPagatore() {

	return indirizzoPagatore;
    }

    public void setIndirizzoPagatore(String indirizzoPagatore) {

	this.indirizzoPagatore = indirizzoPagatore;
    }

    public String getCivicoPagatore() {

	return civicoPagatore;
    }

    public void setCivicoPagatore(String civicoPagatore) {

	this.civicoPagatore = civicoPagatore;
    }

    public String getCapPagatore() {

	return capPagatore;
    }

    public void setCapPagatore(String capPagatore) {

	this.capPagatore = capPagatore;
    }

    public String getLocalitaPagatore() {

	return localitaPagatore;
    }

    public void setLocalitaPagatore(String localitaPagatore) {

	this.localitaPagatore = localitaPagatore;
    }

    public String getCodiceNazionePagatore() {

	return codiceNazionePagatore;
    }

    public void setCodiceNazionePagatore(String codiceNazionePagatore) {

	this.codiceNazionePagatore = codiceNazionePagatore;
    }

    public String getProvinciaPagatore() {

	return provinciaPagatore;
    }

    public void setProvinciaPagatore(String provinciaPagatore) {

	this.provinciaPagatore = provinciaPagatore;
    }

    public String getEmailPagatore() {

	return emailPagatore;
    }

    public void setEmailPagatore(String emailPagatore) {

	this.emailPagatore = emailPagatore;
    }

    public String getTipoIdBeneficiario() {

	return tipoIdBeneficiario;
    }

    public void setTipoIdBeneficiario(String tipoIdBeneficiario) {

	this.tipoIdBeneficiario = tipoIdBeneficiario;
    }

    public String getIdentificativoBeneficiario() {

	return identificativoBeneficiario;
    }

    public void setIdentificativoBeneficiario(String identificativoBeneficiario) {

	this.identificativoBeneficiario = identificativoBeneficiario;
    }

    public String getAnagraficaBeneficiario() {

	return anagraficaBeneficiario;
    }

    public void setAnagraficaBeneficiario(String anagraficaBeneficiario) {

	this.anagraficaBeneficiario = anagraficaBeneficiario;
    }

    public String getCodiceUnitaOperativaBeneficiario() {

	return codiceUnitaOperativaBeneficiario;
    }

    public void setCodiceUnitaOperativaBeneficiario(String codiceUnitaOperativaBeneficiario) {

	this.codiceUnitaOperativaBeneficiario = codiceUnitaOperativaBeneficiario;
    }

    public String getDenominazioneUnitaOperativaBeneficiario() {

	return denominazioneUnitaOperativaBeneficiario;
    }

    public void setDenominazioneUnitaOperativaBeneficiario(String denominazioneUnitaOperativaBeneficiario) {

	this.denominazioneUnitaOperativaBeneficiario = denominazioneUnitaOperativaBeneficiario;
    }

    public String getIndirizzoBeneficiario() {

	return indirizzoBeneficiario;
    }

    public void setIndirizzoBeneficiario(String indirizzoBeneficiario) {

	this.indirizzoBeneficiario = indirizzoBeneficiario;
    }

    public String getCivicoBeneficiario() {

	return civicoBeneficiario;
    }

    public void setCivicoBeneficiario(String civicoBeneficiario) {

	this.civicoBeneficiario = civicoBeneficiario;
    }

    public String getCapBeneficiario() {

	return capBeneficiario;
    }

    public void setCapBeneficiario(String capBeneficiario) {

	this.capBeneficiario = capBeneficiario;
    }

    public String getLocalitaBeneficiario() {

	return localitaBeneficiario;
    }

    public void setLocalitaBeneficiario(String localitaBeneficiario) {

	this.localitaBeneficiario = localitaBeneficiario;
    }

    public String getProvinciaBeneficiario() {

	return provinciaBeneficiario;
    }

    public void setProvinciaBeneficiario(String provinciaBeneficiario) {

	this.provinciaBeneficiario = provinciaBeneficiario;
    }

    public String getCodiceNazioneBeneficiario() {

	return codiceNazioneBeneficiario;
    }

    public void setCodiceNazioneBeneficiario(String codiceNazioneBeneficiario) {

	this.codiceNazioneBeneficiario = codiceNazioneBeneficiario;
    }

    public String getDataScadenzaPagamento() {

	return dataScadenzaPagamento;
    }

    public void setDataScadenzaPagamento(String dataScadenzaPagamento) {

	this.dataScadenzaPagamento = dataScadenzaPagamento;
    }

    public String getImportoPagamento() {

	return importoPagamento;
    }

    public void setImportoPagamento(String importoPagamento) {

	this.importoPagamento = importoPagamento;
    }

    public String getTipoVersamento() {

	return tipoVersamento;
    }

    public void setTipoVersamento(String tipoVersamento) {

	this.tipoVersamento = tipoVersamento;
    }

    public String getIdentificativoUnivocoVersamento() {

	return identificativoUnivocoVersamento;
    }

    public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {

	this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }

    public String getTipoRiferimentoCreditore() {

	return tipoRiferimentoCreditore;
    }

    public void setTipoRiferimentoCreditore(String tipoRiferimentoCreditore) {

	this.tipoRiferimentoCreditore = tipoRiferimentoCreditore;
    }

    public String getCodiceRiferimentoCreditore() {

	return codiceRiferimentoCreditore;
    }

    public void setCodiceRiferimentoCreditore(String codiceRiferimentoCreditore) {

	this.codiceRiferimentoCreditore = codiceRiferimentoCreditore;
    }

    public String getTipoPresentazione() {

	return tipoPresentazione;
    }

    public void setTipoPresentazione(String tipoPresentazione) {

	this.tipoPresentazione = tipoPresentazione;
    }

    public String getCodiceIdentificativoPresentazione() {

	return codiceIdentificativoPresentazione;
    }

    public void setCodiceIdentificativoPresentazione(String codiceIdentificativoPresentazione) {

	this.codiceIdentificativoPresentazione = codiceIdentificativoPresentazione;
    }

    public String getCodiceContestoPagamento() {

	return codiceContestoPagamento;
    }

    public void setCodiceContestoPagamento(String codiceContestoPagamento) {

	this.codiceContestoPagamento = codiceContestoPagamento;
    }

    public String getIbanAddebito() {

	return ibanAddebito;
    }

    public void setIbanAddebito(String ibanAddebito) {

	this.ibanAddebito = ibanAddebito;
    }

    public String getBicAddebito() {

	return bicAddebito;
    }

    public void setBicAddebito(String bicAddebito) {

	this.bicAddebito = bicAddebito;
    }

    public String getTipoFirmaRicevuta() {

	return tipoFirmaRicevuta;
    }

    public void setTipoFirmaRicevuta(String tipoFirmaRicevuta) {

	this.tipoFirmaRicevuta = tipoFirmaRicevuta;
    }

    public String getImportoVersamento() {

	return importoVersamento;
    }

    public void setImportoVersamento(String importoVersamento) {

	this.importoVersamento = importoVersamento;
    }

    public String getImportoCommissioniPa() {

	return importoCommissioniPa;
    }

    public void setImportoCommissioniPa(String importoCommissioniPa) {

	this.importoCommissioniPa = importoCommissioniPa;
    }

    public String getIbanAccreditoBt() {

	return ibanAccreditoBt;
    }

    public void setIbanAccreditoBt(String ibanAccreditoBt) {

	this.ibanAccreditoBt = ibanAccreditoBt;
    }

    public String getBicAccreditoBt() {

	return bicAccreditoBt;
    }

    public void setBicAccreditoBt(String bicAccreditoBt) {

	this.bicAccreditoBt = bicAccreditoBt;
    }

    public String getIbanAccreditoAppoggioPsp() {

	return ibanAccreditoAppoggioPsp;
    }

    public void setIbanAccreditoAppoggioPsp(String ibanAccreditoAppoggioPsp) {

	this.ibanAccreditoAppoggioPsp = ibanAccreditoAppoggioPsp;
    }

    public String getBicAccreditoAppoggioPsp() {

	return bicAccreditoAppoggioPsp;
    }

    public void setBicAccreditoAppoggioPsp(String bicAccreditoAppoggioPsp) {

	this.bicAccreditoAppoggioPsp = bicAccreditoAppoggioPsp;
    }

    public String getCredenzialiPagatore() {

	return credenzialiPagatore;
    }

    public void setCredenzialiPagatore(String credenzialiPagatore) {

	this.credenzialiPagatore = credenzialiPagatore;
    }

    public String getCausaleVersamento() {

	return causaleVersamento;
    }

    public void setCausaleVersamento(String causaleVersamento) {

	this.causaleVersamento = causaleVersamento;
    }

    public String getTipoDatiSpecificiRiscossione() {

	return tipoDatiSpecificiRiscossione;
    }

    public void setTipoDatiSpecificiRiscossione(String tipoDatiSpecificiRiscossione) {

	this.tipoDatiSpecificiRiscossione = tipoDatiSpecificiRiscossione;
    }

    public String getDatiSpecificiRiscossione() {

	return datiSpecificiRiscossione;
    }

    public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {

	this.datiSpecificiRiscossione = datiSpecificiRiscossione;
    }

    public String getImportoTotaleRiscosso() {

	return importoTotaleRiscosso;
    }

    public void setImportoTotaleRiscosso(String importoTotaleRiscosso) {

	this.importoTotaleRiscosso = importoTotaleRiscosso;
    }

    public String getProvenienza() {

	return provenienza;
    }

    public void setProvenienza(String provenienza) {

	this.provenienza = provenienza;
    }

    public String getStato() {

	return stato;
    }

    public void setStato(String stato) {

	this.stato = stato;
    }

    public String getIdDominio() {

	return idDominio;
    }

    public void setIdDominio(String idDominio) {

	this.idDominio = idDominio;
    }

    public ServizioResponse getServizio() {

	return servizio;
    }

    public void setServizio(ServizioResponse servizio) {

	this.servizio = servizio;
    }

    public boolean isScaduto() {

	return scaduto;
    }

    public void setScaduto(boolean scaduto) {

	this.scaduto = scaduto;
    }

    public String getCausaleFormattataXMLRPT() {

	return causaleFormattataXMLRPT;
    }

    public void setCausaleFormattataXMLRPT(String causaleFormattataXMLRPT) {

	this.causaleFormattataXMLRPT = causaleFormattataXMLRPT;
    }

    @XmlTransient
    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
