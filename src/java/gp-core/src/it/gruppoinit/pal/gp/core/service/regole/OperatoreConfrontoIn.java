package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreConfrontoEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public class OperatoreConfrontoIn extends AbstractOperatoreConfronto {

    public OperatoreConfrontoIn() {

	this.tipoOperatore = OperatoreConfrontoEnum.IN;
    }

    @Override
    protected Boolean confrontaStringhe(String value, String compareTo) throws Dyn2RegoleSyntaxError {

	Collection<String> compValues = getValoreElenco(compareTo);
	Boolean retVal = Boolean.FALSE;
	for (String compVal : compValues) {
	    if (value.equalsIgnoreCase(compVal)) {
		retVal = Boolean.TRUE;
		break;
	    }
	}
	return retVal;
    }

    @Override
    protected Boolean confrontaDate(Date value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean retVal = Boolean.FALSE;
	Collection<String> compValues = getValoreElenco(compareTo);
	for (String compVal : compValues) {
	    Date compDate = getValoreData(compVal);
	    if (Utilities.compareDates(value, compDate) == 0) {
		retVal = Boolean.TRUE;
		break;
	    }
	}
	return retVal;
    }

    @Override
    protected Boolean confrontaNumeri(BigDecimal value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean retVal = Boolean.FALSE;
	Collection<String> compValues = getValoreElenco(compareTo);
	for (String compVal : compValues) {
	    BigDecimal compNum = getValoreNumero(compVal);
	    if (value.compareTo(compNum) == 0) {
		retVal = Boolean.TRUE;
		break;
	    }
	}
	return retVal;
    }

    /**
     * nel caso di valori di tipo {@link Collection} viene restituito true se almeno uno degli elementi della lista è
     * uguale ad almeno uno dei valori di confronto passati.
     * 
     * @throws Dyn2RegoleSyntaxError
     */
    @Override
    protected Boolean confrontaElenco(Collection<Object> value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean retVal = Boolean.FALSE;
	Collection<String> compValues = getValoreElenco(compareTo);
	for (String compVal : compValues) {
	    if (!retVal) {
		for (Object val : value) {
		    if (confronta(val, compVal)) {
			retVal = Boolean.TRUE;
		    }
		}
	    }
	}
	return retVal;
    }

    @Override
    protected Boolean confrontaNull(String compareTo) {

	return Boolean.FALSE;
    }

    @Override
    protected Boolean confrontaBoolean(Boolean value, Boolean compareTo) throws Dyn2RegoleSyntaxError {

	throw new UnsupportedOperationException("Il metodo confrontaBoolean non è supportato per la classe " + getClass().getName());
    }
}
