package it.alveo.segnalazioniproxy.controllers;

import it.alveo.segnalazioniproxy.clients.StcClient;
import it.alveo.segnalazioniproxy.servicies.StcService;
import it.alveo.stc.InserimentoPraticaResponse;
import it.alveo.stc.LoginResponse;
import it.alveo.stc.RichiestaPraticaResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StcController {
    private static final Logger log = LoggerFactory.getLogger(StcController.class);

    StcClient stcClient;
    StcService stcService;

    @Autowired
    public StcController(StcClient stcClient, StcService stcService) {
        this.stcClient = stcClient;
        this.stcService = stcService;
    }

    @PostMapping("/inserimentopratica/{alias}/{software}/{uuid}")
    public ResponseEntity<?> inserimentoPraticaTest(
            @PathVariable String alias,
            @PathVariable String software,
            @PathVariable String uuid) {
        LoginResponse loginResponse = stcClient.login(alias, software);
        try {
            InserimentoPraticaResponse resp = stcClient.inserimentoPratica(loginResponse.getToken(), alias, software, uuid);
            stcService.savePraticaFromSTC(resp, uuid);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("PRATICA INSERITA -> BACK OFFICE");
        } catch (Exception e) {
            log.error("inserimentoPraticaTest - Errore: ", e);
            return ResponseEntity.status(500)
                    .body("Errore durante l'invio della pratica verso STC: " + e.getMessage());

        }
    }

    @PostMapping("/richiestapratica/{alias}/{software}/{uuid}")
    public ResponseEntity<?> richiestaPraticaTest(
            @PathVariable String alias,
            @PathVariable String software,
            @PathVariable String uuid) {
        LoginResponse loginResponse = stcClient.login(alias, software);
        try {
            RichiestaPraticaResponse resp = stcClient.richiestaPratica(loginResponse.getToken(), alias, software, uuid);
            stcService.updatePraticaFromSTC(resp, uuid);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("PRATICA RECUPERATA DAL BACK OFFICE -> DATI A DB AGGIORNATI");
        } catch (Exception e) {
            log.error("richiestaPraticaTest - Errore: ", e);
            return ResponseEntity.status(500)
                    .body("Errore durante la richiesta della pratica da STC: " + e.getMessage());

        }
    }
}
