package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RigaDettaglioCalcoloMercati;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleBuilder;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleBuilderRequest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleGG;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleGGPres;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata;

public class SegnapostoFormuleBuilderTest {

    private static final String SEGNAPOSTO_CUSTOM = "SEGNAPOSTO_CUSTOM";
    private static final String SEGNAPOSTO_CUSTOM_2 = "SEGNAPOSTO_CUSTOM_2";

    public class FakeRecuperaInformazioniGiornataService implements IRecuperaInformazioniGiornataService {

	private boolean isAssenzaGiustificata = false;
	private List<LivelloServizio> disponibili = null;
	private List<ValoriLivelloServizio> configurati = null;
	private boolean isConcessionarioPresente = false;
	private boolean isSpuntistaPresente = false;
	private boolean isBattitore = false;
	private BigDecimal coefficienteMercato = null;
	private Integer idConto = null;

	public FakeRecuperaInformazioniGiornataService() {

	    this.disponibili = new ArrayList<LivelloServizio>();
	    LivelloServizio lv = new LivelloServizio();
	    lv.setSegnaposto(SEGNAPOSTO_CUSTOM);
	    this.disponibili.add(lv);
	    LivelloServizio lv2 = new LivelloServizio();
	    lv2.setSegnaposto(SEGNAPOSTO_CUSTOM_2);
	    this.disponibili.add(lv2);
	    this.configurati = new ArrayList<ValoriLivelloServizio>();
	    ValoriLivelloServizio v = new ValoriLivelloServizio(BigDecimal.valueOf(10), BigDecimal.valueOf(2), SEGNAPOSTO_CUSTOM);
	    this.configurati.add(v);
	}

	public FakeRecuperaInformazioniGiornataService(boolean isAssenzaGiustificata, List<LivelloServizio> disponibili,
		List<ValoriLivelloServizio> configurati, boolean isConcessionarioPresente, boolean isSpuntistaPresente, Integer idConto,
		BigDecimal coefficienteMercato, boolean isBattitore) {

	    this.isAssenzaGiustificata = isAssenzaGiustificata;
	    this.disponibili = disponibili;
	    this.configurati = configurati;
	    this.isConcessionarioPresente = isConcessionarioPresente;
	    this.isSpuntistaPresente = isSpuntistaPresente;
	    this.coefficienteMercato = coefficienteMercato;
	    this.idConto = idConto;
	    this.isBattitore = isBattitore;
	}

	@Override
	public List<ValoriLivelloServizio> livelliDiServizioConfiguratiPerGiornataEIdPosteggio(Integer idGiornata, Integer idPosteggio) {

	    return this.configurati;
	}

	@Override
	public List<LivelloServizio> livelliDiServizioElencoCompletoDisponibili() {

	    return this.disponibili;
	}

	@Override
	public BigDecimal getCoefficienteMercato(Integer idConto, Integer idGiornata, Integer idPosteggio) {

	    return this.coefficienteMercato;
	}

	@Override
	public Integer getIdContoAttivoDaFormulaEIdGiornata(MercatiFormuleCalcolo formula, Integer idGiornata) {

	    return this.idConto;
	}

	@Override
	public boolean isBattitore(String codiceIstatPresenza) {

	    return this.isBattitore;
	}

	@Override
	public void resetObjectCached() {

	}
    }

    private SegnapostoFormuleBuilderRequest buildRequest(Boolean assenzaGiustificata, String provenienza) {

	String testoFormula = "(" + SegnapostoFormuleGG.SEGNAPOSTO + "+" + SegnapostoFormuleGGPres.SEGNAPOSTO + "+" +
			      SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata.SEGNAPOSTO + ")*[" + SEGNAPOSTO_CUSTOM + "]";
	return this.buildRequest(testoFormula, assenzaGiustificata, provenienza);
    }

    private SegnapostoFormuleBuilderRequest buildRequest(String testoFormula, Boolean assenzaGiustificata, String provenienza) {

	MercatiFormuleCalcolo formula = new MercatiFormuleCalcolo();
	formula.setFormula(testoFormula);
	RigaDettaglioCalcoloMercati request = new RigaDettaglioCalcoloMercati();
	request.setAssenzaGiustificata(assenzaGiustificata);
	request.setProvenienza(provenienza);
	return new SegnapostoFormuleBuilderRequest(formula, request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void costruttore_rilancia_IAE_se_request_nulla() {

	SegnapostoFormuleBuilderRequest request = null;
	IRecuperaInformazioniGiornataService service = new FakeRecuperaInformazioniGiornataService();
	InfoGiornataPresenzaBean info = new InfoGiornataPresenzaBean(false, false, false, null);
	new SegnapostoFormuleBuilder(request, service, info);
    }

    @Test()
    public void build_calcolo_corretto_delle_formule() {

	String provenienza = "presenze";
	boolean assenzaGiustificata = false;
	SegnapostoFormuleBuilderRequest req = buildRequest(assenzaGiustificata, provenienza);
	IRecuperaInformazioniGiornataService service = new FakeRecuperaInformazioniGiornataService();
	InfoGiornataPresenzaBean info = new InfoGiornataPresenzaBean(true, false, false, null);
	SegnapostoFormuleBuilder b = new SegnapostoFormuleBuilder(req, service, info);
	String ret = b.build();
	Assert.assertEquals("la formula tornata è (1+1+1)*20.0", ret, "(1+1+1)*20.0");
    }

    @Test()
    public void calcolo_corretto_delle_formule_in_caso_di_assenze() {

	String provenienza = "assenze";
	Boolean assenzaGiustificata = Boolean.FALSE;
	SegnapostoFormuleBuilderRequest req = buildRequest(assenzaGiustificata, provenienza);
	IRecuperaInformazioniGiornataService service = new FakeRecuperaInformazioniGiornataService();
	InfoGiornataPresenzaBean info = new InfoGiornataPresenzaBean(false, false, false, null);
	SegnapostoFormuleBuilder b = new SegnapostoFormuleBuilder(req, service, info);
	String ret = b.build();
	Assert.assertEquals("la formula tornata è (1+0+1)*20.0", ret, "(1+0+1)*20.0");
    }

    @Test()
    public void build_calcolo_corretto_delle_formule_con_2_segnaposto() {

	String provenienza = "assenze";
	Boolean assenzaGiustificata = Boolean.FALSE;
	boolean isConcessionarioPresente = true;
	boolean isSpuntistaPresente = false;
	boolean isBattitore = false;
	BigDecimal coefficienteMercato = null;
	Integer idConto = null;
	//
	List<LivelloServizio> disponibili = new ArrayList<LivelloServizio>();
	LivelloServizio lv = new LivelloServizio();
	lv.setSegnaposto("GG");
	disponibili.add(lv);
	LivelloServizio lv2 = new LivelloServizio();
	lv2.setSegnaposto("COSAP");
	disponibili.add(lv2);
	//
	List<ValoriLivelloServizio> configurati = new ArrayList<ValoriLivelloServizio>();
	ValoriLivelloServizio v = new ValoriLivelloServizio(BigDecimal.valueOf(2.50), BigDecimal.valueOf(1), "COSAP");
	configurati.add(v);
	//
	String formula = "[GG] * [COSAP]";
	//
	SegnapostoFormuleBuilderRequest req = buildRequest(formula, assenzaGiustificata, provenienza);
	IRecuperaInformazioniGiornataService service = new FakeRecuperaInformazioniGiornataService(assenzaGiustificata, disponibili, configurati,
		isConcessionarioPresente, isSpuntistaPresente, idConto, coefficienteMercato, isBattitore);
	InfoGiornataPresenzaBean info = new InfoGiornataPresenzaBean(false, false, false, null);
	SegnapostoFormuleBuilder b = new SegnapostoFormuleBuilder(req, service, info);
	String actual = b.build();
	String expected = "1 * 2.5";
	Assert.assertEquals("la formula tornata è " + expected, expected, actual);
    }
}
