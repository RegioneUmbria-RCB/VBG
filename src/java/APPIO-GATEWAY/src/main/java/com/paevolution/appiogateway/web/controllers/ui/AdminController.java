package com.paevolution.appiogateway.web.controllers.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/risposte")
    public String risposte() {

	return "risposte";
    }

    @GetMapping("/home")
    public String home() {

	return "home";
    }

    @GetMapping("/servizi")
    public String servizi() {

	return "servizi";
    }

    @GetMapping("/messaggi")
    public String index() {

	return "messaggi";
    }
}