package it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi;

import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEvent;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DatiPagamento;

public class EventoPosizioneDebitoriaPagata implements IEvent {

    private DatiPagamento datiPagamento;
    private String cfEnteCreditore;
    boolean pagamentoOffline = false;

    public static EventoPosizioneDebitoriaPagata fromDatiPagamento(DatiPagamento datiPagamento, String cfEnteCreditore, boolean isPagamentoOffline) {

	EventoPosizioneDebitoriaPagata retVal = new EventoPosizioneDebitoriaPagata();
	retVal.datiPagamento = datiPagamento;
	retVal.cfEnteCreditore = cfEnteCreditore;
	retVal.pagamentoOffline = isPagamentoOffline;
	return retVal;
    }

    public DatiPagamento getDatiPagamento() {

	return datiPagamento;
    }

    public String getCfEnteCreditore() {

	return cfEnteCreditore;
    }

    public boolean isPagamentoOffline() {

	return pagamentoOffline;
    }

    @Override
    public String toString() {

	return "[datiPagamento = " + datiPagamento + //
	       ", cfEnteCreditore = " + cfEnteCreditore + //
	       ", pagamentoOffline = " + pagamentoOffline + "]";
    }
}
