package it.gruppoinit.pal.gp.areariservata.web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.JdkVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.FoArconfigurazione;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.areariservata.IVerticalizzazioneAreaRiservataService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.FoArconfigurazioneService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

@Controller
public class AppmobileController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AppmobileController.class);
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private ExternalDBResolver externalDBResolver;
    @Autowired
    private IVerticalizzazioneAreaRiservataService vertAreaRiservataService;
    @Autowired
    private FoArconfigurazioneService foArconfigurazioneService;
    @Autowired
    private SoftwareService softwareService;
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;

    @Autowired
    public void setComportamentiMercatiService(IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService) {

	this.comportamentiMercatiService = comportamentiMercatiService;
    }

    @RequestMapping
    public String appbootstrap(HttpServletRequest request, HttpServletResponse response) throws IOException {

	logger.debug("appbootstrap");
	String urlAppAmbulanteWeb = this.comportamentiMercatiService.urlAppAmbulanteWeb();
	if (StringUtils.isBlank(urlAppAmbulanteWeb)) {
	    throw new InvalidConfigurationException("Non sono state attivate le configurazioni per l'app AMBULANTE WEB (" +
		    IVerticalizzazioneComportamentiMercatiService.parUrlAppAmbulanteWeb +
		    ")");
	}
	logger.debug("appbootstrap: VERTICALIZZAZIONE_COMPORTAMENTI_MERCATI_URL_APP_AMBULANTE_WEB {}", urlAppAmbulanteWeb);
	Cookie cookie = new Cookie(WebConstants.COOKIEURL_SESSIONE_SCADUTA, urlAppAmbulanteWeb);
	if (JdkVersion.getMajorJavaVersion() == JdkVersion.JAVA_16) {
	    cookie.setPath(";Path=" + request.getContextPath() + ";HttpOnly;");
	} else {
	    cookie.setPath(request.getContextPath());
	}
	cookie.setMaxAge(-1);
	response.addCookie(cookie);
	String redirect = urlAppAmbulanteWeb + ORMHelper.getToken();
	logger.debug("appbootstrap: redirect {}", redirect);
	if (!externalDBResolver.checkTokenValidity(ORMHelper.getToken())) {
	    FoArconfigurazione conf = foArconfigurazioneService.findBySoftware(softwareService.findById(ORMHelper.getSoftware()));
	    SecurityParams tipoLogin = WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_FO_URL;
	    String param = conf == null ? null : conf.getNomeParametroLoginUrl();
	    if (StringUtils.isNotBlank(param) && param.equals(WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_URLCIE.name())) {
		tipoLogin = WebConstants.SecurityParams.AUTHENTICATION_GATEWAY_URLCIE;
	    }
	    String externalAuthUrl = WebConstants.getSecurityParamValue(tipoLogin);
	    String urlOverride = vertAreaRiservataService.getUrlAuthenticationOverride();
	    if (StringUtils.isNotBlank(urlOverride)) {
		externalAuthUrl = urlOverride;
	    }
	    logger.debug("appbootstrap: token valido");
	    String urlFirstRequest = URLEncoder.encode(request.getContextPath() +
		    "/appmobile/appbootstrap.htm?" +
		    WebConstants.IDCOMUNE_ALIAS +
		    "=" +
		    ORMHelper.getIdcomuneAlias() +
		    "&" +
		    WebConstants.SOFTWARE +
		    "=" +
		    ORMHelper.getSoftware(), "UTF-8");
	    externalAuthUrl += "?return_to=" +
		    urlFirstRequest +
		    "&" +
		    WebConstants.IDCOMUNE_ALIAS +
		    "=" +
		    ORMHelper.getIdcomuneAlias() +
		    "&contesto=UTE";
	    redirect = externalAuthUrl;
	    logger.debug("appbootstrap: redirect {}", redirect);
	    request.getSession().invalidate();
	    response.sendRedirect(redirect);
	    return null;
	}
	return "redirect:" + redirect;
    }
}
