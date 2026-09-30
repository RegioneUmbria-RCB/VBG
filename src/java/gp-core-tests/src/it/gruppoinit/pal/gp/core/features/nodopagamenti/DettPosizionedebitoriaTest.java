package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import org.junit.Assert;
import org.junit.Test;

import com.paevolution.ws.pagamenti_types.StatoPagamentoType;

import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;

public class DettPosizionedebitoriaTest {

    @Test
    public void mostraComePagatoSuAppVigiliTornaFalseSeNonPagatoOAnnullato() {

	StatiPosizioniDebitorieConverter con = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] statiPosizioniPagabili = con.getStatiPosizioniPagabili();
	for (StatoPagamentoType statoPagamentoType : statiPosizioniPagabili) {
	    DettPosizioneDebitoria d = new DettPosizioneDebitoria();
	    d.setStato(statoPagamentoType.value());
	    d.setDescStato(statoPagamentoType.value());
	    Assert.assertFalse(d.mostraComePagatoSuAppVigili());
	}
    }
}
