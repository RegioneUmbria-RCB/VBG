package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class InfoPagamentoTelematicoDto {

	@XmlElement
	private String codiceContestoPagamento;
	@XmlElement
    private String dataAccredito; //prendiamola come stringa
	@XmlElement
    private String dataPagamento; //prendiamola come stringa
	@XmlElement
    private DatiContribuente datiDebitore;
	@XmlElement
    private DatiContribuente datiVersante;
	@XmlElement
    private String esitoRichiestaPagamento;
	@XmlElement
    private String flussoRicevuta;
	@XmlElement
    private String identificativoDominio;
	@XmlElement
    private String identificativoUnivocoVersamento;
	@XmlElement
    private String identTransazione;
	@XmlElement
    private BigDecimal importoTotalePagato;
	@XmlElement
    private BigDecimal importoTotaleRichiesta;
	@XmlElement
    private List<InfoVersamentoSingoloDto> infoVersamentoSingolo;
	@XmlElement
    private String numeroAvviso;
	@XmlElement
    private String numeroVersamentiSingoli;
	@XmlElement
    private String statoTecnicoPagamento;
	@XmlElement
    private String testoOriginaleRicevuta;
	@XmlElement
    private String tipoVersamento;
	
	public String getCodiceContestoPagamento() {
		return codiceContestoPagamento;
	}
	public void setCodiceContestoPagamento(String codiceContestoPagamento) {
		this.codiceContestoPagamento = codiceContestoPagamento;
	}
	public String getDataAccredito() {
		return dataAccredito;
	}
	public void setDataAccredito(String dataAccredito) {
		this.dataAccredito = dataAccredito;
	}
	public String getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public DatiContribuente getDatiDebitore() {
		return datiDebitore;
	}
	public void setDatiDebitore(DatiContribuente datiDebitore) {
		this.datiDebitore = datiDebitore;
	}
	public DatiContribuente getDatiVersante() {
		return datiVersante;
	}
	public void setDatiVersante(DatiContribuente datiVersante) {
		this.datiVersante = datiVersante;
	}
	public String getEsitoRichiestaPagamento() {
		return esitoRichiestaPagamento;
	}
	public void setEsitoRichiestaPagamento(String esitoRichiestaPagamento) {
		this.esitoRichiestaPagamento = esitoRichiestaPagamento;
	}
	public String getFlussoRicevuta() {
		return flussoRicevuta;
	}
	public void setFlussoRicevuta(String flussoRicevuta) {
		this.flussoRicevuta = flussoRicevuta;
	}
	public String getIdentificativoDominio() {
		return identificativoDominio;
	}
	public void setIdentificativoDominio(String identificativoDominio) {
		this.identificativoDominio = identificativoDominio;
	}
	public String getIdentificativoUnivocoVersamento() {
		return identificativoUnivocoVersamento;
	}
	public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {
		this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
	}
	public String getIdentTransazione() {
		return identTransazione;
	}
	public void setIdentTransazione(String identTransazione) {
		this.identTransazione = identTransazione;
	}
	public BigDecimal getImportoTotalePagato() {
		return importoTotalePagato;
	}
	public void setImportoTotalePagato(BigDecimal importoTotalePagato) {
		this.importoTotalePagato = importoTotalePagato;
	}
	public BigDecimal getImportoTotaleRichiesta() {
		return importoTotaleRichiesta;
	}
	public void setImportoTotaleRichiesta(BigDecimal importoTotaleRichiesta) {
		this.importoTotaleRichiesta = importoTotaleRichiesta;
	}
	public List<InfoVersamentoSingoloDto> getInfoVersamentoSingolo() {
		return infoVersamentoSingolo;
	}
	public void setInfoVersamentoSingolo(List<InfoVersamentoSingoloDto> infoVersamentoSingolo) {
		this.infoVersamentoSingolo = infoVersamentoSingolo;
	}
	public String getNumeroAvviso() {
		return numeroAvviso;
	}
	public void setNumeroAvviso(String numeroAvviso) {
		this.numeroAvviso = numeroAvviso;
	}
	public String getNumeroVersamentiSingoli() {
		return numeroVersamentiSingoli;
	}
	public void setNumeroVersamentiSingoli(String numeroVersamentiSingoli) {
		this.numeroVersamentiSingoli = numeroVersamentiSingoli;
	}
	public String getStatoTecnicoPagamento() {
		return statoTecnicoPagamento;
	}
	public void setStatoTecnicoPagamento(String statoTecnicoPagamento) {
		this.statoTecnicoPagamento = statoTecnicoPagamento;
	}
	public String getTestoOriginaleRicevuta() {
		return testoOriginaleRicevuta;
	}
	public void setTestoOriginaleRicevuta(String testoOriginaleRicevuta) {
		this.testoOriginaleRicevuta = testoOriginaleRicevuta;
	}
	public String getTipoVersamento() {
		return tipoVersamento;
	}
	public void setTipoVersamento(String tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}  
	
}
