package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class RipartizioneContiHelper {

    private static final String PARAMETRO_DI_RIPARTIZIONE_NON_VALIDO = "Parametro di ripartizione non valido [";
    private static final String CONTI_SEPARATOR = ";";
    private static final String CONTI_PERCENTUALE_SEPARATOR = "\\|";
    private List<RipartizioneContiRicaricheModel> listaConti;

    public static RipartizioneContiHelper fromParametro(String parametro, ContiService contiService) throws InvalidConfigurationException {

	RipartizioneContiHelper ret = new RipartizioneContiHelper();
	ret.validaParametro(parametro, contiService);
	return ret;
    }

    protected RipartizioneContiHelper() {

	super();
    }

    public List<RipartizioneContiRicaricheModel> getListaConti() {

	if (listaConti == null) {
	    listaConti = new ArrayList<RipartizioneContiRicaricheModel>();
	}
	return listaConti;
    }

    private void validaParametro(String parametro, ContiService contiService) throws InvalidConfigurationException {

	if (StringUtils.isBlank(parametro)) {
	    throw new InvalidConfigurationException(PARAMETRO_DI_RIPARTIZIONE_NON_VALIDO + parametro + "]");
	}
	String[] vals = StringUtils.defaultString(parametro).trim().split(CONTI_SEPARATOR);
	Set<Integer> contiInseriti = new HashSet<Integer>();
	for (String val : vals) {
	    String[] v = val.trim().split(CONTI_PERCENTUALE_SEPARATOR);
	    if (v == null || v.length == 0) {
		throw new InvalidConfigurationException(PARAMETRO_DI_RIPARTIZIONE_NON_VALIDO + parametro + "]==>[" + val + "]");
	    }
	    if (!Utilities.isInteger(v[0].trim()) || !Utilities.isInteger(v[1].trim())) {
		throw new InvalidConfigurationException(
			PARAMETRO_DI_RIPARTIZIONE_NON_VALIDO + parametro + "]==>Non è un valore Intero[" + v[0].trim() + "],[" + v[0].trim() + "]");
	    }
	    Integer codiceConto = Integer.parseInt(v[0].trim());
	    if (contiInseriti.contains(codiceConto)) {
		throw new InvalidConfigurationException(PARAMETRO_DI_RIPARTIZIONE_NON_VALIDO + parametro + "]==>Il conto con codice [" + codiceConto +
							"] è stato specificato più volte [" + parametro + "]");
	    }
	    contiInseriti.add(codiceConto);
	    Integer percentualeVal = Integer.parseInt(v[1].trim());
	    Conti conto = contiService.findById(new PkId(codiceConto));
	    if (conto == null) {
		throw new InvalidConfigurationException(
			PARAMETRO_DI_RIPARTIZIONE_NON_VALIDO + parametro + "]==>Il conto con codice [" + codiceConto + "] non è stato trovato");
	    }
	    BigDecimal percentuale = BigDecimal.valueOf(percentualeVal, 0);
	    getListaConti().add(RipartizioneContiRicaricheModel.fromParameters(conto, percentuale));
	}
    }
}
