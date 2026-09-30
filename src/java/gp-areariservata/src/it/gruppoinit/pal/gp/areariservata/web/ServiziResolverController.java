package it.gruppoinit.pal.gp.areariservata.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/servizi/")
@Controller
public class ServiziResolverController extends BaseResolverController {

    private final String uriMapping = "/servizi/";
    private final String uriRedirect = "../nuovaistanza/startDaServizio.htm";

    @RequestMapping
    public String resolveServizio(HttpServletRequest request) {

	return this.resolveServizio(request, uriMapping, uriRedirect);
    }
}
