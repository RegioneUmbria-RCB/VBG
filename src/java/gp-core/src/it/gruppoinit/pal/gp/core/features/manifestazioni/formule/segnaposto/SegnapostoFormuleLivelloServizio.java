package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import java.math.BigDecimal;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;

public class SegnapostoFormuleLivelloServizio implements SegnapostoFormuleMercati {

    private List<LivelloServizio> serviziDisponibili;
    private List<ValoriLivelloServizio> serviziConfigurati;

    public SegnapostoFormuleLivelloServizio(List<ValoriLivelloServizio> serviziConfigurati, List<LivelloServizio> serviziDisponibili) {

	super();
	this.serviziDisponibili = serviziDisponibili;
	this.serviziConfigurati = serviziConfigurati;
    }

    @Override
    public String sostituisci(String formula) {

	if (StringUtils.isEmpty(formula)) {
	    return null;
	}
	//	recupero la struttura dati di questo tipo
	//	SEGNAPOSTO	TARIFFA	QUANTITA
	//	ALLACCIO_IDRICO	0,2	30				
	//	CARRELLI	10	15			
	//	MAGAZZINO	2	10	
	if (serviziConfigurati != null) {
	    for (ValoriLivelloServizio es : serviziConfigurati) {
		String segnaposto = "[" + es.getSegnaposto() + "]";
		if (formula.contains(segnaposto)) {
		    BigDecimal value = es.getQuantita().multiply(es.getTariffa());
		    formula = formula.replace(segnaposto, String.valueOf(value.doubleValue()));
		}
	    }
	}
	if (serviziDisponibili != null) {
	    // questo ciclo serve per ripulire la formula da segnaposto non trovati o usati non correttamente
	    // torno 0 per annullare le operazioni
	    for (LivelloServizio mercatiLivelloServizio : serviziDisponibili) {
		String segnaposto = "[" + mercatiLivelloServizio.getSegnaposto() + "]";
		formula = formula.replace(segnaposto, "0");
	    }
	}
	return formula;
    }
}
