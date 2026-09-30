package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp.aut;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;

public class SegnapostoAutComune implements SegnapostoFormuleAutGen{
    
private static final String segnaposto = "[COMUNE_AUT]" ;
    
    @Override
    public String sostituisci(String formula, Autorizzazioni aut) {

	if(formula == null){
	    return formula;
	}
	
	if(aut.getAutorizcomune() != null && aut.getAutorizcomune().getDescrizioneEstesa() != null){
	    return formula.replace(segnaposto, aut.getAutorizcomune().getDescrizioneEstesa());
	}
	
	return formula.replace(segnaposto, "[undefined]");
    }
}
