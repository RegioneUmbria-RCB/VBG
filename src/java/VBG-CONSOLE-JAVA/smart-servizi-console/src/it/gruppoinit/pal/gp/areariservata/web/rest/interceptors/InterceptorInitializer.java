package it.gruppoinit.pal.gp.areariservata.web.rest.interceptors;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import it.gruppoinit.pal.gp.areariservata.web.AdminController;
import it.gruppoinit.pal.gp.areariservata.web.util.DeployProperties;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.utils.ExternalDBResolver;

public class InterceptorInitializer extends AbstractPhaseInterceptor<Message> {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private ExternalDBResolver externalDBResolver;
    @Autowired
    protected DeployProperties deployProperties;

    public InterceptorInitializer() {

	super(Phase.PRE_LOGICAL);
    }

    public InterceptorInitializer(String phase) {

	super(phase);
    }

    @Override
    public void handleFault(Message message) {

	super.handleFault(message);
	message.put(Message.RESPONSE_CODE, new Integer(401));
	HttpServletResponse response = (HttpServletResponse) message.get(AbstractHTTPDestination.HTTP_RESPONSE);
	try {
	    ORMHelper.destroyORMHelper();
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
	    response.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
	    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
	} catch (IOException e) {
	    log.error("InterceptorInHeaderAuth#handleFault: {}", e);
	}
    }

    @Override
    public void handleMessage(Message message) throws Fault {

	log.debug("InterceptorInitializer IN");
	HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
	if (request != null) {
	    if (!request.getMethod().equals("OPTIONS")) {
		try {
		    setORMHelper(request);
		    return;
		} catch (Exception e) {
		    log.error("InterceptorInHeaderAuth: {}", e);
		}
	    }
	}
	log.debug("InterceptorInitializer OUT");
    }

    protected void setORMHelper(HttpServletRequest request) {

	Properties connProps = externalDBResolver.getConnectionProperties(deployProperties.getIdcomuneAliasDefault());
	ORMHelper.setIdcomune(connProps.getProperty(WebConstants.IDCOMUNE));
	ORMHelper.setIdcomuneAlias(connProps.getProperty(WebConstants.IDCOMUNE_ALIAS));
	ORMHelper.setSoftware(WebConstants.SOFTWARE_TT);
	ORMHelper.setHibernateSFKey(ORMHelper.getHibernateKeyFromProps(connProps));
    }
}