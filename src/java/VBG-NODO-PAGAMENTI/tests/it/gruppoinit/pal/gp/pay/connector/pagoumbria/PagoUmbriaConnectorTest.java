package it.gruppoinit.pal.gp.pay.connector.pagoumbria;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.pay.ws.schema.StatoPagamentoType;

public class PagoUmbriaConnectorTest {

    private PagoUmbriaPayConnector connector = new PagoUmbriaPayConnector();

    @Test
    public void verificaStatoAnnullatoACQUISITO() {

	verificaStatoAnnullatoInternal(StatoPagamentoType.ACQUISITO, true);
    }

    @Test
    public void verificaStatoAnnullatoATTIVATOINPSP() {

	verificaStatoAnnullatoInternal(StatoPagamentoType.ATTIVATO_IN_PSP, false);
    }

    @Test
    public void verificaStatoAnnullatoCONERRORE() {

	verificaStatoAnnullatoInternal(StatoPagamentoType.CON_ERRORE, true);
    }

    @Test
    public void verificaStatoAnnullatoNONACQUISITO() {

	verificaStatoAnnullatoInternal(StatoPagamentoType.NON_ACQUISITO, true);
    }

    @Test
    public void verificaStatoAnnullatoRENDICONTATODAIC() {

	verificaStatoAnnullatoInternal(StatoPagamentoType.RENDICONTATO_DA_IC, false);
    }

    @Test
    public void verificaStatoAnnullatoTRASMESSOAPSP() {

	verificaStatoAnnullatoInternal(StatoPagamentoType.TRASMESSO_A_PSP, false);
    }

    @Test
    public void verificaStatoAnnullatoANNULLAMENTORICHIESTO() {

	verificaStatoAnnullatoInternal(StatoPagamentoType.ANNULLAMENTO_RICHIESTO, true);
    }

    @Test
    public void verificaStatoAnnullatoANNULLATO() {

	verificaStatoAnnullatoInternal(StatoPagamentoType.ANNULLATO, true);
    }

    @Test
    public void verificaStatoAnnullatoPAGATOOFFLINEANNULLATO() {

	verificaStatoAnnullatoInternal(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO, true);
    }

    @Test
    public void verificaStatoAnnullatoPAGATOOFFLINEDAANNULLARE() {

	verificaStatoAnnullatoInternal(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE, true);
    }

    @Test
    public void verificaStatiAnnullatiNonCensiti() {

	StatoPagamentoType[] values = StatoPagamentoType.values();
	for (StatoPagamentoType s : values) {
	    if (!(s.equals(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE) || s.equals(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO)
		    || s.equals(StatoPagamentoType.ANNULLAMENTO_RICHIESTO) || s.equals(StatoPagamentoType.ANNULLATO)
		    || s.equals(StatoPagamentoType.CON_ERRORE) || s.equals(StatoPagamentoType.ACQUISITO)
		    || s.equals(StatoPagamentoType.NON_ACQUISITO))) {
		verificaStatoAnnullatoInternal(s, false);
	    }
	}
    }

    private void verificaStatoAnnullatoInternal(StatoPagamentoType param, boolean expected) {

	Assert.assertEquals("Lo stato " + param + " è uno stato di annullamento ==> ? " + expected, connector.isStatoAnnullato(param), expected);
    }

    @Test
    public void verificaStatoPagatoACQUISITO() {

	verificaStatoPagatoInternal(StatoPagamentoType.ACQUISITO, false);
    }

    @Test
    public void verificaStatoPagatoATTIVATOINPSP() {

	verificaStatoPagatoInternal(StatoPagamentoType.ATTIVATO_IN_PSP, false);
    }

    @Test
    public void verificaStatoPagatoCONERRORE() {

	verificaStatoPagatoInternal(StatoPagamentoType.CON_ERRORE, false);
    }

    @Test
    public void verificaStatoPagatoNONACQUISITO() {

	verificaStatoPagatoInternal(StatoPagamentoType.NON_ACQUISITO, false);
    }

    @Test
    public void verificaStatoPagatoRENDICONTATODAIC() {

	verificaStatoPagatoInternal(StatoPagamentoType.RENDICONTATO_DA_IC, true);
    }

    @Test
    public void verificaStatoPagatoTRASMESSOAPSP() {

	verificaStatoPagatoInternal(StatoPagamentoType.TRASMESSO_A_PSP, false);
    }

    @Test
    public void verificaStatoPagatoANNULLAMENTORICHIESTO() {

	verificaStatoPagatoInternal(StatoPagamentoType.ANNULLAMENTO_RICHIESTO, false);
    }

    @Test
    public void verificaStatoPagatoANNULLATO() {

	verificaStatoPagatoInternal(StatoPagamentoType.ANNULLATO, false);
    }

    @Test
    public void verificaStatoPagatoPAGATOOFFLINEANNULLATO() {

	verificaStatoPagatoInternal(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO, true);
    }

    @Test
    public void verificaStatoPagatoPAGATOOFFLINEDAANNULLARE() {

	verificaStatoPagatoInternal(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE, true);
    }

    @Test
    public void verificaStatiPagatiNonCensiti() {

	StatoPagamentoType[] values = StatoPagamentoType.values();
	for (StatoPagamentoType s : values) {
	    if (!(s.equals(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE) || s.equals(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO)
		    || s.equals(StatoPagamentoType.NOTIFICATO_DA_PSP) || s.equals(StatoPagamentoType.RENDICONTATO_DA_IC))) {
		verificaStatoPagatoInternal(s, false);
	    }
	}
    }

    private void verificaStatoPagatoInternal(StatoPagamentoType param, boolean expected) {

	Assert.assertEquals("Lo stato " + param + " è uno stato di pagamento ==> ? " + expected, connector.isStatoPagato(param), expected);
    }

    @Test
    public void verificaCondizioneUscitaPendenzeAnnullabili() {

	verificaCondizioneUscitaInternal(new StatoPagamentoType[] { StatoPagamentoType.CON_ERRORE, StatoPagamentoType.ACQUISITO,
		StatoPagamentoType.NON_ACQUISITO, StatoPagamentoType.ANNULLAMENTO_RICHIESTO, StatoPagamentoType.ANNULLATO,
		StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE, StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO }, false);
	// 
	verificaCondizioneUscitaInternal(new StatoPagamentoType[] { StatoPagamentoType.ATTIVATO_IN_PSP, StatoPagamentoType.TRASMESSO_A_PSP,
		StatoPagamentoType.RENDICONTATO_DA_IC, StatoPagamentoType.NOTIFICATO_DA_PSP }, true);
    }

    public void verificaCondizioneUscitaInternal(StatoPagamentoType[] stati, boolean expected) {

	for (StatoPagamentoType stato : stati) {
	    Assert.assertEquals("Lo stato " + stato + " è uno stato annullabile ==> ? " + expected, connector.verificaPendenzeNonAnnullabili(stato),
		    expected);
	}
    }
}
