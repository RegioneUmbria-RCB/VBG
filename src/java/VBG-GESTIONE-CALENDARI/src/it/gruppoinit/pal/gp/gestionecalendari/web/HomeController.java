package it.gruppoinit.pal.gp.gestionecalendari.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping
    public String start(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("start");
	return "home/start";
    }

    @RequestMapping
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("login");
	String authGateFoUrl = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_URL);
	log.debug("login: AUTHENTICATION_GATEWAY_URL: {}", authGateFoUrl);
	if (StringUtils.isNotBlank(authGateFoUrl)) {
	    try {
		authGateFoUrl += "?" + WebConstants.IDCOMUNE_ALIAS + "=" + (String) request.getSession().getAttribute(WebConstants.IDCOMUNE_ALIAS);
		authGateFoUrl += "&contesto=OPE";
		authGateFoUrl += "&return_to=" + getUrlFirstRequest(request);
		log.info("login: redirect: {}", authGateFoUrl);
		response.sendRedirect(authGateFoUrl);
	    } catch (IOException e) {
		log.error("login", e);
		throw new RuntimeException("Invalid redirect to AuthenticationGateway: " + authGateFoUrl);
	    }
	    return null;
	}
	return "home/login";
    }

    @RequestMapping
    public String logoutBoxImage(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("logoutBoxImage");
	String url = "redirect:../j_spring_security_logout?logoutSuccessUrl=/images/logout_box.gif";
	log.info("logoutBoxImage: {}", url);
	return url;
    }

    @RequestMapping
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("logout");
	String authGateFoUrl = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_URL);
	if (StringUtils.isNotBlank(authGateFoUrl)) {
	    try {
		String returnTo = getUrlFirstRequest(request);
		authGateFoUrl = authGateFoUrl.substring(0, authGateFoUrl.lastIndexOf("/"));
		authGateFoUrl += "/logout?" + WebConstants.TOKEN + "=" + request.getSession().getAttribute(WebConstants.TOKEN) + "&return_to=" +
				 returnTo;
		log.info("logout: redirect to: {}", authGateFoUrl);
		request.getSession().invalidate();
		response.sendRedirect(authGateFoUrl);
	    } catch (IOException e) {
		log.error("logout: {}", e.getMessage());
		throw new RuntimeException("Invalid redirect to AuthenticationGateway: " + authGateFoUrl);
	    }
	    return null;
	}
	return "redirect:../j_spring_security_logout";
    }

    /**
     * il metodo recupera dalla sessione l'attributo "URL_FIRST_REQUEST" e lo restituisce come url
     * 
     * @param request
     * @return
     */
    private String getUrlFirstRequest(HttpServletRequest request) {

	String urlBack = (String) request.getSession().getAttribute(WebConstants.URL_FIRST_REQUEST);
	log.error("urlBack: {}", urlBack);
	urlBack = urlBack.replace("http", "https");
	log.error("urlBack: {}", urlBack);
	urlBack = urlBack.replace("http:", "https:");
	log.error("urlBack: {}", urlBack);
	try {
	    urlBack = URLDecoder.decode(urlBack, "UTF-8");
	    log.debug("getUrlFirstRequest: {}", urlBack);
	    urlBack = URLEncoder.encode(urlBack, "UTF-8");
	} catch (Exception e) {
	    log.error("getUrlFirstRequest", e);
	}
	return urlBack;
    }
}
