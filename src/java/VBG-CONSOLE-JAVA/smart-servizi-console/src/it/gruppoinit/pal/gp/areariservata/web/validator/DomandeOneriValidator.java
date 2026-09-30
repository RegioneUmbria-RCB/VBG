package it.gruppoinit.pal.gp.areariservata.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DomandeOneriValidator implements Validator {

    @Override
    public boolean supports(Class arg0) {

	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public void validate(Object arg0, Errors arg1) {

	// TODO Auto-generated method stub
    }
    //    private FoArjDomandeOneriService foArjDomandeOneriService;
    //
    //    private DomandeOneriValidator() {
    //
    //	super();
    //    }
    //
    //    public DomandeOneriValidator(FoArjDomandeOneriService foArjDomandeOneriService) {
    //
    //	this();
    //	this.foArjDomandeOneriService = foArjDomandeOneriService;
    //    }
    //
    //    @Override
    //    public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
    //
    //	return DomandaOneriHelper.class.equals(clazz);
    //    }
    //
    //    @Override
    //    public void validate(Object obj, Errors errs) {
    //
    //	DomandaOneriHelper helper = (DomandaOneriHelper) obj;
    //	List<FoArjDomandeOneri> oneriIntervento = helper.getOneriIntervento();
    //	for (FoArjDomandeOneri copy : oneriIntervento) {
    //	    FoArjDomandeOneri dbobj = foArjDomandeOneriService.findById(new PkId(copy.getId().getCodice()));
    //	    if (!isPagato(dbobj)) {
    //		errs.rejectValue("domandaOneriHelper", "error.pagamenti-non-effettuati");
    //		return;
    //	    }
    //	}
    //	List<FoArjDomandeOneri> oneriProcedimenti = helper.getOneriProcedimenti();
    //	for (FoArjDomandeOneri copy : oneriProcedimenti) {
    //	    FoArjDomandeOneri dbobj = foArjDomandeOneriService.findById(new PkId(copy.getId().getCodice()));
    //	    if (!isPagato(dbobj)) {
    //		errs.rejectValue("domandaOneriHelper", "error.pagamenti-non-effettuati");
    //		return;
    //	    }
    //	}
    //    }
    //
    //    private boolean isPagato(FoArjDomandeOneri dbobj) {
    //
    //	if (BooleanUtils.isTrue(dbobj.getFlagStato())) {
    //	    return true;
    //	}
    //	return false;
    //    }
}
