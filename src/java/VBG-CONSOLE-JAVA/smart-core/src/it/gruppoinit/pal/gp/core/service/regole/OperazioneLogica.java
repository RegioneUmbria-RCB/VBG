package it.gruppoinit.pal.gp.core.service.regole;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreLogicoEnum;

public class OperazioneLogica implements EspressioneBooleana {

    private static final Logger log = LoggerFactory.getLogger(OperazioneLogica.class);
    private EspressioneBooleana bool1;
    private EspressioneBooleana bool2;
    private OperatoreLogico operatore;

    public OperazioneLogica(EspressioneBooleana bool1, OperatoreLogicoEnum operType, EspressioneBooleana bool2) throws Dyn2RegoleSyntaxError {

	this.bool1 = bool1;
	this.bool2 = bool2;
	this.operatore = operatoreFromEnum(operType);
    }
    
    public Boolean eseguiOperazioneLogica() throws Dyn2RegoleSyntaxError {
	return this.operatore.eseguiOperazioneLogica(this.bool1, this.bool2);
    }

    @Override
    public Boolean valutaBoolean() throws Dyn2RegoleSyntaxError {

	return eseguiOperazioneLogica();
    }

    public static OperatoreLogico operatoreFromEnum(OperatoreLogicoEnum opEnum) throws Dyn2RegoleSyntaxError {

	OperatoreLogico operatore = null;
	switch (opEnum) {
	case AND:
	    operatore = new OperatoreLogicoAnd();
	    break;
	case OR:
	    operatore = new OperatoreLogicoOr();
	    break;
	case NOT:
	    operatore = new OperatoreLogicoNot();
	    break;
	default:
	    log.error("operatoreFromEnum - operatore logico non supportato {}", new Object[] { opEnum });
	    Dyn2RegoleSyntaxError err = new Dyn2RegoleSyntaxError("dyn2regole.error.invalid_operatorelogico");
	    err.addWrongExpressionProperty("operatoreLogico");
	    throw err;
	}
	return operatore;
    }
}
