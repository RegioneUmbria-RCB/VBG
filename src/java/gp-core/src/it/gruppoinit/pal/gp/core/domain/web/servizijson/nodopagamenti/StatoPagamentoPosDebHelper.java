package it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.StatiPosizioniDebitorieHelper;

public class StatoPagamentoPosDebHelper {

    private Integer id;
    private String causale;
    private Double importo;
    private String iuv;
    private SoggettoDebitorePagamentoHelper debitore;
    private StatiPosizioniDebitorieHelper statoAttuale;
    private PagamentiPosizioniDebitorieHelper pagamenti;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public Double getImporto() {

	return importo;
    }

    public void setImporto(Double importo) {

	this.importo = importo;
    }

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    public SoggettoDebitorePagamentoHelper getDebitore() {

	return debitore;
    }

    public void setDebitore(SoggettoDebitorePagamentoHelper debitore) {

	this.debitore = debitore;
    }

    public PagamentiPosizioniDebitorieHelper getPagamenti() {

	return pagamenti;
    }

    public void setPagamenti(PagamentiPosizioniDebitorieHelper pagamenti) {

	this.pagamenti = pagamenti;
    }

    public StatiPosizioniDebitorieHelper getStatoAttuale() {

	return statoAttuale;
    }

    public void setStatoAttuale(StatiPosizioniDebitorieHelper statoAttuale) {

	this.statoAttuale = statoAttuale;
    }
}
