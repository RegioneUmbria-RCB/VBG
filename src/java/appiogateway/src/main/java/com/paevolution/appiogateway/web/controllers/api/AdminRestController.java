package com.paevolution.appiogateway.web.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paevolution.appiogateway.connector.ud.IReloadableProperties;
import com.paevolution.appiogateway.core.dto.MessaggiDTO;
import com.paevolution.appiogateway.core.dto.ServiziDTO;
import com.paevolution.appiogateway.core.service.MessaggiService;
import com.paevolution.appiogateway.core.service.ServiziService;

@RestController
@RequestMapping(path = "/admin", produces = "application/json")
@CrossOrigin(origins = "*")
public class AdminRestController {

    private ServiziService serviziService;
    private MessaggiService messaggiService;
    @Autowired
    private @Qualifier("udClientRegistrationRepository") IReloadableProperties udClientRegistrationRepository;

    @Autowired
    public void setServiziService(ServiziService serviziService) {

	this.serviziService = serviziService;
    }

    @Autowired
    public void setMessaggiService(MessaggiService messaggiService) {

	this.messaggiService = messaggiService;
    }

    @GetMapping("servizi/findAll")
    public List<ServiziDTO> findAllServizi() throws Exception {

	return this.serviziService.findAllServizi();
    }

    @GetMapping("messaggi/findAll")
    public List<MessaggiDTO> findAllMessaggi() throws Exception {

	return this.messaggiService.findAllMessaggi();
    }

    @GetMapping("reload")
    public void reload() throws Exception {

	udClientRegistrationRepository.clearCachedProperties();
    }
}
