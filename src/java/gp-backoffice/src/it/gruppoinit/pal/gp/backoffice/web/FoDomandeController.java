package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.FoDomande;
import it.gruppoinit.pal.gp.core.service.FoDomandeService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FoDomandeController extends BaseController<FoDomande> {

    @Autowired
    private FoDomandeService foDomandeService;

    @RequestMapping
    public ModelMap list(HttpServletRequest request, HttpServletResponse response) {

	List<FoDomande> fodomandes = foDomandeService.findDomandeInSospeso(null, null);
	ModelMap model = new ModelMap(fodomandes);
	boolean export = createJMesaExport(request, response, fodomandes);
	if (export)
	    return null;
	model.addAttribute("fodomandes", fodomandes);
	return model;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(FoDomande entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(FoDomande entity) {

	// TODO Auto-generated method stub
    }
}
