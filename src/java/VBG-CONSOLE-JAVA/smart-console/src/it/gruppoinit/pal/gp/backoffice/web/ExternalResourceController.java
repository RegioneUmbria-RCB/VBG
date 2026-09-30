package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExternalResourceController extends BaseController<Object> {

    private static final Logger log = LoggerFactory.getLogger(ExternalResourceController.class);

    @RequestMapping
    public String goTo(@RequestParam("url") String url, HttpServletRequest request, HttpServletResponse response) {

	String _url = elaboraUrl(url, request);
	boolean isPost = checkIsPost(request);
	if (log.isDebugEnabled()) {
	    log.debug("goTo: set iframe url to {}", _url);
	}
	request.setAttribute("external_url", _url);
	request.setAttribute("isPost", Boolean.valueOf(isPost));
	return "externalresource/goto";
    }

    @RequestMapping
    public String goToPopup(@RequestParam("url") String url, HttpServletRequest request, HttpServletResponse response) {

	String _url = elaboraUrl(url, request);
	boolean isPost = checkIsPost(request);
	if (isPost) {
	    if (log.isDebugEnabled()) {
		log.debug("goToPopup: set iframe url to chiamato in post");
	    }
	    request.setAttribute("external_url", _url);
	    return "externalresource/gotoPost";
	} else {
	    try {
		if (log.isDebugEnabled()) {
		    log.debug("goToPopup: response.sendRedirect to {}", _url);
		}
		response.sendRedirect(_url);
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    return null;
	}
    }

    /**
     * @param url
     * @param request
     * @return
     */
    private String elaboraUrl(String url, HttpServletRequest request) {

	String _url = "";
	try {
	    _url = URLDecoder.decode(url, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	String token = (String) request.getSession().getAttribute(WebConstants.TOKEN);
	if (_url.indexOf("?") > 0) {
	    _url += "&";
	} else {
	    _url += "?";
	}
	_url += WebConstants.TOKEN + "=" + token;
	return _url;
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

	// TODO Auto-generated method stub
    }

    @Override
    protected void fixRenderEntityProperty(Object entity) {

	// TODO Auto-generated method stub
    }

    @Override
    protected void setPageAttributes(Model model) {

	// TODO Auto-generated method stub
    }
}
