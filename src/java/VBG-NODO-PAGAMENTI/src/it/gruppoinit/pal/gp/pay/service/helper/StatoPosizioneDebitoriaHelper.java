package it.gruppoinit.pal.gp.pay.service.helper;

import java.util.List;

import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti.StatiPagamento;

public class StatoPosizioneDebitoriaHelper {

    private List<PayStatoPagamenti> cronoStatus = null;

    public StatoPosizioneDebitoriaHelper(List<PayStatoPagamenti> cronoStatus) {

	super();
	this.cronoStatus = cronoStatus;
    }

    public boolean hasStatus(StatiPagamento status) {

	boolean has = false;
	for (PayStatoPagamenti stato : cronoStatus) {
	    if (stato.getStato().equals(status.name())) {
		has = true;
	    }
	}
	return has;
    }

    public boolean isAnnullato() {

	return hasStatus(StatiPagamento.ANNULLATO) || hasStatus(StatiPagamento.PAGATO_OFFLINE_ANNULLATO);
    }

    public boolean isPagato() {

	return hasStatus(StatiPagamento.NOTIFICATO_DA_PSP) || hasStatus(StatiPagamento.RENDICONTATO_DA_IC)
		|| hasStatus(StatiPagamento.PAGATO_OFFLINE_ANNULLATO) || hasStatus(StatiPagamento.PAGATO_OFFLINE_DA_ANNULLARE);
    }

    public PayStatoPagamenti getStatoCorrente() {

	return this.cronoStatus != null && this.cronoStatus.size() > 0 ? this.cronoStatus.get(0) : null;
    }

    public boolean isConErrore() {

	PayStatoPagamenti statoAttuale = getStatoCorrente();
	return (statoAttuale != null && statoAttuale.getStato().equalsIgnoreCase(StatiPagamento.CON_ERRORE.name()));
    }

    public List<PayStatoPagamenti> getCronologia() {

	return this.cronoStatus;
    }

    /**
     * StatoPagamentoType.ANNULLAMENTO_RICHIESTO <br />
     * StatoPagamentoType.RENDICONTATO_DA_IC <br />
     * StatoPagamentoType.NOTIFICATO_DA_PSP <br />
     * StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE <br />
     * StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO <br />
     * StatoPagamentoType.ANNULLATO <br />
     * StatoPagamentoType.CON_ERRORE
     * 
     * @return
     */
    public boolean possoModificareLaDataDiScadenza() {

	boolean ret = true;
	ret = !(isAnnullato() || isPagato() || isConErrore());
	return ret;
    }
    
    public boolean possoModificareLaDataFineValidita() {

    	boolean ret = true;
    	ret = !(isAnnullato() || isPagato() || isConErrore());
    	return ret;
    }
}
