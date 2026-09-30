package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.service.regole.OperatoreConfrontoMatches;
import it.gruppoinit.pal.gp.core.service.regole.AbstractOperatoreConfronto;
import it.gruppoinit.pal.gp.core.service.regole.OperatoreConfrontoMatches;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreConfrontoEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperatoreConfrontoMatches extends AbstractOperatoreConfronto {

    private static final Logger log = LoggerFactory.getLogger(OperatoreConfrontoMatches.class);

    public OperatoreConfrontoMatches() {

	this.tipoOperatore = OperatoreConfrontoEnum.MATCHES;
    }

    @Override
    protected Boolean confrontaStringhe(String value, String compareTo) throws Dyn2RegoleSyntaxError {

	Boolean retVal = Boolean.FALSE;
	if (StringUtils.isNotBlank(compareTo)) {
	    try {
		Pattern p = Pattern.compile(compareTo);
		retVal = p.matcher(value).matches();
	    } catch (Exception e) {
		log.error("confrontaStringhe - confronto con espressione regolare non valida {}", new Object[] { compareTo });
		Dyn2RegoleSyntaxError err = new Dyn2RegoleSyntaxError("dyn2regole.error.invalid_valoreconfronto_regex",e);
		err.addWrongExpressionProperty("operatoreConfronto");
	    }
	}
	return retVal;
    }

    /**
     * Le date vengono trasformate nella loro rappresentazione stringa prima di essere confrontate con l'espressione
     * regolare.
     */
    @Override
    protected Boolean confrontaDate(Date value, String compareTo) throws Dyn2RegoleSyntaxError{

	String dateString = Utilities.formatDate(value, false);
	return confrontaStringhe(dateString, compareTo);
    }

    /**
     * I numeri vengono trasformati nella loro rappresentazione stringa prima di essere confrontati con l'espressione
     * regolare.
     */
    @Override
    protected Boolean confrontaNumeri(BigDecimal value, String compareTo) throws Dyn2RegoleSyntaxError{

	String stringNum = Utilities.formatImporto(value, 0, 0, false);
	return confrontaStringhe(stringNum, compareTo);
    }

    /**
     * nel caso di valori di tipo {@link Collection} viene restituito true se almeno uno degli elementi della lista
     * combacia con l'espressione regolare.
     */
    @Override
    protected Boolean confrontaElenco(Collection<Object> value, String compareTo) throws Dyn2RegoleSyntaxError{

	Boolean retVal = Boolean.FALSE;
	for (Object innerVal : value) {
	    Boolean matches = confronta(innerVal, compareTo);
	    if (BooleanUtils.isTrue(matches)) {
		retVal = Boolean.TRUE;
		break;
	    }
	}
	return retVal;
    }

    @Override
    protected Boolean confrontaNull(String compareTo) {

	return Boolean.FALSE;
    }
}
