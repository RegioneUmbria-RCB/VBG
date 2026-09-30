package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp.aut;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;



public class SegnapostoAutBuilder {
    
    private List<SegnapostoFormuleAutGen> segnaposti = new ArrayList<SegnapostoFormuleAutGen>();
    
    public SegnapostoAutBuilder(){
	segnaposti.add(new SegnapostoNumAutConc());
	//segnaposti.add(new SegnapostoAssenzeConSuMerc());
	segnaposti.add(new SegnapostoAutComune());
	segnaposti.add(new SegnapostoDataAnzAut());
	segnaposti.add(new SegnapostoDataAut());
	//segnaposti.add(new SegnapostoPresenzeAutMerc());
    }
    
    public String sostituisci(String formula, Autorizzazioni aut){
	String out = formula;
	for(SegnapostoFormuleAutGen segnaposto : segnaposti){
	    out = segnaposto.sostituisci(out, aut);
	}
	return out;
    }
}
