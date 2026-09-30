package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagamentiResponseType {

    @XmlElement(name = "data_sistema")
    private Date dataSistema;
    @XmlElement(name = "data_pagamento")
    private Date dataPagamento;
    @XmlElement(name = "importo_pagato")
    private BigDecimal importoPagato;
    @XmlElement(name = "iuv")
    private String iuv;
    @XmlElement(name = "id_posizione_psp")
    private String idPosizionePsp;
    @XmlElement(name = "rif_pagamento")
    private String rifPagamento;
    @XmlElement(name = "modalita_pagamento")
    private String modalitaPagamento;
    @XmlElement(name = "id_flusso_rendicontazione")
    private String idFlussoRendicontazione;
    @XmlElement(name = "data_ora_inizio_trans")
    private Date dataOraInizioTrans;
    @XmlElement(name = "data_ora_autorizzazione")
    private Date dataOraAutorizzazione;
    @XmlElement(name = "iur")
    private String iur;
    @XmlElement(name = "importo_transato")
    private BigDecimal importoTransato;
    @XmlElement(name = "importo_commissioni")
    private BigDecimal importoCommissioni;
    @XmlElement(name = "id_psp")
    private String idPsp;
    @XmlElement(name = "rag_soc_psp")
    private String ragSocPsp;

    public PagamentiResponseType() {

    }

    public Date getDataSistema() {

	return dataSistema;
    }

    public void setDataSistema(Date dataSistema) {

	this.dataSistema = dataSistema;
    }

    public Date getDataPagamento() {

	return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {

	this.dataPagamento = dataPagamento;
    }

    public BigDecimal getImportoPagato() {

	return importoPagato;
    }

    public void setImportoPagato(BigDecimal importoPagato) {

	this.importoPagato = importoPagato;
    }

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public String getIdPosizionePsp() {

	return idPosizionePsp;
    }

    public void setIdPosizionePsp(String idPosizionePsp) {

	this.idPosizionePsp = idPosizionePsp;
    }

    public String getRifPagamento() {

	return rifPagamento;
    }

    public void setRifPagamento(String rifPagamento) {

	this.rifPagamento = rifPagamento;
    }

    public String getModalitaPagamento() {

	return modalitaPagamento;
    }

    public void setModalitaPagamento(String modalitaPagamento) {

	this.modalitaPagamento = modalitaPagamento;
    }

    public String getIdFlussoRendicontazione() {

	return idFlussoRendicontazione;
    }

    public void setIdFlussoRendicontazione(String idFlussoRendicontazione) {

	this.idFlussoRendicontazione = idFlussoRendicontazione;
    }

    public Date getDataOraInizioTrans() {

	return dataOraInizioTrans;
    }

    public void setDataOraInizioTrans(Date dataOraInizioTrans) {

	this.dataOraInizioTrans = dataOraInizioTrans;
    }

    public Date getDataOraAutorizzazione() {

	return dataOraAutorizzazione;
    }

    public void setDataOraAutorizzazione(Date dataOraAutorizzazione) {

	this.dataOraAutorizzazione = dataOraAutorizzazione;
    }

    public String getIur() {

	return iur;
    }

    public void setIur(String iur) {

	this.iur = iur;
    }

    public BigDecimal getImportoTransato() {

	return importoTransato;
    }

    public void setImportoTransato(BigDecimal importoTransato) {

	this.importoTransato = importoTransato;
    }

    public BigDecimal getImportoCommissioni() {

	return importoCommissioni;
    }

    public void setImportoCommissioni(BigDecimal importoCommissioni) {

	this.importoCommissioni = importoCommissioni;
    }

    public String getIdPsp() {

	return idPsp;
    }

    public void setIdPsp(String idPsp) {

	this.idPsp = idPsp;
    }

    public String getRagSocPsp() {

	return ragSocPsp;
    }

    public void setRagSocPsp(String ragSocPsp) {

	this.ragSocPsp = ragSocPsp;
    }
}
