package it.gruppoinit.pal.gp.backoffice.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.gruppoinit.pal.gp.core.domain.Sorteggidettagliomovimenti;

/**
 * 
 * @author
 */
@Controller
@SessionAttributes("sorteggidettagliomovimenti")
public class SorteggidettagliomovimentiController extends BaseController<Sorteggidettagliomovimenti> {

    @Override
    protected void fixMergeEntityProperty(Sorteggidettagliomovimenti entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Sorteggidettagliomovimenti entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
