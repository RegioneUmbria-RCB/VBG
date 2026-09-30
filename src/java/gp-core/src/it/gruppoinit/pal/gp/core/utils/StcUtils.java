package it.gruppoinit.pal.gp.core.utils;

import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoPraticaNLARequest;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ValoreParametroType;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StcUtils {

    private static final Logger log = LoggerFactory.getLogger(StcUtils.class);

    /**
     * Ritorna l'oggetto ValoreParametroType della sezione altri dati per il valore "nome" passato dell'oggetto
     * ParametroType della sezione altri dati. Se non esiste tale valore ritorna un oggetto null
     * 
     * @param praticaNla
     * @param nomeParametroType
     * @return null se non trovato
     */
    public static ValoreParametroType getCampoDaAltriDati(InserimentoPraticaNLARequest praticaNla, String nomeParametroType) {

	return getCampoDaAltriDati(praticaNla.getDettaglioPratica().getAltriDati(), nomeParametroType);
    }

    /**
     * Ritorna l'oggetto ValoreParametroType della sezione altri dati per il valore "nome" passato dell'oggetto
     * ParametroType della sezione altri dati. Se non esiste tale valore ritorna un oggetto null
     * 
     * @param praticaNla
     * @param nomeParametroType
     * @return null se non trovato
     */
    public static ValoreParametroType getCampoDaAltriDati(InserimentoAttivitaNLARequest praticaNla, String nomeParametroType) {

	return getCampoDaAltriDati(praticaNla.getDatiAttivita().getAltriDati(), nomeParametroType);
    }

    /**
     * Ritorna l'oggetto ValoreParametroType della sezione altri dati per il valore "nome" passato dell'oggetto
     * ParametroType della sezione altri dati. Se non esiste tale valore ritorna un oggetto null
     * 
     * @param praticaNla
     * @param nomeParametroType
     * @return null se non trovato
     */
    public static ValoreParametroType getCampoDaAltriDati(List<ParametroType> altridati, String nomeParametroType) {

	if (altridati != null) {
	    if (altridati.size() > 0) {
		ValoreParametroType valoreParametroType = null;
		for (ParametroType parametroType : altridati) {
		    if (parametroType != null && StringUtils.isNotBlank(parametroType.getNome()) && parametroType.getNome().equals(nomeParametroType)) {
			log.debug("Trovato campo di Altri dati con ParametroType.nome {}", nomeParametroType);
			List<ValoreParametroType> valoreParametroTypes = parametroType.getValore();
			if (valoreParametroTypes != null && !valoreParametroTypes.isEmpty()) {
			    log.debug("Trovato campo di Altri dati con ParametroType.nome con valoreParametroType non vuoto");
			    valoreParametroType = new ValoreParametroType();
			    valoreParametroType = valoreParametroTypes.get(0);
			    log.debug("Valore trovato, fine iterazione lista");
			    break;
			}
		    }
		}
		return valoreParametroType;
	    }
	}
	return null;
    }

    public static List<ValoreParametroType> getCampiDaAltriDati(List<ParametroType> altridati, String nomeParametroType) {

	if (altridati != null) {
	    if (altridati.size() > 0) {
		for (ParametroType parametroType : altridati) {
		    if (parametroType != null && StringUtils.isNotBlank(parametroType.getNome()) && parametroType.getNome().equals(nomeParametroType)) {
			log.debug("Trovato campo di Altri dati con ParametroType.nome {}", nomeParametroType);
			List<ValoreParametroType> valoreParametroTypes = parametroType.getValore();
			if (valoreParametroTypes != null && !valoreParametroTypes.isEmpty()) {
			    return valoreParametroTypes;
			}
		    }
		}
	    }
	}
	return null;
    }
}
