package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreConfrontoEnum;

public class OperazioneConfronto implements EspressioneBooleana {

    private Object valore;
    private String valoreConfronto;
    private OperatoreConfronto operatore;

    public OperazioneConfronto(Object valore, OperatoreConfrontoEnum operType, String valoreConfronto) {

	this.valore = valore;
	this.valoreConfronto = valoreConfronto;
	this.operatore = operatoreFromEnum(operType);
    }

    public Boolean eseguiConfronto() throws Dyn2RegoleSyntaxError {

	return operatore.confronta(valore, valoreConfronto);
    }

    @Override
    public Boolean valutaBoolean() throws Dyn2RegoleSyntaxError {

	return eseguiConfronto();
    }

    private static OperatoreConfronto operatoreFromEnum(OperatoreConfrontoEnum operType) {

	OperatoreConfronto operatore = null;
	switch (operType) {
	case EQ:
	case NOT_EQ:
	    operatore = new OperatoreConfrontoEquals();
	    break;
	case LT:
	case GT_EQ:
	    operatore = new OperatoreConfrontoLessThen();
	    break;
	case GT:
	case LT_EQ:
	    operatore = new OperatoreConfrontoGreaterThen();
	    break;
	case IS_NULL:
	case NOT_IS_NULL:
	    operatore = new OperatoreConfrontoIsNull();
	    break;
	case IN:
	case NOT_IN:
	    operatore = new OperatoreConfrontoIn();
	    break;
	case MATCHES:
	case NOT_MATCHES:
	    operatore = new OperatoreConfrontoMatches();
	    break;
	default:
	    break;
	}
	return operatore;
    }
}
