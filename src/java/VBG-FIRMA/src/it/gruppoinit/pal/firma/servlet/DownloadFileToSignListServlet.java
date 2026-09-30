package it.gruppoinit.pal.firma.servlet;

import it.gruppoinit.pal.firma.FileInfo;
import it.gruppoinit.pal.firma.FileManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadFileToSignListServlet
 */
public class DownloadFileToSignListServlet extends BaseServlet {

    private static final long serialVersionUID = 1L;
    private FileManager fileManager;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFileToSignListServlet() {

	super();
	fileManager = new FileManager();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	log.debug("doGet...");
	String sessionId = request.getParameter("sessionId");
	response.setContentType("text/plain");
	PrintWriter out = response.getWriter();
	List<FileInfo> fileCaricati = fileManager.list(sessionId);
	out.println(getJSON(fileCaricati));
	out.close();
	log.debug("doGet...end");
    }
}
