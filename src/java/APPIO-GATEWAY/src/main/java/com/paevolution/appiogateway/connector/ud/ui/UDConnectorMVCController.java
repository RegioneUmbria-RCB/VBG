package com.paevolution.appiogateway.connector.ud.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paevolution.appiogateway.core.dto.MessaggiIODTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/udhome")
public class UDConnectorMVCController {

    @GetMapping
    public String umbriaAPIHome(Model model) {

	MessaggiIODTO messaggio = new MessaggiIODTO();
	model.addAttribute("messaggio", messaggio);
	return "/udhome";
    }

    @PostMapping
    public String postMessaggioToUmbriaAPI(MessaggiIODTO messaggio) {

	log.info("New Message: {}", messaggio.getSubject());
	return "redirect:/";
    }

    @PostMapping(path = "/post_message")
    public String showForm(Model model) {

	return "udform";
    }
}