package it.gruppoinit.pal.gp.pay.service.helper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;
import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;

public class StatoPosizioneDebitoriaHelperTest {

    @Test
    public void possoModificareLaDataDiScadenza_torna_false_per_stati_non_modificabili() {

	StatoPosizioneDebitoriaHelper h = new StatoPosizioneDebitoriaHelper(getStatiDataScadenzaNonModificabili());
	Assert.assertFalse("Torna false per stati non modificabili", h.possoModificareLaDataDiScadenza());
    }

    @Test
    public void possoModificareLaDataDiScadenza_torna_true_per_stati_modificabili() {

	StatoPosizioneDebitoriaHelper h = new StatoPosizioneDebitoriaHelper(getStatiDataScadenzaModificabili());
	Assert.assertTrue("Torna true per stati modificabili", h.possoModificareLaDataDiScadenza());
    }

    private List<PayStatoPagamenti> getStatiDataScadenzaModificabili() {

	List<PayStatoPagamenti> ret = new ArrayList<>();
	PayStatoPagamenti s = new PayStatoPagamenti();
	s.setStato(StatoPagamentoType.ATTIVATO_IN_PSP.name());
	ret.add(s);
	s = new PayStatoPagamenti();
	s.setStato(StatoPagamentoType.ACQUISITO.name());
	ret.add(s);
	return ret;
    }

    private List<PayStatoPagamenti> getStatiDataScadenzaNonModificabili() {

	List<PayStatoPagamenti> ret = new ArrayList<>();
	ret.addAll(getStatiConErrore());
	ret.addAll(getStatiPagati());
	ret.addAll(getStatiAnnullati());
	return ret;
    }

    private List<PayStatoPagamenti> getStatiConErrore() {

	List<PayStatoPagamenti> ret = new ArrayList<>();
	PayStatoPagamenti s = new PayStatoPagamenti();
	s.setStato(StatoPagamentoType.CON_ERRORE.name());
	ret.add(s);
	return ret;
    }

    private List<PayStatoPagamenti> getStatiAnnullati() {

	List<PayStatoPagamenti> ret = new ArrayList<>();
	PayStatoPagamenti s = new PayStatoPagamenti();
	s.setStato(StatoPagamentoType.ANNULLAMENTO_RICHIESTO.name());
	ret.add(s);
	s = new PayStatoPagamenti();
	s.setStato(StatoPagamentoType.ANNULLATO.name());
	ret.add(s);
	return ret;
    }

    private List<PayStatoPagamenti> getStatiPagati() {

	List<PayStatoPagamenti> ret = new ArrayList<>();
	PayStatoPagamenti s = new PayStatoPagamenti();
	s.setStato(StatoPagamentoType.NOTIFICATO_DA_PSP.name());
	ret.add(s);
	s = new PayStatoPagamenti();
	s.setStato(StatoPagamentoType.RENDICONTATO_DA_IC.name());
	ret.add(s);
	s = new PayStatoPagamenti();
	s.setStato(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE.name());
	ret.add(s);
	s = new PayStatoPagamenti();
	s.setStato(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO.name());
	ret.add(s);
	return ret;
    }
}
