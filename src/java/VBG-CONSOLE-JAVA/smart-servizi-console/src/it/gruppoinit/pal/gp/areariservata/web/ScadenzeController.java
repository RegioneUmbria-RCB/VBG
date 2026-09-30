package it.gruppoinit.pal.gp.areariservata.web;

import it.gruppoinit.pal.gp.areariservata.ws.client.ARJSTCWSClient;
import it.gruppoinit.pal.gp.core.service.FirmaDigitaleService;
import it.gruppoinit.pal.gp.core.service.MessagesService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

@Secured("ROLE_USER")
// @Controller
// @SessionAttributes("scadenzaHelper")
public class ScadenzeController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ScadenzeController.class);
    @Autowired
    private ARJSTCWSClient arjStcWsClient;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private FirmaDigitaleService firmaDigitaleService;
    @Autowired
    private MessagesService messagesService;
}
