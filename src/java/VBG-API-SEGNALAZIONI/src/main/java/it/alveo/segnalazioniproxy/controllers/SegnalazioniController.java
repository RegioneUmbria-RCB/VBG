package it.alveo.segnalazioniproxy.controllers;

import it.alveo.segnalazioniproxy.clients.StcClient;
import it.alveo.segnalazioniproxy.dto.SegnalazioneRequest;
import it.alveo.segnalazioniproxy.entities.SgPratiche;
import it.alveo.segnalazioniproxy.enums.Stato;
import it.alveo.segnalazioniproxy.exceptions.ErrorResponse;
import it.alveo.segnalazioniproxy.servicies.*;
import it.alveo.segnalazioniproxy.utils.Utils;
import it.alveo.stc.LoginResponse;
import it.alveo.stc.RichiestaPraticaResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.alveo.segnalazioniproxy.utils.Utils.*;

/*
Premessa:
Laddove nell’API si fa riferimento a parametri di path {alias} e {software} questi saranno dei parametri impostati in setup e sempre uguali per installazione.
Ad esempio, per l’applicazione che serve i comuni dell’orvietano saranno come esempio:
    alias: G148SE
    software: XZ
 */

@RestController
@RequestMapping("/api/{alias}/{software}/segnalazioni")
public class SegnalazioniController {
    private static final Logger log = LoggerFactory.getLogger(SegnalazioniController.class);

    private final SegnalazioneService segnalazioneService;
    private final ConfigurazioneService configurazioneService;
    private final AllegatiService allegatiService;
    private final StcClient stcClient;
    private final StcService stcService;
    private final SigeproSecurityService sigeproSecurityService;

    @Autowired
    public SegnalazioniController(SegnalazioneService segnalazioneService, ConfigurazioneService configurazioneService, AllegatiService allegatiService, StcClient stcClient, StcService stcService, SigeproSecurityService sigeproSecurityService) {
        this.segnalazioneService = segnalazioneService;
        this.configurazioneService = configurazioneService;
        this.allegatiService = allegatiService;
        this.stcClient = stcClient;
        this.stcService = stcService;
        this.sigeproSecurityService = sigeproSecurityService;
    }


    // ---------- ↓ CONFIGURAZIONE ↓ ----------

    /**
     * GET /api/{alias}/{software}/segnalazioni/configurazione
     * <p>
     * Il metodo restituisce le configurazioni (enti, Categorie)
     * L’elemento categorie è ricorsivo per formare una struttura ad albero ed è possibile selezionare per l’invio solamente le voci con valore "attivabile": true
     *
     * @param alias    the alias, nel path della url
     * @param software the software, nel path della url
     * @param clientId the client id, dall'header
     * @return la configurazione in formato JSON
     * @throws JSONException the json exception
     */
    @GetMapping("/configurazione")
    public ResponseEntity<?> getConfigurazione(
            @PathVariable String alias,
            @PathVariable String software,
            @RequestHeader("X-client-id") String clientId) throws JSONException {
        /*
        Il metodo torna le configurazioni (enti, Categorie)
        L’elemento categorie è ricorsivo per formare una struttura ad albero ed è possibile selezionare per l’invio solamente le voci con valore "attivabile": true

         */

        // ERRORE - KO
        ResponseEntity<?> errorResponse = checkBaseParams(alias, software, clientId);
        if (errorResponse != null)
            return errorResponse;


        int configurationId;
        try {
            configurationId = configurazioneService.getConfigurationId(alias, software);
        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);
        }

        // OK
        String configurazione;
        try {
            configurazione = configurazioneService.getConfigurazione(alias, software, configurationId, clientId);
            // DEBUG
            log.info("configurazione da controller: ");
            log.info(configurazione);

        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(configurazione);
    }

    // ---------- ↑ CONFIGURAZIONE ↑ ----------


    // ---------- ↓ SEGNALAZIONI ↓ ----------

    /**
     * GET /api/{alias}/{software}/segnalazioni
     *
     * @param alias     the alias, nel path della url
     * @param software  the software, nel path della url
     * @param clientId  the client id, dall'header
     * @param utente    the utente, query parameter
     * @param comune    the comune, query parameter
     * @param categoria the categoria, query parameter
     * @param dallaData the dalla data, query parameter
     * @param allaData  the alla data, query parameter
     * @param limit     the limit, query parameter
     * @param offset    the offset, query parameter
     * @return Una <code>ResponseEntity</code> contenente il JSON con la lista delle segnalazioni
     * @throws JSONException the json exception
     */
    @GetMapping
    public ResponseEntity<?> listSegnalazioni(
            @PathVariable String alias,
            @PathVariable String software,
            @RequestHeader("X-client-id") String clientId,
            @RequestParam(required = false) String utente,
            @RequestParam(required = false) List<String> comune,
            @RequestParam(required = false) List<Integer> categoria,
            @RequestParam(required = false, name = "dalla_data") LocalDateTime dallaData,
            @RequestParam(required = false, name = "alla_data") LocalDateTime allaData,
            @RequestParam(required = false, name = "stato_avanzamento") String statoAvanzamento,
            @RequestParam(required = false, name = "stato_pratica") String statoPratica,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset
    ) throws JSONException {
        // Logica per elencare segnalazioni

        ResponseEntity<?> errorResponse = checkBaseParams(alias, software, clientId);
        if (errorResponse != null)
            return errorResponse;

        int configurationId;
        try {
            configurationId = configurazioneService.getConfigurationId(alias, software);
        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);
        }

        String jsonSegnalazioni;
        try {
            jsonSegnalazioni = segnalazioneService.ottieniSegnalazioni(alias, software, configurationId, clientId, utente, comune, categoria, dallaData, allaData, statoAvanzamento, statoPratica, limit, offset);
            log.info("json risultato estrazione: ");
            log.info(jsonSegnalazioni);

        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonSegnalazioni);
    }

    /**
     * POST /api/{alias}/{software}/segnalazioni
     *
     * @param alias               the alias, nel path della url
     * @param software            the software, nel path della url
     * @param clientId            the client id, dall'header
     * @param segnalazioneRequest the segnalazione request
     * @return Una <code>ResponseEntity</code> contenente i'ID della segnalazione creata
     * @throws JSONException the json exception
     */
    @PostMapping
    public ResponseEntity<?> createSegnalazione(
            @PathVariable String alias,
            @PathVariable String software,
            @RequestHeader("X-client-id") String clientId,
            @RequestBody SegnalazioneRequest segnalazioneRequest) throws JSONException {
        // Logica per creare una segnalazione

        ResponseEntity<?> errorResponse = checkBaseParams(alias, software, clientId);
        if (errorResponse != null)
            return errorResponse;

        // DEBUG
        log.info("Elementi ottenuti: " +
                "\n " + segnalazioneRequest);

        SgPratiche savedPratica;
        try {
            savedPratica = segnalazioneService.creaSegnalazione(alias, software, clientId,
                    segnalazioneRequest.getUtente(), segnalazioneRequest.getComune().getCodice(), segnalazioneRequest.getCategoria().getId(),
                    segnalazioneRequest.getStato(), segnalazioneRequest.getOggetto(), segnalazioneRequest.getTesto());
            log.info("savedPratica: ");
            log.info(String.valueOf(savedPratica));

        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);
        }

        // Crea la risposta
        JSONObject esito = new JSONObject();
        esito.put("codice", "OK");
        esito.put("descrizione", "Pratica creata con successo");
        JSONObject result = new JSONObject();
        result.put("identificativo", savedPratica.getUuid());
        result.put("esito", esito);

        log.info("result: ");
        log.info(String.valueOf(result));

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    /**
     * GET /api/{alias}/{software}/segnalazioni/{id_segnalazione}
     *
     * @param alias           the alias, nel path della url
     * @param software        the software, nel path della url
     * @param clientId        the client id, dall'header
     * @param id_segnalazione the id segnalazione, nel path della url
     * @return Una <code>ResponseEntity</code> contenente il JSON con i dettagli della segnalazione
     */
    @GetMapping("/{id_segnalazione}")
    public ResponseEntity<?> getSegnalazione(
            @PathVariable String alias,
            @PathVariable String software,
            @RequestHeader("X-client-id") String clientId,
            @PathVariable String id_segnalazione) {
        // Logica per recuperare una segnalazione specifica

        ResponseEntity<?> errorResponse = checkBaseParams(alias, software, clientId);
        if (errorResponse != null)
            return errorResponse;

        int configurationId;
        try {
            configurationId = configurazioneService.getConfigurationId(alias, software);
            segnalazioneService.checkConfigurazioneSegnalazione(configurationId, id_segnalazione);
        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);
        }

        // DEBUG
        log.info("ID richiesto: " + id_segnalazione);

        Map<String, Object> jsonSegnalazione;
        LoginResponse loginResponse = stcClient.login(alias, software);
        try {
            RichiestaPraticaResponse resp = null;
            // Recupera i dati da STC solo se è già stata inviata ad STC
            if (segnalazioneService.getSegnalazione(id_segnalazione).getStato() == Stato.RICEVUTA) {
                resp = stcClient.richiestaPratica(loginResponse.getToken(), alias, software, id_segnalazione);
                stcService.updatePraticaFromSTC(resp, id_segnalazione);
            }

            jsonSegnalazione = segnalazioneService.getSegnalazioneJson(alias, software, configurationId, clientId, id_segnalazione, resp);
            log.info("json risultato estrazione: ");
            log.info(String.valueOf(jsonSegnalazione));

        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);
        } catch (Exception e) {
            log.error("getSegnalazione - Errore: ", e);
            throw new RuntimeException(e);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonSegnalazione);
    }

    /**
     * PUT /api/{alias}/{software}/segnalazioni/{id_segnalazione}
     *
     * @param alias               the alias, nel path della url
     * @param software            the software, nel path della url
     * @param clientId            the client id, dall'header
     * @param id_segnalazione     the id segnalazione, nel path della url
     * @param segnalazioneRequest the segnalazione request
     * @return Una <code>ResponseEntity</code> contenente i'ID della segnalazione aggiornata
     * @throws JSONException the json exception
     */
    @PutMapping("/{id_segnalazione}")
    public ResponseEntity<?> updateSegnalazione(
            @PathVariable String alias,
            @PathVariable String software,
            @RequestHeader("X-client-id") String clientId,
            @PathVariable String id_segnalazione,
            @RequestBody SegnalazioneRequest segnalazioneRequest) throws JSONException {
        // Logica per aggiornare una segnalazione

        ResponseEntity<?> errorResponse = checkBaseParams(alias, software, clientId);
        if (errorResponse != null)
            return errorResponse;

        int configurationId;
        try {
            configurationId = configurazioneService.getConfigurationId(alias, software);
            segnalazioneService.checkConfigurazioneSegnalazione(configurationId, id_segnalazione);
        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);
        }

        // DEBUG
        log.info("Elementi ottenuti: " +
                "\n " + segnalazioneRequest);

        SgPratiche updatedPratica;
        try {
            updatedPratica = segnalazioneService.updateSegnalazione(alias, software, configurationId, clientId, id_segnalazione,
                    segnalazioneRequest.getUtente(), segnalazioneRequest.getComune().getCodice(), segnalazioneRequest.getCategoria().getId(),
                    segnalazioneRequest.getStato(), segnalazioneRequest.getOggetto(), segnalazioneRequest.getTesto());
            log.info("updatedPratica: ");
            log.info(String.valueOf(updatedPratica));

        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);
        }

        // Crea la risposta
        JSONObject esito = new JSONObject();
        esito.put("codice", "OK");
        esito.put("descrizione", "Pratica aggiornata con successo");
        JSONObject result = new JSONObject();
        result.put("identificativo", updatedPratica.getUuid());
        result.put("esito", esito);

        log.info("result: ");
        log.info(String.valueOf(result));

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    /**
     * DELETE /api/{alias}/{software}/segnalazioni/{id_segnalazione}
     *
     * @param alias           the alias, nel path della url
     * @param software        the software, nel path della url
     * @param clientId        the client id, dall'header
     * @param id_segnalazione the id segnalazione, nel path della url
     * @return Una <code>ResponseEntity</code> contenente i'ID della segnalazione eliminata
     */
    @DeleteMapping("/{id_segnalazione}")
    public ResponseEntity<?> deleteSegnalazione(
            @PathVariable String alias,
            @PathVariable String software,
            @RequestHeader("X-client-id") String clientId,
            @PathVariable String id_segnalazione) throws JSONException {
        // Logica per eliminare una segnalazione

        ResponseEntity<?> errorResponse = checkBaseParams(alias, software, clientId);
        if (errorResponse != null)
            return errorResponse;

        int configurationId;
        try {
            configurationId = configurazioneService.getConfigurationId(alias, software);
            segnalazioneService.checkConfigurazioneSegnalazione(configurationId, id_segnalazione);
        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);
        }


        // DEBUG
        log.info("ID da rimuovere: " + id_segnalazione);

        SgPratiche deletedPratica;
        try {
            deletedPratica = segnalazioneService.deleteSegnalazione(alias, software, configurationId, clientId, id_segnalazione);
            log.info("deletedPratica: ");
            log.info(String.valueOf(deletedPratica));

        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);
        }

        // Crea la risposta
        JSONObject esito = new JSONObject();
        esito.put("codice", "OK");
        esito.put("descrizione", "Pratica eliminata con successo");
        JSONObject result = new JSONObject();
        result.put("identificativo", deletedPratica.getUuid());
        result.put("esito", esito);

        log.info("result: ");
        log.info(String.valueOf(result));

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toString());
    }

    // ---------- ↑ SEGNALAZIONI ↑ ----------


    // ---------- ↓ ALLEGATI ↓ ----------

    /**
     * POST /api/{alias}/{software}/segnalazioni/{id_segnalazione}/allegati
     *
     * @param alias           the alias, nel path della url
     * @param software        the software, nel path della url
     * @param clientId        the client id, dall'header
     * @param id_segnalazione the id segnalazione, nel path della url
     * @param nome            the nome
     * @param content         the content
     * @return Una <code>ResponseEntity</code> contenente i'ID dell'allegato inserito
     */
    @PostMapping("/{id_segnalazione}/allegati")
    public ResponseEntity<?> addAllegato(
            @PathVariable String alias,
            @PathVariable String software,
            @RequestHeader("X-client-id") String clientId,
            @PathVariable String id_segnalazione,
            @RequestPart("filename") String nome,
            @RequestPart("file") MultipartFile content) {

        ResponseEntity<?> errorResponse = checkBaseParams(alias, software, clientId);
        if (errorResponse != null)
            return errorResponse;

        if (Utils.isNullOrEmpty(nome) || content.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(
                            String.valueOf(HttpStatus.BAD_REQUEST),
                            STANDARD_ERROR_TXT,
                            "Il nome del file o il file stesso sono assenti"
                    )
            );
        }

        try {
            String idAllegato = allegatiService.saveAllegato(alias, software, nome, content, id_segnalazione);

            Map<String, String> response = new HashMap<>();
            response.put("id_allegato", idAllegato);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);

        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);

        } catch (Exception e) {
            log.error("addAllegato - Errore: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il caricamento dell'allegato: " + e.getMessage());
        }
    }

    /**
     * GET /api/{alias}/{software}/segnalazioni/{id_segnalazione}/allegati/{id_allegato}
     *
     * @param alias           the alias, nel path della url
     * @param software        the software, nel path della url
     * @param clientId        the client id, dall'header
     * @param id_segnalazione the id segnalazione, nel path della url
     * @param id_allegato     the id allegato, nel path della url
     * @return Una <code>ResponseEntity</code> contenente il JSON con i dettagli dell'allegato
     */
    @GetMapping("/{id_segnalazione}/allegati/{id_allegato}")
    public ResponseEntity<?> getAllegato(
            @PathVariable String alias,
            @PathVariable String software,
            @RequestHeader("X-client-id") String clientId,
            @PathVariable String id_segnalazione,
            @PathVariable String id_allegato) {

        ResponseEntity<?> errorResponse = checkBaseParams(alias, software, clientId);
        if (errorResponse != null)
            return errorResponse;

        if (Utils.isNullOrEmpty(id_segnalazione) || Utils.isNullOrEmpty(id_allegato)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(
                            String.valueOf(HttpStatus.BAD_REQUEST),
                            STANDARD_ERROR_TXT,
                            "L'id della segnalazione o l'allegato sono assenti"
                    )
            );
        }

        try {
            String response = allegatiService.getAllegato(alias, software, id_segnalazione, id_allegato);
            String filename = String.valueOf(allegatiService.findByUuid(id_allegato));

            return ResponseEntity.status(HttpStatus.OK)
                    .header("X-nome-file", filename)
                    .header("X-id-allegato", id_allegato)
                    .body(response);

        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);

        } catch (Exception e) {
            log.error("getAllegato - Errore: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante il recupero dell'allegato: " + e.getMessage());
        }

    }

    /**
     * DELETE /api/{alias}/{software}/segnalazioni/{id_segnalazione}/allegati/{id_allegato}
     *
     * @param alias           the alias, nel path della url
     * @param software        the software, nel path della url
     * @param clientId        the client id, dall'header
     * @param id_segnalazione the id segnalazione, nel path della url
     * @param id_allegato     the id allegato, nel path della url
     * @return Una <code>ResponseEntity</code> con codice <code>HttpStatus.OK</code>
     * @throws JSONException the json exception
     */
    @DeleteMapping("/{id_segnalazione}/allegati/{id_allegato}")
    public ResponseEntity<?> deleteAllegato(
            @PathVariable String alias,
            @PathVariable String software,
            @RequestHeader("X-client-id") String clientId,
            @PathVariable String id_segnalazione,
            @PathVariable String id_allegato) throws JSONException {

        ResponseEntity<?> errorResponse = checkBaseParams(alias, software, clientId);
        if (errorResponse != null)
            return errorResponse;

        if (Utils.isNullOrEmpty(id_segnalazione) || Utils.isNullOrEmpty(id_allegato)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(
                            String.valueOf(HttpStatus.BAD_REQUEST),
                            STANDARD_ERROR_TXT,
                            "L'id della segnalazione o l'allegato sono assenti"
                    )
            );
        }

        try {
            SgPratiche pratica = segnalazioneService.findByUuidAndStato(id_segnalazione, Stato.IN_COMPILAZIONE);
            if (pratica != null) {
                allegatiService.deleteAllegato(alias, software, id_segnalazione, id_allegato);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body("");
            }

        } catch (IllegalArgumentException e) {
            return getEsitoKO(e);

        } catch (Exception e) {
            log.error("deleteAllegato - Errore: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante la cancellazione dell'allegato: " + e.getMessage());
        }
        return null;
    }

    // ---------- ↑ ALLEGATI ↑ ----------


}
