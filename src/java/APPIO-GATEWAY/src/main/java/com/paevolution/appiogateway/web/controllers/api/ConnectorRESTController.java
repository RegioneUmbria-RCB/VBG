package com.paevolution.appiogateway.web.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.paevolution.appiogateway.core.service.MessaggiService;

@RestController("/connectorapi")
public class ConnectorRESTController {

    private MessaggiService messaggiService;

    @Autowired
    public void setMessaggiService(MessaggiService messaggiService) {

	this.messaggiService = messaggiService;
    }
}
