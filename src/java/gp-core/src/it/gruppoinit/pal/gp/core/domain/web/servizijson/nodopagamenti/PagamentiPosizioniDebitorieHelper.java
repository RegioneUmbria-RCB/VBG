package it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti;

import java.util.Date;

public class PagamentiPosizioniDebitorieHelper {

    private Integer id;
    private Date dataPagamento;
    private Double importoPagamento;
    private Double importoCommissioni;
    private Double importoTransato;
    private String modalitaPagamento;
    private String rifPagamento;
    private String note;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public Date getDataPagamento() {

	return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {

	this.dataPagamento = dataPagamento;
    }

    public Double getImportoPagamento() {

	return importoPagamento;
    }

    public void setImportoPagamento(Double importoPagamento) {

	this.importoPagamento = importoPagamento;
    }

    public Double getImportoCommissioni() {

	return importoCommissioni;
    }

    public void setImportoCommissioni(Double importoCommissioni) {

	this.importoCommissioni = importoCommissioni;
    }

    public Double getImportoTransato() {

	return importoTransato;
    }

    public void setImportoTransato(Double importoTransato) {

	this.importoTransato = importoTransato;
    }

    public String getModalitaPagamento() {

	return modalitaPagamento;
    }

    public void setModalitaPagamento(String modalitaPagamento) {

	this.modalitaPagamento = modalitaPagamento;
    }

    public String getRifPagamento() {

	return rifPagamento;
    }

    public void setRifPagamento(String rifPagamento) {

	this.rifPagamento = rifPagamento;
    }

    public String getNote() {

	return note;
    }

    public void setNote(String note) {

	this.note = note;
    }
}
