package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

public class DescrizioneRigaBollettazioneMercatoTests {

    @Test(expected = BusinessValidationException.class)
    public void getDescrizione_con_tutti_i_dati_null_ritorna_eccezione() {

	new DescrizioneRigaBollettazioneMercato(null, null, null).getDescrizione();
    }

    @Test(expected = BusinessValidationException.class)
    public void getDescrizione_con_conto_e_posteggio_null_ritorna_eccezione() {

	new DescrizioneRigaBollettazioneMercato(new BollettazioneDAODescrizionePosteggioFake(), null, null).getDescrizione();
    }

    @Test(expected = BusinessValidationException.class)
    public void getDescrizione_posteggio_null_ritorna_eccezione() {

	new DescrizioneRigaBollettazioneMercato(new BollettazioneDAODescrizionePosteggioFake(), 1, null).getDescrizione();
    }

    @Test(expected = BusinessValidationException.class)
    public void getDescrizione_con_conto_null_ritorna_eccezione() {

	new DescrizioneRigaBollettazioneMercato(new BollettazioneDAODescrizionePosteggioFake(), null, 1).getDescrizione();
    }

    @Test
    public void getDescrizione_con_concessione_e_conto_ritorna_valorizzato() {

	DescrizioneRigaBollettazioneMercato descrizioneRigaBollettazioneMercato = new DescrizioneRigaBollettazioneMercato(
		new BollettazioneDAODescrizionePosteggioFake(), 1, 1);
	String expected = "Mercati merci varie - Mercato merci varie DINEGRO Giovedì - 01";
	Assert.assertEquals("Deve restituire " + expected, expected, descrizioneRigaBollettazioneMercato.getDescrizione());
    }
}
