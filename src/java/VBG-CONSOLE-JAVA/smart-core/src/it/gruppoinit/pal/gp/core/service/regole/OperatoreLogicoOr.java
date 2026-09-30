package it.gruppoinit.pal.gp.core.service.regole;

import it.gruppoinit.pal.gp.core.domain.helper.Dyn2RegoleSyntaxError;
import it.gruppoinit.pal.gp.core.service.Dyn2RegoleService.OperatoreLogicoEnum;

public class OperatoreLogicoOr extends AbstractOperatoreLogico {

    public OperatoreLogicoOr() {

	this.tipoOperatore = OperatoreLogicoEnum.OR;
    }

    @Override
    public Boolean eseguiOperazioneLogica(EspressioneBooleana expr1, EspressioneBooleana expr2) throws Dyn2RegoleSyntaxError {

	checkNull(expr1);
	checkNull(expr2);
	return expr1.valutaBoolean() || expr2.valutaBoolean();
    }
}
