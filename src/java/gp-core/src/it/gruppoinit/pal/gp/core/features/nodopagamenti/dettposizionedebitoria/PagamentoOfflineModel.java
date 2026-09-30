package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;

public class PagamentoOfflineModel {

    private DatiPagamento datiPagamento;
    private List<Tipimodalitapagamento> modalitaPagamento;

    public DatiPagamento getDatiPagamento() {

	return datiPagamento;
    }

    public List<Tipimodalitapagamento> getModalitaPagamento() {

	return modalitaPagamento;
    }

    public void setDatiPagamento(DatiPagamento datiPagamento) {

	this.datiPagamento = datiPagamento;
    }

    public void setModalitaPagamento(List<Tipimodalitapagamento> modalitaPagamento) {

	this.modalitaPagamento = modalitaPagamento;
    }

    public PagamentoOfflineModel(DatiPagamento datiPagamento, List<Tipimodalitapagamento> modalitaPagamento) {

	super();
	this.datiPagamento = datiPagamento;
	this.modalitaPagamento = modalitaPagamento;
    }

    public PagamentoOfflineModel() {

    }
}
