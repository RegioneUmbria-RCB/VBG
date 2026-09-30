package it.alveo.segnalazioniproxy.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    //DEBUG: scommentare per consultare Swagger
    @RequestMapping("/")
    public String index() {

        // DEBUG
        log.info("SWAGGER! --------");

        return "redirect:swagger-ui.html";
    }
}
