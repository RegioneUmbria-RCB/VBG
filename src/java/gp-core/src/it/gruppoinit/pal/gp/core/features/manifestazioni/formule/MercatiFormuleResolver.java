package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ContoImportoTotaleHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleBuilder;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto.SegnapostoFormuleBuilderRequest;

public class MercatiFormuleResolver {

    private static final Logger log = LoggerFactory.getLogger(MercatiFormuleResolver.class);
    // private MercatipresenzeT giornata;
    private Date dataGiornata;
    private IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService;
    private SegnapostoFormuleBuilderRequest request;
    private InfoGiornataPresenzaBean info;

    public MercatiFormuleResolver(SegnapostoFormuleBuilderRequest request, IRecuperaInformazioniGiornataService recuperaInformazioniGiornataService,
	    Date dataGiornata, InfoGiornataPresenzaBean info) {

	this.dataGiornata = dataGiornata;
	this.request = request;
	this.recuperaInformazioniGiornataService = recuperaInformazioniGiornataService;
	this.info = info;
    }

    public ContoImportoTotaleHelper risolvi() {

	String formulaSostituita = new SegnapostoFormuleBuilder(this.request, this.recuperaInformazioniGiornataService, info).build();
	BigDecimal importoSenzaIVA = this.importoFormula(formulaSostituita);
	Conti conto = this.request.getFormula().getContoAttivo(dataGiornata).getConti();
	Integer idConto = conto.getId().getCodice();
	Integer iva = conto.getIva();
	return new ContoImportoTotaleHelper(idConto, importoSenzaIVA, iva);
    }

    public BigDecimal importoFormula(String testoFormulaDaValutare) {

	return new FormulaEvalService().importoFormula(testoFormulaDaValutare);
    }
}
