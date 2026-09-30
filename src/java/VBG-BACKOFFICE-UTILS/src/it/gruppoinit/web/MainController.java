package it.gruppoinit.web;

import it.gruppoinit.service.BackendService;
import it.gruppoinit.service.helper.StatusHelper;
import it.gruppoinit.sigeprosecurity.schema.LoginResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurityWSClient;
import it.gruppoinit.utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author riccardob
 *
 */
@Controller
public class MainController {

    @Autowired
    private SigeproSecurityWSClient sigeproSecurityWSClient;

    @RequestMapping(value = "/main.htm", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request) throws SQLException {

	request.setAttribute("security_url", sigeproSecurityWSClient.getSigeproSecurityUrl());
	return new ModelAndView("main");
    }

    @RequestMapping(value = "/sincronizza.htm", method = RequestMethod.POST)
    public ModelAndView sincronizza(@RequestParam("idOperazione") String idOperazione, @RequestParam("aliasOrigine") String aliasOrigine,
	    @RequestParam("softwareOrigine") String softwareOrigine, @RequestParam("aliasDestinazione") String aliasDestinazione,
	    @RequestParam("softwareDestinazione") String softwareDestinazione, @RequestParam("username") String username,
	    @RequestParam("password") String password, @RequestParam("escludiDisabilitati") Boolean escludiDisabilitati, HttpServletRequest request)
	    throws SQLException {

	password = Utils.getHashText(password, "MD5", false);
	LoginResponse token = sigeproSecurityWSClient.login(aliasDestinazione, username, password);
	if (StringUtils.isBlank(token.getToken())) {
	    throw new SecurityException();
	}
	BackendService bs = new BackendService(aliasOrigine, aliasDestinazione, softwareOrigine, softwareDestinazione, sigeproSecurityWSClient,
		idOperazione);
	bs.doWork(escludiDisabilitati);
	return new ModelAndView("main");
    }

    @RequestMapping(value = "/status.htm", method = RequestMethod.GET)
    public void status(@RequestParam("idOperazione") String idOperazione, HttpServletRequest request, HttpServletResponse response)
	    throws IOException {

	response.getOutputStream().write(StatusHelper.attivita(idOperazione).getBytes());
    }
}
