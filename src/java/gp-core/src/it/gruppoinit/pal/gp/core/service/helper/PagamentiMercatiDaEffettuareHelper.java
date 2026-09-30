package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.util.Date;

public class PagamentiMercatiDaEffettuareHelper {

    private Integer codiceMercatoPresenza;
    private CodiceDescrizioneBean mercato;
    private CodiceDescrizioneBean giorno;
    private CodiceDescrizioneBean posteggio;
    private Date dataMercato;
    private Double importo;
    private String riferimentiPagamento;
    private String modalitaPagamento;

    public Integer getCodiceMercatoPresenza() {

	return codiceMercatoPresenza;
    }

    public void setCodiceMercatoPresenza(Integer codiceMercatoPresenza) {

	this.codiceMercatoPresenza = codiceMercatoPresenza;
    }

    public CodiceDescrizioneBean getGiorno() {

	return giorno;
    }

    public void setGiorno(CodiceDescrizioneBean giorno) {

	this.giorno = giorno;
    }

    public CodiceDescrizioneBean getMercato() {

	return mercato;
    }

    public void setMercato(CodiceDescrizioneBean mercato) {

	this.mercato = mercato;
    }

    public CodiceDescrizioneBean getPosteggio() {

	return posteggio;
    }

    public void setPosteggio(CodiceDescrizioneBean posteggio) {

	this.posteggio = posteggio;
    }

    public Date getDataMercato() {

	return dataMercato;
    }

    public void setDataMercato(Date dataMercato) {

	this.dataMercato = dataMercato;
    }

    public Double getImporto() {

	return importo;
    }

    public void setImporto(Double importo) {

	this.importo = importo;
    }

    public String getRiferimentiPagamento() {

	return riferimentiPagamento;
    }

    public void setRiferimentiPagamento(String riferimentiPagamento) {

	this.riferimentiPagamento = riferimentiPagamento;
    }

    public String getModalitaPagamento() {

	return modalitaPagamento;
    }

    public void setModalitaPagamento(String modalitaPagamento) {

	this.modalitaPagamento = modalitaPagamento;
    }
}
