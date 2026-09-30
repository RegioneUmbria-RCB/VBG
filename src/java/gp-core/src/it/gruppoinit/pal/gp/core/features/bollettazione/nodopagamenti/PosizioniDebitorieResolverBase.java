package it.gruppoinit.pal.gp.core.features.bollettazione.nodopagamenti;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.AnagraficaBollettazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.RigaBollettazione;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.ImportoBean;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.RataBean;
import it.gruppoinit.pal.gp.core.service.BollGestDettaglioService;

public class PosizioniDebitorieResolverBase {

    private Logger logger = LoggerFactory.getLogger(PosizioniDebitorieResolverBase.class);

    protected void verificaConfigurazioniCausali(List<AnagraficaBollettazione> anagraficaBollettazioneList,
	    BollGestDettaglioService bollGestDettaglioService) throws InvalidConfigurationException {

	Set<Integer> s = new HashSet<Integer>();
	for (AnagraficaBollettazione ab : anagraficaBollettazioneList) {
	    List<RigaBollettazione> righeBollettazioneList = ab.getRigheBollettazioneList();
	    for (RigaBollettazione r : righeBollettazioneList) {
		s.add(r.getId());
	    }
	}
	bollGestDettaglioService.verificaConfigurazioniCausali(s);
    }

    protected String getComunePerDettaglio(Integer id, Map<Integer, Set<String>> comuniPerDettagliBollettazione, boolean isComuniAssociati,
	    String defaultCodiceComune) {

	if (!isComuniAssociati) {
	    if (StringUtils.isBlank(defaultCodiceComune)) {
		throw new InvalidConfigurationException(
			"Non è stato possibile possibile recuperare il codiceComune dell'installazione comuni associati defaultCodiceComune ==> nullo");
	    }
	    return defaultCodiceComune;
	}
	Set<String> comuni = comuniPerDettagliBollettazione.get(id);
	if (comuni.isEmpty()) {
	    return null;
	}
	if (comuni.size() > 1) {
	    throw new InvalidConfigurationException(
		    "Ops! sembra che il tuo configuratore non abbia fatto il suo lavoro oppure un bel BUG da schiacciare. Allontanarsi immediatamente dalla postazione...");
	}
	return comuni.iterator().next();
    }

    protected void verificaImportiRate(BigDecimal importoTotale, BigDecimal importoRateizzato, List<RataBean> rate) {

	logger.debug(" verificaImportiRate importoTotale:{}==>importoRateizzato:{}", importoTotale, importoRateizzato);
	if (importoTotale.compareTo(importoRateizzato) == 0) {
	    return;
	}
	if (importoTotale.compareTo(importoRateizzato) < 0) {
	    logger.debug(" verificaImportiRate: devo togliere dall'ultima rata la differenza");
	    // devo togliere dall'ultima rata la differenza
	    BigDecimal diff = importoTotale.subtract(importoRateizzato).setScale(2, RoundingMode.HALF_UP);
	    RataBean r = rate.get(rate.size() - 1);
	    List<ImportoBean> importi = r.getImporti();
	    for (ImportoBean i : importi) {
		if (i.getImporto().compareTo(diff) > 0) {
		    //tolgo
		    i.addImporto(diff);
		    return;
		}
	    }
	} else if (importoTotale.compareTo(importoRateizzato) > 0) {
	    // devo aggiungere all'ultima rata la differenza
	    logger.debug(" verificaImportiRate: devo aggiungere all'ultima rata la differenza");
	    BigDecimal diff = importoRateizzato.subtract(importoTotale).setScale(2, RoundingMode.HALF_UP);
	    RataBean r = rate.get(rate.size() - 1);
	    List<ImportoBean> importi = r.getImporti();
	    for (ImportoBean i : importi) {
		if (i.getImporto().compareTo(diff) > 0) {
		    //tolgo
		    i.addImporto(diff);
		    return;
		}
	    }
	}
    }
}
