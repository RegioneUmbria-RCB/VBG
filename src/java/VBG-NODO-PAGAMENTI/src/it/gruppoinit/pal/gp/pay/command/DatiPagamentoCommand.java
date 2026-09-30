package it.gruppoinit.pal.gp.pay.command;

import it.gruppoinit.pal.gp.pay.ws.schema.DatiPagamentoType;

public class DatiPagamentoCommand {

    private DatiPagamentoType datiPagamento;
    private Integer idPosizione;

    public DatiPagamentoType getDatiPagamento() {

	return datiPagamento;
    }

    public void setDatiPagamento(DatiPagamentoType datiPagamento) {

	this.datiPagamento = datiPagamento;
    }

    public Integer getIdPosizione() {

	return idPosizione;
    }

    public void setIdPosizione(Integer idPosizione) {

	this.idPosizione = idPosizione;
    }
}
