package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp.aut;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;

public class SegnapostoDataAut implements SegnapostoFormuleAutGen{

    private static final String segnaposto = "[DATA_AUT]" ;
    private static final String dateformat = "dd-MM-yyyy";
    
    @Override
    public String sostituisci(String formula, Autorizzazioni aut) {

	if(formula == null){
	    return formula;
	}
	
	Date data = aut.getAutorizdata();
	if(data == null){
	    return formula.replace(segnaposto, "[undefined]");
	}
	
	try{
	    SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
	    return formula.replace(segnaposto, sdf.format(data));
	}catch(Exception e){
	    return formula;
	}

    }

   
}
