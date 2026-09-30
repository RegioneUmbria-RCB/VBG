/**
 * 
 */
package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.domain.Ricerche;
import it.gruppoinit.pal.gp.core.service.MailtipoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

/**
 * @author francescop
 * 
 */
@Controller
public class JSONMailController extends BaseController<Ricerche> {

    @Autowired
    private MailtipoService mailtipoService;

    @Override
    protected void fixMergeEntityProperty(Ricerche entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Ricerche entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
