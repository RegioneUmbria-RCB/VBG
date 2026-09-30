package it.gruppoinit.sigeprorenderer;

import it.gruppoinit.visualizer.Main;

import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GraphServlet extends HttpServlet {

    private static final long serialVersionUID = -69392883149356194L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	try {
	    Main m = new Main();
	    byte[] b = m.generaGrafico(req.getParameter("idcomunealias"), req.getParameter("codiceprocedura"), req.getParameter("color"));
	    resp.setContentType("image/jpeg");
	    ServletOutputStream output = resp.getOutputStream();
	    BufferedOutputStream buffOut = new BufferedOutputStream(output);
	    buffOut.write(b);
	    buffOut.flush();
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }
}
