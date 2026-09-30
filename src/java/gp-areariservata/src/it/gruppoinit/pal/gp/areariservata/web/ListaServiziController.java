package it.gruppoinit.pal.gp.areariservata.web;

import it.gruppoinit.pal.gp.core.domain.FoArjServizi;
import it.gruppoinit.pal.gp.core.service.FoArjServiziService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ListaServiziController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ListaServiziController.class);
    @Autowired
    private FoArjServiziService foArjServiziService;

    @RequestMapping
    public String list(Model model) {

	log.debug("list");
	List<FoArjServizi> list = foArjServiziService.findBySoftware(null, null);
	model.addAttribute("servizi", list);
	return "servizi/list";
    }
}
