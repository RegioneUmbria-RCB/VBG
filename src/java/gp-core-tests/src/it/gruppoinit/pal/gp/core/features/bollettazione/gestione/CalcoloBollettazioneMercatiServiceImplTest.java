package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiFormuleCalcoloContestoEnum;

public class CalcoloBollettazioneMercatiServiceImplTest {

    private static final String DEVE_RESTITUIRE = "Deve restituire ";

    @Test
    public void verificaAggiungiContoSoloConPresenza() {

	CalcoloBollettazioneMercatiServiceImpl c = new CalcoloBollettazioneMercatiServiceImpl(null, null, null, null, null, null, null, null);
	Integer idConto = 1;
	List<Integer> filtriConti = new ArrayList<Integer>(0);
	MercatiFormuleCalcolo formula = new MercatiFormuleCalcolo();
	formula.setContesto(MercatiFormuleCalcoloContestoEnum.PRESENZA);
	boolean esito = false;
	Assert.assertEquals(DEVE_RESTITUIRE + esito, c.verificaAggiungiConto(idConto, filtriConti, formula), esito);
    }

    @Test
    public void verificaAggiungiContoSoloConBollettazione() {

	CalcoloBollettazioneMercatiServiceImpl c = new CalcoloBollettazioneMercatiServiceImpl(null, null, null, null, null, null, null, null);
	Integer idConto = 1;
	List<Integer> filtriConti = new ArrayList<Integer>(0);
	MercatiFormuleCalcolo formula = new MercatiFormuleCalcolo();
	formula.setContesto(MercatiFormuleCalcoloContestoEnum.BOLLETTAZIONE);
	boolean esito = true;
	Assert.assertEquals(DEVE_RESTITUIRE + esito, c.verificaAggiungiConto(idConto, filtriConti, formula), esito);
    }

    @Test
    public void verificaAggiungiContoConContiEBollettazione() {

	CalcoloBollettazioneMercatiServiceImpl c = new CalcoloBollettazioneMercatiServiceImpl(null, null, null, null, null, null, null, null);
	Integer idConto = 1;
	List<Integer> filtriConti = new ArrayList<Integer>(0);
	filtriConti.add(1);
	MercatiFormuleCalcolo formula = new MercatiFormuleCalcolo();
	formula.setContesto(MercatiFormuleCalcoloContestoEnum.BOLLETTAZIONE);
	boolean esito = true;
	Assert.assertEquals(DEVE_RESTITUIRE + esito, c.verificaAggiungiConto(idConto, filtriConti, formula), esito);
    }

    @Test
    public void verificaAggiungiContoConContiEPresenza() {

	CalcoloBollettazioneMercatiServiceImpl c = new CalcoloBollettazioneMercatiServiceImpl(null, null, null, null, null, null, null, null);
	Integer idConto = 1;
	List<Integer> filtriConti = new ArrayList<Integer>(0);
	filtriConti.add(1);
	MercatiFormuleCalcolo formula = new MercatiFormuleCalcolo();
	formula.setContesto(MercatiFormuleCalcoloContestoEnum.PRESENZA);
	boolean esito = true;
	Assert.assertEquals(DEVE_RESTITUIRE + esito, c.verificaAggiungiConto(idConto, filtriConti, formula), esito);
    }

    @Test
    public void verificaAggiungiContoConContiSbagliatiEBollettazione() {

	CalcoloBollettazioneMercatiServiceImpl c = new CalcoloBollettazioneMercatiServiceImpl(null, null, null, null, null, null, null, null);
	Integer idConto = 1;
	List<Integer> filtriConti = new ArrayList<Integer>(0);
	filtriConti.add(2);
	MercatiFormuleCalcolo formula = new MercatiFormuleCalcolo();
	formula.setContesto(MercatiFormuleCalcoloContestoEnum.BOLLETTAZIONE);
	boolean esito = false;
	Assert.assertEquals(DEVE_RESTITUIRE + esito, c.verificaAggiungiConto(idConto, filtriConti, formula), esito);
    }

    @Test
    public void verificaAggiungiContoConContiSbagliatiEPresenza() {

	CalcoloBollettazioneMercatiServiceImpl c = new CalcoloBollettazioneMercatiServiceImpl(null, null, null, null, null, null, null, null);
	Integer idConto = 1;
	List<Integer> filtriConti = new ArrayList<Integer>(0);
	filtriConti.add(2);
	MercatiFormuleCalcolo formula = new MercatiFormuleCalcolo();
	formula.setContesto(MercatiFormuleCalcoloContestoEnum.PRESENZA);
	boolean esito = false;
	Assert.assertEquals(DEVE_RESTITUIRE + esito, c.verificaAggiungiConto(idConto, filtriConti, formula), esito);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verificaAggiungiContoConContestoNullo() {

	new CalcoloBollettazioneMercatiServiceImpl(null, null, null, null, null, null, null, null).verificaAggiungiConto(null, null,
		new MercatiFormuleCalcolo());
    }
}
