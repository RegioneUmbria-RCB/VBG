package it.gruppoinit.federaag.servlet;

import it.gruppoinit.auth.util.AuthCostants;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet implementation class LogoutServlet
 */
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(LogoutServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	this.doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	//	String appAsp = "";
	//	String appNet = "";
	//	String appJava = "";
	//	String appARJava = "";
	//	String baseUrl = "";
	//	String returnTo = request.getParameter(AuthCostants.RETURN_TO);
	//	String token = request.getParameter(AuthCostants.TOKEN);
	//	if (log.isInfoEnabled()) {
	//	    log.info("logout: token=" + token);
	//	    log.info("logout: return_to=" + returnTo);
	//	}
	//	try {
	//	    TokenLoginService tokenLoginService = new TokenLoginService();
	//	    SigeproSecurity sigeproSecurity = tokenLoginService.getPort();
	//	    GetApplicationInfoRequest applicationInfoRequest = new GetApplicationInfoRequest();
	//	    ApplicationInfoType[] applicationInfoTypes = sigeproSecurity.getApplicationInfo(applicationInfoRequest);
	//	    for (int i = 0; i < applicationInfoTypes.length; i++) {
	//		if (applicationInfoTypes[i].getParam().equals("APP_ASP")) {
	//		    appAsp = applicationInfoTypes[i].getValue();
	//		}
	//		if (applicationInfoTypes[i].getParam().equals("APP_ASPNET")) {
	//		    appNet = applicationInfoTypes[i].getValue();
	//		}
	//		if (applicationInfoTypes[i].getParam().equals("APP_JAVA")) {
	//		    appJava = applicationInfoTypes[i].getValue();
	//		}
	//		if (applicationInfoTypes[i].getParam().equals("APP_AR_JAVA")) {
	//		    appARJava = applicationInfoTypes[i].getValue();
	//		}
	//		//FIXME se è presente la baseurl la logout fallisce quando lavora in https
	//		if (applicationInfoTypes[i].getParam().equals("BASE_URL")) {
	//		    baseUrl = applicationInfoTypes[i].getValue();
	//		    if (StringUtils.isBlank(baseUrl)) {
	//			baseUrl = "";
	//		    }
	//		}
	//	    }
	//	    appAsp = baseUrl + "/" + appAsp;
	//	    appNet = baseUrl + "/" + appNet;
	//	    appJava = baseUrl + "/" + appJava;
	//	    appARJava = baseUrl + "/" + appARJava;
	//	    request.setAttribute("APP_ASP", appAsp);
	//	    request.setAttribute("APP_ASPNET", appNet);
	//	    request.setAttribute("APP_JAVA", appJava);
	//	    request.setAttribute("APP_AR_JAVA", appARJava);
	//	    // CheckTokenRequest checkTokenRequest = new CheckTokenRequest(token, true);
	//	    // CheckTokenResponse checkTokenResponse = sigeproSecurity.checkToken(checkTokenRequest);
	//	    // if (!checkTokenResponse.isValid()) {
	//	    //throw new RuntimeException("Token non valido");
	//	    //}
	//	    // TokenInfoType tokenInfoType = checkTokenResponse.getTokenInfo();
	//	    // String contesto = tokenInfoType.getContesto().getValue();
	//	    //	    request.setAttribute("doLogout", true);
	//	    //	    request.setAttribute(AuthCostants.CONTESTO, contesto);
	//	    if (StringUtils.isNotBlank(returnTo)) {
	//		request.setAttribute("LOGIN_URI", returnTo);
	//	    }
	//	    request.setAttribute("federaLogoutUrl", this.getInitParameter("federaLogoutUrl"));
	//	    request.setAttribute("spid", this.getInitParameter("spid"));
	//	} catch (ServiceException e) {
	//	    log.error(e);
	//	}
	request.getRequestDispatcher(AuthCostants.LOGOUT_PAGE).forward(request, response);
    }
}
