package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import org.junit.Assert;
import org.junit.Test;

import com.paevolution.ws.pagamenti_types.StatoPagamentoType;

import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

public class StatiPosizioniDebitorieConverterTests {

    @Test
    public void convertStato_con_stato_sconosciuto_restituisceNonDefinito() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato("Non conosco questo stato");
	Assert.assertTrue(result.getId().equals(-1));
    }

    @Test
    public void convertStato_con_stato_nullo_restituisceNonDefinito() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(null);
	Assert.assertTrue(result.getId().equals(-1));
    }

    @Test
    public void convertStato_con_stato_ACQUISITO_restituisceInCorso() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(StatoPagamentoType.ACQUISITO.name());
	Assert.assertTrue(result.getId().equals(0));
	Assert.assertTrue(result.getDescrizione().equalsIgnoreCase(StatiPosizioniDebitorieConverter.IN_CORSO));
    }

    @Test
    public void convertStato_con_stato_ANNULLAMENTO_RICHIESTO_restituisceInCorso() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(StatoPagamentoType.ANNULLAMENTO_RICHIESTO.name());
	Assert.assertTrue(result.getId().equals(0));
	Assert.assertTrue(result.getDescrizione().equalsIgnoreCase(StatiPosizioniDebitorieConverter.IN_CORSO));
    }

    @Test
    public void convertStato_con_stato_ATTIVATO_IN_PSP_restituisceInCorso() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(StatoPagamentoType.ATTIVATO_IN_PSP.name());
	Assert.assertTrue(result.getId().equals(0));
	Assert.assertTrue(result.getDescrizione().equalsIgnoreCase(StatiPosizioniDebitorieConverter.IN_CORSO));
    }

    @Test
    public void convertStato_con_stato_NOTIFICATO_DA_PSP_restituisceConclusaPOsitivamente() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(StatoPagamentoType.NOTIFICATO_DA_PSP.name());
	Assert.assertTrue(result.getId().equals(200));
	Assert.assertTrue(result.getDescrizione().equalsIgnoreCase(StatiPosizioniDebitorieConverter.CONCLUSA_POSITIVAMENTE));
    }

    @Test
    public void convertStato_con_stato_PAGATO_OFFLINE_DA_ANNULLARE_restituisceInCorso() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE.name());
	Assert.assertTrue(result.getId().equals(0));
	Assert.assertTrue(result.getDescrizione().equalsIgnoreCase(StatiPosizioniDebitorieConverter.IN_CORSO));
    }

    @Test
    public void convertStato_con_stato_TRASMESSO_A_PSP_restituisceInCorso() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(StatoPagamentoType.TRASMESSO_A_PSP.name());
	Assert.assertTrue(result.getId().equals(0));
	Assert.assertTrue(result.getDescrizione().equalsIgnoreCase(StatiPosizioniDebitorieConverter.IN_CORSO));
    }

    @Test
    public void convertStato_con_stato_RENDICONTATO_DA_IC_restituisceConclusaPositivamente() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(StatoPagamentoType.RENDICONTATO_DA_IC.name());
	Assert.assertTrue(result.getId().equals(200));
	Assert.assertTrue(result.getDescrizione().equalsIgnoreCase(StatiPosizioniDebitorieConverter.CONCLUSA_POSITIVAMENTE));
    }

    @Test
    public void convertStato_con_stato_PAGATO_OFFLINE_ANNULLATO_restituisceConclusaPositivamente() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO.name());
	Assert.assertTrue(result.getId().equals(200));
	Assert.assertTrue(result.getDescrizione().equalsIgnoreCase(StatiPosizioniDebitorieConverter.CONCLUSA_POSITIVAMENTE));
    }

    @Test
    public void convertStato_con_stato_NON_ACQUISITO_restituisceConErrore() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(StatoPagamentoType.NON_ACQUISITO.name());
	Assert.assertTrue(result.getId().equals(500));
	Assert.assertTrue(result.getDescrizione().equalsIgnoreCase(StatiPosizioniDebitorieConverter.CONCLUSA_NEGATIVAMENTE));
    }

    @Test
    public void convertStato_con_stato_CON_ERRORE_restituisceConErrore() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(StatoPagamentoType.NON_ACQUISITO.name());
	Assert.assertTrue(result.getId().equals(500));
	Assert.assertTrue(result.getDescrizione().equalsIgnoreCase(StatiPosizioniDebitorieConverter.CONCLUSA_NEGATIVAMENTE));
    }

    @Test
    public void convertStato_con_stato_ANNULLATO_restituisceConErrore() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	IdentificativoDescrizioneBean result = s.convertStato(StatoPagamentoType.ANNULLATO.name());
	Assert.assertTrue(result.getId().equals(500));
	Assert.assertTrue(result.getDescrizione().equalsIgnoreCase(StatiPosizioniDebitorieConverter.CONCLUSA_NEGATIVAMENTE));
    }

    @Test
    public void getStatiPosizioniInCorso_TornaArrayDiNElementi() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] result = s.getStatiPosizioniInCorso();
	Assert.assertTrue(result.length == 5);
    }

    @Test
    public void getStatiPosizioniChiuse_TornaArrayDiNElementi() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] result = s.getStatiPosizioniChiusePositivamente();
	Assert.assertTrue(result.length == 3);
    }

    @Test
    public void getStatiPosizioniNonConclusivi_TornaArrayDiNElementi() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] result = s.getStatiPosizioniNonConclusivi();
	Assert.assertTrue(result.length == 7);
    }

    @Test
    public void getDefaultStatiNonConclusiviString_TornaArrayDiNElementi() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	String[] result = s.getDefaultStatiNonConclusiviString();
	Assert.assertTrue(result.length == 7);
    }

    @Test
    public void convertiStatiDaStringa_torna_nullo_se_param_nullo() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	String[] result = s.convertiStatiDaStringa(null);
	Assert.assertTrue(result == null);
    }

    @Test
    public void convertiStatiDaStringa_torna_nullo_se_param_vuoto() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	String[] result = s.convertiStatiDaStringa("");
	Assert.assertTrue(result == null);
    }

    @Test
    public void convertiStatiDaStringa_torna_nullo_se_param_con_valori_vuoti() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	String[] result = s.convertiStatiDaStringa(",,,,,,");
	Assert.assertTrue(result == null);
    }

    @Test
    public void convertiStatiDaStringa_torna_array_correttamente_dimensionato_se_param_con_valori_corretti() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	String[] result = s.convertiStatiDaStringa("PAGATO_OFFLINE_ANNULLATO , CON_ERRORE, ANNULLATO");
	Assert.assertTrue(result.length == 3);
    }

    @Test
    public void convertiStatiDaStringa_torna_array_correttamente_dimensionato_se_param_con_valori_non_corretti() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	String[] result = s
		.convertiStatiDaStringa(",,PAGATO_OFFLINE_ANNULLATO , CONCLUSA_NEGATIVAMENTE_bla_bla ,CONCLUSA_POSITIVAMENTE,TRASMESSO_A_PSP ");
	Assert.assertTrue(result.length == 2);
    }

    @Test
    public void convertiStatiDaStringaInEnumeration_torna_nullo_se_param_nullo() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] result = s.convertiStatiDaStringaInEnumeration(null);
	Assert.assertTrue(result == null);
    }

    @Test
    public void convertiStatiDaStringaInEnumeration_torna_nullo_se_param_vuoto() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] result = s.convertiStatiDaStringaInEnumeration("");
	Assert.assertTrue(result == null);
    }

    @Test
    public void convertiStatiDaStringaInEnumeration_torna_nullo_se_param_con_valori_vuoti() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] result = s.convertiStatiDaStringaInEnumeration(",,,,,,");
	Assert.assertTrue(result == null);
    }

    @Test
    public void convertiStatiDaStringaInEnumeration_torna_array_correttamente_dimensionato_se_param_con_valori_corretti() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] result = s.convertiStatiDaStringaInEnumeration("PAGATO_OFFLINE_ANNULLATO , CON_ERRORE, ANNULLATO");
	Assert.assertTrue(result.length == 3);
    }

    @Test
    public void convertiStatiDaStringaInEnumeration_torna_array_correttamente_dimensionato_se_param_con_valori_non_corretti() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] result = s.convertiStatiDaStringaInEnumeration(
		",,PAGATO_OFFLINE_ANNULLATO , CONCLUSA_NEGATIVAMENTE_bla_bla ,CONCLUSA_POSITIVAMENTE,TRASMESSO_A_PSP ");
	Assert.assertTrue(result.length == 2);
    }

    @Test
    public void convertiStatiRimuovibileDaBlackListTornaValoriConsentiti() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	Assert.assertTrue("Con " + StatoPagamentoType.ANNULLAMENTO_RICHIESTO.name() + " torno true ",
		s.rimuovibileDaBlackList(StatoPagamentoType.ANNULLAMENTO_RICHIESTO.name()));
	Assert.assertTrue("Con " + StatoPagamentoType.ANNULLATO.name() + " torno true ",
		s.rimuovibileDaBlackList(StatoPagamentoType.ANNULLATO.name()));
	Assert.assertTrue("Con " + StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO.name() + " torno true ",
		s.rimuovibileDaBlackList(StatoPagamentoType.PAGATO_OFFLINE_ANNULLATO.name()));
	Assert.assertTrue("Con " + StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE.name() + " torno true ",
		s.rimuovibileDaBlackList(StatoPagamentoType.PAGATO_OFFLINE_DA_ANNULLARE.name()));
    }

    public void convertiStatiStatiPagamentoBlackListNonPagati_torna_un_solo_record() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] statiPagamentoBlackListNonPagati = s.getStatiPagamentoBlackListNonPagati();
	Assert.assertTrue("Ritorna un solo risultato", statiPagamentoBlackListNonPagati.length == 1);
	Assert.assertTrue("Il risultato deve essere " + StatoPagamentoType.ATTIVATO_IN_PSP.name(),
		StatoPagamentoType.ATTIVATO_IN_PSP.equals(statiPagamentoBlackListNonPagati));
    }

    @Test
    public void convertiStatiGetStatiPosizioniPagabiliSoloLePosizioni() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] s2 = s.getStatiPosizioniPagabili();
	for (StatoPagamentoType statoPagamentoType : s2) {
	    boolean ris = StatoPagamentoType.ACQUISITO.equals(statoPagamentoType) || StatoPagamentoType.ATTIVATO_IN_PSP.equals(statoPagamentoType)
		    || StatoPagamentoType.TRASMESSO_A_PSP.equals(statoPagamentoType);
	    Assert.assertTrue("Il risultato deve essere " + StatoPagamentoType.ACQUISITO.name() + ", " + StatoPagamentoType.TRASMESSO_A_PSP.name() +
			      ", " + StatoPagamentoType.ATTIVATO_IN_PSP.name(),
		    ris);
	}
    }

    @Test
    public void isStatoPosizionePagabileEAttivate_torna_true_solo_per_attivate_in_psp() {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	StatoPagamentoType[] statiPagamentoBlackListNonPagati = s.getStatiPagamentoBlackListNonPagati();
	Assert.assertTrue("Ritorna true",
		statiPagamentoBlackListNonPagati.length == 1 && s.isStatoPosizionePagabileEAttivate(statiPagamentoBlackListNonPagati[0].name()));
	StatoPagamentoType[] stati = StatoPagamentoType.values();
	for (StatoPagamentoType statoPagamentoType : stati) {
	    if (!statoPagamentoType.equals(StatoPagamentoType.ATTIVATO_IN_PSP)) {
		Assert.assertFalse("Il risultato deve essere false per " + statoPagamentoType.name(),
			s.isStatoPosizionePagabileEAttivate(statoPagamentoType.name()));
	    }
	}
    }
}
