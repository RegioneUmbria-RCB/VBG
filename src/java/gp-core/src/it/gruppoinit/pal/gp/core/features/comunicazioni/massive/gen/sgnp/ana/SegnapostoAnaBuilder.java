package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp.ana;

import java.util.ArrayList;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;

public class SegnapostoAnaBuilder {
    
    private List<SegnapostoFormuleAnaGen> segnaposti = new ArrayList<SegnapostoFormuleAnaGen>();
    
    public SegnapostoAnaBuilder(){
	segnaposti.add(new SegnapostoNomeCognome());
    }
    
    public String sostituisci(String formula, Anagrafe ana){
	String out = formula;
	for(SegnapostoFormuleAnaGen segnaposto : segnaposti){
	    out = segnaposto.sostituisci(out, ana);
	}
	return out;
    }
}
