package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreConfrontoEnum;
import it.gruppoinit.pal.gp.core.service.regole.AbstractOperatoreConfronto;
import it.gruppoinit.pal.gp.core.service.regole.OperatoreConfronto;
import it.gruppoinit.pal.gp.core.service.regole.AbstractOperatoreConfronto;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractOperatoreConfronto implements OperatoreConfronto {

    private static final Logger log = LoggerFactory.getLogger(AbstractOperatoreConfronto.class);
    protected OperatoreConfrontoEnum tipoOperatore;

    public OperatoreConfrontoEnum getTipoOperatore() {

	return tipoOperatore;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean confronta(Object value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean retVal = null;
	if (value != null) {
	    if (value instanceof String) {
		retVal = confrontaStringhe((String) value, compareTo);
	    } else if (value instanceof Date) {
		retVal = confrontaDate((Date) value, compareTo);
	    } else if (value instanceof XMLGregorianCalendar) {
		Date dateVal = Utilities.createDate((XMLGregorianCalendar) value);//createDate mi esclude i valori dei campi HH MM SS e millisec
		retVal = confrontaDate(dateVal, compareTo);
	    } else if (value instanceof Number) {
		retVal = confrontaNumeri((BigDecimal) value, compareTo);
	    } else if (value instanceof Collection) {
		retVal = confrontaElenco((Collection<Object>) value, compareTo);
	    } else if (value instanceof Boolean) {
		retVal = confrontaBoolean((Boolean) value, BooleanUtils.toBoolean(compareTo));
	    } else {
		log.error("confronta - Tipo di dato {} non supportato dagli operatori di confronto.", new Object[] { value.getClass() });
		//retVal = confrontaOggetto(value, compareTo);
	    }
	} else {
	    retVal = confrontaNull(compareTo);
	}
	return retVal;
    }

    public Date getValoreData(String valore) throws Dyn2RegoleSyntaxError {

	Date retDate = null;
	try {
	    if (StringUtils.isNotBlank(valore)) {
		if (valore.equalsIgnoreCase(VALORE_CONFRONTO_TODAY)) {
		    retDate = new Date();
		} else {
		    retDate = Utilities.parseDateString(valore.trim(), false);
		}
	    }
	} catch (Exception e) {
	    Dyn2RegoleSyntaxError err = new Dyn2RegoleSyntaxError("dyn2regole.error.invalid_valoreconfronto_data", e);
	    err.addWrongExpressionProperty("valoreConfronto");
	    throw err;
	}
	return retDate;
    }

    public BigDecimal getValoreNumero(String valore) throws Dyn2RegoleSyntaxError {

	BigDecimal retVal = null;
	try {
	    if (StringUtils.isNotBlank(valore)) {
		valore = valore.replaceAll(",", ".");
		retVal = new BigDecimal(valore.trim());
	    }
	} catch (Exception e) {
	    Dyn2RegoleSyntaxError err = new Dyn2RegoleSyntaxError("dyn2regole.error.invalid_valoreconfronto_numero", e);
	    err.addWrongExpressionProperty("valoreConfronto");
	    throw err;
	}
	return retVal;
    }

    protected Collection<String> getValoreElenco(String valore) throws Dyn2RegoleSyntaxError {

	List<String> retVals = new ArrayList<String>();
	try {
	    if (StringUtils.isNotBlank(valore)) {
		String[] vals = valore.trim().split("\\|");
		retVals = Arrays.asList(vals);
	    }
	} catch (Exception e) {
	    Dyn2RegoleSyntaxError err = new Dyn2RegoleSyntaxError("dyn2regole.error.invalid_valoreconfronto_lista", e);
	    err.addWrongExpressionProperty("valoreConfronto");
	    throw err;
	}
	return retVals;
    }

    protected abstract Boolean confrontaStringhe(String value, String compareTo) throws Dyn2RegoleSyntaxError;

    protected abstract Boolean confrontaDate(Date value, String compareTo) throws Dyn2RegoleSyntaxError;

    protected abstract Boolean confrontaNumeri(BigDecimal value, String compareTo) throws Dyn2RegoleSyntaxError;

    protected abstract Boolean confrontaElenco(Collection<Object> value, String compareTo) throws Dyn2RegoleSyntaxError;

    protected abstract Boolean confrontaNull(String compareTo) throws Dyn2RegoleSyntaxError;

    protected abstract Boolean confrontaBoolean(Boolean value, Boolean compareTo) throws Dyn2RegoleSyntaxError;
    /*
    protected Boolean confrontaOggetto(Object value, String compareTo) {

    return null;
    }
    */
}
