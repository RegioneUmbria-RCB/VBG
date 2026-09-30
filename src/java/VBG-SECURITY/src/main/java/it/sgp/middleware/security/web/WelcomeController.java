package it.sgp.middleware.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.sgp.middleware.security.service.impl.GestioneTokenConsumer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller()
@RequestMapping("/welcome")
public class WelcomeController {

    @Autowired
    private GestioneTokenConsumer gestioneTokenConsumer;

    @GetMapping("/start.htm")
    public String start(Model model, HttpServletRequest request, HttpServletResponse response) {

	return "welcome/start";
    }

    @GetMapping("/login.htm")
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {

	return "/login";
    }

    @GetMapping("/deleteToken.htm")
    public void deleteToken(Model model, HttpServletRequest request, HttpServletResponse response) {

	gestioneTokenConsumer.deleteToken(true, true);
    }
}
