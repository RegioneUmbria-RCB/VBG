package it.gruppoinit.pal.gp.core.features.comunicazioni.massive;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;

public class SceltaTipoMailAnagrafeResolver {

    private SceltaTipoMailAnagrafeEnum config;

    public SceltaTipoMailAnagrafeResolver(SceltaTipoMailAnagrafeEnum config) {

	this.config = config;
    }

    public String getIndirizzo(String pec, String email) {

	switch (this.config) {
	case SOLO_MAIL:
	    return email;
	case SOLO_PEC:
	    return pec;
	case PEC_O_MAIL:
	    return StringUtils.isNotBlank(pec) ? pec : email;
	default:
	    throw new NotImplementedException("Non è stata implementata la possibilità di scelta mail di tipo " + this.config.name());
	}
    }
}
