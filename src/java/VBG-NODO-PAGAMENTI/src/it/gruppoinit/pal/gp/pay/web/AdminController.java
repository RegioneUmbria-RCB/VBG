package it.gruppoinit.pal.gp.pay.web;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gruppoinit.pal.gp.pay.service.PayConnectorService;

@Controller()
public class AdminController {

    @Autowired
    private PayConnectorService payConnectorService;

    @RequestMapping(path = "/admin/reload")
    public void handleRedirect(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

	Map<String, String> reloadConnectorParams = payConnectorService.reloadConnectorParams();
	StringBuilder sbf = new StringBuilder("<ul>");
	for (Entry<String, String> i : reloadConnectorParams.entrySet()) {
	    sbf.append("<li>").append(i.getKey()).append("==>").append(i.getValue()).append("</li>");
	}
	sbf.append("</ul>");
	response.setContentType("text/html");
	response.getOutputStream().write(sbf.toString().getBytes());
    }
}
