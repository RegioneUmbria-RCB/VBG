/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.service.RegistrazioniService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Classe per inserire le rate mancanti di una registrazione.
 * 
 * @author francescop
 * 
 */
@Controller
@SessionAttributes()
public class RegistrazioniUtil extends BaseController{

    @Autowired
    private RegistrazioniService registrazioniService;

    /**
     * Metodo che inserisce le rate mancati di tutte le registrazioni per un determinato anno. Metodo utilizzato per il
     * Comune di Bari. 12 rate che partono da Gennaio.
     * 
     * @author francescop
     * @param anno
     *            delle registrazioni
     * @param request
     * @param response
     * @return
     */
    @RequestMapping
    public String insertRate(@RequestParam("anno") short anno, HttpServletRequest request, HttpServletResponse response) {

	registrazioniService.insertRateMancanti(anno, 12);
	return "redirect:/registrazioni/list.htm";
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }
}
