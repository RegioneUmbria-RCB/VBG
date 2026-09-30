package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.domain.Concessionitipi;
import it.gruppoinit.pal.gp.core.service.ConcessionitipiService;

@Controller
public class ConcessionitipiController extends BaseController<Concessionitipi> {

    @Autowired
    private ConcessionitipiService concessionitipiService;

    @RequestMapping
    public void ajaxIsConcessioneStagionale(@RequestParam(value = "tipoconcessione") String tipoconcessione, HttpServletRequest request,
	    HttpServletResponse response) throws IOException {

	Concessionitipi entity = concessionitipiService.findById(tipoconcessione);
	boolean isFlagStagionale = false;
	if (entity.getFlagStagionale() != null) {
	    isFlagStagionale = entity.getFlagStagionale().booleanValue();
	}
	response.setContentType("text/plain");
	response.getWriter().write(String.valueOf(isFlagStagionale));
    }

    @Override
    protected void fixMergeEntityProperty(Concessionitipi entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Concessionitipi entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
