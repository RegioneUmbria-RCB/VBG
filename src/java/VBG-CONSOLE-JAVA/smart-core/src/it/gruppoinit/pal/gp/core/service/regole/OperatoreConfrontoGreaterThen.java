package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreConfrontoEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatoreConfrontoGreaterThen extends AbstractOperatoreConfronto {

    private static final Logger log = LoggerFactory.getLogger(OperatoreConfrontoGreaterThen.class);

    public OperatoreConfrontoGreaterThen() {

	this.tipoOperatore = OperatoreConfrontoEnum.GT;
    }

    @Override
    protected Boolean confrontaStringhe(String value, String compareTo) throws Dyn2RegoleSyntaxError {

	log.error("confrontaStringhe - l'operatore {} non può essere applicato a proprietà di tipo stringa.", new Object[] { this.tipoOperatore });
	Dyn2RegoleSyntaxError err = new Dyn2RegoleSyntaxError("dyn2regole.error.invalid_operatoreconfronto");
	err.addWrongExpressionProperty("operatoreConfronto");
	throw err;
	//return null;
    }

    @Override
    protected Boolean confrontaDate(Date value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean equals = Boolean.FALSE;
	if (StringUtils.isNotBlank(compareTo)) {
	    Date compareDate = getValoreData(compareTo);
	    if (null != compareDate) {
		equals = Utilities.compareDates(value, compareDate) > 0;
	    }
	}
	return equals;
    }

    @Override
    protected Boolean confrontaNumeri(BigDecimal value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean equals = Boolean.FALSE;
	BigDecimal compareNum = getValoreNumero(compareTo);
	if (null != compareNum) {
	    equals = value.compareTo(compareNum) > 0;
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
	    List<Object> listValue = new ArrayList<Object>(value);
	    for (int i = 0; i < listValue.size() && !retVal.booleanValue(); i++) {
		Object idxVal = listValue.get(i);
		retVal = confronta(idxVal, compareTo);
		//
	    }
	}
	return retVal;
    }

    @Override
    protected Boolean confrontaNull(String compareTo) {

	return Boolean.FALSE;
    }
}
