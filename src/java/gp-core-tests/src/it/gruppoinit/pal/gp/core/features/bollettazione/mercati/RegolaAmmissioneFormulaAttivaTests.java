package it.gruppoinit.pal.gp.core.features.bollettazione.mercati;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;

public class RegolaAmmissioneFormulaAttivaTests {

    private Date getDataInizioDefault() {

	Calendar c = new GregorianCalendar(2020, 0, 1, 12, 34, 56);
	return c.getTime();
    }

    private Date getDataFineDefault() {

	Calendar c = new GregorianCalendar(2020, 0, 31, 12, 34, 56);
	return c.getTime();
    }

    private List<MercatiFormuleCalcolo> getFormuleValideDefault() {

	List<MercatiFormuleCalcolo> lista = new ArrayList<MercatiFormuleCalcolo>(0);
	MercatiFormuleCalcolo mfc = new MercatiFormuleCalcolo();
	mfc.setDataInizioValidita(this.getDataInizioDefault());
	mfc.setDataFineValidita(this.getDataFineDefault());
	lista.add(mfc);
	return lista;
    }

    private List<MercatiFormuleCalcolo> getFormuleNonValideDefault() {

	Calendar d1 = new GregorianCalendar(2019, 0, 1, 12, 34, 56);
	Calendar d2 = new GregorianCalendar(2019, 0, 31, 12, 34, 56);
	List<MercatiFormuleCalcolo> lista = new ArrayList<MercatiFormuleCalcolo>(0);
	MercatiFormuleCalcolo mfc = new MercatiFormuleCalcolo();
	mfc.setDataInizioValidita(d1.getTime());
	mfc.setDataFineValidita(d2.getTime());
	lista.add(mfc);
	return lista;
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifica_regola_con_tutti_i_parametri_null_genera_IllegalArgumentException() {

	new RegolaAmmissioneFormulaAttiva(null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifica_regola_con_solo_data_inizio_genera_IllegalArgumentException() {

	new RegolaAmmissioneFormulaAttiva(this.getDataInizioDefault(), null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifica_regola_con_data_inizio_e_fine_genera_IllegalArgumentException() {

	new RegolaAmmissioneFormulaAttiva(this.getDataInizioDefault(), this.getDataFineDefault(), null);
    }

    @Test()
    public void verifica_regola_con_formule_valide_torna_true() {

	Boolean retVal = new RegolaAmmissioneFormulaAttiva(this.getDataInizioDefault(), this.getDataFineDefault(), this.getFormuleValideDefault())
		.valida();
	Assert.assertTrue("Le regole passate sono tutte valide", retVal);
    }

    @Test()
    public void verifica_regola_con_formule_scadute_torna_false() {

	Boolean retVal = new RegolaAmmissioneFormulaAttiva(this.getDataInizioDefault(), this.getDataFineDefault(), this.getFormuleNonValideDefault())
		.valida();
	Assert.assertFalse("Le regole passate NON sono valide", retVal);
    }
}
