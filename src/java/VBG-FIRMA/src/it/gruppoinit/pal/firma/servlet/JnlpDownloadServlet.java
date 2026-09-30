package it.gruppoinit.pal.firma.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JnlpDownloadServlet extends BaseServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -1031162792293198039L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

	log.info("doGet");
	// String uri = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String uri = getBaseUrl(request);
	request.setAttribute("uri", uri);
	request.getRequestDispatcher("WEB-INF/jsp/jnlp.jsp").forward(request, resp);
    }
}
