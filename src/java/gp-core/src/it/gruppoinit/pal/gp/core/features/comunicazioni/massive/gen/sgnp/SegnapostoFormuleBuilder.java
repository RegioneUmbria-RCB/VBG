package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.LivelloServizio;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.ValoriLivelloServizio;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.IRecuperaInformazioniGiornataService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.formule.InfoGiornataPresenzaBean;

public class SegnapostoFormuleBuilder {

    private List<SegnapostoFormuleGen> segnaposti = new ArrayList<SegnapostoFormuleGen>();

    

    public String build(String testoFormula) {

	if (!StringUtils.isEmpty(testoFormula)) {
	    MercatipresenzeDServiceImpl.log.debug("build TESTO FORMULA {}", testoFormula);
	    for (SegnapostoFormuleGen segnaposto : segnaposti) {
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
    
    public void addSegnapostoFormula(SegnapostoFormuleGen formula){
	segnaposti.add(formula);
    }
}
