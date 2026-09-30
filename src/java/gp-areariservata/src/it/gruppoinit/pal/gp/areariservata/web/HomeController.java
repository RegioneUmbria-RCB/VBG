package it.gruppoinit.pal.gp.areariservata.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gruppoinit.pal.gp.areariservata.web.util.DeployProperties;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazione;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.service.FoArconfigurazioneService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;

@Controller
public class HomeController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    private DeployProperties deployProperties;
    @Autowired
    private IVerticalizzazioneAreaRiservataService vertAreaRiservataService;
    @Autowired
    private FoArconfigurazioneService foArconfigurazioneService;
    @Autowired
    private SoftwareService softwareService;

    @Secured("ROLE_USER")
    @RequestMapping
    public String start(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("start");
	return "home/start";
    }

    @RequestMapping
    public String registrazione(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("registrazione");
	return "home/registrazione";
    }

    @RequestMapping
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("login");
	FoArconfigurazione conf = foArconfigurazioneService.findBySoftware(softwareService.findById(ORMHelper.getSoftware()));
	SecurityParams tipoLogin = WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_FO_URL;
	String param = conf == null ? null : conf.getNomeParametroLoginUrl();
	if (StringUtils.isNotBlank(param) && param.equals(WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_URLCIE.name())) {
	    tipoLogin = WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_URLCIE;
	}
	log.debug("login: AUTHENTICATION_GATEWAY_FO_URL tipologin: {}", tipoLogin);
	String authGateFoUrl = WebConstants.getSecurityParamValue(tipoLogin);
	log.debug("login: AUTHENTICATION_GATEWAY_FO_URL: {}", authGateFoUrl);
	String urlOverride = vertAreaRiservataService.getUrlAuthenticationOverride();
	if (StringUtils.isNotBlank(urlOverride)) {
	    authGateFoUrl = urlOverride;
	}
	log.debug("login: AUTHENTICATION_GATEWAY_FO_URL: {}", authGateFoUrl);
	if (StringUtils.isNotBlank(authGateFoUrl)) {
	    try {
		authGateFoUrl += "?" + WebConstants.IDCOMUNE_ALIAS + "=" + (String) request.getSession().getAttribute(WebConstants.IDCOMUNE_ALIAS);
		authGateFoUrl += "&contesto=UTE";
		authGateFoUrl += "&return_to=" + getUrlFirstRequest(request);
		log.info("login: redirect: {}", authGateFoUrl);
		response.sendRedirect(authGateFoUrl);
	    } catch (IOException e) {
		log.error("login: {}", e.getMessage());
		throw new InvalidConfigurationException("Invalid redirect to AuthenticationGateway: " + authGateFoUrl);
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
	String authGateFoUrl = WebConstants.getSecurityParamValue(WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_FO_URL);
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
		throw new InvalidConfigurationException("Invalid redirect to AuthenticationGateway: " + authGateFoUrl);
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
	try {
	    urlBack = URLDecoder.decode(urlBack, "UTF-8");
	    log.debug("getUrlFirstRequest: {}", urlBack);
	    //gestione https
	    boolean forceHttps = deployProperties.isForceHttps();
	    if (forceHttps) {
		if (urlBack.indexOf("https://") == -1) {
		    urlBack = urlBack.replaceFirst("http://", "https://");
		    if (urlBack.indexOf(":80/") != -1) {
			urlBack = urlBack.replaceFirst(":80/", "/");
		    }
		}
		log.debug("getUrlFirstRequest: (force https is true) {}", urlBack);
	    }
	    urlBack = removeProtocol(urlBack);
	    urlBack = URLEncoder.encode(urlBack, "UTF-8");
	} catch (Exception e) {
	    log.error("getUrlFirstRequest: {}", e.getMessage());
	}
	return urlBack;
    }

    private String removeProtocol(String urlBack) throws IOException {

	if (isRemoveProtocol()) {
	    log.debug("getUrlFirstRequest: remove protocol {}", urlBack);
	    String protocol = "http:";
	    if (urlBack.indexOf("https:") >= 0) {
		protocol = "https:";
	    }
	    urlBack = urlBack.replaceFirst(protocol, "");
	    log.debug("getUrlFirstRequest: remove protocol {},{}", protocol, urlBack);
	}
	return urlBack;
    }

    private boolean isRemoveProtocol() throws IOException {

	Properties p = getDeployProperties();
	String property = p.getProperty("removeFirstRequestProtocol");
	log.debug("getUrlFirstRequest:  is remove protocol {}", property);
	return StringUtils.defaultString(property, "false").equalsIgnoreCase("true");
    }

    private Properties getDeployProperties() throws IOException {

	Properties p = new Properties();
	InputStream is = getClass().getClassLoader().getResourceAsStream("deploy.properties");
	p.load(is);
	is.close();
	return p;
    }
}
