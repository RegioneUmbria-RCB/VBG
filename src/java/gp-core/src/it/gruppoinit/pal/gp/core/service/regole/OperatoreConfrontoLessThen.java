package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreConfrontoEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatoreConfrontoLessThen extends AbstractOperatoreConfronto {

    private static final Logger log = LoggerFactory.getLogger(OperatoreConfrontoLessThen.class);

    public OperatoreConfrontoLessThen() {

	this.tipoOperatore = OperatoreConfrontoEnum.LT;
    }

    @Override
    protected Boolean confrontaStringhe(String value, String compareTo) throws Dyn2RegoleSyntaxError {

	log.error("confrontaStringhe - l'operatore {} non può essere applicato a proprietà di tipo stringa.", new Object[] { this.tipoOperatore });
	Dyn2RegoleSyntaxError err = new Dyn2RegoleSyntaxError("dyn2regole.error.invalid_operatoreconfronto");
	err.addWrongExpressionProperty("operatoreConfronto");
	throw err;
    }

    @Override
    protected Boolean confrontaDate(Date value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean equals = Boolean.FALSE;
	if (StringUtils.isNotBlank(compareTo)) {
	    Date compareDate = getValoreData(compareTo);
	    if (null != compareDate) {
		equals = Utilities.compareDates(value, compareDate) < 0;
	    }
	}
	return equals;
    }

    @Override
    protected Boolean confrontaNumeri(BigDecimal value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean equals = Boolean.FALSE;
	BigDecimal compareNum = getValoreNumero(compareTo);
	if (null != compareNum) {
	    equals = value.compareTo(compareNum) < 0;
	}
	return equals;
    }

    /**
     * Gli operatori GT, LT, GT_EQ e LT_EQ nel caso di valori di tipo {@link Collection} effettuano confronti sulle
     * dimansioni della collection. Per esempio se per un certo attributo di tipo lista esiste l'espressione
     * [operatoreConfronto=GT valoreConfronto=1] L'espressione è verificata (restituisce true) se la lista ha una
     * lunghezza maggiore di 1. Il valore di confronto deve poter essere convertito ad un numero intero.
     */
    @Override
    protected Boolean confrontaElenco(Collection<Object> value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean retVal = Boolean.FALSE;
	if (StringUtils.isNotBlank(compareTo)) {
	    compareTo = compareTo.trim();
	    Integer intCompare = getValoreNumero(compareTo).intValue();
	    retVal = value.size() < intCompare;
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
