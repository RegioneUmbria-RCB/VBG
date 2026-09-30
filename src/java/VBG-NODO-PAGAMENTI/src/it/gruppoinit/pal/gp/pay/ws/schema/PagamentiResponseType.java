package it.gruppoinit.pal.gp.pay.ws.schema;

import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.pay.domain.PayPagamenti;

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

    public PagamentiResponseType(PayPagamenti pagamento) {

	if (pagamento != null) {
	    this.dataSistema = pagamento.getDataSistema();
	    this.dataPagamento = pagamento.getDataPagamento();
	    this.importoPagato = pagamento.getImportoPagato();
	    this.iuv = pagamento.getIuv();
	    this.idPosizionePsp = pagamento.getIdPosizionePsp();
	    this.rifPagamento = pagamento.getRifPagamento();
	    this.modalitaPagamento = pagamento.getModalitaPagamento();
	    this.idFlussoRendicontazione = pagamento.getIdFlussoRendicontazione();
	    this.dataOraInizioTrans = pagamento.getDataOraInizioTrans();
	    this.dataOraAutorizzazione = pagamento.getDataOraAutorizzazione();
	    this.iur = pagamento.getIur();
	    this.importoTransato = pagamento.getImportoTransato();
	    this.importoCommissioni = pagamento.getImportoCommissioni();
	    this.idPsp = pagamento.getIdPsp();
	    this.ragSocPsp = pagamento.getRagSocPsp();
	}
    }
}
