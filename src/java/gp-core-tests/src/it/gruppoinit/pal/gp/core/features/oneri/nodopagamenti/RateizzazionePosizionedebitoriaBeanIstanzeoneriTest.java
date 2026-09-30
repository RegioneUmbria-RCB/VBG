package it.gruppoinit.pal.gp.core.features.oneri.nodopagamenti;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

import com.paevolution.ws.pagamenti_types.ImportoPagamentoWsInType;
import com.paevolution.ws.pagamenti_types.InserisciPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.PosizioneDebitoriaWsInType;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;

public class RateizzazionePosizionedebitoriaBeanIstanzeoneriTest {

    @Test()
    public void toInserisciPosizioneDebitorieTypeRestituisceIlNumeroDiRateCorretto() {

	RateizzazionePosizionedebitoriaBeanIstanzeoneri r = buildTreRate();
	InserisciPosizioniDebitorieType ret = r.toInserisciPosizioneDebitorieType();
	assertEquals("Deve restituire 3 rate", 3, ret.getRegistrazione().get(0).getRate().size());
    }

    @Test()
    public void toInserisciPosizioneDebitorieTypeRestituisceIlNumeroDiRataValorizzatoCorrettamente() {

	RateizzazionePosizionedebitoriaBeanIstanzeoneri r = buildTreRate();
	InserisciPosizioniDebitorieType ret = r.toInserisciPosizioneDebitorieType();
	assertEquals("Deve restituire numero rata 1", BigInteger.valueOf(1), ret.getRegistrazione().get(0).getRate().get(0).getNumeroRata());
	assertEquals("Deve restituire numero rata 2", BigInteger.valueOf(2), ret.getRegistrazione().get(0).getRate().get(1).getNumeroRata());
	assertEquals("Deve restituire numero rata 3", BigInteger.valueOf(3), ret.getRegistrazione().get(0).getRate().get(2).getNumeroRata());
    }

    @Test()
    public void toInserisciPosizioneDebitorieTypeRestituisceImportoCorrettamente() {

	RateizzazionePosizionedebitoriaBeanIstanzeoneri r = buildTreRate();
	InserisciPosizioniDebitorieType ret = r.toInserisciPosizioneDebitorieType();
	BigDecimal importo = BigDecimal.ZERO;
	List<PosizioneDebitoriaWsInType> rate = ret.getRegistrazione().get(0).getRate();
	for (PosizioneDebitoriaWsInType pwsin : rate) {
	    List<ImportoPagamentoWsInType> importi = pwsin.getImporti();
	    for (ImportoPagamentoWsInType imp : importi) {
		importo = importo.add(imp.getImporto());
	    }
	}
	assertEquals("Deve restituire importo 300", BigDecimal.valueOf(300), importo);
    }

    private RateizzazionePosizionedebitoriaBeanIstanzeoneri buildTreRate() {

	List<PosizioneDebitoriaBeanIstanzeoneri> treRate = new ArrayList<PosizioneDebitoriaBeanIstanzeoneri>();
	String cfEnte = "cfEnte";
	Conti conto = newConti();
	Anagrafe richiedente = newAnagrafe();
	PosizioneDebitoriaBeanIstanzeoneri pd1 = PosizioneDebitoriaBeanIstanzeoneri.newInstance(newIstanzeoneri(1, conto), cfEnte, conto,
		richiedente);
	treRate.add(pd1);
	PosizioneDebitoriaBeanIstanzeoneri pd2 = PosizioneDebitoriaBeanIstanzeoneri.newInstance(newIstanzeoneri(2, conto), cfEnte, conto,
		richiedente);
	treRate.add(pd2);
	PosizioneDebitoriaBeanIstanzeoneri pd3 = PosizioneDebitoriaBeanIstanzeoneri.newInstance(newIstanzeoneri(3, conto), cfEnte, conto,
		richiedente);
	treRate.add(pd3);
	return new RateizzazionePosizionedebitoriaBeanIstanzeoneri(treRate);
    }

    private Istanzeoneri newIstanzeoneri(int numerorata, Conti conto) {

	Istanzeoneri istoneri = new Istanzeoneri();
	istoneri.setTipicausalioneri(newTipicausali(conto));
	istoneri.getId().setCodice(1);
	istoneri.setIstanza(newIstanza());
	istoneri.setPrezzo(BigDecimal.valueOf(100));
	istoneri.setDatascadenza(Calendar.getInstance().getTime());
	istoneri.setNumerorata(numerorata);
	return istoneri;
    }

    private DettPosizioneDebitoria newDettposizioneDebitoria() {

	DettPosizioneDebitoria d = new DettPosizioneDebitoria();
	d.setAnagrafe(newAnagrafe());
	d.setCodiceAvviso("123456");
	d.setId(new PkId(20));
	d.setDescrizioneCausale("PagamentoOneri");
	d.setImportoIvato(BigDecimal.valueOf(10));
	return d;
    }

    private Conti newConti() {

	Conti conto = new Conti();
	conto.setMappaturanodopag("1");
	return conto;
    }

    private Tipicausalioneri newTipicausali(Conti conto) {

	Tipicausalioneri t = new Tipicausalioneri();
	t.setId(new PkId(100));
	t.setCoDescrizione("Causale onere");
	t.setContoAttivo(conto.getId().getCodice());
	return t;
    }

    private Istanze newIstanza() {

	Istanze ist = new Istanze();
	Anagrafe r = newAnagrafe();
	ist.setRichiedente(r);
	ist.setNumeroistanza("23");
	ist.getId().setCodice(100);
	ist.getId().setIdcomune("E256");
	ist.setLavori("Lavori");
	return ist;
    }

    private Anagrafe newAnagrafe() {

	Anagrafe r = new Anagrafe();
	r.setNome("Riccardo");
	r.setNominativo("Bacci");
	r.setCodicefiscale("sprmnn23h73g9o");
	r.setTipoanagrafe("F");
	return r;
    }
}
