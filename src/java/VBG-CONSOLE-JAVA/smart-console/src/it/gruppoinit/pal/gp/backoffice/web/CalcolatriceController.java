/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.CalcoloInteressiLegali;
import it.gruppoinit.pal.gp.core.domain.helper.InteressiLegaliHelper;
import it.gruppoinit.pal.gp.core.service.CalcoloInteressiLegaliService;
import it.gruppoinit.pal.gp.core.service.InteressiLegaliService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * @author francescop
 * 
 */
//DAELIMINARE @Controller
@SessionAttributes(value = { "calcolatrice" })
public class CalcolatriceController extends BaseController<CalcoloInteressiLegali> {

    @Autowired
    private InteressiLegaliService interessiLegaliService;
    @Autowired
    private CalcoloInteressiLegaliService calcoloInteressiLegaliService;

    @RequestMapping
    public String create(Model model) {

	CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
	model.addAttribute("calcolatrice", calcoloInteressiLegali);
	return "calcolatrice/calcolatrice";
    }

    @RequestMapping
    public String calculate(@ModelAttribute("calcolatrice") CalcoloInteressiLegali calcoloInteressiLegali, BindingResult result,
	    SessionStatus status, Model model, HttpServletRequest request, HttpServletResponse response) {

	calcoloInteressiLegaliService.setBindingResult(result);
	calcoloInteressiLegaliService.validate(calcoloInteressiLegali);
	if (!result.hasErrors()) {
	    InteressiLegaliHelper interessiLegaliHelper = new InteressiLegaliHelper(this.interessiLegaliService);
	    List<CalcoloInteressiLegali> list = interessiLegaliHelper.getInteressiLegali(calcoloInteressiLegali.getImporto(),
		    calcoloInteressiLegali.getDataFine(), calcoloInteressiLegali.getDataInizio());
	    model.addAttribute("calcoloInteressiLegaliList", list);
	    model.addAttribute("risultato", true);
	} else {
	    model.addAttribute("calcolatrice", calcoloInteressiLegali);
	}
	return "calcolatrice/calcolatrice";
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(CalcoloInteressiLegali entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(CalcoloInteressiLegali entity) {

	// TODO Auto-generated method stub
    }
}
