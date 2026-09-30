package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp.aut;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;

public class SegnapostoNumAutConc implements SegnapostoFormuleAutGen{

    private static final String segnaposto = "[NUM_AUT_CONC]" ;
    
    @Override
    public String sostituisci(String formula, Autorizzazioni aut) {

	if(formula == null){
	    return formula;
	}
	
	String autNumero = aut.getAutoriznumero();
	if(aut.getAutorizzazioniConcessionisForFkAutconcAutatt() != null && !aut.getAutorizzazioniConcessionisForFkAutconcAutatt().isEmpty()){
	    for(AutorizzazioniConcessioni concessioni : aut.getAutorizzazioniConcessionisForFkAutconcAutatt()){
		
		if(concessioni.getAutorizzazioniByFkAutconcAutcoll() != null){
		    autNumero = autNumero + " (" + concessioni.getAutorizzazioniByFkAutconcAutcoll().getAutoriznumero() + ")";
		}
		break;
	    }
	}
	
	return formula.replace(segnaposto, autNumero);
    }

   
}
