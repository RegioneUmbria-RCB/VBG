package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreConfrontoEnum;
import it.gruppoinit.pal.gp.core.service.regole.AbstractOperatoreConfronto;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class OperatoreConfrontoEquals extends AbstractOperatoreConfronto {

    public OperatoreConfrontoEquals() {

	this.tipoOperatore = OperatoreConfrontoEnum.EQ;
    }

    @Override
    protected Boolean confrontaStringhe(String value, String compareTo) throws Dyn2RegoleSyntaxError {

	return value.toString().trim().equalsIgnoreCase(StringUtils.defaultString(compareTo));
    }

    @Override
    protected Boolean confrontaDate(Date value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean equals = Boolean.FALSE;
	if (StringUtils.isNotBlank(compareTo)) {
	    Date compareDate = getValoreData(compareTo);
	    if (null != compareDate) {
		equals = Utilities.compareDates(value, compareDate) == 0;
	    }
	}
	return equals;
    }

    @Override
    protected Boolean confrontaNumeri(BigDecimal value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean equals = Boolean.FALSE;
	BigDecimal compareNum = getValoreNumero(compareTo);
	if (null != compareNum) {
	    value.equals(compareNum);
	}
	return equals;
    }

    @Override
    protected Boolean confrontaElenco(Collection<Object> value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean retVal = Boolean.FALSE;
	if (null != compareTo) {
	    for (Object innerVal : value) {
		if (confronta(innerVal, compareTo)) {
		    retVal = Boolean.TRUE;
		    break;
		}
	    }
	}
	return retVal;
    }

    @Override
    protected Boolean confrontaNull(String compareTo) throws Dyn2RegoleSyntaxError {

	return StringUtils.isEmpty(compareTo);
    }
}
