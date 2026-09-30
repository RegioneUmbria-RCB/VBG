package it.gruppoinit.pal.gp.areariservata.web.validator;

import it.gruppoinit.pal.gp.areariservata.domain.DomandaOneriHelper;
import it.gruppoinit.pal.gp.core.domain.FoArjDomandeOneri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FoArjDomandeOneriService;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DomandeOneriValidator implements Validator {

    private FoArjDomandeOneriService foArjDomandeOneriService;

    private DomandeOneriValidator() {

	super();
    }

    public DomandeOneriValidator(FoArjDomandeOneriService foArjDomandeOneriService) {

	this();
	this.foArjDomandeOneriService = foArjDomandeOneriService;
    }

    @Override
    public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {

	return DomandaOneriHelper.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errs) {

	DomandaOneriHelper helper = (DomandaOneriHelper) obj;
	List<FoArjDomandeOneri> oneriIntervento = helper.getOneriIntervento();
	for (FoArjDomandeOneri copy : oneriIntervento) {
	    FoArjDomandeOneri dbobj = foArjDomandeOneriService.findById(new PkId(copy.getId().getCodice()));
	    if (!isPagato(dbobj)) {
		errs.rejectValue("domandaOneriHelper", "error.pagamenti-non-effettuati");
		return;
	    }
	}
	List<FoArjDomandeOneri> oneriProcedimenti = helper.getOneriProcedimenti();
	for (FoArjDomandeOneri copy : oneriProcedimenti) {
	    FoArjDomandeOneri dbobj = foArjDomandeOneriService.findById(new PkId(copy.getId().getCodice()));
	    if (!isPagato(dbobj)) {
		errs.rejectValue("domandaOneriHelper", "error.pagamenti-non-effettuati");
		return;
	    }
	}
    }

    private boolean isPagato(FoArjDomandeOneri dbobj) {

	if (BooleanUtils.isTrue(dbobj.getFlagStato())) {
	    return true;
	}
	return false;
    }
}
