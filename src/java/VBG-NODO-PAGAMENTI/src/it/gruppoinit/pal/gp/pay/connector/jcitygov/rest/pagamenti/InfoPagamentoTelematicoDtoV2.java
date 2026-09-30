package it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.pagamenti;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import it.gruppoinit.pal.gp.pay.connector.jcitygov.rest.InfoVersamentoSingoloDto;

@XmlAccessorType(XmlAccessType.FIELD)
public class InfoPagamentoTelematicoDtoV2 {
    @XmlElement
    private String codiceContestoPagamento;
    @XmlElement
    private String dataRegolamento;
    @XmlElement
    private String dataValuta;
    @XmlElement
    private String dataPagamento;
    @XmlElement
    private DatiSoggettoDto datiDebitore;
    @XmlElement
    private DatiSoggettoDto datiVersante;
    @XmlElement
    private String esitoRichiestaPagamento;
    @XmlElement
    private String flussoRicevuta;
    @XmlElement
    private String identTransazione;
    @XmlElement
    private String identificativoDominio;
    @XmlElement
    private String identificativoUnivocoVersamento;
    @XmlElement
    private BigDecimal importoTotalePagato;
    @XmlElement
    private BigDecimal importoTotaleRichiesta;
    @XmlElement
    private List<InfoVersamentoSingoloDto> infoVersamentoSingolo;
    @XmlElement
    private String numeroAvviso;
    @XmlElement
    private Integer numeroVersamentiSingoli;
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
    
    
    public String getDataPagamento() {
    
        return dataPagamento;
    }
    
    public void setDataPagamento(String dataPagamento) {
    
        this.dataPagamento = dataPagamento;
    }
    
    public DatiSoggettoDto getDatiDebitore() {
    
        return datiDebitore;
    }
    
    public void setDatiDebitore(DatiSoggettoDto datiDebitore) {
    
        this.datiDebitore = datiDebitore;
    }
    
    public DatiSoggettoDto getDatiVersante() {
    
        return datiVersante;
    }
    
    public void setDatiVersante(DatiSoggettoDto datiVersante) {
    
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
    
    public String getIdentTransazione() {
    
        return identTransazione;
    }
    
    public void setIdentTransazione(String identTransazione) {
    
        this.identTransazione = identTransazione;
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
    
    public Integer getNumeroVersamentiSingoli() {
    
        return numeroVersamentiSingoli;
    }
    
    public void setNumeroVersamentiSingoli(Integer numeroVersamentiSingoli) {
    
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

    
    public String getDataRegolamento() {
    
        return dataRegolamento;
    }

    
    public void setDataRegolamento(String dataRegolamento) {
    
        this.dataRegolamento = dataRegolamento;
    }

    
    public String getDataValuta() {
    
        return dataValuta;
    }

    
    public void setDataValuta(String dataValuta) {
    
        this.dataValuta = dataValuta;
    }

}
