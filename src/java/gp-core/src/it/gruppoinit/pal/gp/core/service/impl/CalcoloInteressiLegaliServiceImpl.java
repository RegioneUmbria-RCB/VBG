/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.domain.CalcoloInteressiLegali;
import it.gruppoinit.pal.gp.core.service.CalcoloInteressiLegaliService;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

/**
 * @author francescop
 * 
 */
@Service
public class CalcoloInteressiLegaliServiceImpl implements CalcoloInteressiLegaliService {

    protected BindingResult result;

    @Override
    public BindingResult getBindingResult() {

	return this.result;
    }

    @Override
    public void setBindingResult(BindingResult result) {

	this.result = result;
    }

    @Override
    public void validate(CalcoloInteressiLegali calcoloInteressiLegali) {

	if (calcoloInteressiLegali.getImporto() == null) {
	    result.rejectValue("importo", "validator.nonvuoto");
	}
	if (calcoloInteressiLegali.getDataInizio() == null || calcoloInteressiLegali.getDataFine() == null) {
	    result.rejectValue("dataInizio", "validator.interessilegali.datainiziofine.nonvuoto");
	}
	if (calcoloInteressiLegali.getDataInizio() != null && calcoloInteressiLegali.getDataFine() != null) {
	    if (calcoloInteressiLegali.getDataInizio().compareTo(calcoloInteressiLegali.getDataFine()) > 0) {
		result.rejectValue("dataInizio", "errors.date.sequenza");
	    }
	}
    }
}
