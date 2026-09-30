package it.gruppoinit.pal.gp.backoffice.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AvviaController {

    @RequestMapping
    public String start(Model model, @RequestParam("idcomunealias") String idcomunealias, HttpServletRequest request, HttpServletResponse response) {

	model.addAttribute("idcomunealias", idcomunealias);
	return "avvia/start";
    }
}
