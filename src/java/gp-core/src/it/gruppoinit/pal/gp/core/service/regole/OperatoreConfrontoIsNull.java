package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreConfrontoEnum;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

public class OperatoreConfrontoIsNull extends AbstractOperatoreConfronto {

    public OperatoreConfrontoIsNull() {

	this.tipoOperatore = OperatoreConfrontoEnum.IS_NULL;
    }

    /**
     * Le stringhe vuote vengono considerate valori nulli.
     */
    @Override
    protected Boolean confrontaStringhe(String value, String compareTo) {

	return StringUtils.isBlank(value);
    }

    @Override
    protected Boolean confrontaDate(Date value, String compareTo) {

	return Boolean.FALSE;
    }

    @Override
    protected Boolean confrontaNumeri(BigDecimal value, String compareTo) {

	return Boolean.FALSE;
    }

    /**
     * I valori di tipo {@link Collection} vengono considerati nulli quando nella lista ci sono zero elementi.
     */
    @Override
    protected Boolean confrontaElenco(Collection<Object> value, String compareTo) {

	Boolean retVal = value.isEmpty();
	if (!retVal) {
	    Boolean allNulls = Boolean.TRUE;
	    for (Object ithValue : value) {
		try {
		    retVal = this.confronta(ithValue, null);
		} catch (Dyn2RegoleSyntaxError e) {
		    //confronto con tipo di djato non valido/supportato
		    retVal = ithValue == null;
		}
		if (BooleanUtils.isFalse(retVal)) {
		    allNulls = Boolean.FALSE;
		    break;
		}
	    }
	}
	return retVal;
    }

    @Override
    protected Boolean confrontaNull(String compareTo) {

	return Boolean.TRUE;
    }

    @Override
    protected Boolean confrontaBoolean(Boolean value, Boolean compareTo) throws Dyn2RegoleSyntaxError {

	return value == null;
    }
}
