package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;
// List<ValoriLivelloServizio> serviziConfigurati, List<LivelloServizio> serviziDisponibili

public class SegnapostoFormuleLivelloServizioTests {

    @Test()
    public void sostituisci_con_formula_null_torna_null() {

	SegnapostoFormuleLivelloServizio segnaposto = new SegnapostoFormuleLivelloServizio(null, null);
	String actual = segnaposto.sostituisci(null);
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_con_formula_vuota_torna_null() {

	SegnapostoFormuleLivelloServizio segnaposto = new SegnapostoFormuleLivelloServizio(null, null);
	String actual = segnaposto.sostituisci("");
	Assert.assertNull("Il valore tornato è null", actual);
    }

    @Test()
    public void sostituisci_con_null_e_null_torna_fomula_passata() {

	SegnapostoFormuleLivelloServizio segnaposto = new SegnapostoFormuleLivelloServizio(null, null);
	String formula = "GG = ";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + formula, formula, actual);
    }

    @Test()
    public void sostituisci_con_null_e_serviziDisponibili_torna_fomula_passata() {

	List<LivelloServizio> serviziDisponibili = new ArrayList<LivelloServizio>();
	LivelloServizio livelloServizio = new LivelloServizio();
	livelloServizio.setSegnaposto("FAVA");
	serviziDisponibili.add(livelloServizio);
	SegnapostoFormuleLivelloServizio segnaposto = new SegnapostoFormuleLivelloServizio(null, serviziDisponibili);
	String formula = "GG = ";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + formula, formula, actual);
    }

    @Test()
    public void sostituisci_con_null_e_serviziDisponibili_torna_fomula_sostituita() {

	List<LivelloServizio> serviziDisponibili = new ArrayList<LivelloServizio>();
	LivelloServizio livelloServizio = new LivelloServizio();
	livelloServizio.setSegnaposto("FAVA");
	serviziDisponibili.add(livelloServizio);
	SegnapostoFormuleLivelloServizio segnaposto = new SegnapostoFormuleLivelloServizio(null, serviziDisponibili);
	String formula = "GG = [FAVA]";
	String expected = "GG = 0";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }

    @Test()
    public void sostituisci_con_serviziConfigurati_e_null_torna_fomula_passata() {

	List<ValoriLivelloServizio> serviziConfigurati = new ArrayList<ValoriLivelloServizio>();
	ValoriLivelloServizio valore = new ValoriLivelloServizio(new BigDecimal(10), new BigDecimal(25), "COSAP");
	serviziConfigurati.add(valore);
	SegnapostoFormuleLivelloServizio segnaposto = new SegnapostoFormuleLivelloServizio(serviziConfigurati, null);
	String formula = "GG = ";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + formula, formula, actual);
    }

    @Test()
    public void sostituisci_con_serviziConfigurati_e_null_torna_fomula_sostituita() {

	List<ValoriLivelloServizio> serviziConfigurati = new ArrayList<ValoriLivelloServizio>();
	ValoriLivelloServizio valore = new ValoriLivelloServizio(new BigDecimal(10), new BigDecimal(25), "COSAP");
	serviziConfigurati.add(valore);
	SegnapostoFormuleLivelloServizio segnaposto = new SegnapostoFormuleLivelloServizio(serviziConfigurati, null);
	String formula = "GG = [COSAP]";
	String expected = "GG = 250.0";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }

    @Test()
    public void sostituisci_con_serviziConfigurati_e_serviziDisponibili_torna_fomula_passata() {

	List<ValoriLivelloServizio> serviziConfigurati = new ArrayList<ValoriLivelloServizio>();
	ValoriLivelloServizio valore = new ValoriLivelloServizio(new BigDecimal(10), new BigDecimal(25), "COSAP");
	serviziConfigurati.add(valore);
	//
	List<LivelloServizio> serviziDisponibili = new ArrayList<LivelloServizio>();
	LivelloServizio livelloServizio = new LivelloServizio();
	livelloServizio.setSegnaposto("IMU");
	serviziDisponibili.add(livelloServizio);
	//
	SegnapostoFormuleLivelloServizio segnaposto = new SegnapostoFormuleLivelloServizio(serviziConfigurati, serviziDisponibili);
	String formula = "GG = ";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + formula, formula, actual);
    }

    @Test()
    public void sostituisci_con_serviziConfigurati_e_serviziDisponibili_torna_fomula_sostituita() {

	List<ValoriLivelloServizio> serviziConfigurati = new ArrayList<ValoriLivelloServizio>();
	ValoriLivelloServizio valore = new ValoriLivelloServizio(new BigDecimal(10), new BigDecimal(25), "COSAP");
	serviziConfigurati.add(valore);
	//
	List<LivelloServizio> serviziDisponibili = new ArrayList<LivelloServizio>();
	LivelloServizio livelloServizio = new LivelloServizio();
	livelloServizio.setSegnaposto("IMU");
	serviziDisponibili.add(livelloServizio);
	//
	SegnapostoFormuleLivelloServizio segnaposto = new SegnapostoFormuleLivelloServizio(serviziConfigurati, serviziDisponibili);
	String formula = "COSAP = [COSAP] e IMU = [IMU]";
	String expected = "COSAP = 250.0 e IMU = 0";
	String actual = segnaposto.sostituisci(formula);
	Assert.assertEquals("Il valore tornato è " + expected, expected, actual);
    }
}
