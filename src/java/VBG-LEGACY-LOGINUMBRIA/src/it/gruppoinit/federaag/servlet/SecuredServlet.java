package it.gruppoinit.federaag.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecuredServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public SecuredServlet() {

	super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	request.getRequestDispatcher("secured/secure.jsp").forward(request, response);
    }
}
