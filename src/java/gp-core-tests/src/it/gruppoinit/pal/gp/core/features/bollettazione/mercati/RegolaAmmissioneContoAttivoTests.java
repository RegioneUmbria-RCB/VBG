package it.gruppoinit.pal.gp.core.features.bollettazione.mercati;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;

public class RegolaAmmissioneContoAttivoTests {

    private Date getDataInizioDefault() {

	Calendar c = new GregorianCalendar(2020, 0, 1, 12, 34, 56);
	return c.getTime();
    }

    private Date getDataFineDefault() {

	Calendar c = new GregorianCalendar(2020, 0, 31, 12, 34, 56);
	return c.getTime();
    }

    private List<MercatiContabilitaTributi> getContiValidiDefault() {

	List<MercatiContabilitaTributi> lista = new ArrayList<MercatiContabilitaTributi>(0);
	MercatiContabilitaTributi mct = new MercatiContabilitaTributi();
	mct.setDataInizioValidita(this.getDataInizioDefault());
	mct.setDataFineValidita(this.getDataFineDefault());
	lista.add(mct);
	return lista;
    }

    private List<MercatiContabilitaTributi> getContiNonValidiDefault() {

	Calendar d1 = new GregorianCalendar(2019, 0, 1, 12, 34, 56);
	Calendar d2 = new GregorianCalendar(2019, 0, 31, 12, 34, 56);
	List<MercatiContabilitaTributi> lista = new ArrayList<MercatiContabilitaTributi>(0);
	MercatiContabilitaTributi mct = new MercatiContabilitaTributi();
	mct.setDataInizioValidita(d1.getTime());
	mct.setDataFineValidita(d2.getTime());
	lista.add(mct);
	return lista;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void verifica_regola_con_tutti_i_parametri_null_genera_IllegalArgumentException() {

	new RegolaAmmissioneContoAttivo(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifica_regola_con_solo_data_inizio_genera_IllegalArgumentException() {

	new RegolaAmmissioneContoAttivo(this.getDataInizioDefault(), null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifica_regola_con_data_inizio_e_fine_genera_IllegalArgumentException() {

	new RegolaAmmissioneContoAttivo(this.getDataInizioDefault(), this.getDataFineDefault(), null);
    }

    @Test()
    public void verifica_regola_con_conti_validi_torna_true() {

	Boolean retVal = new RegolaAmmissioneContoAttivo(this.getDataInizioDefault(), this.getDataFineDefault(), this.getContiValidiDefault())
		.valida();
	Assert.assertTrue("Le regole passate sono tutte valide", retVal);
    }

    @Test()
    public void verifica_regola_con_conti_scaduti_torna_true() {

	Boolean retVal = new RegolaAmmissioneContoAttivo(this.getDataInizioDefault(), this.getDataFineDefault(), this.getContiNonValidiDefault())
		.valida();
	Assert.assertFalse("Le regole passate NON sono valide", retVal);
    }
    
}
