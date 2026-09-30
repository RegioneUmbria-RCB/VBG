package it.gruppoinit.federaag.servlet;

import it.gruppoinit.auth.service.impl.TokenLoginService;
import it.gruppoinit.auth.util.AuthCostants;
import it.gruppoinit.sigeprosecurity.schema.LogoutRequest;
import it.gruppoinit.sigeprosecurity.schema.LogoutResponse;
import it.gruppoinit.sigeprosecurity.ws.SigeproSecurity;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet implementation class InvalidateToken
 */
public class InvalidateToken extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(InvalidateToken.class);

    public InvalidateToken() {

	super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	try {
	    TokenLoginService tokenLoginService = new TokenLoginService();
	    SigeproSecurity sigeproSecurity = tokenLoginService.getPort();
	    String token = request.getParameter(AuthCostants.TOKEN);
	    LogoutRequest logoutRequest = new LogoutRequest();
	    logoutRequest.setToken(token);
	    if (log.isDebugEnabled()) {
		log.debug("doPost: chiamata a security.logout con token=" + token + " in corso...");
	    }
	    LogoutResponse logoutResponse = sigeproSecurity.logout(logoutRequest);
	    if (log.isInfoEnabled()) {
		log.info("invalidate token=" + token + ", " + logoutResponse.isSuccess());
	    }
	} catch (ServiceException e) {
	    log.error("errore durante la logout");
	    e.printStackTrace();
	}
	response.sendRedirect("images/logout_box.gif");
    }
}
