package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IRecuperaInformazioniGiornataService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.InfoGiornataPresenzaBean;

public class SegnapostoFormuleBuilder {

    private String formula;
    private List<SegnapostoFormuleMercati> segnaposti = new ArrayList<SegnapostoFormuleMercati>(0);

    public SegnapostoFormuleBuilder(String formula, Integer idConto, BigDecimal coefficienteMercato, List<ValoriLivelloServizio> serviziConfigurati,
	    List<LivelloServizio> serviziDisponibili, boolean isBattitore, String provenienza, boolean assenzaGiustificata,
	    boolean concessionarioPresente, boolean spuntistaPresente) {

	super();
	this.formula = formula;
	this.inizializza(idConto, coefficienteMercato, serviziConfigurati, serviziDisponibili, isBattitore, provenienza, assenzaGiustificata,
		concessionarioPresente, spuntistaPresente);
    }

    public SegnapostoFormuleBuilder(SegnapostoFormuleBuilderRequest request, IRecuperaInformazioniGiornataService giornataService,
	    InfoGiornataPresenzaBean info) {

	super();
	if (request == null) {
	    throw new IllegalArgumentException("La request non può essere nulla");
	}
	this.formula = request.getFormula().getFormula();
	Integer idConto = giornataService.getIdContoAttivoDaFormulaEIdGiornata(request.getFormula(), request.getIdGiornata());
	BigDecimal coefficienteMercato = giornataService.getCoefficienteMercato(idConto, request.getIdGiornata(), request.getIdPosteggio());
	List<ValoriLivelloServizio> serviziConfigurati = giornataService.livelliDiServizioConfiguratiPerGiornataEIdPosteggio(request.getIdGiornata(),
		request.getIdPosteggio());
	List<LivelloServizio> serviziDisponibili = giornataService.livelliDiServizioElencoCompletoDisponibili();
	boolean isBattitore = giornataService.isBattitore(info.getCategoriaMerceologicaSalvata());
	boolean assenzaGiustificata = info.isAssenzaGiustificata();
	boolean concessionarioPresente = info.isConcessionarioPresente();
	boolean spuntistaPresente = info.isSpuntistaPresente();
	this.inizializza(idConto, coefficienteMercato, serviziConfigurati, serviziDisponibili, isBattitore, request.getProvenienza(),
		assenzaGiustificata, concessionarioPresente, spuntistaPresente);
    }

    private void inizializza(Integer idConto, BigDecimal coefficienteMercato, List<ValoriLivelloServizio> serviziConfigurati,
	    List<LivelloServizio> serviziDisponibili, boolean isBattitore, String provenienza, boolean assenzaGiustificata,
	    boolean concessionarioPresente, boolean spuntistaPresente) {

	MercatipresenzeDServiceImpl.log.debug("SegnapostoFormuleBuilder ");
	this.segnaposti.add(new SegnapostoFormuleGG());
	MercatipresenzeDServiceImpl.log.debug("SegnapostoFormuleBuilder SegnapostoFormuleGG");
	this.segnaposti.add(new SegnapostoFormuleGGPres(provenienza));
	MercatipresenzeDServiceImpl.log.debug("SegnapostoFormuleBuilder SegnapostoFormuleGGPres");
	this.segnaposti.add(new SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata(provenienza, assenzaGiustificata));
	MercatipresenzeDServiceImpl.log.debug("SegnapostoFormuleBuilder SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata");
	this.segnaposti.add(new SegnapostoFormuleLivelloServizio(serviziConfigurati, serviziDisponibili));
	MercatipresenzeDServiceImpl.log.debug("SegnapostoFormuleBuilder SegnapostoFormuleLivelloServizio");
	this.segnaposti.add(new SegnapostoFormuleConcessionario(concessionarioPresente));
	MercatipresenzeDServiceImpl.log.debug("SegnapostoFormuleBuilder SegnapostoFormuleConcessionario");
	this.segnaposti.add(new SegnapostoFormuleSpuntista(spuntistaPresente));
	MercatipresenzeDServiceImpl.log.debug("SegnapostoFormuleBuilder SegnapostoFormuleSpuntista");
	this.segnaposti.add(new SegnapostoFormuleCoefficienteMercato(coefficienteMercato));
	MercatipresenzeDServiceImpl.log.debug("SegnapostoFormuleBuilder SegnapostoFormuleCoefficienteMercato");
	this.segnaposti.add(new SegnapostoFormuleBattitore(isBattitore));
	MercatipresenzeDServiceImpl.log.debug("SegnapostoFormuleBuilder isBattitore");
    }

    public String build() {

	if (!StringUtils.isEmpty(formula)) {
	    String testoFormula = formula.toUpperCase();
	    MercatipresenzeDServiceImpl.log.debug("build TESTO FORMULA {}", testoFormula);
	    for (SegnapostoFormuleMercati segnaposto : segnaposti) {
		MercatipresenzeDServiceImpl.log.debug("build {} BEGIN", segnaposto);
		testoFormula = segnaposto.sostituisci(testoFormula);
		MercatipresenzeDServiceImpl.log.debug("build TESTO FORMULA sostituisco il segnaposto {}", testoFormula);
		MercatipresenzeDServiceImpl.log.debug("build {} DONE", segnaposto);
	    }
	    MercatipresenzeDServiceImpl.log.debug("build TESTO FORMULA FINALE {}", testoFormula);
	    return testoFormula;
	}
	return null;
    }
}
