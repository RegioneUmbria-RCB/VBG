package it.gruppoinit.pal.gp.backoffice.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.features.sistema.IVerticalizzazioneParametriSistemaService;
import it.gruppoinit.pal.gp.core.features.sistema.SecurityUtils;

@Controller
public class ExternalResourceController extends BaseController<Object> {

    private static final Logger log = LoggerFactory.getLogger(ExternalResourceController.class);
    @Autowired
    private IVerticalizzazioneParametriSistemaService verticalizzazioneParametriSistemaService;

    @RequestMapping
    public String goTo(@RequestParam("url") String url, HttpServletRequest request, HttpServletResponse response) {

	String lclurl = SecurityUtils.elaboraUrl(url, request, verticalizzazioneParametriSistemaService.attivaComportamentiSicurezza());
	boolean isPost = checkIsPost(request);
	if (log.isDebugEnabled()) {
	    log.debug("goTo: set iframe url to {}", lclurl);
	}
	request.setAttribute("external_url", lclurl);
	request.setAttribute("isPost", Boolean.valueOf(isPost));
	return "externalresource/goto";
    }

    @RequestMapping
    public String goToPopup(@RequestParam("url") String url, HttpServletRequest request, HttpServletResponse response) {

	String lclurl = SecurityUtils.elaboraUrl(url, request, verticalizzazioneParametriSistemaService.attivaComportamentiSicurezza());
	boolean isPost = checkIsPost(request);
	if (isPost) {
	    if (log.isDebugEnabled()) {
		log.debug("goToPopup: set iframe url to chiamato in post");
	    }
	    request.setAttribute("external_url", lclurl);
	    return "externalresource/gotoPost";
	} else {
	    try {
		if (log.isDebugEnabled()) {
		    log.debug("goToPopup: response.sendRedirect to {}", lclurl);
		}
		response.sendRedirect(lclurl);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    return null;
	}
    }

    /**
     * @param request
     * @return
     */
    private boolean checkIsPost(HttpServletRequest request) {

	String isPostAction = request.getParameter(WebConstants.IS_POST_ACTION);
	boolean isPost = false;
	if (StringUtils.defaultIfEmpty(isPostAction, "false").equalsIgnoreCase("true")) {
	    isPost = true;
	}
	return isPost;
    }

    @Override
    protected void fixMergeEntityProperty(Object entity) {

	//  Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

	//  Auto-generated method stub
    }

    @Override
    protected void setPageAttributes(Model model) {

	//  Auto-generated method stub
    }
}
