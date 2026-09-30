package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.gruppoinit.pal.gp.areariservata.domain.ProcedimentoHelper;
import it.gruppoinit.pal.gp.core.domain.InventarioprocTipititolo;
import it.init.sigepro.rte.types.EstremiAttoType;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TipiTitoloValidator implements Validator {

    private InventarioprocTipititolo tipoTitolo;

    public TipiTitoloValidator(InventarioprocTipititolo tipoTitolo) {

	this.tipoTitolo = tipoTitolo;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean supports(Class clazz) {

	return ProcedimentoHelper.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors e) {

	ProcedimentoHelper ph = (ProcedimentoHelper) obj;
	EstremiAttoType eat = ph.getProcedimento().getEstremiAtto();
	if (BooleanUtils.isTrue(tipoTitolo.getFlgMostraNumero())) {
	    if (StringUtils.isBlank(eat.getRiferimento())) {
		e.rejectValue("procedimentiSelezionati[" + ph.getIdx() + "].procedimento.estremiAtto.riferimento", "error.dato-obbligatorio");
	    }
	}
	if (BooleanUtils.isTrue(tipoTitolo.getFlgMostraData())) {
	    if (eat.getData() == null) {
		e.rejectValue("procedimentiSelezionati[" + ph.getIdx() + "].procedimento.estremiAtto.data", "error.dato-obbligatorio");
	    }
	}
	if (BooleanUtils.isTrue(tipoTitolo.getFlgMostraRilasciatoDa())) {
	    if (StringUtils.isBlank(eat.getRilasciatoDa())) {
		e.rejectValue("procedimentiSelezionati[" + ph.getIdx() + "].procedimento.estremiAtto.rilasciatoDa", "error.dato-obbligatorio");
	    }
	}
    }
}
