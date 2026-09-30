package it.gruppoinit.pal.gp.areariservata.web.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

public class RestHandlerMapping extends DefaultAnnotationHandlerMapping {

    private String[] requestNames;

    @Override
    protected Object getHandlerInternal(HttpServletRequest req) throws Exception {

	String uri = req.getRequestURI();
	String ctxPath = req.getContextPath();
	for (String requestName : requestNames) {
	    if (uri.startsWith(ctxPath + requestName)) {
		return new HandlerExecutionChain(this.getHandlerMap().get(requestName));
	    }
	}
	return null;
    }

    @Required
    public void setRequestNames(String[] requestNames) {

	this.requestNames = requestNames;
    }
}
